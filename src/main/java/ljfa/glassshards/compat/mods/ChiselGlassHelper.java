package ljfa.glassshards.compat.mods;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.compat.ModGlassHandler;
import ljfa.glassshards.compat.ModGlassRegistry;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;

public class ChiselGlassHelper {
    public static void init() {
        //Fetch Chisel's block instances
        Block[] chiselStainedGlass, chiselStainedPane;
        try {
            Class<?> classModBlocks = Class.forName("com.cricketcraft.chisel.init.ModBlocks");
            
            chiselStainedGlass = (Block[])classModBlocks.getDeclaredField("stainedGlass").get(null);
            chiselStainedPane = (Block[])classModBlocks.getDeclaredField("stainedGlassPane").get(null);
        } catch(Exception ex) {
            FMLLog.log(Reference.MODNAME, Level.ERROR, ex, "Failed to load Chisel compatibility.");
            return;
        }
        
        //Add blocks to the registry
        if(Config.chiselFixPaneDrops)
            for(Block block: chiselStainedPane)
                ModGlassRegistry.addRemoveDrops(block);
        
        for(int i = 0; i < chiselStainedGlass.length; i++)
            ModGlassRegistry.addHandler(chiselStainedGlass[i], new ChiselStainedGlassHandler(i));
        
        for(int i = 0; i < chiselStainedPane.length; i++)
            ModGlassRegistry.addHandler(chiselStainedPane[i], new ChiselStainedPaneHandler(i));
        
        LogHelper.info("Successfully loaded Chisel compatibility.");
    }
    
    // How stained glass works in Chisel:
    // array index = color >> 2
    // metadata = (color & 3) << 2
    // Blame Chisel for using such hardcoded values
    public static class ChiselStainedGlassHandler extends ModGlassHandler {
        protected int arrayIndex;
        
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
        protected int arrayIndex;
        
        public ChiselStainedPaneHandler(int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }
        
        @Override
        public GlassType getType(Block block, int meta) {
            int color = (arrayIndex << 1) | (meta >> 3);
            return new GlassType(GlassType.mult_block, true, color);
        }
    }
}
