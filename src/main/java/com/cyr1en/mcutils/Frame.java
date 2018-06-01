package com.cyr1en.mcutils;

import com.cyr1en.mcutils.config.ConfigurationBuilder;
import com.cyr1en.mcutils.config.ConfigurationInjector;
import com.cyr1en.mcutils.diagnosis.ReportLoader;
import com.cyr1en.mcutils.dispatcher.help.HelpTopicUtil;
import com.cyr1en.mcutils.events.BukkitEventsInjector;
import com.cyr1en.mcutils.hook.HookInjector;
import com.cyr1en.mcutils.hook.HookLoader;
import com.cyr1en.mcutils.hook.IPluginHook;
import com.cyr1en.mcutils.inject.FrameInjector;
import com.cyr1en.mcutils.loader.CommandInjector;
import com.cyr1en.mcutils.logger.Logger;
import com.cyr1en.mcutils.module.ModuleInjector;
import com.cyr1en.mcutils.module.ModuleLoader;

public class Frame {
    public static void main() {
        Logger.info("- Starting plugin framework initialization.");

        // Build configurations
        try {
            ConfigurationBuilder.buildAwaiting();
        } catch (Exception e) {
            Logger.err("- Error building configurations!");
            Logger.err(e.getMessage());
            e.printStackTrace();
        }

        new FrameInjector()
                .injector(new CommandInjector())
                .injector(new HookInjector())
                .injector(new ModuleInjector())
                .injector(new ConfigurationInjector())
                .injector(new BukkitEventsInjector())
                .injectAll();
        HelpTopicUtil.index();

        Logger.info("- Finished plugin framework initialization.");
    }

    /*
     * Util functions for adding modules, configurations and hooks.
     */

    /**
     * Register and load your module into Frame
     *
     * @param clazz Class containing your module
     */
    public static void addModule(Class clazz) {
        ModuleLoader.add(clazz);
    }

    /**
     * Register and load diagnostics reporter into Frame
     *
     * @param clazz Class that implements {@link IPluginHook}
     */
    public static void addReporter(Class clazz) {
        ReportLoader.addReporter(clazz);
    }

    public static void addConfiguration(Class clazz) {
        ConfigurationBuilder.add(clazz);
    }

    /**
     * Register specified hook in Frame
     *
     * @param clazz Class that implements {@link IPluginHook}
     */
    public static void addHook(Class<? extends IPluginHook> clazz) {
        HookLoader.addHook(clazz);
    }

    public static Object getConfig(Class clazz) {
        return ConfigurationBuilder.get(clazz);
    }
}
