package ljfa.glassshards.compat;

import java.util.List;

import ljfa.glassshards.Config;
import ljfa.glassshards.ModRecipes;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.ModGlassHandler;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.event.FMLInterModComms;
import crazypants.enderio.EnderIO;
import crazypants.enderio.machine.crusher.CrusherRecipeManager;
import crazypants.enderio.machine.recipe.OreDictionaryRecipeInput;
import crazypants.enderio.machine.recipe.RecipeInput;

public class EnderIOGlassHelper {
    public static final int clear_meta = 1, enlightened_meta = 3;
    
    public static void init() {
        
        GlassRegistry.addHandler(EnderIO.blockFusedQuartz, new ModGlassHandler() {
            @Override
            public void addShardsDrop(HarvestDropsEvent event) {
                super.addShardsDrop(event);
                if(event.blockMetadata == enlightened_meta)
                    for(int i = 0; i < 4; i++) //Drop up to 4
                        event.drops.add(new ItemStack(Items.glowstone_dust));
            }
            
            @Override
            public GlassType getType(Block block, int meta) {
                if(meta == clear_meta || meta == enlightened_meta)
                    return new GlassType(GlassType.mult_block);
                else
                    return null;
            }
            
            @Override
            public boolean shouldRemoveDrop(Block block, int meta) {
                return meta == clear_meta || meta == enlightened_meta;
            }
        });
        
        LogHelper.info("Successfully loaded EnderIO glass compatibility.");
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
            //Doesn't work
            /*FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill",
                "<grindingBalls>" +
                  "<excludes>" +
                    "<itemStack oreDictionary=\"shardsGlass\" />" +
                  "</excludes>" +
                "</grindingBalls>");*/
        
        }
        
        if(Config.eioAlloySmelter) {
            //Alloy Smelter: glass shards -> quite clear glass
            //and glass shards + 4 glowstone dust -> enlightened clear glass
            FMLInterModComms.sendMessage("EnderIO", "recipe:alloysmelter",
                "<recipeGroup name=\"GlassShards\">" +
                  "<recipe name=\"Fused Glass\" energyCost=\"2500\">" +
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
                "</recipe>" +
                "</recipeGroup>");
        }
    }
    
    /**
     * Adds glass shards to the grinding ball blacklist.
     * This is a hacky workaround since EnderIO's InterModComms don't support this (yet).
     */
    @SuppressWarnings("unchecked")
    public static void setupGrindingBallExcludes() {
        try {
            CrusherRecipeManager recipeManager = CrusherRecipeManager.getInstance();
            List<RecipeInput> excludes = (List<RecipeInput>)ReflectionHelper.getField(CrusherRecipeManager.class, "ballExcludes", recipeManager);
            excludes.add(new OreDictionaryRecipeInput(new ItemStack(ModItems.glass_shards, 1, 16), OreDictionary.getOreID("shardsGlass"), 0));
            
            LogHelper.info("Successfully added to EnderIO grinding ball excludes list.");
        } catch(ReflectiveOperationException ex) {
            LogHelper.log(Level.ERROR, ex, "Failed to manipulate EnderIO grinding ball excludes list.");
            return;
        }
    }
}
