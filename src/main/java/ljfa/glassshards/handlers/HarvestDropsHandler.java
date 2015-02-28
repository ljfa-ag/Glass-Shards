package ljfa.glassshards.handlers;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.api.IShatterableGlass;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.ModGlassHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HarvestDropsHandler {

    @SubscribeEvent
    public void onBlockHarvested(HarvestDropsEvent event) {
        if(event.world.isRemote || event.isSilkTouching)
            return;
        
        Block block = event.block;
        int meta = event.blockMetadata;

        if(block instanceof IShatterableGlass) {
            addShardsDrop(event, ((IShatterableGlass)block).getType(block, meta));
        }
        else {
            ModGlassHandler handler = GlassRegistry.get(event.block);
            if(handler != null) {
                // Sometimes the list of drops is not empty even if the drop chance is set to zero.
                // This might lead to duplication bugs.
                if(!event.drops.isEmpty()) {
                    if(event.dropChance < 1e-6f || handler.shouldRemoveDrop(block, meta))
                        event.drops.clear();
                    else
                        return;
                }

                addShardsDrop(event, handler.getType(block, meta));
            }
        }
    }

    private void addShardsDrop(HarvestDropsEvent event, GlassType gtype) {
        if(gtype != null) {
            event.dropChance = 1.0f;
            
            float chance = gtype.getMultiplier() * Math.min(Config.shardsChance + event.fortuneLevel*Config.shardsFortuneChance, 1.0f);
            if(event.world.rand.nextFloat() <= chance) {
                int meta = gtype.isStained() ? gtype.getColor() : 16;
                event.drops.add(new ItemStack(ModItems.glass_shards, 1, meta));
            }
        }
    }
}
