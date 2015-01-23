package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.ModGlassHandler;
import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.event.FMLInterModComms;

public class EnderIOGlassHelper {
    public static void init() {
        //Fetch EnderIO's block instance
        Block eioGlass;
        try {
            Class<?> classModBlocks = Class.forName("crazypants.enderio.EnderIO");
            
            eioGlass = (Block)classModBlocks.getDeclaredField("blockFusedQuartz").get(null);
        } catch(Exception ex) {
            LogHelper.log(Level.ERROR, ex, "Failed to load EnderIO glass compatibility.");
            return;
        }
        
        //Add block to the registry
        //meta 1 = quite clear glass
        GlassRegistry.addHandler(eioGlass, new ModGlassHandler() {
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
        FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill",
            "<recipeGroup name=\"EnderIO\">"
          + "<recipe name=\"Glass\" energyCost=\"1200\">"
          + "<input><itemStack oreDictionary=\"glass\" /></input>"
          + "<output><itemStack modID=\"glass_shards\" itemName=\"glass_shards\" itemMeta=\"16\" /></output>"
          + "</recipe>"
          + "</recipeGroup>");
    }
}
