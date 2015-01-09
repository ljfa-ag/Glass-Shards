package ljfa.glassshards;

import ljfa.glassshards.compat.ChiselGlassHelper;
import ljfa.glassshards.handlers.HarvestDropsHandler;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.LogHelper;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class GlassShards {
    @Mod.Instance(Reference.MODID)
    public static GlassShards instance;
    
    /*@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;*/
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
        ModItems.init();
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        MinecraftForge.EVENT_BUS.register(new HarvestDropsHandler());
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        try {
            chiselHelper = new ChiselGlassHelper();
            LogHelper.info("Successfully loaded Chisel compatibility.");
        } catch(Exception ex) {
            FMLLog.log(Reference.MODID, Level.INFO, ex, "Failed to load Chisel compatibility. Chisel is probably not present.");
        }
    }
    
    public static ChiselGlassHelper chiselHelper = null;
}
