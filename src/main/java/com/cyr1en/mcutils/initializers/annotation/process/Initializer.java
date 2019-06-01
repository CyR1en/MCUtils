package com.cyr1en.mcutils.initializers.annotation.process;

import com.cyr1en.mcutils.initializers.Initializable;
import com.cyr1en.mcutils.initializers.NegativeComparator;
import com.cyr1en.mcutils.initializers.annotation.Ignore;
import com.cyr1en.mcutils.initializers.annotation.Initialize;
import com.cyr1en.mcutils.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class Initializer {

  private static boolean interrupted = false;
  private static boolean finished = false;

  public synchronized static void initAll(Initializable clazz) {
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

  public synchronized static void initFields(Initializable clazz) {
    try {
      Field[] fields = clazz.getClass().getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(Ignore.class))
          continue;
        field.setAccessible(true);
        Class type = field.getType();
        Object v = field.get(clazz);
        if (!interrupted && v == null) {
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

  public synchronized static void initMethods(Initializable clazz) {
    TreeMap<Integer, Method> methods = prioritizedMethods(clazz.getClass().getDeclaredMethods());
    methods.forEach((priority, method) -> {
      if (!interrupted)
        try {
          method.setAccessible(true);
          Logger.debug("Invoking %s...", method.getName());
          method.invoke(clazz);
          Logger.debug("Successfully invoked %s", method.getName());
        } catch (IllegalAccessException | InvocationTargetException e) {
          Logger.debug("Failed to invoke %s", method.getName());
          e.printStackTrace();
        }

    });
  }

  private static TreeMap<Integer, Method> prioritizedMethods(Method[] methods) {
    AtomicReference<HashMap<Integer, Method>> atomicMap = new AtomicReference<>(new HashMap<>());
    AtomicInteger atomicPriority = new AtomicInteger(0);
    Arrays.asList(methods).forEach(method -> {
      if (method.isAnnotationPresent(Initialize.class)) {
        Initialize meta = method.getAnnotation(Initialize.class);
        //if (!meta.conditional().check())
        if (meta.priority() < 0) atomicMap.get().put(atomicPriority.addAndGet(-1), method);
        else atomicMap.get().put(meta.priority(), method);
      }
    });
    TreeMap<Integer, Method> sorted = new TreeMap<>(NegativeComparator.atEnd(Integer::compare));
    sorted.putAll(atomicMap.get());
    return sorted;
  }


}
