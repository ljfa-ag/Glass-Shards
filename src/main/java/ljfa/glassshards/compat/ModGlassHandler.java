package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

/** 
 * Base class for glass handlers.
 * Through the ModGlassRegistry each supported block is being assigned a ModGlassHandler instance.
 */
public abstract class ModGlassHandler {
    /** @return the glass type for a given block */
    public abstract GlassType getType(Block block, int meta);
}
