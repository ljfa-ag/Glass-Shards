package ljfa.glassshards.items;

import ljfa.glassshards.GlassShards;
import ljfa.glassshards.Reference;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * NBT Data:
 * Color: TagByte
 */
public class ItemGlassSword extends ItemSword {

    public ItemGlassSword() {
        super(GlassShards.toolMatGlass);
        setRegistryName("glass_sword");
        setUnlocalizedName(Reference.MODID + ":glass_sword");
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
        String prefix = getUnlocalizedName();
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
    
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this));
        for(int i = 0; i < 16; i++) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Color", (byte)i);
            ItemStack stack = new ItemStack(this);
            stack.setTagCompound(tag);
            list.add(stack);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        final ModelResourceLocation[] variants = new ModelResourceLocation[17];
        for(int i = 0; i < 17; i++)
            variants[i] = new ModelResourceLocation(Reference.MODID + ":glass_sword", "color=" + ItemGlassShards.colorNames[i]);
        
        ModelBakery.registerItemVariants(this, variants);
        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return variants[getColor(stack)];
            }
        });
    }

}
