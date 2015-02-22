package ljfa.glassshards.compat;

import java.util.List;

import ljfa.glassshards.Config;
import ljfa.glassshards.ModRecipes;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.ModGlassHandler;
import ljfa.glassshards.util.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.event.FMLInterModComms;
import crazypants.enderio.EnderIO;
import crazypants.enderio.machine.crusher.CrusherRecipeManager;
import crazypants.enderio.machine.recipe.OreDictionaryRecipeInput;
import crazypants.enderio.machine.recipe.RecipeInput;

public class EnderIOGlassHelper {
    public static void init() {
        //Add block to the registry
        //meta 1 = quite clear glass
        GlassRegistry.addHandler(EnderIO.blockFusedQuartz, new ModGlassHandler() {
            @Override
            public GlassType getType(Block block, int meta) {
                if(meta == 1)
                    return new GlassType(GlassType.mult_block);
                else
                    return null;
            }
            
            @Override
            public boolean shouldRemoveDrop(Block block, int meta) {
                return meta == 1;
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
                  "<recipe name=\"Glass\" energyCost=\"1200\">" +
                    "<input>" +
                      "<itemStack oreDictionary=\"glass\" />" +
                    "</input>" +
                    "<output>" +
                      "<itemStack modID=\"glass_shards\" itemName=\"glass_shards\" itemMeta=\"16\" />" +
                    "</output>" +
                  "</recipe>" +
                "</recipeGroup>");
            
            String msg = "<recipeGroup name=\"GlassShards\">";
            //SAG mill: stained glass -> stained shards
            for(int i = 0; i < 16; i++) {
                String dye = ModRecipes.dyes[i];
                msg += "<recipe name=\"Glass" + dye + "\" energyCost=\"1200\">" +
                         "<input>" +
                           "<itemStack oreDictionary=\"blockGlass" + dye + "\" />" +
                         "</input>" +
                         "<output>" +
                           "<itemStack modID=\"glass_shards\" itemName=\"glass_shards\" itemMeta=\"" + i + "\" />" +
                         "</output>" +
                       "</recipe>";
            }
            
            //SAG mill: glass shards -> sand
            msg += "<recipe name=\"Shards\" energyCost=\"600\">" +
                     "<input>" +
                       "<itemStack oreDictionary=\"shardsGlass\" />" +
                     "</input>" +
                     "<output>" +
                       "<itemStack modID=\"minecraft\" itemName=\"sand\" />" +
                     "</output>" +
                   "</recipe>";
            
            msg += "</recipeGroup>";
            FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", msg);
            
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
                "</recipeGroup>");
        }
    }
    
    /**
     * Adds glass shards to the grinding ball blacklist.
     * This is a hacky workaround since EnderIO's InterModComms don't support this (yet).
     */
    public static void setupGrindingBallExcludes() {
        try {
            CrusherRecipeManager recipeManager = CrusherRecipeManager.getInstance();
            List<RecipeInput> excludes = (List<RecipeInput>)ReflectionHelper.getField(CrusherRecipeManager.class, "ballExcludes", recipeManager);
            excludes.add(new OreDictionaryRecipeInput(new ItemStack(ModItems.glass_shards, 16), OreDictionary.getOreID("shardsGlass"), 0));
            
            LogHelper.info("Successfully added to EnderIO grinding ball excludes list.");
        } catch(Exception ex) {
            LogHelper.log(Level.ERROR, ex, "Failed to manipulate EnderIO grinding ball excludes list.");
            return;
        }
    }
}
