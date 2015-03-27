package ljfa.glassshards.handlers;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BreakSpeedHandler {
    
    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if(event.block.getMaterial() == Material.glass && event.block.getHarvestTool(event.metadata) == null) {
            ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
            if(stack == null)
                return;
            Item tool = stack.getItem();
            if(tool instanceof ItemTool && tool.getHarvestLevel(stack, "pickaxe") >= 0) {
                float factor = 0.4f * ((ItemTool)tool).func_150913_i().getEfficiencyOnProperMaterial();
                int efficiency = EnchantmentHelper.getEfficiencyModifier(event.entityPlayer);
                if(efficiency > 0)
                    factor *= 1.0f + 0.15f * efficiency*efficiency;
                if(factor > 1.0f)
                    event.newSpeed = factor * event.originalSpeed;
            }
        }
    }
}
