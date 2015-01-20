package ljfa.glassshards.util;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

/** 
 * Base class for glass handlers.
 * Each supported block is being assigned a ModGlassHandler instance through the GlassRegistry.
 */
public abstract class ModGlassHandler {
    /** @return the glass type for a given block */
    public abstract GlassType getType(Block block, int meta);
    
    /** @return whether this block should drop nothing */
    public boolean shouldRemoveDrop(Block block, int meta) {
        return true;
    }
}
