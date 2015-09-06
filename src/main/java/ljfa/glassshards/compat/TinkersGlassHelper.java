package ljfa.glassshards.compat;

import static ljfa.glassshards.GlassShards.logger;

import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.SimpleGlassHandler;
import ljfa.glassshards.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.crafting.Smeltery;

public class TinkersGlassHelper {
    public static void init() {
        //Add blocks to the registry
        GlassRegistry.addHandler("TConstruct:GlassBlock", SimpleGlassHandler.clearingBlockInstance);
        GlassRegistry.addHandler("TConstruct:GlassPane", SimpleGlassHandler.clearingPaneInstance);
        GlassRegistry.addHandler("TConstruct:GlassBlock.StainedClear", SimpleGlassHandler.clearingStainedBlockInstance);
        //Stained panes work fine without need to register them here
        
        logger.info("Successfully loaded Tinkers Construct compatibility.");
    }
    
    public static void addSmelteryRecipe() {
        Fluid moltenGlass = FluidRegistry.getFluid("glass.molten");
        Smeltery.addMelting(new ItemStack(ModItems.glass_shards, 1, 16),
                Blocks.glass, 0, 625, new FluidStack(moltenGlass, 1000));
    }
}
