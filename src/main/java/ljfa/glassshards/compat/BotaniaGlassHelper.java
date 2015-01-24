package ljfa.glassshards.compat;

import java.util.List;

import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.SimpleGlassHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import org.apache.logging.log4j.Level;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;

public class BotaniaGlassHelper {
    public static void init() {
        //Add block to the registry
        Block managlass = vazkii.botania.common.block.ModBlocks.manaGlass;
        GlassRegistry.addHandler(managlass, SimpleGlassHandler.blockInstance);
        
        LogHelper.info("Successfully loaded Botania glass compatibility.");
    }
    
    public static void removeVitreousPick() {
        Item vitreousPick = vazkii.botania.common.item.ModItems.glassPick;
        
        //Remove recipe
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for(int i = 0; i < recipes.size(); i++) {
            ItemStack output = recipes.get(i).getRecipeOutput();
            if(output != null && output.getItem() == vitreousPick) {
                recipes.remove(i);
                LogHelper.info("Successfully removed Vitreous Pickaxe recipe.");
                break;
            }
        }
        
        //Remove lexicon entry
        List<LexiconEntry> entries = BotaniaAPI.getAllEntries();
        for(int i = 0; i < entries.size(); i++) {
            LexiconEntry entry = entries.get(i);
            if(entry.getUnlocalizedName().equals("botania.entry.glassPick")) {
                entries.remove(i);
                entry.category.entries.remove(entry);
                LogHelper.info("Successfully removed Vitreous Pickaxe lexicon entry.");
                break;
            }
        }
    }
}
