package ljfa.glassshards.gui;

import java.util.ArrayList;
import java.util.List;

import ljfa.glassshards.Config;
import ljfa.glassshards.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class GsConfigGui extends GuiConfig {
    public GsConfigGui(GuiScreen parent) {
        super(parent, getConfigElements(), Reference.MODID, false, false, "Glass Shards configuration");
    }
    
    /** Compiles a list of config elements
     * Borrowed from EnderIO's implementation
     */
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        for(String name: Config.conf.getCategoryNames())
            list.add(new ConfigElement<ConfigCategory>(Config.conf.getCategory(name)));
        
        return list;
    }
}
