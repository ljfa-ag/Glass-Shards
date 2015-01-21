package ljfa.glassshards.items;

import net.minecraft.client.renderer.ItemModelMesher;

/** Allows an item to register its models on initialization */
public interface IModeledItem {
    public void registerModels(ItemModelMesher mesher);
}
