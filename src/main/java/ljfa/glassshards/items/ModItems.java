package ljfa.glassshards.items;

import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    public static ItemGlassShards glass_shards;
    public static ItemGlassSword glass_sword;
    
    public static void init() {
        glass_shards = new ItemGlassShards();
        if(Config.enableSword)
            glass_sword = new ItemGlassSword();
    }
    
    /** Sets the item's name and registers it */
    public static <T extends Item> T register(T item, String name) {
        item.setUnlocalizedName(Reference.MODID + ":" + name);
        GameRegistry.registerItem(item, name);
        return item;
    }
}
