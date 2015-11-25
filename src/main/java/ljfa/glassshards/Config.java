package ljfa.glassshards;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {
    public static Configuration conf;
    
    public static final String CAT_GENERAL = "general";
    
    public static float shardsChance;
    public static float shardsFortuneChance;
    public static boolean recipesRecolor;
    public static boolean recipeUncolor;
    public static boolean recipesFurnace;
    public static boolean enableSword;
    public static int swordDurability;
    public static boolean incrBreakSpeed;
    
    public static void loadConfig(File file) {
        if(conf == null)
            conf = new Configuration(file);
        
        conf.load();
        loadValues();
        
        MinecraftForge.EVENT_BUS.register(new ChangeHandler());
    }
    
    public static void loadValues() {
        conf.getCategory(CAT_GENERAL).setComment("General options");
        
        shardsChance = (float)conf.get(CAT_GENERAL, "shardsChance", 0.7, "Base chance that a block of glass drops shards", 0.0, 1.0).getDouble();
        shardsFortuneChance = (float)conf.get(CAT_GENERAL, "shardsFortuneChance", 0.07, "Chance per fortune level that a block of glass drops shards", 0.0, 1.0).getDouble();
        recipesRecolor = conf.get(CAT_GENERAL, "recipesColor", true, "Add recipes for coloring shards").setRequiresMcRestart(true).getBoolean();
        recipeUncolor = conf.get(CAT_GENERAL, "recipeUncolor", true, "Add recipe to remove the color from shards").setRequiresMcRestart(true).getBoolean();
        recipesFurnace = conf.get(CAT_GENERAL, "recipesFurnace", true, "Add furnace recipes to smelt shards to glass blocks\n(if you disable this, you will probably want to add some other way to process shards)").setRequiresMcRestart(true).getBoolean();
        enableSword = conf.get(CAT_GENERAL, "enableSword", true, "Enables the glass sword").setRequiresMcRestart(true).getBoolean();
        swordDurability = conf.get(CAT_GENERAL, "swordDurability", 109, "Durability of the glass sword", 1, 1561).setRequiresMcRestart(true).getInt();
        incrBreakSpeed = conf.get(CAT_GENERAL, "increaseGlassBreakSpeed", true, "Glass breaks faster when mined with a pickaxe").setRequiresMcRestart(true).getBoolean();
        //----------------
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
