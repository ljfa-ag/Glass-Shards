package ljfa.glassshards.compat;

import static ljfa.glassshards.GlassShards.logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.versioning.ComparableVersion;
import ljfa.glassshards.Config;
import ljfa.glassshards.ModRecipes;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.ModGlassHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class EnderIOGlassHelper {
    //Metadata of EnderIO regular, enlightene and dark clear glass
    public static final int clear_meta = 1, enlightened_meta = 3, dark_meta = 5;
    
    public static void init() {
        GlassRegistry.addHandler("EnderIO:blockFusedQuartz", new ModGlassHandler() {
            @Override
            public void addShardsDrop(HarvestDropsEvent event) {
                super.addShardsDrop(event);
                if(event.blockMetadata == enlightened_meta) {
                    for(int i = 0; i < 4; i++) //Drop 4 distinct stacks
                        event.drops.add(new ItemStack(Items.glowstone_dust));
                }
                else if(event.blockMetadata == dark_meta) {
                    for(int i = 0; i < 4; i++)
                        event.drops.add(new ItemStack(Items.dye, 1, 0)); //Ink sac
                }
            }
            
            @Override
            public GlassType getType(Block block, int meta) {
                if(meta == clear_meta || meta == enlightened_meta || meta == dark_meta)
                    return new GlassType(GlassType.mult_block);
                else
                    return null;
            }
            
            @Override
            public boolean shouldRemoveDrop(Block block, int meta) {
                return meta == clear_meta || meta == enlightened_meta || meta == dark_meta;
            }
        });
        
        logger.info("Successfully loaded EnderIO glass compatibility.");
    }
    
    public static void addRecipes() {
        if(Config.eioSagMill) {
            //SAG mill: glass -> glass shards
            //Replace EnderIO's internal recipe
            FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill",
                "<recipeGroup name=\"EnderIO\">" +
                  "<recipe name=\"Glass\" energyCost=\"1000\">" +
                    "<input>" +
                      "<itemStack oreDictionary=\"glass\" />" +
                    "</input>" +
                    "<output>" +
                      "<itemStack modID=\"glass_shards\" itemName=\"glass_shards\" itemMeta=\"16\" />" +
                    "</output>" +
                  "</recipe>" +
                "</recipeGroup>");
            
            StringBuilder msg = new StringBuilder("<recipeGroup name=\"GlassShards\">");
            //SAG mill: stained glass -> stained shards
            for(int i = 0; i < 16; i++) {
                String dye = ModRecipes.dyes[i];
                msg.append("<recipe name=\"Glass" + dye + "\" energyCost=\"1000\">" +
                             "<input>" +
                               "<itemStack oreDictionary=\"blockGlass" + dye + "\" />" +
                             "</input>" +
                             "<output>" +
                               "<itemStack modID=\"glass_shards\" itemName=\"glass_shards\" itemMeta=\"" + i + "\" />" +
                             "</output>" +
                           "</recipe>");
            }
            
            //SAG mill: glass shards -> sand
            msg.append("<recipe name=\"Shards\" energyCost=\"600\">" +
                         "<input>" +
                           "<itemStack oreDictionary=\"shardsGlass\" />" +
                         "</input>" +
                         "<output>" +
                           "<itemStack modID=\"minecraft\" itemName=\"sand\" />" +
                         "</output>" +
                       "</recipe>");
            
            msg.append("</recipeGroup>");
            FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", msg.toString());
            
            //Disable grinding ball for shards
            FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill",
                "<grindingBalls>" +
                  "<excludes>" +
                    "<itemStack oreDictionary=\"shardsGlass\" />" +
                  "</excludes>" +
                "</grindingBalls>");
        
        }
        
        if(Config.eioAlloySmelter) {
            //Only version >= 2.3 has dark clear glass
            boolean isEnderIO23 = new ComparableVersion(Loader.instance().getIndexedModList().get("EnderIO").getVersion())
                    .compareTo(new ComparableVersion("1.7.10-2.3")) >= 0;
            if(isEnderIO23)
                logger.debug("We have EnderIO >= 2.3, adding Dark Clear Glass recipe");
            else
                logger.debug("We don't have EnderIO >= 2.3");
            
            //Alloy Smelter: glass shards -> quite clear glass
            //and glass shards + 4 glowstone dust -> enlightened clear glass
            //and glass shards + 4 ink sacs -> dark clear glass
            StringBuilder msg = new StringBuilder("<recipeGroup name=\"GlassShards\">");
            msg.append("<recipe name=\"Fused Glass\" energyCost=\"2500\">" +
                         "<input>" +
                           "<itemStack modID=\"glass_shards\" itemName=\"glass_shards\" itemMeta=\"16\" />" +
                         "</input>" +
                         "<output>" +
                           "<itemStack modID=\"EnderIO\" itemName=\"blockFusedQuartz\" itemMeta=\"1\" exp=\"0.2\" />" +
                         "</output>" +
                       "</recipe>" +
                       "<recipe name=\"Enlightened Fused Glass\" energyCost=\"2500\">" +
                         "<input>" +
                           "<itemStack modID=\"glass_shards\" itemName=\"glass_shards\" itemMeta=\"16\" />" +
                           "<itemStack modID=\"minecraft\" itemName=\"glowstone_dust\" number=\"4\" />" +
                         "</input>" +
                         "<output>" +
                           "<itemStack modID=\"EnderIO\" itemName=\"blockFusedQuartz\" itemMeta=\"3\" exp=\"0.2\" />" +
                         "</output>" +
                       "</recipe>");
            if(isEnderIO23)
                msg.append("<recipe name=\"Dark Fused Glass\" energyCost=\"2500\">" +
                             "<input>" +
                               "<itemStack modID=\"glass_shards\" itemName=\"glass_shards\" itemMeta=\"16\" />" +
                               "<itemStack modID=\"minecraft\" itemName=\"dye\" itemMeta=\"0\" number=\"4\" />" +
                             "</input>" +
                             "<output>" +
                               "<itemStack modID=\"EnderIO\" itemName=\"blockFusedQuartz\" itemMeta=\"5\" exp=\"0.2\" />" +
                             "</output>" +
                          "</recipe>");
            msg.append("</recipeGroup>");
            FMLInterModComms.sendMessage("EnderIO", "recipe:alloysmelter", msg.toString());
        }
    }

}
