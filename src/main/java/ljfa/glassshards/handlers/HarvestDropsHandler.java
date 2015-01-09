package ljfa.glassshards.handlers;

import ljfa.glassshards.Config;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.GlassHelper;
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
        if(event.dropChance < 0.0001f)
            event.drops.clear();
        else if(!event.drops.isEmpty())
            return;
        
        GlassHelper.GlassType gtype = GlassHelper.getType(event.block, event.blockMetadata);
        if(gtype != null) {
            int meta;
            if(GlassHelper.isGlassStained(event.block, event.blockMetadata))
                meta = GlassHelper.getGlassColor(event.block, event.blockMetadata);
            else
                meta = 16;
            event.drops.add(new ItemStack(ModItems.glass_shards, 1, meta));
            
            float chance = Math.min(Config.shardsChance + event.fortuneLevel*Config.shardsFortuneChance, 1.0f);
            event.dropChance = gtype.multiplier * chance;
        }
    }
}
