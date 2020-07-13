package fr.syrows.easyinventories.configs.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigContainerImpl implements ConfigContainer {

    private Plugin plugin;
    private String resourcePath;
    private Path path;

    private FileConfiguration config;

    public ConfigContainerImpl(Plugin plugin, String resourcePath, Path path) {
        this.plugin = plugin;
        this.resourcePath = resourcePath;
        this.path = path;
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public void reloadConfig() {

        this.loadFromResources();

        this.config = YamlConfiguration.loadConfiguration(this.path.toFile());
    }

    private void loadFromResources() {

        try {

            ConfigUtils.loadFromResources(this.plugin, this.resourcePath, this.path);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
