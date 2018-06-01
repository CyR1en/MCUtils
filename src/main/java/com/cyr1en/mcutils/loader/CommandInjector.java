package com.cyr1en.mcutils.loader;

import com.cyr1en.mcutils.annotations.Command;
import com.cyr1en.mcutils.dispatcher.CommandDispatcher;
import com.cyr1en.mcutils.inject.Injector;

import java.lang.reflect.Method;

public class CommandInjector implements Injector {
    @Override
    public void inject(Class<?> c, Object instance) {
        final Method[] methods = c.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Command.class)) {
                Command details = method.getAnnotation(Command.class);

	            CommandDispatcher.getDispatcher().registerCommand(details, method, instance);
            }
        }
    }
}
