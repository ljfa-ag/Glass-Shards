package ljfa.glassshards.compat;

import ljfa.glassshards.Reference;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.SimpleGlassHandler;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;

public class TinkersGlassHelper {
    public static void init() {
        //Fetch TConstruct's block instances
        Block tcGlass, tcPane, tcStainedGlass, tcStainedPane;
        try {
            Class<?> classModBlocks = Class.forName("tconstruct.smeltery.TinkerSmeltery");
            
            tcGlass = (Block)classModBlocks.getDeclaredField("clearGlass").get(null);
            tcPane = (Block)classModBlocks.getDeclaredField("glassPane").get(null);
            tcStainedGlass = (Block)classModBlocks.getDeclaredField("stainedGlassClear").get(null);
            tcStainedPane = (Block)classModBlocks.getDeclaredField("stainedGlassClearPane").get(null);
        } catch(Exception ex) {
            FMLLog.log(Reference.MODNAME, Level.ERROR, ex, "Failed to load Tinkers Construct compatibility.");
            return;
        }
        
        //Add blocks to the registry
        GlassRegistry.addRemoveDrops(tcGlass);
        GlassRegistry.addRemoveDrops(tcPane);
        GlassRegistry.addRemoveDrops(tcStainedGlass);
        //Don't need to add stained panes here since they already drop nothing
        
        GlassRegistry.addHandler(tcGlass, SimpleGlassHandler.blockInstance);
        GlassRegistry.addHandler(tcPane, SimpleGlassHandler.paneInstance);
        GlassRegistry.addHandler(tcStainedGlass, SimpleGlassHandler.stainedBlockInstance);
        GlassRegistry.addHandler(tcStainedPane, SimpleGlassHandler.stainedPaneInstance);
        
        LogHelper.info("Successfully loaded Tinkers Construct compatibility.");
    }
}
