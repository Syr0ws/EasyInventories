package fr.syrows.modernshop.categories;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.syrows.modernshop.items.CustomItem;
import fr.syrows.modernshop.items.CustomItemFactory;
import fr.syrows.modernshop.shops.items.ShopItem;
import fr.syrows.modernshop.shops.items.ShopItemFactory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShopCategory {

    private String identifier, name;
    private ItemStack item;
    private Map<String, ShopItem> contents;

    public ShopCategory(String identifier, String name, ItemStack item, ArrayList<ShopItem> contents) {

        this.identifier = identifier;
        this.name = name;
        this.item = item;
        this.contents = new LinkedHashMap<>();

        contents.forEach(content -> this.contents.put(content.getIdentifier(), content));
    }

    public boolean contains(ItemStack stack) {
        return this.contains(ShopItemFactory.getInstance(stack));
    }

    public boolean contains(ShopItem item) {
        return this.contents.containsKey(item.getIdentifier());
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public ArrayList<ShopItem> getContents() {
        return new ArrayList<>(this.contents.values());
    }

    public static class Deserializer implements JsonDeserializer<ShopCategory> {

        @Override
        public ShopCategory deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {

            JsonObject object = element.getAsJsonObject();

            String identifier = object.get("identifier").getAsString();
            String name = object.get("name").getAsString();

            CustomItem item = CustomItemFactory.getInstance();
            item.deserialize(object.get("item").getAsJsonObject());

            ArrayList<ShopItem> contents = new ArrayList<>();

            JsonArray array = object.get("contents").getAsJsonArray();

            for(int i = 0; i < array.size(); i++) {

                ShopItem content = context.deserialize(array.get(i), ShopItem.class);

                contents.add(content);
            }
            return new ShopCategory(identifier, name, item.getItemStack(), contents);
        }
    }
}
