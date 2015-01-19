package ljfa.glassshards.compat;

import ljfa.glassshards.Reference;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.SimpleGlassHandler;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;

public class MFRGlassHelper {
    public static void init() {
        //Fetch MFR's glass block instances
        Block mfrGlass, mfrPane;
        try {
            Class<?> classModBlocks = Class.forName("powercrystals.minefactoryreloaded.setup.MFRThings");
            
            mfrGlass = (Block)classModBlocks.getField("factoryGlassBlock").get(null);
            mfrPane = (Block)classModBlocks.getField("factoryGlassPaneBlock").get(null);
        } catch(Exception ex) {
            FMLLog.log(Reference.MODNAME, Level.ERROR, ex, "Failed to load MineFactory Reloaded compatibility.");
            return;
        }
        
        //Add blocks to the registry
        GlassRegistry.addHandler(mfrGlass, SimpleGlassHandler.stainedBlockInstance);
        GlassRegistry.addHandler(mfrPane, SimpleGlassHandler.stainedPaneInstance);
        
        LogHelper.info("Successfully loaded MineFactory Reloaded compatibility.");
    }
}
