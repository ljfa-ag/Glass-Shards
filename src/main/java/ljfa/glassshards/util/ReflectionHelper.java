package ljfa.glassshards.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper {
    public static Object getField(Class<?> cl, String name, Object obj) throws ReflectiveOperationException {
        Field field = cl.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(obj);
    }
    
    public static Object getStaticField(Class<?> cl, String name) throws ReflectiveOperationException {
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
}
