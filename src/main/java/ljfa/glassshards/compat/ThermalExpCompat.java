package ljfa.glassshards.compat;

import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cofh.api.modhelpers.ThermalExpansionHelper;

public class ThermalExpCompat {
    public static void addRecipes() {
        //PulverizerManager.removeRecipe(new ItemStack(Blocks.glass));
        ThermalExpansionHelper.addPulverizerRecipe(3200, new ItemStack(Blocks.glass), new ItemStack(ModItems.glass_shards, 1, 16));
        ThermalExpansionHelper.addPulverizerRecipe(1600, new ItemStack(ModItems.glass_shards, 1, 16), new ItemStack(Blocks.sand));
        LogHelper.info("Successfully added Thermal Expansion Pulverizer recipes.");
    }
}
