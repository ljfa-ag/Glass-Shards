package ljfa.glassshards.api;

import net.minecraft.block.Block;

/**
 * @author ljfa-ag
 * To be implemented by glass blocks (or panes or anything) for retrieving traits about the glass and making it drop shards.
 */
public interface IShatterableGlass {
    /**
     * @param block The block
     * @param meta The metadata
     * @return A GlassType object representing the block, or null.
     */
    public GlassType getType(Block block, int meta);
}
