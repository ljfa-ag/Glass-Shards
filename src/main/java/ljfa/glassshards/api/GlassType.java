package ljfa.glassshards.api;

import net.minecraft.item.EnumDyeColor;

/**
 * @author ljfa-ag
 * Represents the traits of a glass block. Or pane or anything made of glass really.
 */
public class GlassType {
    /** The multiplier for glass blocks. */
    public static final float mult_block = 1.0f;
    /** The multiplier for glass panes. This is 6/16 of a full block. */
    public static final float mult_pane = 0.375f;

    /**
     * @param multiplier How much of a full block's of glass it is worth. Usually either mult_block or mult_pane.
     * @param stained Whether the glass is stained.
     * @param color The color of the glass, just like vanilla stained glass metadata. Should be -1 if not stained.
     */
    public GlassType(float multiplier, boolean stained, EnumDyeColor color) {
        this.multiplier = multiplier;
        this.stained = stained;
        this.color = color;
    }
    
    /**
     * Constructor for non-stained glass.
     * @param multiplier How much of a full block's of glass it is worth. Should be either mult_block or mult_pane.
     */
    public GlassType(float multiplier) {
        this(multiplier, false, null);
    }
    
    /** @return How much of a full block's of glass it is worth. */
    public float getMultiplier() { return multiplier; }
    
    /** @return Whether the glass is stained.  */
    public boolean isStained() { return stained; }
    
    /** @return The color of the glass if it is stained. This is the same as vanilla stained glass metadata. */
    public EnumDyeColor getColor() { return color; }
    
    /** How much of a full block's of glass it is worth. */
    private float multiplier;
    /** Whether the glass is stained. */
    private boolean stained;
    /** The color of the glass if it is stained. This is the same as vanilla stained glass metadata. */
    private EnumDyeColor color;
}
