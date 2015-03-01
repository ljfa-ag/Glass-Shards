package ljfa.glassshards.compat;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.ModGlassHandler;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;

import com.cricketcraft.chisel.block.BlockCarvableGlass;
import com.cricketcraft.chisel.block.BlockCarvablePane;
import com.cricketcraft.chisel.init.ChiselBlocks;

public class ChiselGlassHelper {
    public static void init() {
        BlockCarvableGlass[] chiselStainedGlass = ChiselBlocks.stainedGlass;
        BlockCarvablePane[] chiselStainedPane = ChiselBlocks.stainedGlassPane;
        
        //Add blocks to the registry
        for(int i = 0; i < chiselStainedGlass.length; i++)
            GlassRegistry.addHandler(chiselStainedGlass[i], new ChiselStainedGlassHandler(i));
        
        for(int i = 0; i < chiselStainedPane.length; i++)
            GlassRegistry.addHandler(chiselStainedPane[i], new ChiselStainedPaneHandler(i));
        
        LogHelper.info("Successfully loaded Chisel compatibility.");
    }
    
    // How stained glass works in Chisel:
    // array index = color >> 2
    // metadata = (color & 3) << 2
    // Blame Chisel for using such hardcoded values
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
