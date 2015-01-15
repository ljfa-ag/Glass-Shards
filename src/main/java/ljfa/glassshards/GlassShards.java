package ljfa.glassshards;

import ljfa.glassshards.handlers.HarvestDropsHandler;
import ljfa.glassshards.items.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
        
        /*if(Config.renderTransparent && event.getSide() == Side.CLIENT)
            MinecraftForgeClient.registerItemRenderer(ModItems.glass_shards, new TransparentItemRenderer());*/
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        /*if(Config.chiselEnable && Loader.isModLoaded("chisel")) {
            try {
                chiselHelper = new ChiselGlassHelper();
                LogHelper.info("Successfully loaded Chisel compatibility.");
            } catch(Exception ex) {
                FMLLog.log(Reference.MODNAME, Level.ERROR, ex, "Failed to load Chisel compatibility.");
            }
        }*/
    }
    
    //public static ChiselGlassHelper chiselHelper = null;
}
