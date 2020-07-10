package fr.syrows.modernshop.utils.parsers;

import org.bukkit.ChatColor;

public class MessageParser {

    public static String parseColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
