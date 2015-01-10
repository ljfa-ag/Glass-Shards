package ljfa.glassshards.compat;

import ljfa.glassshards.util.GlassType;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;

public class ChiselGlassHelper {
    public ChiselGlassHelper() throws ReflectiveOperationException {
        Class<?> classModBlocks = Class.forName("com.cricketcraft.chisel.init.ModBlocks");
        
        classGlass = Class.forName("com.cricketcraft.chisel.block.BlockCarvableGlass");
        classPane = Class.forName("com.cricketcraft.chisel.block.BlockCarvablePane");
        
        chiselGlass = (Block)classModBlocks.getDeclaredField("glass").get(classModBlocks);
        chiselStainedGlass = (Block[])classModBlocks.getDeclaredField("stainedGlass").get(classModBlocks);
        chiselPane = (Block)classModBlocks.getDeclaredField("paneGlass").get(classModBlocks);
        chiselStainedPane = (Block[])classModBlocks.getDeclaredField("stainedGlassPane").get(classModBlocks);
    }
    
    public GlassType getType(Block block, int meta) {
        if(block.getClass() == classGlass) {
            if(block == chiselGlass)
                return new GlassType(GlassType.mult_block);
            
            // How stained glass works in Chisel:
            // array index = color >> 2
            // metadata = (color & 3) << 2
            // Blame Chisel for using such hardcoded values
            for(int i = 0; i < chiselStainedGlass.length; i++) {
                if(block == chiselStainedGlass[i]) {
                    int color = (i << 2) | (meta >> 2);
                    return new GlassType(GlassType.mult_block, true, color);
                }
            }
        } else if(block.getClass() == classPane) {
            if(block == chiselPane)
                return new GlassType(GlassType.mult_pane);
            
            // How stained panes work in Chisel:
            // array index = color >> 1
            // metadata = (color & 1) << 3
            for(int i = 0; i < chiselStainedPane.length; i++) {
                if(block == chiselStainedPane[i]) {
                    int color = (i << 1) | (meta >> 3);
                    return new GlassType(GlassType.mult_pane, true, color);
                }
            }
        }
        return null;
    }
    
    public boolean shouldRemoveDrop(Block block, int meta) {
        return block.getClass() == classPane;
    }
    
    private Class<?> classGlass;
    private Class<?> classPane;
    
    private Block chiselGlass;
    private Block[] chiselStainedGlass;
    private Block chiselPane;
    private Block[] chiselStainedPane;
}
