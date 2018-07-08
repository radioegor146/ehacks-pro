/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.util.reflect;

import java.io.PrintStream;
import java.lang.reflect.Field;

public class ReflectionHelper {
    public static void setField(Class clazz, Object o, String s, Object val) {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if (!fields[i].getName().equals(s)) continue;
            ReflectionHelper.setField(clazz, o, i, val);
            return;
        }
        System.out.println("Fix Reflection usage: No such field: \"" + s + "\"!");
    }

    public static void setStringFieldWW(Class clazz, Object o, String s, Object val) {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if (!fields[i].getName().equals(s)) continue;
            ReflectionHelper.setField(clazz, o, i, val);
            return;
        }
        System.out.println("Fix Reflection usage: No such field: \"" + s + "\"!");
    }

    public static void setField(Class c, Object o, int n, Object val) {
        try {
            Field field = c.getDeclaredFields()[n];
            field.setAccessible(true);
            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & -17);
            field.set(o, val);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

