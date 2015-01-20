package ljfa.glassshards.compat;

import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.SimpleGlassHandler;
import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

public class TinkersGlassHelper {
    public static void init() {
        //Fetch TConstruct's block instances
        Block tcGlass, tcPane, tcStainedGlass, tcStainedPane;
        try {
            Class<?> classModBlocks = Class.forName("tconstruct.smeltery.TinkerSmeltery");
            
            tcGlass = (Block)classModBlocks.getDeclaredField("clearGlass").get(null);
            tcPane = (Block)classModBlocks.getDeclaredField("glassPane").get(null);
            tcStainedGlass = (Block)classModBlocks.getDeclaredField("stainedGlassClear").get(null);
        } catch(Exception ex) {
            LogHelper.log(Level.ERROR, ex, "Failed to load Tinkers Construct compatibility.");
            return;
        }
        
        //Add blocks to the registry
        GlassRegistry.addHandler(tcGlass, SimpleGlassHandler.blockInstance);
        GlassRegistry.addHandler(tcPane, SimpleGlassHandler.paneInstance);
        GlassRegistry.addHandler(tcStainedGlass, SimpleGlassHandler.stainedBlockInstance);
        //Stained panes work fine without need to register them here
        
        LogHelper.info("Successfully loaded Tinkers Construct compatibility.");
    }
}
