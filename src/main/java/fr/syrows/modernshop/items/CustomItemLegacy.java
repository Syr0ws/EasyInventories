package fr.syrows.modernshop.items;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItemLegacy extends CustomItem {

    public CustomItemLegacy() {} // Used for deserialization.

    public CustomItemLegacy(ItemStack stack) {
        super(stack);
    }

    public CustomItemLegacy(Material material) {
        super(material);
    }

    public CustomItemLegacy(Material material, byte data) {
        super(new ItemStack(material, 1, data));
    }

    @Override
    protected ItemStack deserializeItemStack(JsonObject object) {

        String material = object.get("material").getAsString();
        byte data = object.has("data") ? object.get("data").getAsByte() : 0;

        return new ItemStack(Material.valueOf(material), 1, data);
    }
}
