package ljfa.glassshards.util;

import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.handlers.HarvestDropsHandler;
import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

/** 
 * Base class for glass handlers. Not intended to be used by other mods, use the API instead.
 */
public abstract class ModGlassHandler {
    /**
     * With the correct probability, adds the shards drop to the drop list.
     * The default implementation uses the return value of getType() for that.
     */
    public void addShardsDrop(HarvestDropsEvent event) {
        HarvestDropsHandler.addDropForType(event, getType(event.block, event.blockMetadata));
    }
    
    /** @return the glass type for a given block, or null if not applicable */
    public GlassType getType(Block block, int meta) {
        return null;
    }
    
    /** @return whether this block's drops should be removed */
    public boolean shouldRemoveDrop(Block block, int meta) {
        return false;
    }
}
