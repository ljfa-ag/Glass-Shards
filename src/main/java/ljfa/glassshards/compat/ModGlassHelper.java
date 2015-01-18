package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

public abstract class ModGlassHelper {
    public abstract GlassType getType(Block block, int meta);
}
