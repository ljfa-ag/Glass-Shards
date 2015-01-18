package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

/** This handler infers the color simply from the metadata. Use this if metadata == color always. */
public class SimpleStainedHandler extends ModGlassHandler {
    protected float multiplier;
    
    public SimpleStainedHandler(float multiplier) {
        this.multiplier = multiplier;
    }
    
    @Override
    public GlassType getType(Block block, int meta) {
        return new GlassType(multiplier, true, meta);
    }
    
    public static SimpleStainedHandler blockInstance = new SimpleStainedHandler(GlassType.mult_block);
    public static SimpleStainedHandler paneInstance = new SimpleStainedHandler(GlassType.mult_pane);
}
