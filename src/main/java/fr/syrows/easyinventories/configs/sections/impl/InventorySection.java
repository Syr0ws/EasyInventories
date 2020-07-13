package fr.syrows.easyinventories.configs.sections.impl;

import fr.syrows.easyinventories.configs.sections.IMemorySection;
import org.bukkit.configuration.ConfigurationSection;

public class InventorySection extends IMemorySection {

    public InventorySection(ConfigurationSection section) {
        super(section);
    }

    public PaginationSection getPaginationSection(String path) {

        ConfigurationSection current = super.getAsConfigurationSection();

        return new PaginationSection(current.getConfigurationSection(path));
    }
}
