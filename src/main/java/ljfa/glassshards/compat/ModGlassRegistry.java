package ljfa.glassshards.compat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ModGlassRegistry {
    public static Set<Block> removeDropsSet = new HashSet<Block>();
    public static Map<Block, ModGlassHelper> helperMap = new HashMap<Block, ModGlassHelper>();
    
    public static void postInit() {
        if(Config.chiselEnable && Loader.isModLoaded("chisel")) {
            try {
                ChiselGlassHelper.init();
                LogHelper.info("Successfully loaded Chisel compatibility.");
            } catch(Exception ex) {
                FMLLog.log(Reference.MODNAME, Level.ERROR, ex, "Failed to load Chisel compatibility.");
            }
        }
    }

    public static boolean shouldRemoveDrop(Block block, int meta) {
        return removeDropsSet.contains(block);
    }
    
    public static GlassType getType(Block block, int meta) {
        ModGlassHelper helper = helperMap.get(block);
        if(helper != null)
            return helper.getType(block, meta);
        else
            return null;
    }
}
