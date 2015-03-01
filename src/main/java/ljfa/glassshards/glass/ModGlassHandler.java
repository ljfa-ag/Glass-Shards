package ljfa.glassshards.glass;

import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.handlers.HarvestDropsHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

/** 
 * Base class for glass handlers.
 * Each supported block is being assigned a ModGlassHandler instance through the GlassRegistry.
 */
public abstract class ModGlassHandler {
    /**
    * With the correct probability, adds the shards drop to the drop list.
    * The default implementation uses the return value of getType() for that.
    */
    public void addShardsDrop(HarvestDropsEvent event) {
        HarvestDropsHandler.addDropForType(event, getType(event.state));
    }
    
    /** @return the glass type for a given block, or null if not applicable */
    public GlassType getType(IBlockState state) {
        return null;
    }
    
    /** @return whether this block should drop nothing */
    public boolean shouldRemoveDrop(IBlockState state) {
        return false;
    }
}
