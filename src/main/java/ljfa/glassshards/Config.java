package ljfa.glassshards;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {
    public static Configuration conf;
    
    public static final String CATEGORY_GENERAL = "general";
    
    public static float shardsChance;
    public static float shardsFortuneChance;
    public static boolean recipesRecolor;
    public static boolean recipeUncolor;
    
    public static void loadConfig(File file) {
        if(conf == null)
            conf = new Configuration(file);
        
        conf.load();
        loadValues();
        
        FMLCommonHandler.instance().bus().register(new ChangeHandler());
    }
    
    public static void loadValues() {
        conf.getCategory(CATEGORY_GENERAL).setComment("General options");
        
        shardsChance = (float)conf.get(CATEGORY_GENERAL, "shardsChance", 0.66, "Base chance that a block of glass drops shards", 0.0, 1.0).getDouble();
        shardsFortuneChance = (float)conf.get(CATEGORY_GENERAL, "shardsFortuneChance", 0.08, "Chance per fortune level that a block of glass drops shards", 0.0, 1.0).getDouble();
        recipesRecolor = conf.get(CATEGORY_GENERAL, "recipesColor", true, "Add recipes for coloring shards").setRequiresMcRestart(true).getBoolean();
        recipeUncolor = conf.get(CATEGORY_GENERAL, "recipeUncolor", true, "Add recipe to remove the color from shards").setRequiresMcRestart(true).getBoolean();
        
        if(conf.hasChanged())
            conf.save();
    }
    
    /** Reloads the config values upon change */
    public static class ChangeHandler {
        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.modID.equals(Reference.MODID))
                loadValues();
        }
    }
}
