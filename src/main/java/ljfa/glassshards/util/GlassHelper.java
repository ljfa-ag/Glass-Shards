package ljfa.glassshards.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;

public class GlassHelper {
    /** Represents if a block is a glass block or pane */
    public static enum GlassType {
        block(1.0f), pane(0.375f);
        
        public final float multiplier;
        
        private GlassType(float mult) { multiplier = mult; }
    }
    
    /** Checks if the block is some kind of glass
     * @return A GlassType representing the type, or null if it is not a glass block
     */
    public static GlassType getType(Block block, int meta) {
        if(block instanceof BlockGlass || block instanceof BlockStainedGlass)
            return GlassType.block;
        else if(block instanceof BlockStainedGlassPane
                || block instanceof BlockPane && block.getMaterial() == Material.glass)
            return GlassType.pane;
        else
            return null;
    }
    
    /** Checks if the glass block is stained */
    public static boolean isGlassStained(Block block, int meta) {
        return block instanceof BlockStainedGlass || block instanceof BlockStainedGlassPane;
    }
    
    /** Returns the color for stained glass */
    public static int getGlassColor(Block block, int meta) {
        return meta;
    }
}
