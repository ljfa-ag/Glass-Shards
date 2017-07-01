package ljfa.glassshards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.handlers.BreakSpeedHandler;
import ljfa.glassshards.handlers.HarvestDropsHandler;
import ljfa.glassshards.items.ItemGlassShards;
import ljfa.glassshards.items.ItemGlassSword;
import ljfa.glassshards.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS,
        acceptedMinecraftVersions = "[1.12,)", updateJSON = Reference.UPDATE_JSON)
public class GlassShards {
    @Mod.Instance(Reference.MODID)
    public static GlassShards instance;
    
    public static final Logger logger = LogManager.getLogger(Reference.MODNAME);
    
    public static ToolMaterial toolMatGlass;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        Config.loadConfig(event.getSuggestedConfigurationFile());
        toolMatGlass = EnumHelper.addToolMaterial("GLASS", 2, Config.swordDurability, 4.0f, 2.0f, 5);
        toolMatGlass.setRepairItem(new ItemStack(ModItems.glass_shards, 1, 16));
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new HarvestDropsHandler());
        if(Config.incrBreakSpeed)
            MinecraftForge.EVENT_BUS.register(new BreakSpeedHandler());
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GlassRegistry.registerAll();
    }
    
    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemGlassShards());
        if(Config.enableSword)
            event.getRegistry().register(new ItemGlassSword());
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        ModItems.glass_shards.registerModels();
        if(ModItems.glass_sword != null)
            ModItems.glass_sword.registerModels();
    }
    
    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        ModRecipes.init();
    }
}
