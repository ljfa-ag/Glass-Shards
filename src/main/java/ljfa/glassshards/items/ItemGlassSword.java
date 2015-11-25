package ljfa.glassshards.items;

import java.util.List;

import ljfa.glassshards.GlassShards;
import ljfa.glassshards.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/*
 * NBT Data:
 * Color: TagByte
 */
public class ItemGlassSword extends ItemSword {
    @SideOnly(Side.CLIENT)
    private IIcon[] textures, textures_opaque;
    
    public ItemGlassSword() {
        super(GlassShards.toolMatGlass);
        ModItems.register(this, "glass_sword");
    }
    
    /**
     * Returns the color index for the sword, or 16 if uncolored.
     * These values are the same as the metadata of glass shards.
     */
    public int getColor(ItemStack stack) {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Color", Constants.NBT.TAG_BYTE))
            return stack.getTagCompound().getByte("Color");
        else
            return 16;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String prefix = "item." + Reference.MODID + ":glass_sword";
        int color = getColor(stack);
        if(0 <= color && color < 16)
            return prefix + "." + ItemGlassShards.colorNames[color];
        else
            return prefix;
    }
    
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if(repair == null || repair.getItem() != ModItems.glass_shards)
            return false;
        return repair.getItemDamage() == getColor(toRepair);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item));
        for(int i = 0; i < 16; i++) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Color", (byte)i);
            ItemStack stack = new ItemStack(item);
            stack.setTagCompound(tag);
            list.add(stack);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        textures = new IIcon[17];
        textures_opaque = new IIcon[17];
        
        String prefix = Reference.MODID + ":glass_sword";
        for(int i = 0; i < 16; i++) {
            textures[i] = iconRegister.registerIcon(prefix + "_" + ItemGlassShards.colorNames[i]);
            textures_opaque[i] = iconRegister.registerIcon(prefix + "_" + ItemGlassShards.colorNames[i] + "_opaque");
        }
        
        //Actually these are the same right now for the uncolored sword 
        textures[16] = textures_opaque[16] = iconRegister.registerIcon(prefix);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconIndex(ItemStack stack) {
        boolean fancy = Minecraft.isFancyGraphicsEnabled();
        int color = getColor(stack);
        if(0 <= color && color < 16)
            return fancy ? textures[color] : textures_opaque[color];
        else
            return fancy ? textures[16] : textures_opaque[16];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return getIconIndex(stack);
    }
}
