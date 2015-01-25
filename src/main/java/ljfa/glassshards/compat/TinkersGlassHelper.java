package ljfa.glassshards.compat;

import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.SimpleGlassHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

public class TinkersGlassHelper {
    public static void init() {
        Block tcGlass = tconstruct.smeltery.TinkerSmeltery.clearGlass;
        Block tcPane = tconstruct.smeltery.TinkerSmeltery.glassPane;
        Block tcStainedGlass = tconstruct.smeltery.TinkerSmeltery.stainedGlassClear;
        
        //Add blocks to the registry
        GlassRegistry.addHandler(tcGlass, SimpleGlassHandler.blockInstance);
        GlassRegistry.addHandler(tcPane, SimpleGlassHandler.paneInstance);
        GlassRegistry.addHandler(tcStainedGlass, SimpleGlassHandler.stainedBlockInstance);
        //Stained panes work fine without need to register them here
        
        LogHelper.info("Successfully loaded Tinkers Construct compatibility.");
    }
    
    public static void addSmelteryRecipe() {
        Fluid moltenGlass = tconstruct.smeltery.TinkerSmeltery.moltenGlassFluid;
        tconstruct.library.crafting.Smeltery.addMelting(new ItemStack(ModItems.glass_shards, 1, 16),
                Blocks.glass, 0, 625, new FluidStack(moltenGlass, 1000));
    }
}
