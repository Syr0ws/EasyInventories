package fr.syrows.easyinventories.configs;

import fr.syrows.easyinventories.configs.sections.InventorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.nio.file.Path;

public class InventoryConfig {

    private Path path;
    private YamlConfiguration config;

    public InventoryConfig(Path path) {
        this.path = path;
        this.reloadConfig();
    }

    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(this.path.toFile());
    }

    public Path getPath() {
        return this.path;
    }

    public YamlConfiguration getConfig() {
        return this.config;
    }

    public InventorySection getSection(String path) {

        if(!this.config.isSet(path))
            throw new NullPointerException(String.format("No section found at '%s'.", path));

        return new InventorySection(this.config.getConfigurationSection(path));
    }
}
