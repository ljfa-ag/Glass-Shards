package ljfa.glassshards.util;

import java.lang.reflect.Field;

public class ReflectionHelper {
    @SuppressWarnings("unchecked")
    public static <T> T getField(Class<?> cl, String name, Object obj) throws ReflectiveOperationException {
        Field field = cl.getDeclaredField(name);
        field.setAccessible(true);
        return (T)field.get(obj);
    }
    
    public static <T> T getStaticField(Class<?> cl, String name) throws ReflectiveOperationException {
        return getField(cl, name, null);
    }
    
    public static void setField(Class<?> cl, String name, Object obj, Object value) throws ReflectiveOperationException {
        Field field = cl.getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }
    
    public static void setStaticField(Class<?> cl, String name, Object value) throws ReflectiveOperationException {
        setField(cl, name, null, value);
    }
    
    /** Attempts to get a Class instance from name. Returns null if the class could not be found. */
    public static Class<?> tryGetClass(String name) {
        try {
            return Class.forName(name);
        }
        catch(ClassNotFoundException e) {
            return null;
        }
    }
}
