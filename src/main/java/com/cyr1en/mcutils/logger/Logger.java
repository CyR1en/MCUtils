package com.cyr1en.mcutils.logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class Logger {

    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_GOLD_FOREGROUND = "\033[33m";
    public static final String ANSI_RED_FOREGROUND = "\033[31m";

    private static String prefix = "";
    private static boolean debugMode = false;

    public static void init(String prefix){
        Logger.prefix = String.format("[%s]", prefix);
    }
    public static void log(Level level, String msg) {
        String pre = debugMode ? "[" + prefix + "-Debug] " : getPrefix();
        Bukkit.getLogger().log(level, pre + msg);
    }

    public static void info(String msg) {
        log(Level.INFO, msg);
    }

    public static void warn(String msg) {
        log(Level.WARNING, ANSI_GOLD_FOREGROUND + msg + ANSI_RESET);
    }

    public static void err(String msg) {
        log(Level.SEVERE, ANSI_RED_FOREGROUND + msg + ANSI_RESET);
    }

    public static void debug(String msg) {
        if (debugMode)
            log(Level.INFO, ANSI_GOLD_FOREGROUND + msg + ANSI_RESET);
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
