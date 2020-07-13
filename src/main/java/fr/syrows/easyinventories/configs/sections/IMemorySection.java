package fr.syrows.easyinventories.configs.sections;

import fr.syrows.easyinventories.configs.sections.impl.ItemSection;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.NumberConversions;

import java.util.List;

public class IMemorySection implements IConfigurationSection {

    protected ConfigurationSection section;

    public IMemorySection(ConfigurationSection section) {
        this.section = section;
    }

    @Override
    public boolean contains(String path) {
        return this.section.contains(path);
    }

    @Override
    public byte getByte(String path) {
        Object object = this.section.get(path);
        return object instanceof Number ? NumberConversions.toByte(object) : 0;
    }

    @Override
    public short getShort(String path) {
        Object object = this.section.get(path);
        return object instanceof Number ? NumberConversions.toShort(object) : 0;
    }

    @Override
    public int getInt(String path) {
        return this.section.getInt(path);
    }

    @Override
    public long getLong(String path) {
        return this.section.getLong(path);
    }

    @Override
    public double getDouble(String path) {
        return this.section.getDouble(path);
    }

    @Override
    public float getFloat(String path) {
        Object object = this.section.get(path);
        return object instanceof Number ? NumberConversions.toFloat(object) : 0.0f;
    }

    @Override
    public String getString(String path) {
        return this.section.getString(path);
    }

    @Override
    public Material getMaterial(String path) {
        String matValue = this.getString(path);
        return Material.valueOf(matValue);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return this.section.getIntegerList(path);
    }

    @Override
    public int[] getIntArray(String path) {

        List<Integer> integers = this.getIntegerList(path);

        int[] array = new int[integers.size()];

        for(int i = 0; i < array.length; i++) array[i] = integers.get(i);

        return array;
    }

    @Override
    public ItemSection getItemSection(String path) {

        ConfigurationSection current = this.getAsConfigurationSection();

        return new ItemSection(current.getConfigurationSection(path));
    }

    @Override
    public IConfigurationSection getConfigurationSection(String path) {

        ConfigurationSection current = this.getAsConfigurationSection();

        if(!current.isConfigurationSection(path))
            throw new UnsupportedOperationException(String.format("No section found at '%s.%s'.", current.getCurrentPath(), path));

        ConfigurationSection section = current.getConfigurationSection(path);

        return new IMemorySection(section);
    }

    @Override
    public ConfigurationSection getAsConfigurationSection() {
        return this.section;
    }
}
