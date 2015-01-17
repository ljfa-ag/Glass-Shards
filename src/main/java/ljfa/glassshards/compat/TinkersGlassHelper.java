package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

public class TinkersGlassHelper extends ModHelperBase {
    public TinkersGlassHelper() {
        
    }
    
    @Override
    public GlassType getType(Block block, int meta) {
        return null;
    }

    @Override
    public boolean shouldRemoveDrop(Block block, int meta) {
        return false;
    }

}
