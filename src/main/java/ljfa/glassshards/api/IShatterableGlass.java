package ljfa.glassshards.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author ljfa-ag
 * An interface that can be implemented by blocks that are made of glass and should drop glass shards when broken
 */
public interface IShatterableGlass {
    /**
     * @param world The world
     * @param pos The position of the block
     * @param state The block state
     * @return A GlassType object representing the block, or null.
     */
    public GlassType getGlassType(World world, BlockPos pos, IBlockState state);
}
