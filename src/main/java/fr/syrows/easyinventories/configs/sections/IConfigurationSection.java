package fr.syrows.easyinventories.configs.sections;

import fr.syrows.easyinventories.configs.sections.impl.ItemSection;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface IConfigurationSection {

    boolean contains(String path);

    byte getByte(String path);

    short getShort(String path);

    int getInt(String path);

    long getLong(String path);

    double getDouble(String path);

    float getFloat(String path);

    String getString(String path);

    Material getMaterial(String path);

    List<Integer> getIntegerList(String path);

    int[] getIntArray(String path);

    ItemSection getItemSection(String path);

    IConfigurationSection getConfigurationSection(String path);

    ConfigurationSection getAsConfigurationSection();
}
