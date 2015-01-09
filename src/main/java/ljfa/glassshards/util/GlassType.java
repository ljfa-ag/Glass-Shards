package ljfa.glassshards.util;

import ljfa.glassshards.util.GlassHelper.GlassShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;

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
    
    /** Checks if the block is some kind of glass
     * @return A GlassShape representing the type, or null if it is not glass
     */
    public static GlassType getType(Block block, int meta) {
        if(block instanceof BlockGlass)
            return new GlassType(GlassShape.block);
        else if(block instanceof BlockStainedGlass)
            return new GlassType(GlassShape.block, true, meta);
        else if(block instanceof BlockPane && block.getMaterial() == Material.glass)
            return new GlassType(GlassShape.pane);
        else if(block instanceof BlockStainedGlassPane)
            return new GlassType(GlassShape.pane, true, meta);
        else
            return null;
    }
}
