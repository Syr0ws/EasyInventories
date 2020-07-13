package fr.syrows.easyinventories.configs.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;

public interface ConfigContainer {

    FileConfiguration getConfig();

    void reloadConfig();

    static ConfigContainer newContainer(Plugin plugin, String resourcePath, Path path) {
        return new ConfigContainerImpl(plugin, resourcePath, path);
    }
}
