package ljfa.glassshards.compat;

import net.minecraft.block.Block;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.api.IShatterableGlass;

public class ChiselGlassHelper {
    public static void init() throws ReflectiveOperationException {
        Class<?> classModBlocks = Class.forName("com.cricketcraft.chisel.init.ModBlocks");
        
        chiselGlass = (Block)classModBlocks.getDeclaredField("glass").get(null);
        chiselStainedGlass = (Block[])classModBlocks.getDeclaredField("stainedGlass").get(null);
        chiselPane = (Block)classModBlocks.getDeclaredField("paneGlass").get(null);
        chiselStainedPane = (Block[])classModBlocks.getDeclaredField("stainedGlassPane").get(null);
        
        for(Block b: chiselStainedPane)
            ModGlassHelper.removeDropsSet.add(b);
        
        ModGlassHelper.helperMap.put(chiselStainedGlass[0], new ChiselTypeHandler());
    }
    
    public static class ChiselTypeHandler implements IShatterableGlass {
        @Override
        public GlassType getType(Block block, int meta) {
            return new GlassType(GlassType.mult_block, true, meta);
        }
    }
    
    private static Block chiselGlass;
    private static Block[] chiselStainedGlass;
    private static Block chiselPane;
    private static Block[] chiselStainedPane;
}
