package ljfa.glassshards.items;

import ljfa.glassshards.Config;
import ljfa.glassshards.GlassShards;
import ljfa.glassshards.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static ItemGlassShards glass_shards;
    public static ItemSword glass_sword;
    
    public static void init() {
        glass_shards = new ItemGlassShards();
        if(Config.enableSword)
            glass_sword = register(new ItemSword(GlassShards.toolMatGlass), "glass_sword");
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        
        glass_shards.registerModels(mesher);
        if(glass_sword != null)
            mesher.register(glass_sword, 0, new ModelResourceLocation(Reference.MODID + ":glass_sword", "inventory"));
    }
    
    /** Sets the item's name and registers it */
    public static <T extends Item> T register(T item, String name) {
        item.setUnlocalizedName(Reference.MODID + ":" + name);
        GameRegistry.registerItem(item, name);
        return item;
    }
}
