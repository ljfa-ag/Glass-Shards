package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

public class SimpleTypeHandler extends ModGlassHandler {
    private float multiplier;
    
    public SimpleTypeHandler(float multiplier) {
        this.multiplier = multiplier;
    }
    
    @Override
    public GlassType getType(Block block, int meta) {
        return new GlassType(multiplier, true, meta);
    }
}
