package fr.syrows.easyinventories.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class Utils {

    public static String parseColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static int[] getIntArray(List<Integer> list) {

        int[] array = new int[list.size()];

        for(int i = 0; i < array.length; i++) array[i] = list.get(i);

        return array;
    }
}
