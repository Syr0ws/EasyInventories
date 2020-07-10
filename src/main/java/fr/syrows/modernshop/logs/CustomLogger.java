package fr.syrows.modernshop.logs;

import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class CustomLogger {

    private static final String LOG_DIRECTORY_NAME = "logs";
    private static final String LOG_FILE_NAME = "shop_plugin.log";

    private Plugin plugin;

    public CustomLogger(Plugin plugin) {
        this.plugin = plugin;
    }

    public void enable() throws IOException {

        Path directory = this.getLogDirectory();

        if(!Files.exists(directory)) Files.createDirectory(directory);
    }

    public void broadcast(Level level, String message) {

        this.plugin.getLogger().log(level, message);
    }

    public void log(Level level, String message) {

    }

    private Path getLogDirectory() {
        return Paths.get(this.plugin.getDataFolder() + "/" + LOG_DIRECTORY_NAME);
    }
}
