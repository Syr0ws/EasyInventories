package fr.syrows.modernshop.shops.items;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopItemLegacy extends ShopItem {

    private byte data;

    public ShopItemLegacy() {} // Used for deserialization.

    public ShopItemLegacy(ItemStack stack) {

        super(stack);

        this.data = stack.getData().getData();
    }

    public ShopItemLegacy(Material material, byte data) {

        super(material);

        this.data = data;
    }

    public byte getData() {
        return this.data;
    }

    @Override
    public void deserialize(JsonObject object) {

        super.deserialize(object);

        this.data = object.has("data") ? object.get("data").getAsByte() : 0;
    }

    @Override
    public JsonObject serialize() {

        JsonObject object = super.serialize();

        object.addProperty("data", this.data);

        return object;
    }

    @Override
    public String getIdentifier() {
        return String.format("%s:%d", super.getIdentifier(), this.data);
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemStack(super.getMaterial(), 1, this.data);
    }
}
