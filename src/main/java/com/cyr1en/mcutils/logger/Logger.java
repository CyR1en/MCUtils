package com.cyr1en.mcutils.logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class Logger {

    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_GOLD_FOREGROUND = "\033[33m";
    public static final String ANSI_RED_FOREGROUND = "\033[31m";

    private static String prefix = "";
    private static String plainPrefix = "";
    private static boolean debugMode = false;

    public static void init(String prefix){
        Logger.plainPrefix = prefix;
        Logger.prefix = String.format("[%s]", prefix);
    }

    public static void log(Level level, String msg, String... args) {
        String pre = debugMode ? "[" + plainPrefix + "-Debug] " : getPrefix();
        if(msg.contains("%s"))
            msg = String.format(msg, (Object[]) args);
        Bukkit.getLogger().log(level, pre + msg);
    }

    public static void info(String msg, String... args) {
        log(Level.INFO, msg, args);
    }

    public static void warn(String msg, String... args) {
        log(Level.WARNING, ANSI_GOLD_FOREGROUND + msg + ANSI_RESET, args);
    }

    public static void err(String msg, String... args) {
        log(Level.SEVERE, ANSI_RED_FOREGROUND + msg + ANSI_RESET, args);
    }

    public static void debug(String msg, String... args) {
        if (debugMode)
            log(Level.INFO, ANSI_GOLD_FOREGROUND + msg + ANSI_RESET, args);
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
