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

/**
 * Class that handles glasses form other mods that don't implement the API.
 * Using the API is preferred before this.
 */
public class ModGlassRegistry {
    /** Set of blocks for which the drops should be removed */
    public static Set<Block> removeDropsSet = new HashSet<Block>();
    /** Map that assigns a handler to each glass block */
    public static Map<Block, ModGlassHandler> handlerMap = new HashMap<Block, ModGlassHandler>();
    
    public static void postInit() {
        if(Config.chiselEnable && Loader.isModLoaded("chisel"))
            ChiselGlassHelper.init();
    }

    public static boolean shouldRemoveDrop(Block block, int meta) {
        return removeDropsSet.contains(block);
    }
    
    public static GlassType getType(Block block, int meta) {
        ModGlassHandler helper = handlerMap.get(block);
        if(helper != null)
            return helper.getType(block, meta);
        else
            return null;
    }
}
