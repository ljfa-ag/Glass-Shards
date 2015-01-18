package ljfa.glassshards.compat.mods;

import ljfa.glassshards.Reference;
import ljfa.glassshards.compat.ModGlassRegistry;
import ljfa.glassshards.compat.SimpleGlassHandler;
import ljfa.glassshards.util.LogHelper;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;

public class TinkersGlassHelper {
    public static void init() {
        //Fetch TConstruct's block instances
        Block tcGlass, tcStainedGlass, tcPane;
        try {
            Class<?> classModBlocks = Class.forName("tconstruct.smeltery.TinkerSmeltery");
            
            tcGlass = (Block)classModBlocks.getDeclaredField("clearGlass").get(null);
            tcStainedGlass = (Block)classModBlocks.getDeclaredField("stainedGlassClear").get(null);
            tcPane = (Block)classModBlocks.getDeclaredField("glassPane").get(null);
        } catch(Exception ex) {
            FMLLog.log(Reference.MODNAME, Level.ERROR, ex, "Failed to load Tinkers Construct compatibility.");
            return;
        }
        
        //Add blocks to the registry
        ModGlassRegistry.addRemoveDrops(tcGlass);
        ModGlassRegistry.addRemoveDrops(tcStainedGlass);
        ModGlassRegistry.addRemoveDrops(tcPane);
        
        ModGlassRegistry.addHandler(tcGlass, SimpleGlassHandler.blockInstance);
        ModGlassRegistry.addHandler(tcStainedGlass, SimpleGlassHandler.stainedBlockInstance);
        ModGlassRegistry.addHandler(tcPane, SimpleGlassHandler.paneInstance);
        
        LogHelper.info("Successfully loaded Tinkers Construct compatibility.");
    }
}
