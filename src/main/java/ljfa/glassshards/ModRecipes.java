package ljfa.glassshards;

import ljfa.glassshards.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes {
    public static final String[] dyes = {"White", "Orange", "Magenta", "LightBlue", "Yellow", "Lime",
        "Pink", "Gray", "LightGray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"};
    
    public static void init() {
        ModRecipes.addOredict();
        ModRecipes.addCrafting();
        ModRecipes.addSmelting();
    }
    
    private static void addOredict() {
        OreDictionary.registerOre("dustGlass", new ItemStack(ModItems.glass_shards, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("shardsGlass", new ItemStack(ModItems.glass_shards, 1, OreDictionary.WILDCARD_VALUE));
        for(int i = 0; i < 16; i++) {
            OreDictionary.registerOre("dustGlass" + dyes[i], new ItemStack(ModItems.glass_shards, 1, i));
            OreDictionary.registerOre("shardsGlass" + dyes[i], new ItemStack(ModItems.glass_shards, 1, i));
        }
    }
    
    private static void addCrafting() {
        if(Config.recipesRecolor)
            for(int i = 0; i < 16; i++) {
                ItemStack shards = new ItemStack(ModItems.glass_shards, 1, 16);
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.glass_shards, 8, i),
                    "SSS", "SDS", "SSS", 'S', shards, 'D', "dye" + dyes[i]));
            }
        
        if(Config.recipeUncolor)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.glass_shards, 8, 16),
                "SSS", "SGS", "SSS", 'S', "shardsGlass", 'G', Items.gunpowder));
        
        if(Config.enableSword) {
            GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.glass_sword, "S", "S", "|",
                    'S', new ItemStack(ModItems.glass_shards, 1, 16), '|', "stickWood"));
            
            for(int i = 0; i < 16; i++) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Color", (byte)i);
                ItemStack sword = new ItemStack(ModItems.glass_sword);
                sword.setTagCompound(tag);
                GameRegistry.addRecipe(new ShapedOreRecipe(sword, "S", "S", "|",
                        'S', new ItemStack(ModItems.glass_shards, 1, i), '|', "stickWood"));
            }   
        }
    }
    
    private static void addSmelting() {
        if(Config.recipesFurnace) {
            GameRegistry.addSmelting(new ItemStack(ModItems.glass_shards, 1, 16), new ItemStack(Blocks.glass), 0.05f);
            for(int i = 0; i < 16; i++) {
                GameRegistry.addSmelting(new ItemStack(ModItems.glass_shards, 1, i), new ItemStack(Blocks.stained_glass, 1, i), 0.05f);
            }
        }
    }
}
