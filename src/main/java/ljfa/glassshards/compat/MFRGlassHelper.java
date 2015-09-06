package ljfa.glassshards.compat;

import static ljfa.glassshards.GlassShards.logger;

import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.SimpleGlassHandler;

public class MFRGlassHelper {
    public static void init() {
        GlassRegistry.addHandler("MineFactoryReloaded:stainedglass.block", SimpleGlassHandler.stainedBlockInstance);
        GlassRegistry.addHandler("MineFactoryReloaded:stainedglass.pane", SimpleGlassHandler.stainedPaneInstance);
        
        logger.info("Successfully loaded MineFactory Reloaded compatibility.");
    }
}
