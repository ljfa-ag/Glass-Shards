package ljfa.glassshards.compat;

import ljfa.glassshards.glass.GlassRegistry;
import ljfa.glassshards.glass.SimpleGlassHandler;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;

public class TinkersGlassHelper {
    public static void init() {
        Block tcGlass = TinkerSmeltery.clearGlass;
        Block tcPane = TinkerSmeltery.glassPane;
        Block tcStainedGlass = TinkerSmeltery.stainedGlassClear;
        
        //Add blocks to the registry
        GlassRegistry.addHandler(tcGlass, SimpleGlassHandler.clearingBlockInstance);
        GlassRegistry.addHandler(tcPane, SimpleGlassHandler.clearingPaneInstance);
        GlassRegistry.addHandler(tcStainedGlass, SimpleGlassHandler.clearingStainedBlockInstance);
        //Stained panes work fine without need to register them here
        
        LogHelper.info("Successfully loaded Tinkers Construct compatibility.");
    }
    
    public static void addSmelteryRecipe() {
        Fluid moltenGlass = TinkerSmeltery.moltenGlassFluid;
        Smeltery.addMelting(new ItemStack(ModItems.glass_shards, 1, 16),
                Blocks.glass, 0, 625, new FluidStack(moltenGlass, 1000));
    }
}
