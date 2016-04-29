package ljfa.glassshards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.handlers.BreakSpeedHandler;
import ljfa.glassshards.handlers.HarvestDropsHandler;
import ljfa.glassshards.items.ModItems;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS,
        acceptedMinecraftVersions = "[1.9,1.10)", updateJSON = Reference.UPDATE_JSON)
public class GlassShards {
    @Mod.Instance(Reference.MODID)
    public static GlassShards instance;
    
    public static final Logger logger = LogManager.getLogger(Reference.MODNAME);
    
    public static ToolMaterial toolMatGlass;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
        toolMatGlass = EnumHelper.addToolMaterial("GLASS", 2, Config.swordDurability, 4.0f, 2.0f, 5);
        ModItems.init();
        toolMatGlass.setRepairItem(new ItemStack(ModItems.glass_shards, 1, 16));
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        MinecraftForge.EVENT_BUS.register(new HarvestDropsHandler());
        if(Config.incrBreakSpeed)
            MinecraftForge.EVENT_BUS.register(new BreakSpeedHandler());
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GlassRegistry.registerAll();
        initCompatModules();
    }

    /** Initialize compatibility with other mods */
    private static void initCompatModules() {
    }
}
