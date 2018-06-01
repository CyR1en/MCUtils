package com.cyr1en.mcutils.hook;

import com.cyr1en.mcutils.logger.Logger;
import java.util.HashMap;

public class HookLoader {

	private static HashMap<Class<? extends IPluginHook>, IPluginHook> hooks = new HashMap<>();

	public static void addHook(Class<? extends IPluginHook> clazz) {
		try {
			IPluginHook newInstance = clazz.newInstance();
			hooks.put(clazz, newInstance);
			if(!newInstance.customLogMessage().isEmpty())
			    Logger.info(newInstance.customLogMessage());
		} catch (InstantiationException | IllegalAccessException e) {
			Logger.err("Error instantiating hook class; is it private? Offender: " + clazz.getName());
		}
	}

	public static IPluginHook get(Class clazz) {
		return hooks.get(clazz);
	}
}
