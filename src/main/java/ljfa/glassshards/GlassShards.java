package ljfa.glassshards;

import ljfa.glassshards.compat.ChiselGlassHelper;
import ljfa.glassshards.compat.TinkersGlassHelper;
import ljfa.glassshards.handlers.HarvestDropsHandler;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.render.TransparentItemRenderer;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.SimpleGlassHandler;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class GlassShards {
    @Mod.Instance(Reference.MODID)
    public static GlassShards instance;
    
    /*@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;*/
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
        ModItems.preInit();
        
        GlassRegistry.addHandler(Blocks.glass, SimpleGlassHandler.blockInstance);
        GlassRegistry.addHandler(Blocks.glass_pane, SimpleGlassHandler.paneInstance);
        GlassRegistry.addHandler(Blocks.stained_glass, SimpleGlassHandler.stainedBlockInstance);
        GlassRegistry.addHandler(Blocks.stained_glass_pane, SimpleGlassHandler.stainedPaneInstance);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        MinecraftForge.EVENT_BUS.register(new HarvestDropsHandler());
        
        if(Config.renderTransparent && event.getSide() == Side.CLIENT)
            MinecraftForgeClient.registerItemRenderer(ModItems.glass_shards, new TransparentItemRenderer());
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        initCompatModules();
    }
    
    private void initCompatModules() {
        if(Config.chiselEnable && Loader.isModLoaded("chisel"))
            ChiselGlassHelper.init();
        if(Config.tinkersEnable && Loader.isModLoaded("TConstruct"))
            TinkersGlassHelper.init();
    }
}
