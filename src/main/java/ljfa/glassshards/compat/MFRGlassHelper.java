package ljfa.glassshards.compat;

import static ljfa.glassshards.GlassShards.logger;

import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.SimpleGlassHandler;
import net.minecraft.block.Block;
import powercrystals.minefactoryreloaded.setup.MFRThings;

public class MFRGlassHelper {
    public static void init() {
        Block mfrStainedGlass = MFRThings.factoryGlassBlock;
        Block mfrStainedPane = MFRThings.factoryGlassPaneBlock;
        
        //Add blocks to the registry
        GlassRegistry.addHandler(mfrStainedGlass, SimpleGlassHandler.stainedBlockInstance);
        GlassRegistry.addHandler(mfrStainedPane, SimpleGlassHandler.stainedPaneInstance);
        
        logger.info("Successfully loaded MineFactory Reloaded compatibility.");
    }
}
