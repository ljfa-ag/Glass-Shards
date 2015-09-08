package ljfa.glassshards;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config {
    public static Configuration conf;
    
    public static final String CAT_GENERAL = "general";
    public static final String CAT_CHISEL = "chisel";
    public static final String CAT_TCONSTRUCT = "tconstruct";
    public static final String CAT_MFR = "mfr";
    public static final String CAT_ENDERIO = "enderio";
    public static final String CAT_THERMALEXP = "thermalexp";
    public static final String CAT_THAUMCRAFT = "thaumcraft";
    
    public static float shardsChance;
    public static float shardsFortuneChance;
    public static boolean recipesRecolor;
    public static boolean recipeUncolor;
    public static boolean renderTransparent;
    public static boolean enableSword;
    public static int swordDurability;
    public static boolean incrBreakSpeed;
    
    public static boolean chiselEnable;
    public static boolean chiselFixPaneDrops;
    
    public static boolean tinkersEnable;
    public static boolean tinkersMeltShards;
    
    public static boolean mfrEnable;
    
    public static boolean eioDropShards;
    public static boolean eioSagMill;
    public static boolean eioAlloySmelter;
    
    public static boolean tePulverizer;
    
    public static boolean thaumAspects;
    
    public static void loadConfig(File file) {
        if(conf == null)
            conf = new Configuration(file);
        
        conf.load();
        loadValues();
        
        FMLCommonHandler.instance().bus().register(new ChangeHandler());
    }
    
    public static void loadValues() {
        conf.getCategory(CAT_GENERAL).setComment("General options");
        
        shardsChance = (float)conf.get(CAT_GENERAL, "shardsChance", 0.7, "Base chance that a block of glass drops shards", 0.0, 1.0).getDouble();
        shardsFortuneChance = (float)conf.get(CAT_GENERAL, "shardsFortuneChance", 0.07, "Chance per fortune level that a block of glass drops shards", 0.0, 1.0).getDouble();
        recipesRecolor = conf.get(CAT_GENERAL, "recipesColor", true, "Add recipes for coloring shards").setRequiresMcRestart(true).getBoolean();
        recipeUncolor = conf.get(CAT_GENERAL, "recipeUncolor", true, "Add recipe to remove the color from shards").setRequiresMcRestart(true).getBoolean();
        renderTransparent = conf.get(CAT_GENERAL, "renderTransparent", true, "The shards will be transparent when lying on the ground or held in the hand (fancy graphics only)").setRequiresMcRestart(true).getBoolean();
        enableSword = conf.get(CAT_GENERAL, "enableSword", true, "Enables the glass sword").setRequiresMcRestart(true).getBoolean();
        swordDurability = conf.get(CAT_GENERAL, "swordDurability", 109, "Durability of the glass sword", 1, 1561).setRequiresMcRestart(true).getInt();
        incrBreakSpeed = conf.get(CAT_GENERAL, "increaseGlassBreakSpeed", true, "Glass breaks faster when mined with a pickaxe").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CAT_CHISEL).setComment("Compatibility options for Chisel");
        
        chiselEnable = conf.get(CAT_CHISEL, "dropShards", true, "Activates compatibility if Chisel is present.\n"
                + "Note that not activating this even though Chisel is present might lead to unexpected behavior.\n"
                + "Deactivating this doesn't mean Chisel glass will not drop shards. The shards will just not be stained.").setRequiresMcRestart(true).getBoolean();
        chiselFixPaneDrops = conf.get(CAT_CHISEL, "fixStainedPanesDrops", true,
                "By default, Chisel stained glass panes are behaving inconsistently as in they drop themselves\n"
                + "when broken, unlike all the other Chisel glass types.\n"
                + "This option changes this behavior and makes them drop shards instead.").getBoolean();
        //----------------
        conf.getCategory(CAT_TCONSTRUCT).setComment("Compatibility options for Tinkers Construct");
        
        tinkersEnable = conf.get(CAT_TCONSTRUCT, "dropShards", false, "Clear Glass and Stained Glass will drop shards rather than themselves").setRequiresMcRestart(true).getBoolean();
        tinkersMeltShards = conf.get(CAT_TCONSTRUCT, "addSmelteryRecipe", true, "Makes shards smeltable in the Smeltery").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CAT_MFR).setComment("Compatibility options for MineFactory Reloaded");
        
        mfrEnable = conf.get(CAT_MFR, "activate", true, "Activates compatibility if MineFactory Reloaded is present.\n"
                + "When activated, stained glass blocks will drop stained shards.").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CAT_ENDERIO).setComment("Compatibility options for EnderIO");
        
        eioDropShards = conf.get(CAT_ENDERIO, "dropShards", true, "Quite Clear Glass will drop shards and Enlightened Clear Glass will drop shards and 4 glowstone").setRequiresMcRestart(true).getBoolean();
        eioSagMill = conf.get(CAT_ENDERIO, "addSAGMillRecipes", true, "Adds SAG Mill recipes for Glass -> Shards and Shards -> Sand.\n"
                + "This will replace the Glass -> Sand recipe.").setRequiresMcRestart(true).getBoolean();
        eioAlloySmelter = conf.get(CAT_ENDERIO, "addAlloySmelterRecipes", true, "Adds an Alloy Smelter recipe for Shards -> Quite Clear Glass").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CAT_THERMALEXP).setComment("Compatibility options for Thermal Expansion");
        
        tePulverizer = conf.get(CAT_THERMALEXP, "addPulverizerRecipes", true, "Adds Pulverizer recipes for Glass -> Shards and Shards -> Sand.\n"
                + "This will replace the Glass -> Sand recipe.").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CAT_THAUMCRAFT).setComment("Compatibility options for Thaumcraft");

        thaumAspects = conf.get(CAT_THAUMCRAFT, "addAspects", true, "Adds Thaumcraft aspects to the shards").setRequiresMcRestart(true).getBoolean();
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
