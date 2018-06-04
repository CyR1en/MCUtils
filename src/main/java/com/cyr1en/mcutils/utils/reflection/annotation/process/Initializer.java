package com.cyr1en.mcutils.utils.reflection.annotation.process;

import com.cyr1en.mcutils.logger.Logger;
import com.cyr1en.mcutils.utils.reflection.Initializable;
import com.cyr1en.mcutils.utils.reflection.annotation.Ignore;
import com.cyr1en.mcutils.utils.reflection.annotation.Initialize;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.TreeMap;


public class Initializer {

    private static boolean interrupted = false;
    private static boolean finished = false;

    public static void initAll(Initializable clazz) {
        initFields(clazz);
        initMethods(clazz);
        finished = true;
    }

    public static void interrupt() {
        interrupted = true;
    }

    public static boolean finished() {
        return finished;
    }

    public static void initFields(Initializable clazz) {
        try {
            Field[] fields = clazz.getClass().getDeclaredFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent(Ignore.class))
                    continue;
                Class type = field.getType();
                Object v = field.get(clazz);
                if (!interrupted && v == null) {
                    field.setAccessible(true);
                    if (type.equals(boolean.class))
                        field.set(clazz, false);
                    else if (type.isPrimitive())
                        field.set(clazz, 0);
                    else if (!type.isPrimitive())
                        field.set(clazz, null);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void initMethods(Initializable clazz) {
        TreeMap<Integer, Method> methods = prioritizedMethods(clazz.getClass().getDeclaredMethods());
        methods.forEach((priority, method) -> {
            if (!interrupted)
                try {
                    method.setAccessible(true);
                    Logger.debug("Invoking " + method.getName() + "...");
                    method.invoke(clazz);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    Logger.debug("Failed to invoke " + method.getName());
                    e.printStackTrace();
                }

        });
    }

    private static TreeMap<Integer, Method> prioritizedMethods(Method[] methods) {
        HashMap<Integer, Method> taggedMethods = new HashMap<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Initialize.class)) {
                Initialize meta = method.getAnnotation(Initialize.class);
                taggedMethods.put(meta.priority(), method);
            }
        }
        return new TreeMap<>(taggedMethods);
    }
}
