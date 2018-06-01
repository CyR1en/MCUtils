package com.cyr1en.mcutils.hook;


import com.cyr1en.mcutils.annotations.Hook;
import com.cyr1en.mcutils.inject.Injector;
import com.cyr1en.mcutils.logger.Logger;

import java.lang.reflect.Field;

public class HookInjector implements Injector {
	@Override
	public void inject(Class<?> c, Object instance) {
		for (Field f : c.getDeclaredFields()) {
			if (f.isAnnotationPresent(Hook.class)) {
				IPluginHook hook = HookLoader.get(f.getType());
				f.setAccessible(true);

				if (hook == null) {
					Logger.warn("Couldn't find hook " + f.getType().getName() + ", was it registered?");
					continue;
				}

				try {
					if (hook.available())
						f.set(instance, hook);
					else
						f.set(instance, null);
				} catch (IllegalAccessException e) {
					Logger.err("Couldn't access field " + f.getName() + "!");
				}
			}
		}
	}
}
