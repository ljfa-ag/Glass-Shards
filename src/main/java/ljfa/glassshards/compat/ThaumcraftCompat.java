package ljfa.glassshards.compat;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ThaumcraftCompat {
    public static void addAspects() {
        ThaumcraftApi.registerObjectTag("shardsGlass", new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.ENTROPY, 1));
    }
}
