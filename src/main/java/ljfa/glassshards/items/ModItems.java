package ljfa.glassshards.items;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Level;

import ljfa.glassshards.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static ItemGlassShards glass_shards;
    
    public static void init() {
        glass_shards = register(new ItemGlassShards(), "glass_shards");
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        glass_shards.registerModels();
    }
    
    /** Sets the item's name and registers it */
    public static <T extends Item> T register(T item, String name) {
        item.setUnlocalizedName(Reference.MODID + ":" + name);
        GameRegistry.registerItem(item, name);
        return item;
    }
}
