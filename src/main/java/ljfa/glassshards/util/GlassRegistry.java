package ljfa.glassshards.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.compat.ChiselGlassHelper;
import ljfa.glassshards.compat.TinkersGlassHelper;
import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

/**
 * Class that handles glasses form other mods that don't implement the API.
 * Using the API is preferred before this.
 */
public class GlassRegistry {
    /** Adds a handler for a given glass block */
    public static void addHandler(Block block, ModGlassHandler handler) {
        handlerMap.put(block, handler);
        LogHelper.trace("Added glass handler for %s (%d)", block.getUnlocalizedName(), Block.getIdFromBlock(block));
    }
    
    /** Checks if the drops should be removed for this block */
    public static boolean shouldRemoveDrop(Block block, int meta) {
        ModGlassHandler helper = handlerMap.get(block);
        if(helper != null)
            return helper.shouldRemoveDrop(block, meta);
        else
            return false;
    }
    
    /** 
     * Calls the assigned handler for this block if one exists.
     * @return The type as returned by the handler, or null if no handler exists.
     */
    public static GlassType getType(Block block, int meta) {
        ModGlassHandler helper = handlerMap.get(block);
        if(helper != null)
            return helper.getType(block, meta);
        else
            return null;
    }

    /** @return the map that assigns a handler to each glass block */
    public static Map<Block, ModGlassHandler> getHandlerMap() {
        return handlerMap;
    }
    
    private static Map<Block, ModGlassHandler> handlerMap = new HashMap<Block, ModGlassHandler>();
}
