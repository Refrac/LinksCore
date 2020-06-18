/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */

package me.refrac.linkscore.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    protected static PlaceholderAPI placeholderApi;

    public static boolean isPlaceholderAPIEnabled() { return (placeholderApi != null); }

    public static String setupPlaceholderAPI(Player player , String message) {
        String placeholders = message;
        if (isPlaceholderAPIEnabled() && PlaceholderAPI.containsPlaceholders(placeholders)) {
            placeholders = PlaceholderAPI.setPlaceholders(player, placeholders);
        }
        return placeholders;
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String colorFormat(Player player, String message) {
        if (isPlaceholderAPIEnabled()) {
            return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, message));
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String DEVELOPER_NAME = "Refrac";

    public static String SUPPORT_URL = "https://discord.io/RefracDev";

    public static String VERSION = "1.7";

    public static String PREFIX = "&8[&bLinks&8] ";

}