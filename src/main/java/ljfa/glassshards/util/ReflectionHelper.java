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
    
    public static Object invoke(Class<?> cl, String name, Object obj, Object... args) throws ReflectiveOperationException {
        Method method = cl.getDeclaredMethod(name, inferTypes(args));
        method.setAccessible(true);
        return method.invoke(obj, args);
    }
    
    public static Object invokeStatic(Class<?> cl, String name, Object... args) throws ReflectiveOperationException {
        return invoke(cl, name, null, args);
    }
    
    public static Object newInstance(Class<?> cl, Object... args) throws ReflectiveOperationException {
        Constructor constr = cl.getConstructor(inferTypes(args));
        return constr.newInstance(args);
    }
    
    public static Class[] inferTypes(Object... args) {
        Class[] types = new Class[args.length];
        for(int i = 0; i < args.length; i++) {
            Class cl = args[i].getClass();
            if(cl == Integer.class)
                cl = int.class;
            else if(cl == Boolean.class)
                cl = boolean.class;
            else if(cl == Long.class)
                cl = long.class;
            else if(cl == Short.class)
                cl = short.class;
            else if(cl == Byte.class)
                cl = byte.class;
            else if(cl == Character.class)
                cl = char.class;
            
            types[i] = cl;
        }
        return types;
    }
}
