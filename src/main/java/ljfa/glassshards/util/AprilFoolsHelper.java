package ljfa.glassshards.util;

import java.util.Calendar;

import net.minecraft.item.ItemDye;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AprilFoolsHelper {

    public static final boolean isApril1;
    public static final int white = 0, blue = 3, gold = 4, black = 15;

    static {
        Calendar now = Calendar.getInstance();
        isApril1 = now.get(Calendar.DAY_OF_MONTH) == 1 && now.get(Calendar.MONTH) == Calendar.APRIL;
    }
    
    public static void init() {
        permutateRev(ItemDye.field_150921_b);
        
        LogHelper.info("Happy April Fools' day :P");
    }
    
    public static <T> void permutate(T[] arr) {
        swap(arr, white, blue);
        swap(arr, gold, black);
    }
    
    public static <T> void permutateRev(T[] arr) {
        swap(arr, 15-white, 15-blue);
        swap(arr, 15-gold, 15-black);
    }
    
    public static <T> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
