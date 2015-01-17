package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

public class TinkersGlassHelper extends ModHelperBase {
    public TinkersGlassHelper() throws ReflectiveOperationException {
        Class<?> classModBlocks = Class.forName("tconstruct.smeltery.TinkerSmeltery");
        
        tcGlass = (Block)classModBlocks.getDeclaredField("clearGlass").get(null);
        tcStainedGlass = (Block)classModBlocks.getDeclaredField("stainedGlassClear").get(null);
        tcPane = (Block)classModBlocks.getDeclaredField("glassPane").get(null);
        tcStainedPane = (Block)classModBlocks.getDeclaredField("stainedGlassClearPane").get(null);
    }
    
    @Override
    public GlassType getType(Block block, int meta) {
        if(block == tcGlass)
            return new GlassType(GlassType.mult_block);
        else if(block == tcStainedGlass)
            return new GlassType(GlassType.mult_block, true, meta);
        else if(block == tcPane)
            return new GlassType(GlassType.mult_pane);
        else if(block == tcStainedPane)
            return new GlassType(GlassType.mult_pane, true, meta);
        else
            return null;
    }

    @Override
    public boolean shouldRemoveDrop(Block block, int meta) {
        return block == tcGlass || block == tcStainedGlass || block == tcPane || block == tcStainedPane;
    }

    private Block tcGlass, tcStainedGlass, tcPane, tcStainedPane;
}
