package ljfa.glassshards.util;

import cpw.mods.fml.common.registry.GameData;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.api.IShatterableGlass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;

public class GlassHelper {
    /** Checks if the block is some kind of glass
     * @return A GlassType representing the type, or null if it is not glass
     */
    public static GlassType getType(Block block, int meta) {
        GlassType gtype;
        if(block instanceof IShatterableGlass) {
            gtype = ((IShatterableGlass)block).getType(block, meta);
            if(gtype != null)
                return gtype;
        } else {
            gtype = GlassRegistry.getType(block, meta);
            if(gtype != null)
                return gtype;
        }
        return null;
    }

    /** Adds all registered blocks that are glass to the GlassRegistry */
    public static void registerAll() {
        int counter = 0;
        for(Object obj: GameData.getBlockRegistry()) {
            if(!(obj instanceof Block))
                continue;
            Block block = (Block)obj;
            if(block instanceof BlockGlass)
                GlassRegistry.addHandler(block, SimpleGlassHandler.blockInstance);
            else if(block instanceof BlockStainedGlass)
                GlassRegistry.addHandler(block, SimpleGlassHandler.stainedBlockInstance);
            else if(block instanceof BlockStainedGlassPane)
                GlassRegistry.addHandler(block, SimpleGlassHandler.stainedPaneInstance);
            else if(block instanceof BlockPane && block.getMaterial() == Material.glass)
                GlassRegistry.addHandler(block, SimpleGlassHandler.paneInstance);
            else
                continue;
            counter++;
        }
        LogHelper.info("Added %d blocks to the GlassRegistry", counter);
    }
}
