package fr.syrows.modernshop.categories;

import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.logs.CustomLogger;
import fr.syrows.modernshop.shops.items.ShopItem;
import fr.syrows.modernshop.shops.items.ShopItemFactory;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ShopCategoryManager {

    private final CustomLogger logger;

    private ShopPlugin plugin;
    private ShopCategoryPersistence persistence;

    private ArrayList<ShopCategory> categories;

    public ShopCategoryManager(ShopPlugin plugin) {
        this.plugin = plugin;
        this.persistence = new ShopCategoryPersistence(plugin);
        this.logger = ShopPlugin.getCustomLogger();
    }

    public void loadCategories() {

        try {

            long begin = System.currentTimeMillis();

            this.persistence.createCategoryFile();

            ArrayList<ShopCategory> categories = this.persistence.loadCategories();

            this.categories = this.parseCategories(categories);

            long end = System.currentTimeMillis();

            this.logger.broadcast(Level.INFO, String.format("%d categories were loaded. Took %d ms.", categories.size(), end - begin));

        } catch (IOException e) {

            e.printStackTrace();

            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
    }

    private ArrayList<ShopCategory> parseCategories(ArrayList<ShopCategory> categories) {

        ArrayList<ShopCategory> parsed = new ArrayList<>();

        for(ShopCategory category : categories) {

            boolean hasDuplicated = false;

            for(ShopItem content : category.getContents()) {

                List<String> belonging = categories.stream()
                        .filter(other -> !other.equals(category) && other.contains(content))
                        .map(ShopCategory::getIdentifier)
                        .collect(Collectors.toList());

                if(belonging.size() == 0) continue;

                hasDuplicated = true;

                String identifiers = StringUtils.join(belonging, ", ");

                String message = String.format("Item '%s' is duplicated and belongs to the followed categories: %s.", content.getIdentifier(), identifiers);

                this.logger.broadcast(Level.WARNING, message);
            }

            if(hasDuplicated) {

                String message = String.format("Category '%s' has not been registered because it contains duplicated items.", category.getIdentifier());

                this.plugin.getLogger().log(Level.WARNING, message);

            } else parsed.add(category);
        }
        return parsed;
    }

    public boolean hasShopCategory(ItemStack stack) {
        return this.categories.stream().anyMatch(category -> category.contains(stack));
    }

    public ShopCategory getShopCategory(ShopItem item) {

        Optional<ShopCategory> optional = this.categories.stream()
                .filter(category -> category.contains(item))
                .findFirst();

        return optional.orElse(null);
    }

    public ShopCategory getShopCategory(ItemStack stack) {
        return this.getShopCategory(ShopItemFactory.getInstance(stack));
    }

    public ArrayList<ShopCategory> getShopCategories() {
        return new ArrayList<>(this.categories);
    }
}
