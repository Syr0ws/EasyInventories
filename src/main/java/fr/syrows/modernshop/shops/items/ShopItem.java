package fr.syrows.modernshop.shops.items;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

public class ShopItem {

    private Material material;

    public ShopItem() {} // Used for deserialization.

    public ShopItem(ItemStack stack) {
        this.material = stack.getType();
    }

    public ShopItem(Material material) {
        this.material = material;
    }

    public void deserialize(JsonObject object) {

        String materialValue = object.get("material").getAsString();

        this.material = Material.valueOf(materialValue);
    }

    public JsonObject serialize() {

        JsonObject object = new JsonObject();

        object.addProperty("material", this.material.name());

        return object;
    }

    public String getIdentifier() {
        return this.material.name();
    }

    public Material getMaterial() {
        return this.material;
    }

    public ItemStack getItemStack() {
        return new ItemStack(this.material);
    }

    public static class Converter implements JsonSerializer<ShopItem>, JsonDeserializer<ShopItem> {

        @Override
        public ShopItem deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {

            ShopItem item = ShopItemFactory.getInstance();
            item.deserialize(element.getAsJsonObject());

            return item;
        }

        @Override
        public JsonElement serialize(ShopItem item, Type type, JsonSerializationContext context) {
            return item.serialize();
        }
    }
}
