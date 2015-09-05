package ljfa.glassshards.compat;

import static ljfa.glassshards.GlassShards.logger;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.ModGlassHandler;
import ljfa.glassshards.util.ReflectionHelper;
import net.minecraft.block.Block;

public class ChiselGlassHelper {
    private static Class<?> chiselBlocks;
    
    static {
        chiselBlocks = ReflectionHelper.tryGetClass("com.cricketcraft.chisel.init.ChiselBlocks");
        if(chiselBlocks != null)
            logger.debug("We have Cricket's Chisel version");
        else {
            chiselBlocks = ReflectionHelper.tryGetClass("team.chisel.init.ChiselBlocks");
            if(chiselBlocks != null)
                logger.debug("We have minecreatr's Chisel version");
            else
                throw new LinkageError("Could not load Chisel compatibility: The ChiselBlocks class could not be found");
        }
    }
    
    public static Class<?> getChiselBlocks() {
        return chiselBlocks;
    }
    
    public static void init() {
        try {
            Block[] chiselStainedGlass = ReflectionHelper.getStaticField(chiselBlocks, "stainedGlass");
            Block[] chiselStainedPane = ReflectionHelper.getStaticField(chiselBlocks, "stainedGlassPane");
            
            //Add blocks to the registry
            for(int i = 0; i < chiselStainedGlass.length; i++)
                GlassRegistry.addHandler(chiselStainedGlass[i], new ChiselStainedGlassHandler(i));
            
            for(int i = 0; i < chiselStainedPane.length; i++)
                GlassRegistry.addHandler(chiselStainedPane[i], new ChiselStainedPaneHandler(i));
            
            logger.info("Successfully loaded Chisel compatibility.");
        }
        catch(Exception e) {
            throw new RuntimeException("Could not load Chisel compatibility.\n"
                + "The ChiselBlocks class was " + chiselBlocks.getName(), e);
        }
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
