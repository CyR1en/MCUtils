package com.cyr1en.mcutils.logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class Logger {

    public static final String RESET = "&r";
    public static final String GOLD = "&6";
    public static final String RED = "&c";

    private static String prefix = "";
    private static String plainPrefix = "";
    private static boolean debugMode = false;

    public static void init(String prefix){
        Logger.plainPrefix = prefix;
        Logger.prefix = String.format("[%s] ", prefix);
    }

    public static void log(String prefix, Level level, String msg, Object... args) {
        String pre = prefix == null ? getPrefix() : prefix;
        if(msg.contains("%s"))
            msg = String.format(msg, (Object[]) args);
        Bukkit.getLogger().log(level, ChatColor.translateAlternateColorCodes('&', pre + msg));
    }

    public static void log(Level level, String msg, Object... args) {
        log(null, level, msg, args);
    }

    public static void info(String msg, Object... args) {
        log(Level.INFO, msg, args);
    }

    public static void warn(String msg, Object... args) {
        log(Level.WARNING, GOLD + msg + RESET, args);
    }

    public static void err(String msg, Object... args) {
        log(Level.SEVERE, RED + msg + RESET, args);
    }

    public static void debug(String msg, Object... args) {
        if (debugMode) {
            String pre = String.format("[%s-debug] ", plainPrefix);
            log(pre, Level.INFO, GOLD + msg + RESET, args);
        }
    }

    public static void setDebugMode(boolean b) {
        debugMode = b;
    }

    public static void bukkitWarn(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + getPrefix() + msg);
    }

    private static String getPrefix() {
        return prefix;
    }
}
