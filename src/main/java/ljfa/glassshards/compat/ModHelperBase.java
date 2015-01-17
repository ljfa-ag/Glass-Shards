package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

public abstract class ModHelperBase {
    public abstract GlassType getType(Block block, int meta);
    
    public abstract boolean shouldRemoveDrop(Block block, int meta);
}
