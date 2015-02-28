package ljfa.glassshards.util;

import java.util.HashMap;
import java.util.Map;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

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
    
    /** @return a handler for a given block if one exists, or null */
    public static ModGlassHandler get(Block block) {
        return handlerMap.get(block);
    }

    /** @return the map that assigns a handler to each glass block */
    public static Map<Block, ModGlassHandler> getHandlerMap() {
        return handlerMap;
    }
    
    private static final Map<Block, ModGlassHandler> handlerMap = new HashMap<Block, ModGlassHandler>();
}
