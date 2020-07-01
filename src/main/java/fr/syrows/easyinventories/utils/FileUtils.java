package fr.syrows.easyinventories.utils;

import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static void copyFromResources(Plugin plugin, Path path, String resourcePath) throws IOException {

        if(Files.exists(path)) return;

        InputStream stream = plugin.getResource(resourcePath);

        if(stream == null)
            throw new NullPointerException(String.format("No file found at '%s'.", resourcePath));

        String content = FileUtils.read(stream);

        FileUtils.write(path, content);
    }

    public static void write(Path path, String content) throws IOException {

        if(Files.exists(path)) Files.createFile(path);

        BufferedWriter writer = Files.newBufferedWriter(path);

        writer.write(content);
        writer.flush();
        writer.close();
    }

    public static String read(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();

        String l;
        while((l = reader.readLine()) != null) sb.append(l).append("\n");

        String content = sb.toString();

        reader.close();

        return content;
    }
}
