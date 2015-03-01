package ljfa.glassshards.api;

import net.minecraft.block.state.IBlockState;

/**
 * @author ljfa-ag
 * To be implemented by glass blocks (or panes or anything) for retrieving traits about the glass and making it drop shards.
 */
public interface IShatterableGlass {
    /**
     * @param state The block state
     * @return A GlassType object representing the block, or null.
     */
    public GlassType getType(IBlockState state);
}
