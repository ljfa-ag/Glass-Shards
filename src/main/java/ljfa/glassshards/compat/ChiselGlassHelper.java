package ljfa.glassshards.compat;

import ljfa.glassshards.api.GlassType;
import net.minecraft.block.Block;

public class ChiselGlassHelper {
    public static void init() throws ReflectiveOperationException {
        Class<?> classModBlocks = Class.forName("com.cricketcraft.chisel.init.ModBlocks");
        
        chiselStainedGlass = (Block[])classModBlocks.getDeclaredField("stainedGlass").get(null);
        chiselStainedPane = (Block[])classModBlocks.getDeclaredField("stainedGlassPane").get(null);
        
        for(Block b: chiselStainedPane)
            ModGlassRegistry.removeDropsSet.add(b);
        
        ModGlassRegistry.helperMap.put(chiselStainedGlass[0], new ChiselTypeHandler());
    }
    
    public static class ChiselTypeHandler extends ModGlassHelper {
        @Override
        public GlassType getType(Block block, int meta) {
            return new GlassType(GlassType.mult_block, true, meta);
        }
    }
    
    private static Block[] chiselStainedGlass;
    private static Block[] chiselStainedPane;
}
