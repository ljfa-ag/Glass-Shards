package ljfa.glassshards.items;

import com.google.common.collect.Multimap;

import ljfa.glassshards.Reference;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * Metadata:
 * 0 - 15: colored shards
 * 16    : clear shards
 */
public class ItemGlassShards extends Item {
    
    public static final String[] colorNames = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
            "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black",
            "none"};
    
    public ItemGlassShards() {
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.register(this, "glass_shards");
        
        if(FMLCommonHandler.instance().getSide().isClient())
            registerModels();
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
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);
        
        if(equipmentSlot == EntityEquipmentSlot.MAINHAND) {
	        double damage = 1.0;
	        double speed = -2.0;
	        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
	                new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
	        multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
	                new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speed, 0));
        }
        
        return multimap;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, NonNullList<ItemStack> list) {
        //List clear shards first
        list.add(new ItemStack(item, 1, 16));
        for(int i = 0; i < 16; i++)
            list.add(new ItemStack(item, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
    private void registerModels() {
        final ModelResourceLocation[] variants = new ModelResourceLocation[17];
        for(int i = 0; i < 17; i++)
            variants[i] = new ModelResourceLocation(Reference.MODID + ":glass_shards", "color=" + colorNames[i]);
        
        ModelBakery.registerItemVariants(this, variants);
        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return variants[MathHelper.clamp(stack.getItemDamage(), 0, 16)];
            }
        });
    }
}
