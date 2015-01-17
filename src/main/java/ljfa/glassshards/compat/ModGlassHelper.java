package ljfa.glassshards.compat;

import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ModGlassHelper {
    public static ChiselGlassHelper chiselHelper = null;
    
    public static void postInit() {
        if(Config.chiselEnable && Loader.isModLoaded("chisel")) {
            try {
                chiselHelper = new ChiselGlassHelper();
                LogHelper.info("Successfully loaded Chisel compatibility.");
            } catch(Exception ex) {
                FMLLog.log(Reference.MODNAME, Level.ERROR, ex, "Failed to load Chisel compatibility.");
            }
        }
    }
    
    public static boolean shouldRemoveDrop(Block block, int meta) {
        return Config.chiselFixPaneDrops && chiselHelper != null && chiselHelper.shouldRemoveDrop(block, meta);
    }
    
    public static GlassType getType(Block block, int meta) {
        if(chiselHelper != null)
            return chiselHelper.getType(block, meta);
        else
            return null;
    }
}
