package ljfa.glassshards;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@net.minecraftforge.common.config.Config(modid = Reference.MODID)
public class Config {
    @Comment("Base chance that a block of glass drops shards")
    @RangeDouble(min = 0.0, max = 1.0)
    public static double shardsChance = 0.7;
    
    @Comment("Chance per fortune level that a block of glass drops shards")
    @RangeDouble(min = 0.0, max = 1.0)
    public static double shardsFortuneChance = 0.07;
    
    @Comment("Add recipes for coloring shards")
    @RequiresMcRestart
    public static boolean recipesColor = true;
    
    @Comment("Add recipe to remove the color from shards")
    @RequiresMcRestart
    public static boolean recipeUncolor = true;
    
    @Comment({"Add furnace recipes to smelt shards to glass blocks",
              "(if you disable this, you will probably want to add some other way to process shards)"})
    @RequiresMcRestart
    public static boolean recipesFurnace = true;
    
    @Comment("Enables the glass sword")
    @RequiresMcRestart
    public static boolean enableSword = true;
    
    @Comment("Durability of the glass sword")
    @RangeInt(min = 1, max = 1561)
    @RequiresMcRestart
    public static int swordDurability = 109;
    
    @Comment("Glass breaks faster when mined with a pickaxe")
    @RequiresMcRestart
    public static boolean increaseGlassBreakSpeed = true;
}
