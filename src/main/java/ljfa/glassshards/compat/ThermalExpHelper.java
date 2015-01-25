package ljfa.glassshards.compat;

import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thermalexpansion.util.crafting.PulverizerManager;

public class ThermalExpHelper {
    public static void addRecipes() {
        PulverizerManager.removeRecipe(new ItemStack(Blocks.glass));
        PulverizerManager.addRecipe(3200, new ItemStack(Blocks.glass), new ItemStack(ModItems.glass_shards, 1, 16));
        PulverizerManager.addRecipe(1600, new ItemStack(ModItems.glass_shards, 1, 16), new ItemStack(Blocks.sand));
        LogHelper.info("Successfully added Thermal Expansion Pulverizer recipes.");
    }
}
