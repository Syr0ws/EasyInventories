package fr.syrows.easyinventories.configs;

import fr.syrows.easyinventories.configs.api.ConfigContainerImpl;
import fr.syrows.easyinventories.configs.sections.impl.InventorySection;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;

public class InventoryConfigContainer extends ConfigContainerImpl {

    public InventoryConfigContainer(Plugin plugin, String resourcePath, Path path) {
        super(plugin, resourcePath, path);
    }

    public InventorySection getInventorySection(String path) {

        ConfigurationSection section = super.getConfig().getConfigurationSection(path);

        return new InventorySection(section);
    }
}
