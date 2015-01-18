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
    
    public static void postInit() {
        
    }

    public static boolean shouldRemoveDrop(Block block, int meta) {
        return false;
    }
    
    public static GlassType getType(Block block, int meta) {
        return null;
    }
}
