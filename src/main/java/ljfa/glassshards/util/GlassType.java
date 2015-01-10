package ljfa.glassshards.util;

import ljfa.glassshards.GlassShards;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;

/**
 * @author ljfa-ag
 * Represents the traits of a glass block. Or pane or anything made of glass really.
 */
public class GlassType {
    /** The multiplier for glass blocks. */
    public static final float mult_block = 1.0f;
    /** The multiplier for glass panes. */
    public static final float mult_pane = 0.375f;
    
    /** How much of a full block's of glass it is worth. */
    public final float multiplier;
    /** Whether the glass is stained. */
    public final boolean isStained;
    /** The color of the glass. This is the same as vanilla stained glass metadata. */
    public final int color;
    
    /**
     * @param multiplier How much of a full block's of glass it is worth. Should be either mult_block or mult_pane.
     * @param stained Whether the glass is stained.
     * @param color The color of the glass, just like vanilla stained glass metadata.
     */
    public GlassType(float multiplier, boolean stained, int color) {
        this.multiplier = multiplier;
        this.isStained = stained;
        this.color = color;
    }
    
    /**
     * Constructor for non-stained glass.
     * @param multiplier How much of a full block's of glass it is worth. Should be either mult_block or mult_pane.
     */
    public GlassType(float multiplier) {
        this(multiplier, false, -1);
    }
    
    /** Checks if the block is some kind of glass
     * @return A GlassType representing the type, or null if it is not glass
     */
    public static GlassType getType(Block block, int meta) {
        if(GlassShards.chiselHelper != null) {
            GlassType gtype = GlassShards.chiselHelper.getType(block, meta);
            if(gtype != null)
                return gtype;
        }
        
        if(block instanceof BlockGlass)
            return new GlassType(mult_block);
        else if(block instanceof BlockStainedGlass)
            return new GlassType(mult_block, true, meta);
        else if(block instanceof BlockStainedGlassPane)
            return new GlassType(mult_pane, true, meta);
        else if(block instanceof BlockPane && block.getMaterial() == Material.glass)
            return new GlassType(mult_pane);
        else
            return null;
    }
}
