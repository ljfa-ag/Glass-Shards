package ljfa.glassshards.util;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static Object getField(Class<?> cl, String name, Object obj) throws ReflectiveOperationException {
        Field field = cl.getField(name);
        field.setAccessible(true);
        return field.get(obj);
    }
    
    public static Object getStaticField(Class<?> cl, String name) throws ReflectiveOperationException {
        return getField(cl, name, null);
    }
}
