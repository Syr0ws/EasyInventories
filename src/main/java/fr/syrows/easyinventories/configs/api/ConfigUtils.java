package fr.syrows.easyinventories.configs.api;

import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtils {

    public static String readFromResources(Plugin plugin, String resourcePath) throws IOException {

        InputStream stream = plugin.getResource(resourcePath);

        if(stream == null)
            throw new NullPointerException(String.format("No config found at '%s'.", resourcePath));

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();

        String l;
        while((l = reader.readLine()) != null) sb.append(l).append("\n");

        reader.close();

        return sb.toString();
    }

    public static void writeToFile(Path path, String data) throws IOException {

        ConfigUtils.createFile(path);

        BufferedWriter writer = Files.newBufferedWriter(path);

        writer.write(data);
        writer.flush();
        writer.close();
    }

    public static void createFile(Path path) throws IOException {

        if(!Files.exists(path)) {

            Files.createDirectories(path.getParent()); // Create all parent directories that not exist.
            Files.createFile(path); // Create the file.
        }
    }

    public static void loadFromResources(Plugin plugin, String resourcePath, Path out) throws IOException {

        if(!Files.exists(out)) {

            String data = ConfigUtils.readFromResources(plugin, resourcePath);

            ConfigUtils.writeToFile(out, data);
        }
    }
}
