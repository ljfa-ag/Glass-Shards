package ljfa.glassshards.compat;

import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cofh.api.modhelpers.ThermalExpansionHelper;

public class ThermalExpCompat {
    public static void addRecipes() {
        ThermalExpansionHelper.addPulverizerRecipe(2800, new ItemStack(Blocks.glass), new ItemStack(ModItems.glass_shards, 1, 16));
        ThermalExpansionHelper.addPulverizerRecipe(1600, new ItemStack(ModItems.glass_shards, 1, 16), new ItemStack(Blocks.sand));
        
        for(int i = 0; i < 16; i++) {
            ThermalExpansionHelper.addPulverizerRecipe(2800, new ItemStack(Blocks.stained_glass, 1, i), new ItemStack(ModItems.glass_shards, 1, i));
            ThermalExpansionHelper.addPulverizerRecipe(1600, new ItemStack(ModItems.glass_shards, 1, i), new ItemStack(Blocks.sand));
        }
        
        LogHelper.info("Successfully added Thermal Expansion Pulverizer recipes.");
    }
}
