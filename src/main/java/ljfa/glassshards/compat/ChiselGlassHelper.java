package ljfa.glassshards.compat;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;

public class ChiselGlassHelper {
    public static void init() {
        Block[] chiselStainedGlass, chiselStainedPane;
        try {
            Class<?> classModBlocks = Class.forName("com.cricketcraft.chisel.init.ModBlocks");
            chiselStainedGlass = (Block[])classModBlocks.getDeclaredField("stainedGlass").get(null);
            chiselStainedPane = (Block[])classModBlocks.getDeclaredField("stainedGlassPane").get(null);
        } catch(Exception ex) {
            FMLLog.log(Reference.MODNAME, Level.ERROR, ex, "Failed to load Chisel compatibility.");
            return;
        }
        
        if(Config.chiselFixPaneDrops)
            for(Block b: chiselStainedPane)
                ModGlassRegistry.removeDropsSet.add(b);
        
        for(int i = 0; i < chiselStainedGlass.length; i++)
            ModGlassRegistry.helperMap.put(chiselStainedGlass[i], new ChiselStainedGlassHandler(i));
        
        for(int i = 0; i < chiselStainedPane.length; i++)
            ModGlassRegistry.helperMap.put(chiselStainedPane[i], new ChiselStainedPaneHandler(i));
        
        LogHelper.info("Successfully loaded Chisel compatibility.");
    }
    
    public static class ChiselStainedGlassHandler extends ModGlassHandler {
        protected int arrayIndex;
        
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
    
    public static class ChiselStainedPaneHandler extends ModGlassHandler {
        protected int arrayIndex;
        
        public ChiselStainedPaneHandler(int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }
        
        @Override
        public GlassType getType(Block block, int meta) {
            // How stained panes work in Chisel:
            // array index = color >> 1
            // metadata = (color & 1) << 3
            int color = (arrayIndex << 1) | (meta >> 3);
            return new GlassType(GlassType.mult_block, true, color);
        }
    }
}
