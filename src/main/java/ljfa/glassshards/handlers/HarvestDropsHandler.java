package ljfa.glassshards.handlers;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.api.IShatterableGlass;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.GlassRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HarvestDropsHandler {
    
    @SubscribeEvent
    public void onBlockHarvested(HarvestDropsEvent event) {
        if(event.world.isRemote || event.isSilkTouching)
            return;
        
        if(event.block instanceof IShatterableGlass) {
            addShardsDrop(event, ((IShatterableGlass)event.block).getType(event.block, event.blockMetadata));
        }
        else { 
            // Sometimes the list of drops is not empty even if the drop chance is set to zero.
            // This might lead to duplication bugs.
            if(!event.drops.isEmpty()) {
                if(event.dropChance < 1e-6f || GlassRegistry.shouldRemoveDrop(event.block, event.blockMetadata))
                    event.drops.clear();
                else
                    return;
            }
        
            addShardsDrop(event, GlassRegistry.getType(event.block, event.blockMetadata));
        }
    }
    
    private void addShardsDrop(HarvestDropsEvent event, GlassType gtype) {
        if(gtype != null) {
            int meta = gtype.isStained() ? gtype.getColor() : 16;
            event.drops.add(new ItemStack(ModItems.glass_shards, 1, meta));
            
            float chance = Math.min(Config.shardsChance + event.fortuneLevel*Config.shardsFortuneChance, 1.0f);
            event.dropChance = gtype.getMultiplier() * chance;
        }
    }
}
