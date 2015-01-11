package ljfa.glassshards.api;

import net.minecraft.block.Block;

/**
 * @author ljfa-ag
 * To be implemented by glass blocks (or panes or anything) for retrieving traits about the glass.
 * Note that in order for your block to work with Glass Shards it needs to not drop anything when broken.
 */
public interface IShatterableGlass {
    /**
     * @param block The block
     * @param meta The metadata
     * @return A GlassType object representing the block.
     */
    public GlassType getType(Block block, int meta);
}
