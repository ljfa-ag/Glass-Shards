package ljfa.glassshards.compat;

import ljfa.glassshards.util.GlassType;
import ljfa.glassshards.util.LogHelper;
import net.minecraft.block.Block;

public class ChiselGlassHelper {
    public ChiselGlassHelper() throws ReflectiveOperationException {
        classGlass = Class.forName("com.cricketcraft.chisel.block.BlockCarvableGlass");
        classPane = Class.forName("com.cricketcraft.chisel.block.BlockCarvablePane");
        
        chiselGlass = classGlass.getDeclaredField("glass").get(chiselGlass);
    }
    
    public GlassType getType(Block block, int meta) {
        LogHelper.info("Glass metadata: %d", meta);
        if(classGlass.isInstance(block))
            return new GlassType(GlassType.mult_block);
        else
            return null;
    }
    
    private Class<?> classGlass;
    private Class<?> classPane;
    private Object chiselGlass;
}
