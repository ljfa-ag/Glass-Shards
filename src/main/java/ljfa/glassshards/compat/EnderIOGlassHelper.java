package ljfa.glassshards.compat;

import static ljfa.glassshards.GlassShards.logger;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

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
    //Metadata of EnderIO regular, enlightened and dark clear glass
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
                "<recipeGroup name=\"EnderIO\">" + getRecipeXML("SagShards") + "</recipeGroup>");
            
            StringBuilder msg = new StringBuilder("<recipeGroup name=\"GlassShards\">");
            //SAG mill: stained glass -> stained shards (one recipe for each color)
            String template = getRecipeXML("SagStainedShards");
            for(int i = 0; i < 16; i++)
                msg.append(String.format(template, ModRecipes.dyes[i], i));
            
            //SAG mill: glass shards -> sand
            msg.append(getRecipeXML("SagSand")).append("</recipeGroup>");
            FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", msg.toString());
            
            //Disable grinding ball for shards
            FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", getRecipeXML("SagGrindingBall"));
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
            String msg = "<recipeGroup name=\"GlassShards\">" + getRecipeXML("SmelterClearGlass");
            if(isEnderIO23)
                msg += getRecipeXML("SmelterDarkGlass");
            msg += "</recipeGroup>";
            FMLInterModComms.sendMessage("EnderIO", "recipe:alloysmelter", msg);
        }
    }
    
    private static String getRecipeXML(String name) {
        try {
            return Resources.toString(Resources.getResource("assets/glass_shards/EnderIORecipes/" + name + ".xml"), Charsets.UTF_8);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
