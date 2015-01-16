package ljfa.glassshards.items;

import java.util.List;

import ljfa.glassshards.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * Metadata:
 * 0 - 15: colored shards
 * 16    : clear shards
 */
public class ItemGlassShards extends Item {
    /*@SideOnly(Side.CLIENT)
    private IIcon[] textures;*/
    
    ItemGlassShards() {
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String prefix = "item." + Reference.MODID;
        int meta = stack.getItemDamage();
        if(meta < 16)
            return prefix + ":glass_shards." + getColorName(meta);
        else
            return prefix + ":glass_shards";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        //List clear shards first
        list.add(new ItemStack(item, 1, 16));
        for(int i = 0; i < 16; i++)
            list.add(new ItemStack(item, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerModels() {
        ModelBakery.addVariantName(this, ItemGlassShards.variants);
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        mesher.register(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(getVariant(stack.getItemDamage()), "inventory");
            }
        });
    }
    
    /*@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        textures = new IIcon[17];
        for(int i = 0; i < 16; i++)
            textures[i] = iconRegister.registerIcon(Reference.MODID + ":glass_shards_" + getColorName(i));
        
        textures[16] = iconRegister.registerIcon(Reference.MODID + ":glass_shards");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta) {
        if(meta > 16)
            meta = 16;
        return textures[meta];
    }*/
    
    public static String[] colorNames;
    public static String[] variants;
    
    static {
        colorNames = new String[] {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
                "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
        
        variants = new String[17];
        for(int i = 0; i < 16; i++)
            variants[i] = Reference.MODID + ":glass_shards_" + colorNames[i];
        variants[16] = Reference.MODID + ":glass_shards";
    }
    
    public static String getColorName(int index) {
        return colorNames[index];
    }
    
    public static String getVariant(int meta) {
        if(0 <= meta && meta < 16)
            return variants[meta];
        else
            return variants[16];
    }
}
