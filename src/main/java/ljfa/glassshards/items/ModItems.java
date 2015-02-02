package ljfa.glassshards.items;

import ljfa.glassshards.Config;
import ljfa.glassshards.GlassShards;
import ljfa.glassshards.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static ItemGlassShards glass_shards;
    public static ItemSword glass_sword;
    
    public static void preInit() {
        glass_shards = register(new ItemGlassShards(), "glass_shards");
        if(Config.enableSword)
            glass_sword = register(new ItemGlassSword(), "glass_sword");
    }
    
    /** Sets the item's name and texture and registers it */
    public static <T extends Item> T register(T item, String name, String imageName) {
        item.setUnlocalizedName(Reference.MODID + ":" + name)
        .setTextureName(Reference.MODID + ":" + imageName);
        GameRegistry.registerItem(item, name);
        return item;
    }
    
    /** Sets the item's name and texture and registers it */
    public static <T extends Item> T register(T item, String name) {
        return register(item, name, name);
    }
}
