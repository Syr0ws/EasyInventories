package fr.syrows.modernshop.items;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.syrows.modernshop.utils.parsers.MessageParser;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomItem {

    protected ItemStack stack;
    protected ItemMeta meta;

    public CustomItem() {} // Used for deserialization.

    public CustomItem(ItemStack stack) {
        this.stack = stack;
        this.meta = stack.getItemMeta();
    }

    public CustomItem(Material material) {
        this(new ItemStack(material));
    }

    public CustomItem withDisplayName(String name) {

        this.meta.setDisplayName(MessageParser.parseColors(name));

        return this;
    }

    public CustomItem withLore(List<String> lore) {

        lore = lore.stream().map(MessageParser::parseColors).collect(Collectors.toList());

        this.meta.setLore(lore);

        return this;
    }

    public CustomItem withFlags(ItemFlag... flags) {

        this.meta.addItemFlags(flags);

        return this;
    }

    public CustomItem withEnchant() {

        this.meta.addEnchant(Enchantment.DURABILITY, 1, true);
        this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        return this;
    }

    public CustomItem addLoreLine(String line) {

        List<String> lore = this.meta.hasLore() ? this.meta.getLore() : new ArrayList<>();

        lore.add(MessageParser.parseColors(line));

        this.meta.setLore(lore);

        return this;
    }

    public CustomItem deserialize(JsonObject object) {

        this.stack = this.deserializeItemStack(object);
        this.meta = this.stack.getItemMeta();

        if(object.has("name"))
            this.withDisplayName(object.get("name").getAsString());

        if(object.has("enchant")) {

            boolean enchant = object.get("enchant").getAsBoolean();

            if(enchant) this.withEnchant();
        }

        if(object.has("lore")) {

            JsonArray array = object.get("lore").getAsJsonArray();

            for(int i = 0; i < array.size(); i++) this.addLoreLine(array.get(i).getAsString());
        }

        if(object.has("flags")) {

            JsonArray array = object.get("flags").getAsJsonArray();

            for(int i = 0; i < array.size(); i++) this.withFlags(ItemFlag.valueOf(array.get(i).getAsString()));
        }
        return this;
    }

    protected ItemStack deserializeItemStack(JsonObject object) {

        String material = object.get("material").getAsString();

        return new ItemStack(Material.valueOf(material));
    }

    public ItemStack getItemStack() {

        this.stack.setItemMeta(this.meta);

        return this.stack;
    }
}
