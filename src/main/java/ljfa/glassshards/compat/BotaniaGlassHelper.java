package ljfa.glassshards.compat;

import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.SimpleGlassHandler;
import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

public class BotaniaGlassHelper {
    public static void init() {
        //Fetch Botania's block instance
        Block managlass;
        try {
            Class<?> classModBlocks = Class.forName("vazkii.botania.common.block.ModBlocks");
            managlass = (Block)classModBlocks.getDeclaredField("manaGlass").get(null);
        } catch(Exception ex) {
            LogHelper.log(Level.ERROR, ex, "Failed to load Botania glass compatibility.");
            return;
        }
        
        //Add block to the registry
        GlassRegistry.addHandler(managlass, SimpleGlassHandler.blockInstance);
        
        LogHelper.info("Successfully loaded Botania glass compatibility.");
    }
}
