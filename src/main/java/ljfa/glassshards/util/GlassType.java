package ljfa.glassshards.util;

import ljfa.glassshards.GlassShards;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;

/** Represents a certain type of glass for the purpose of shards */
public class GlassType {
    public static final float mult_block = 1.0f;
    public static final float mult_pane = 0.375f;
    
    public final float multiplier;
    public final boolean isStained;
    public final int color;
    
    public GlassType(float multiplier, boolean stained, int color) {
        this.multiplier = multiplier;
        this.isStained = stained;
        this.color = color;
    }
    
    public GlassType(float multiplier) {
        this(multiplier, false, -1);
    }
    
    /** Checks if the block is some kind of glass
     * @return A GlassShape representing the type, or null if it is not glass
     */
    public static GlassType getType(Block block, int meta) {
        if(GlassShards.chiselHelper != null && GlassShards.chiselHelper.comesFrom(block, meta))
            return GlassShards.chiselHelper.getType(block, meta);
        else if(block instanceof BlockGlass)
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
