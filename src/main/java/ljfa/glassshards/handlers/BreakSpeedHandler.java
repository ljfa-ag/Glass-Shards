package ljfa.glassshards.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BreakSpeedHandler {

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Block block = event.getState().getBlock();
        if(event.getState().getMaterial() == Material.GLASS && block.getHarvestTool(event.getState()) == null) {
            ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
            Item tool = stack.getItem();
            if(tool instanceof ItemTool && tool.getHarvestLevel(stack, "pickaxe", event.getEntityPlayer(), event.getState()) >= 0) {
                ToolMaterial mat = ToolMaterial.valueOf(((ItemTool)tool).getToolMaterialName());
                float factor = 0.35f * mat.getEfficiencyOnProperMaterial();
                int efficiency = EnchantmentHelper.getEfficiencyModifier(event.getEntityPlayer());
                if(efficiency > 0)
                    factor *= 1.0f + 0.15f * efficiency*efficiency;
                if(factor > 1.0f)
                    event.setNewSpeed(factor * event.getOriginalSpeed());
            }
        }
    }
}
