package ljfa.glassshards.handlers;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.api.IShatterableGlass;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.ModGlassHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HarvestDropsHandler {

    @SubscribeEvent
    public void onBlockHarvested(HarvestDropsEvent event) {
        if(event.world.isRemote || event.isSilkTouching)
            return;

        IBlockState state = event.state;
        Block block = state.getBlock();

        if(block instanceof IShatterableGlass) {
            addDropForType(event, ((IShatterableGlass)block).getType(state));
        }
        else {
            ModGlassHandler handler = GlassRegistry.get(block);
            if(handler != null) {
                if(!event.drops.isEmpty()) {
                    if(handler.shouldRemoveDrop(state))
                        event.drops.clear();
                    else
                        return;
                }
                handler.addShardsDrop(event);
            }
        }
    }

    /** With the correct probability, adds shards corresponding to the type to the drop list */
    public static void addDropForType(HarvestDropsEvent event, GlassType gtype) {
        if(gtype != null) {
            float chance = gtype.getMultiplier() * getChanceFromFortune(event.fortuneLevel);
            if(event.world.rand.nextFloat() <= chance) {
                int meta = gtype.isStained() ? gtype.getColor().getMetadata() : 16;
                event.drops.add(new ItemStack(ModItems.glass_shards, 1, meta));
            }
        }
    }

    public static float getChanceFromFortune(int fortune) {
        return Math.min(Config.shardsChance + fortune*Config.shardsFortuneChance, 1.0f);
    }
}
