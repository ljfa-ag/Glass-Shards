package ljfa.glassshards.util;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

/**
 * The default implementation of ModGlassHandler.
 * For stained glass the color is simply inferred from the metadata.
 * This one can be used most of the time.
 */
public class SimpleGlassHandler extends ModGlassHandler {
    /** Instance for uncolored glass blocks */
    public static SimpleGlassHandler blockInstance = new SimpleGlassHandler(GlassType.mult_block, false);
    /** Instance for uncolored glass panes */
    public static SimpleGlassHandler paneInstance = new SimpleGlassHandler(GlassType.mult_pane, false);
    /** Instance for stained glass blocks */
    public static SimpleGlassHandler stainedBlockInstance = new SimpleGlassHandler(GlassType.mult_block, true);
    /** Instance for stained glass panes */
    public static SimpleGlassHandler stainedPaneInstance = new SimpleGlassHandler(GlassType.mult_pane, true);
    
    protected float multiplier;
    protected boolean stained;
    
    protected SimpleGlassHandler(float multiplier, boolean stained) {
        this.stained = stained;
        this.multiplier = multiplier;
    }

    @Override
    public GlassType getType(Block block, int meta) {
        if(stained)
            return new GlassType(multiplier, true, meta);
        else
            return new GlassType(multiplier);
    }
}
