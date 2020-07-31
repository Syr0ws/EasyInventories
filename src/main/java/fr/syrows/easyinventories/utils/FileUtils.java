/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
