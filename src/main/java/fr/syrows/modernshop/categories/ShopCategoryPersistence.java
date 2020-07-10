package fr.syrows.modernshop.categories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.shops.items.ShopItem;
import fr.syrows.modernshop.utils.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ShopCategoryPersistence {

    private static final String CATEGORY_FILE_NAME = "categories";

    private ShopPlugin plugin;

    public ShopCategoryPersistence(ShopPlugin plugin) {
        this.plugin = plugin;
    }

    public void createCategoryFile() throws IOException {

        Path path = this.getCategoryFile();

        if(!Files.exists(path)) FileUtils.write(path, "[]");
    }

    public ArrayList<ShopCategory> loadCategories() throws IOException {

        Path path = this.getCategoryFile();
        Gson gson = this.getGson();

        BufferedReader reader = Files.newBufferedReader(path);

        Type type = new TypeToken<ArrayList<ShopCategory>>(){}.getType();

        return gson.fromJson(reader, type);
    }

    public Path getCategoryFile() {
        return Paths.get(this.plugin.getDataFolder() + "/" + CATEGORY_FILE_NAME + ".json");
    }

    private Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(ShopCategory.class, new ShopCategory.Deserializer())
                .registerTypeAdapter(ShopItem.class, new ShopItem.Converter())
                .create();
    }
}
