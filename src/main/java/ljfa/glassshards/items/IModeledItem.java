package ljfa.glassshards.items;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Allows an item to register its models on initialization */
public interface IModeledItem {
    @SideOnly(Side.CLIENT)
    public void registerModels(ItemModelMesher mesher);
}
