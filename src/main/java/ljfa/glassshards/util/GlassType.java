package ljfa.glassshards.util;

public class GlassType {
    /** Which mod adds the glass block? */
    public static enum GlassOrigin {
        vanilla, chisel
    }
    
    /** Represents if a block is a glass block or pane */
    public static enum GlassShape {
        block(1.0f), pane(0.375f);
        
        public final float multiplier;
        
        private GlassShape(float mult) { multiplier = mult; }
    }
    
    public final GlassOrigin origin;
    public final GlassShape shape;
    public final boolean isStained;
    public final int color;
    
    public GlassType(GlassOrigin origin, GlassShape shape, boolean stained, int color) {
        this.origin = origin;
        this.shape = shape;
        this.isStained = stained;
        this.color = color;
    }
    
    public GlassType(GlassOrigin origin, GlassShape shape) {
        this(origin, shape, false, -1);
    }
}
