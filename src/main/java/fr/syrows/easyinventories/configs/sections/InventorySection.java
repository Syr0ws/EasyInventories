package fr.syrows.easyinventories.configs.sections;

import fr.syrows.easyinventories.configs.tools.InventoryItem;
import fr.syrows.easyinventories.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventorySection {

    private ConfigurationSection section;

    public InventorySection(ConfigurationSection section) {
        this.section = section;
    }

    public String getInventoryTitle() {

        if(!this.section.contains("title"))
            throw new NullPointerException("No inventory title found.");

        return this.section.getString("title");
    }

    public List<InventoryItem> getInventoryContents() {

        ConfigurationSection contents = this.section.getConfigurationSection("contents");

        List<InventoryItem> items = new ArrayList<>();

        for(String key : contents.getKeys(false)) {

            ConfigurationSection itemSection = contents.getConfigurationSection(key);

            InventoryItem item = this.getInventoryItem(itemSection);

            items.add(item);
        }
        return items;
    }

    public InventoryItem getInventoryItem(ConfigurationSection section) {

        ItemStack stack = section.getItemStack("item");
        int[] slots = this.getSlots(section);

        return new InventoryItem(stack, slots);
    }

    public PaginationSection getPaginationSection() {

        if(!this.section.isSet("pagination"))
            throw new NullPointerException("No pagination section found.");

        return new PaginationSection(this.section.getConfigurationSection("pagination"));
    }

    public ConfigurationSection getConfigurationSection() {
        return this.section;
    }

    private int[] getSlots(ConfigurationSection section) {

        int[] slots = new int[1];

        if(section.contains("slot")) slots[0] = section.getInt("slot");
        else if(section.contains("slots")) slots = Utils.getIntArray(section.getIntegerList("slots"));
        else throw new NullPointerException(String.format("No slot found at '%s'.", section.getCurrentPath()));

        return slots;
    }
}
