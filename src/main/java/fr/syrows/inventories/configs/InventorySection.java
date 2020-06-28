package fr.syrows.inventories.configs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class InventorySection {

    private ConfigurationSection section;

    public InventorySection(ConfigurationSection section) {
        this.section = section;
    }

    public String getTitle() {
        return this.section.getString("title");
    }

    public ConfigurationSection getContentSection() {
        return section.getConfigurationSection("contents");
    }

    public ItemStack getItemStack(String path) {
        return this.getContentSection().getItemStack(path);
    }
}
