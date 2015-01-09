package ljfa.glassshards.compat;

import ljfa.glassshards.util.GlassType;
import net.minecraft.block.Block;

public class ChiselGlassHelper {
    public ChiselGlassHelper() throws ClassNotFoundException {
        classGlass = Class.forName("com.cricketcraft.chisel.block.BlockCarvableGlass");
        classPane = null;
    }
    
    public boolean comesFrom(Block block, int meta) {
        return classGlass.isInstance(block);
    }
    
    public GlassType getType(Block block, int meta) {
        return null;
    }
    
    private Class<?> classGlass;
    private Class<?> classPane;
}
