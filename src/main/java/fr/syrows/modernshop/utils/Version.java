package fr.syrows.modernshop.utils;

import org.bukkit.Bukkit;

public class Version {

    public static final boolean IS_LEGACY_VERSION = Version.isLegacy();

    private static final int NEW_VERSION_NUMBER = 13;

    public static boolean isLegacy() {

        String version = Bukkit.getVersion();

        version = version.substring(version.indexOf('(') + 5, version.indexOf(')'));

        String[] array = version.split("\\.");

        int number = Integer.parseInt(array[1]);

        return number < NEW_VERSION_NUMBER;
    }
}
