package ljfa.glassshards.items;

import java.util.List;

import ljfa.glassshards.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/*
 * Metadata:
 * 0 - 15: colored shards
 * 16    : clear shards
 */
public class ItemGlassShards extends Item {
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;
    
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
    }
    
    private static String getColorName(int index) {
        return ItemDye.field_150921_b[15-index]; //Mojang pls
    }
}
