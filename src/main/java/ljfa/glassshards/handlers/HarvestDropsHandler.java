package ljfa.glassshards.handlers;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.compat.ModGlassHelper;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.GlassHelper;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HarvestDropsHandler {
    
    @SubscribeEvent
    public void onBlockHarvested(BlockEvent.HarvestDropsEvent event) {
        if(event.world.isRemote || event.isSilkTouching)
            return;
        
        // Sometimes the list of drops is not empty even if the drop chance is set to zero.
        // This might lead to duplication bugs.
        if(!event.drops.isEmpty()) {
            if(event.dropChance < 1e-6f) {
                event.drops.clear();
                LogHelper.info("%s has a negligible drop chance, but the drop list is not empty", event.block.getUnlocalizedName());
            }
            else if(ModGlassHelper.shouldRemoveDrop(event.block, event.blockMetadata))
                event.drops.clear();
            else
                return;
        }
        
        GlassType gtype = GlassHelper.getType(event.block, event.blockMetadata);
        if(gtype != null) {
            int meta = gtype.isStained() ? gtype.getColor() : 16;
            event.drops.add(new ItemStack(ModItems.glass_shards, 1, meta));
            
            float chance = Math.min(Config.shardsChance + event.fortuneLevel*Config.shardsFortuneChance, 1.0f);
            event.dropChance = gtype.getMultiplier() * chance;
        }
    }
}
