package fr.syrows.utils;

import org.bukkit.ChatColor;

public class Utils {

    public static String parseColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
