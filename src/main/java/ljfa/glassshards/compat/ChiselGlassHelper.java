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
        
        for(int i = 0; i < chiselStainedGlass.length; i++)
            ModGlassRegistry.helperMap.put(chiselStainedGlass[i], new ChiselStainedGlassHandler(i));
    }
    
    public static class ChiselStainedGlassHandler extends ModGlassHandler {
        private int arrayIndex;
        
        public ChiselStainedGlassHandler(int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }
        
        @Override
        public GlassType getType(Block block, int meta) {
            // How stained glass works in Chisel:
            // array index = color >> 2
            // metadata = (color & 3) << 2
            // Blame Chisel for using such hardcoded values
            int color = (arrayIndex << 2) | (meta >> 2);
            return new GlassType(GlassType.mult_block, true, color);
        }
    }
    
    private static Block[] chiselStainedGlass;
    private static Block[] chiselStainedPane;
}
