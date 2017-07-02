package ljfa.glassshards.handlers;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.api.IShatterableGlass;
import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.ModGlassHandler;
import ljfa.glassshards.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HarvestDropsHandler {

    @SubscribeEvent
    public void onBlockHarvested(HarvestDropsEvent event) {
        if(event.getWorld().isRemote || event.isSilkTouching())
            return;

        IBlockState state = event.getState();
        Block block = state.getBlock();

        if(block instanceof IShatterableGlass) {
            addDropForType(event, ((IShatterableGlass)block).getGlassType(event.getWorld(), event.getPos(), state));
        }
        else {
            ModGlassHandler handler = GlassRegistry.get(block);
            if(handler != null) {
                if(!event.getDrops().isEmpty()) {
                    if(handler.shouldRemoveDrop(state))
                        event.getDrops().clear();
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
            float chance = gtype.getMultiplier() * getChanceFromFortune(event.getFortuneLevel());
            if(event.getWorld().rand.nextFloat() < chance) {
                int meta = gtype.isStained() ? gtype.getColor().getMetadata() : 16;
                event.getDrops().add(new ItemStack(ModItems.glass_shards, 1, meta));
            }
        }
    }

    public static float getChanceFromFortune(int fortune) {
        return (float)Math.min(Config.shardsChance + fortune*Config.shardsFortuneChance, 1.0);
    }
}
