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
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String prefix = "item." + Reference.MODID + ":glass_sword";
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Color", Constants.NBT.TAG_BYTE)) {
            byte color = stack.getTagCompound().getByte("Color");
            if(0 <= color && color < 16)
                return prefix + "." + ItemGlassShards.colorNames[color];
        }
        return prefix;
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
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Color", Constants.NBT.TAG_BYTE)) {
            byte color = stack.getTagCompound().getByte("Color");
            if(0 <= color && color < 16)
                return fancy ? textures[color] : textures_opaque[color];
        }
        return fancy ? textures[16] : textures_opaque[16];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return getIconIndex(stack);
    }
}
