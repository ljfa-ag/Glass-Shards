package ljfa.glassshards.glass;

import static ljfa.glassshards.GlassShards.logger;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;

/**
 * Class that handles glasses form other mods that don't implement the API.
 * Using the API is preferred before this.
 */
public class GlassRegistry {
    /** Adds a handler for a given glass block */
    public static void addHandler(Block block, ModGlassHandler handler) {
        handlerMap.put(block, handler);
        logger.trace("Added glass handler for {} ({})", block.getUnlocalizedName(), Block.getIdFromBlock(block));
    }
    
    /** Adds a handler for a given glass block */
    public static void addHandler(String blockname, ModGlassHandler handler) {
        if(Block.blockRegistry.containsKey(blockname))
            addHandler((Block)Block.blockRegistry.getObject(blockname), handler);
        else
            throw new IllegalArgumentException("Could not find block \"" + blockname + "\"");
    }
    
    /** @return a handler for a given block if one exists, or null */
    public static ModGlassHandler get(Block block) {
        return handlerMap.get(block);
    }

    /** @return the map that assigns a handler to each glass block */
    public static Map<Block, ModGlassHandler> getHandlerMap() {
        return handlerMap;
    }
    
    /** Adds all registered blocks that are glass to the GlassRegistry */
    public static void registerAll() {
        int counter = 0;
        for(Object obj: GameData.getBlockRegistry()) {
            if(!(obj instanceof Block))
                continue;
            Block block = (Block)obj;
            if(block instanceof BlockGlass) {
                addHandler(block, SimpleGlassHandler.blockInstance);
                counter++;
            } else if(block instanceof BlockStainedGlass) {
                addHandler(block, SimpleGlassHandler.stainedBlockInstance);
                counter++;
            } else if(block instanceof BlockStainedGlassPane) {
                addHandler(block, SimpleGlassHandler.stainedPaneInstance);
                counter++;
            } else if(block instanceof BlockPane && block.getMaterial() == Material.glass) {
                addHandler(block, SimpleGlassHandler.paneInstance);
                counter++;
            }
        }
        logger.info("Added {} blocks to the GlassRegistry", counter);
    }
    
    private static final Map<Block, ModGlassHandler> handlerMap = new HashMap<Block, ModGlassHandler>();
}
