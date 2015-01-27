package ljfa.glassshards.items;

import java.util.List;

import com.google.common.collect.Multimap;

import ljfa.glassshards.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
    private IIcon[] textures, textures_opaque;
    
    private static final GameSettings settings = Minecraft.getMinecraft().gameSettings;

    ItemGlassShards() {
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String prefix = "item." + Reference.MODID + ":glass_shards";
        int meta = stack.getItemDamage();
        if(meta < 16)
            return prefix + "." + getColorName(meta);
        else
            return prefix;
    }
    
    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        double damage = 1.0;
        Multimap multimap = super.getAttributeModifiers(stack);
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
            textures[i] = iconRegister.registerIcon(prefix + "_" + getColorName(i));
            textures_opaque[i] = iconRegister.registerIcon(prefix + "_" + getColorName(i) + "_opaque");
        }
        
        textures[16] = iconRegister.registerIcon(prefix);
        textures_opaque[16] = iconRegister.registerIcon(prefix + "_opaque");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta) {
        if(meta > 16)
            meta = 16;
        return settings.fancyGraphics ? textures[meta] : textures_opaque[meta];
    }
    
    private static String[] colorNames = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
        "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
    
    private static String getColorName(int index) {
        return colorNames[index];
    }
}
