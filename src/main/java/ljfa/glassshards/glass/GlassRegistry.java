package ljfa.glassshards.glass;

import static ljfa.glassshards.GlassShards.logger;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

/**
 * Class that handles glasses form other mods that don't implement the API.
 * Using the API is preferred before this.
 */
public class GlassRegistry {
    /** Adds a handler for a given glass block */
    public static void addHandler(Block block, ModGlassHandler handler) {
        handlerMap.put(block, handler);
        logger.trace("Added glass handler for {}", block.getRegistryName());
    }
    
    /** Adds a handler for a given glass block */
    public static void addHandler(String blockname, ModGlassHandler handler) {
        if(Block.REGISTRY.containsKey(new ResourceLocation(blockname)))
            addHandler(Block.getBlockFromName(blockname), handler);
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
        for(Block block: Block.REGISTRY) {
            if(block.getDefaultState().getMaterial() != Material.GLASS)
                continue;
            
            if(block instanceof BlockGlass) {
                addHandler(block, SimpleGlassHandler.blockInstance);
                counter++;
            } else if(block instanceof BlockStainedGlass) {
                addHandler(block, SimpleGlassHandler.stainedBlockInstance);
                counter++;
            } else if(block instanceof BlockStainedGlassPane) {
                addHandler(block, SimpleGlassHandler.stainedPaneInstance);
                counter++;
            } else if(block instanceof BlockPane) {
                addHandler(block, SimpleGlassHandler.paneInstance);
                counter++;
            }
        }
        logger.info("Added {} blocks to the GlassRegistry", counter);
    }
    
    private static final Map<Block, ModGlassHandler> handlerMap = new HashMap<Block, ModGlassHandler>();
}
