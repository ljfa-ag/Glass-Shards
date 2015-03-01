package ljfa.glassshards.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameData;

public class GlassHelper {
    /** Adds all registered blocks that are glass to the GlassRegistry */
    public static void registerAll() {
        int counter = 0;
        for(Object obj: GameData.getBlockRegistry()) {
            if(!(obj instanceof Block))
                continue;
            Block block = (Block)obj;
            if(block instanceof BlockGlass) {
                GlassRegistry.addHandler(block, SimpleGlassHandler.blockInstance);
                counter++;
            } else if(block instanceof BlockStainedGlass) {
                GlassRegistry.addHandler(block, SimpleGlassHandler.stainedBlockInstance);
                counter++;
            } else if(block instanceof BlockStainedGlassPane) {
                GlassRegistry.addHandler(block, SimpleGlassHandler.stainedPaneInstance);
                counter++;
            } else if(block instanceof BlockPane && block.getMaterial() == Material.glass) {
                GlassRegistry.addHandler(block, SimpleGlassHandler.paneInstance);
                counter++;
            }
        }
        LogHelper.info("Added %d blocks to the GlassRegistry", counter);
    }
}
