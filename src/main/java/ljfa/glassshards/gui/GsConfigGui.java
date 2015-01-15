package ljfa.glassshards.gui;

import java.util.ArrayList;
import java.util.List;

import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GsConfigGui extends GuiConfig {
    public GsConfigGui(GuiScreen parent) {
        super(parent, getConfigElements(), Reference.MODID, false, false, "Glass Shards configuration");
    }
    
    /** Compiles a list of config elements */
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        //Add categories to config GUI
        list.add(categoryElement(Config.CATEGORY_GENERAL, "General", "glassshards.configgui.ctgy.general"));
        //list.add(categoryElement(Config.CATEGORY_CHISEL, "Chisel compatibility", "glassshards.configgui.ctgy.chisel"));
        
        return list;
    }
    
    /** Creates a button linking to another screen where all options of the category are available */
    private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
                new ConfigElement(Config.conf.getCategory(category)).getChildElements());
    }
}
