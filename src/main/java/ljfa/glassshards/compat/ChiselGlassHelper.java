package ljfa.glassshards.compat;

import static ljfa.glassshards.GlassShards.logger;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.ModGlassHandler;
import net.minecraft.block.Block;

public class ChiselGlassHelper {

    public static void init() {
        //These strings are not actually the correct colors for the Chisel stained glass blocks.
        //In Chisel, stained glass blocks span across four different block ids.
        //But unlike vanilla, the metadata of these blocks do not map 1:1 to the colors.
        //The color is determined by both the metadata and the index of the block in one of
        //Chisel's internally used arrays. Same thing for stained panes across 8 block IDs.
        //See below for details on the colors.
        String[] chiselStainedGlass = {"white", "yellow", "lightgray", "brown"};
        String[] chiselStainedPane = {"white", "magenta", "yellow", "pink", "lightgray", "purple", "brown", "red"};
        
        //Add blocks to the registry
        for(int i = 0; i < chiselStainedGlass.length; i++)
            GlassRegistry.addHandler("chisel:stained_glass_" + chiselStainedGlass[i], new ChiselStainedGlassHandler(i));
        
        for(int i = 0; i < chiselStainedPane.length; i++)
            GlassRegistry.addHandler("chisel:stained_glass_pane_" + chiselStainedPane[i], new ChiselStainedPaneHandler(i));
        
        logger.info("Successfully loaded Chisel compatibility.");
    }
    
    // How stained glass works in Chisel:
    // array index = color >> 2
    // metadata = (color & 3) << 2
    public static class ChiselStainedGlassHandler extends ModGlassHandler {
        private final int arrayIndex;
        
        public ChiselStainedGlassHandler(int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }
        
        @Override
        public GlassType getType(Block block, int meta) {
            int color = (arrayIndex << 2) | (meta >> 2);
            return new GlassType(GlassType.mult_block, true, color);
        }
    }

    // How stained panes work in Chisel:
    // array index = color >> 1
    // metadata = (color & 1) << 3
    public static class ChiselStainedPaneHandler extends ModGlassHandler {
        private final int arrayIndex;
        
        public ChiselStainedPaneHandler(int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }
        
        @Override
        public GlassType getType(Block block, int meta) {
            int color = (arrayIndex << 1) | (meta >> 3);
            return new GlassType(GlassType.mult_pane, true, color);
        }
        
        @Override
        public boolean shouldRemoveDrop(Block block, int meta) {
            return Config.chiselFixPaneDrops;
        }
    }
}
