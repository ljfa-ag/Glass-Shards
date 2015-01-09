package ljfa.glassshards.util;

/** Represents a certain type of glass for the purpose of shards */
public class GlassType {
    /** Represents if a block is a glass block or pane */
    public static enum GlassShape {
        block(1.0f), pane(0.375f);
        
        public final float multiplier;
        
        private GlassShape(float mult) { multiplier = mult; }
    }
    
    public final GlassShape shape;
    public final boolean isStained;
    public final int color;
    
    public GlassType(GlassShape shape, boolean stained, int color) {
        this.shape = shape;
        this.isStained = stained;
        this.color = color;
    }
    
    public GlassType(GlassShape shape) {
        this(shape, false, -1);
    }
}
