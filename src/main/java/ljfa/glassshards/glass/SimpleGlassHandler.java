package ljfa.glassshards.glass;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

/**
 * The default implementation of ModGlassHandler.
 * For stained glass the color is simply inferred from the metadata.
 * This one can be used most of the time.
 */
public class SimpleGlassHandler extends ModGlassHandler {
    /** Instance for uncolored glass blocks */
    public static final SimpleGlassHandler blockInstance = new SimpleGlassHandler(GlassType.mult_block, false);
    /** Instance for uncolored glass panes */
    public static final SimpleGlassHandler paneInstance = new SimpleGlassHandler(GlassType.mult_pane, false);
    /** Instance for stained glass blocks */
    public static final SimpleGlassHandler stainedBlockInstance = new SimpleGlassHandler(GlassType.mult_block, true);
    /** Instance for stained glass panes */
    public static final SimpleGlassHandler stainedPaneInstance = new SimpleGlassHandler(GlassType.mult_pane, true);
    
    /** Instance for uncolored glass blocks where the drops should be removed */
    public static final SimpleGlassHandler clearingBlockInstance = new SimpleGlassHandler(GlassType.mult_block, false, true);
    /** Instance for uncolored glass panes where the drops should be removed */
    public static final SimpleGlassHandler clearingPaneInstance = new SimpleGlassHandler(GlassType.mult_pane, false, true);
    /** Instance for stained glass blocks where the drops should be removed */
    public static final SimpleGlassHandler clearingStainedBlockInstance = new SimpleGlassHandler(GlassType.mult_block, true, true);
    /** Instance for stained glass panes where the drops should be removed */
    public static final SimpleGlassHandler clearingStainedPaneInstance = new SimpleGlassHandler(GlassType.mult_pane, true, true);
    
    protected final float multiplier;
    protected final boolean stained;
    protected final boolean removeDrops;
    
    protected SimpleGlassHandler(float multiplier, boolean stained, boolean removeDrops) {
        this.stained = stained;
        this.multiplier = multiplier;
        this.removeDrops = removeDrops;
    }
    
    protected SimpleGlassHandler(float multiplier, boolean stained) {
        this(multiplier, stained, false);
    }

    @Override
    public GlassType getType(Block block, int meta) {
        return new GlassType(multiplier, stained, meta);
    }
    
    @Override
    public boolean shouldRemoveDrop(Block block, int meta) {
        return removeDrops;
    }
}
