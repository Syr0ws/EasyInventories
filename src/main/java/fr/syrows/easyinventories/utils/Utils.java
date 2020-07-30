package fr.syrows.easyinventories.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class Utils {

    public static String parseColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
