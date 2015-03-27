package ljfa.glassshards.items;

import java.util.List;

import ljfa.glassshards.Reference;
import ljfa.glassshards.util.AprilFoolsHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/*
 * Metadata:
 * 0 - 15: colored shards
 * 16    : clear shards
 */
public class ItemGlassShards extends Item {
    @SideOnly(Side.CLIENT)
    private IIcon[] textures, textures_opaque;

    ItemGlassShards() {
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMaterials);
        ModItems.register(this, "glass_shards");
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String prefix = "item." + Reference.MODID + ":glass_shards";
        int meta = stack.getItemDamage();
        if(meta < 16)
            return prefix + "." + colorNames[meta];
        else
            return prefix;
    }
    
    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        double damage = 1.0;
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(stack);
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Weapon modifier", damage, 0));
        return multimap;
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
        textures_opaque = new IIcon[17];
        
        String prefix = Reference.MODID + ":glass_shards";
        for(int i = 0; i < 16; i++) {
            textures[i] = iconRegister.registerIcon(prefix + "_" + colorNames[i]);
            textures_opaque[i] = iconRegister.registerIcon(prefix + "_" + colorNames[i] + "_opaque");
        }
        
        //Actually these are the same right now for the uncolored shards 
        textures_opaque[16] = textures[16] = iconRegister.registerIcon(prefix);
        
        if(AprilFoolsHelper.isApril1) {
            AprilFoolsHelper.permutate(textures);
            AprilFoolsHelper.permutate(textures_opaque);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta) {
        if(meta > 16)
            meta = 16;
        return Minecraft.isFancyGraphicsEnabled() ? textures[meta] : textures_opaque[meta];
    }

    public static final String[] colorNames = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
        "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
}
