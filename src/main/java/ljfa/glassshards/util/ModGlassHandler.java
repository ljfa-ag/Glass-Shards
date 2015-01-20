package ljfa.glassshards.util;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.state.IBlockState;

/** 
 * Base class for glass handlers.
 * Each supported block is being assigned a ModGlassHandler instance through the GlassRegistry.
 */
public abstract class ModGlassHandler {
    /** @return the glass type for a given block */
    public abstract GlassType getType(IBlockState state);
    
    /** @return whether this block should drop nothing */
    public boolean shouldRemoveDrop(IBlockState state) {
        return true;
    }
}
