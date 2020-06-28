package fr.syrows.inventories.configs;

import fr.syrows.inventories.utils.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Path;

public class InventoryFile {

    private Plugin plugin;
    private String resourcePath;

    private Path path;
    private YamlConfiguration config;

    public InventoryFile(Plugin plugin, Path path, String resourcePath) {
        this.plugin = plugin;
        this.path = path;
        this.resourcePath = resourcePath;
    }

    public void loadConfig() throws IOException {

        FileUtils.copyFromResources(this.plugin, this.path, this.resourcePath);

        this.config = YamlConfiguration.loadConfiguration(this.path.toFile());
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public String getResourcePath() {
        return this.resourcePath;
    }

    public Path getPath() {
        return this.path;
    }

    public YamlConfiguration getConfig() {
        return this.config;
    }

    public InventorySection getSection(String path) {
        return new InventorySection(this.config.getConfigurationSection(path));
    }
}
