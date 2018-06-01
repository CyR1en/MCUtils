package com.cyr1en.mcutils.module;

import com.cyr1en.mcutils.annotations.InjectModule;
import com.cyr1en.mcutils.inject.Injector;
import com.cyr1en.mcutils.logger.Logger;

import java.lang.reflect.Field;


public class ModuleInjector implements Injector {
	@Override
	public void inject(Class<?> c, Object instance) {
		for (Field f : c.getDeclaredFields()) {
			if (f.isAnnotationPresent(InjectModule.class)) {
				if (!ModuleLoader.getModuleClasses().contains(f.getType())) {
					Logger.err("Couldn't find module of class " + f.getType().getName() + "!");
					continue;
				}

				f.setAccessible(true);
				try {
					f.set(instance, ModuleLoader.getInstance(f.getType()));
				} catch (IllegalAccessException e) {
					Logger.err("Couldn't access field " + f.getName() + "!");
				}
			}
		}
	}
}
