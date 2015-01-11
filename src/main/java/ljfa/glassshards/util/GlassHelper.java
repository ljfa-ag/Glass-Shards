package ljfa.glassshards.util;

import ljfa.glassshards.GlassShards;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.api.IShatterableGlass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;

public class GlassHelper {
    /** Checks if the block is some kind of glass
     * @return A GlassType representing the type, or null if it is not glass
     */
    public static GlassType getType(Block block, int meta) {
        if(block instanceof IShatterableGlass) {
            GlassType gtype = ((IShatterableGlass)block).getType(block, meta);
            if(gtype != null)
                return gtype;
        } else if(GlassShards.chiselHelper != null) {
            GlassType gtype = GlassShards.chiselHelper.getType(block, meta);
            if(gtype != null)
                return gtype;
        }
        
        if(block instanceof BlockGlass)
            return new GlassType(GlassType.mult_block);
        else if(block instanceof BlockStainedGlass)
            return new GlassType(GlassType.mult_block, true, meta);
        else if(block instanceof BlockStainedGlassPane)
            return new GlassType(GlassType.mult_pane, true, meta);
        else if(block instanceof BlockPane && block.getMaterial() == Material.glass)
            return new GlassType(GlassType.mult_pane);
        else
            return null;
    }

}
