package com.cyr1en.mcutils.hook;

import org.bukkit.Bukkit;

/**
 * Easily hook other plugins by extending this class to a subclass.
 * Interfaces {@link com.cyr1en.mcutils.hook.IPluginHook}
 * <p>
 * <p> PluginHook implementation example.
 * <pre>
 *         {@code
 *              public MCBHook extends PluginHook<Minecordbot> {
 *                  public MCBHook() {
 *                      this.name = "MineCordBot";
 *                  }
 *              }
 *         }
 *     </pre>
 * </p>
 *
 * @param <T> Type of Plugin that's going to get hooked.
 */
public class PluginHook<T> implements IPluginHook {

    protected String name = "";

    @Override
    public boolean available() {
        return Bukkit.getPluginManager().isPluginEnabled(getPluginName());
    }

    @Override
    public String getPluginName() {
        return name;
    }

    @Override
    public String customLogMessage() {
        return "";
    }

    public T getPlugin() {
        return (T) Bukkit.getPluginManager().getPlugin(getPluginName());
    }

}
