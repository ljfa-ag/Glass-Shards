package ljfa.glassshards.items;

import java.util.List;

import ljfa.glassshards.GlassShards;
import ljfa.glassshards.Reference;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * NBT Data:
 * Color: TagByte
 */
public class ItemGlassSword extends ItemSword {
    private final String[] variants;
    
    public ItemGlassSword() {
        super(GlassShards.toolMatGlass);
        
        variants = new String[17];
        for(int i = 0; i < 16; i++)
            variants[i] = Reference.MODID + ":glass_sword_" + ItemGlassShards.colorNames[i];
        variants[16] = Reference.MODID + ":glass_sword";
        
        ModItems.register(this, "glass_sword");
        
        if(FMLCommonHandler.instance().getSide().isClient())
            registerModels();
    }
    
    public String getVariant(int meta) {
        return variants[MathHelper.clamp_int(meta, 0, 16)];
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
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
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
    private void registerModels() {
        for(String variant: variants)
            ModelBakery.registerItemVariants(this, new ModelResourceLocation(variant, "inventory"));
        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(getVariant(getColor(stack)), "inventory");
            }
        });
    }

}
