package ljfa.glassshards.compat;

import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.SimpleGlassHandler;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;

public class MFRGlassHelper {
    public static void init() {
        Block mfrStainedGlass = powercrystals.minefactoryreloaded.setup.MFRThings.factoryGlassBlock;
        Block mfrStainedPane = powercrystals.minefactoryreloaded.setup.MFRThings.factoryGlassPaneBlock;
        
        //Add blocks to the registry
        GlassRegistry.addHandler(mfrStainedGlass, SimpleGlassHandler.stainedBlockInstance);
        GlassRegistry.addHandler(mfrStainedPane, SimpleGlassHandler.stainedPaneInstance);
        
        LogHelper.info("Successfully loaded MineFactory Reloaded compatibility.");
    }
}
