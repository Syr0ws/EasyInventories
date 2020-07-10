package fr.syrows.modernshop.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static void write(Path path, String data) throws IOException {

        if(!Files.exists(path)) Files.createFile(path);

        BufferedWriter writer = Files.newBufferedWriter(path);

        writer.write(data);
        writer.flush();
        writer.close();
    }
}
