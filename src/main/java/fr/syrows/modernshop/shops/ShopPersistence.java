package fr.syrows.modernshop.shops;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.shops.items.ShopItem;
import fr.syrows.modernshop.shops.owners.ShopOwner;
import fr.syrows.modernshop.utils.FileUtils;
import fr.syrows.modernshop.utils.converters.ConvertibleAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopPersistence {

    private static final String SHOP_FOLDER_NAME = "shops";

    private ShopPlugin plugin;

    public ShopPersistence(ShopPlugin plugin) {
        this.plugin = plugin;
    }

    public void createShopFolder() throws IOException {

        Path path = this.getShopFolder();

        if(!Files.exists(path)) Files.createDirectory(path);
    }

    public ArrayList<Shop> loadShops() throws IOException {

        List<Path> paths = this.getShopFiles();

        ArrayList<Shop> shops = new ArrayList<>();

        for(Path path : paths) {

            try {

                Shop shop = this.loadShop(path);

                shops.add(shop);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return shops;
    }

    public void saveShop(Shop shop) throws IOException {

        Path path = this.getShopFile(shop);
        Gson gson = this.getGson();

        FileUtils.write(path, gson.toJson(shop));
    }

    public void removeShop(Shop shop) throws IOException {

        Path path = this.getShopFile(shop);

        Files.deleteIfExists(path);
    }

    private Shop loadShop(Path path) throws IOException {

        Gson gson = this.getGson();

        BufferedReader reader = Files.newBufferedReader(path);

        Shop shop = gson.fromJson(reader, Shop.class);

        reader.close();

        return shop;
    }

    private Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(ShopOwner.class, new ConvertibleAdapter<ShopOwner>())
                .registerTypeAdapter(ShopItem.class, new ShopItem.Converter())
                .setPrettyPrinting()
                .create();
    }

    private List<Path> getShopFiles() throws IOException {

        Path folder = this.getShopFolder();

        return Files.list(folder).collect(Collectors.toList());
    }

    private Path getShopFile(Shop shop) {
        return Paths.get(this.getShopFolder() + "/" + shop.getIdentifier() + ".json");
    }

    private Path getShopFolder() {
        return Paths.get(this.plugin.getDataFolder() + "/" + SHOP_FOLDER_NAME);
    }
}
