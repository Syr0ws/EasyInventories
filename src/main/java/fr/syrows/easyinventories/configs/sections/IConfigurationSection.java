package fr.syrows.easyinventories.configs.sections;

import fr.syrows.easyinventories.configs.sections.impl.ItemSection;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public interface IConfigurationSection {

    boolean contains(String path);

    Set<String> getKeys(boolean keys);

    byte getByte(String path);

    short getShort(String path);

    int getInt(String path);

    long getLong(String path);

    double getDouble(String path);

    float getFloat(String path);

    String getString(String path);

    Material getMaterial(String path);

    ItemStack getItemStack(String path);

    List<String> getStringList(String path);

    String[] getStringArray(String path);

    List<Integer> getIntegerList(String path);

    int[] getIntArray(String path);

    ItemSection getItemSection(String path);

    IConfigurationSection getConfigurationSection(String path);

    ConfigurationSection getAsConfigurationSection();
}
