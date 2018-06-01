package com.cyr1en.mcutils.inject;

import com.cyr1en.mcutils.logger.Logger;
import com.cyr1en.mcutils.module.ModuleLoader;

import java.util.ArrayList;

public class FrameInjector {
	private ArrayList<Injector> injectors = new ArrayList<>();

	public FrameInjector injector(Injector injector) {
		injectors.add(injector);
		return this;
	}

    public void injectAll() {
        for (Class<?> clazz : ModuleLoader.getModuleClasses()) {
	        Object instance = ModuleLoader.getInstance(clazz);

	        injectors.forEach(i -> i.inject(clazz, instance));
        }
        Logger.info("- Module classes injected.");
    }
}
