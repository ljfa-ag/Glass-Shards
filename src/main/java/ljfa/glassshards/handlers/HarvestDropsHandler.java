package ljfa.glassshards.handlers;

import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.GlassType;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HarvestDropsHandler {
    
    @SubscribeEvent
    public void onBlockHarvested(BlockEvent.HarvestDropsEvent event) {
        if(event.world.isRemote || event.isSilkTouching)
            return;
        
        // Sometimes the list of drops is not empty even if the drop chance is set to zero.
        // This might lead to duplication bugs.
        if(!event.drops.isEmpty()) {
            if(event.dropChance < 0.0001f) {
                event.drops.clear();
                LogHelper.info("%s has a negligible drop chance, but the drop list is not empty",
                        event.block.getUnlocalizedName());
            }
            else
                return;
        }
        
        GlassType gtype = GlassType.getType(event.block, event.blockMetadata);
        if(gtype != null) {
            int meta;
            if(gtype.isStained)
                meta = gtype.color;
            else
                meta = 16;
            event.drops.add(new ItemStack(ModItems.glass_shards, 1, meta));
            
            float chance = Math.min(Config.shardsChance + event.fortuneLevel*Config.shardsFortuneChance, 1.0f);
            event.dropChance = gtype.multiplier * chance;
        }
    }
}
