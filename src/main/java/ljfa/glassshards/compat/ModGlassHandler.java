package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

/** 
 * Base class for glass handlers.
 */
public abstract class ModGlassHandler {
    /** @return the glass type for a given block */
    public abstract GlassType getType(Block block, int meta);
}
