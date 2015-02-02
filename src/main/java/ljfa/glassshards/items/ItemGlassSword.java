package ljfa.glassshards.items;

import ljfa.glassshards.GlassShards;
import net.minecraft.item.ItemSword;

public class ItemGlassSword extends ItemSword {
    public ItemGlassSword() {
        super(GlassShards.toolMatGlass);
        ModItems.register(this, "glass_sword");
    }

}
