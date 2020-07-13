package fr.syrows.easyinventories.configs.sections.impl;

import fr.syrows.easyinventories.configs.sections.IMemorySection;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ItemSection extends IMemorySection {

    public ItemSection(ConfigurationSection section) {
        super(section);
    }

    public ItemStack getItemStack(String path) {

        if(!super.section.isItemStack(path))
            throw new UnsupportedOperationException(String.format("No ItemStack found at '%s.%s'", super.section.getCurrentPath(), path));

        return new ItemStack(super.section.getItemStack(path));
    }

    public ItemStack getDefaultItem() {

        if(!super.section.contains("item"))
            throw new NullPointerException(String.format("Path '%s.item' not found.", super.section.getCurrentPath()));

        return this.getItemStack("item");
    }

    public int getSlot(String path) {
        return super.getInt(path);
    }

    public int getDefaultSlot() {

        if(!super.section.contains("slot"))
            throw new NullPointerException(String.format("Path '%s.slot' not found.", super.section.getCurrentPath()));

        return this.getSlot("slot");
    }

    public int[] getSlots(String path) {
        return super.getIntArray(path);
    }

    public int[] getDefaultSlots() {

        int[] slots = new int[1];

        if(super.section.contains("slot")) slots[0] = this.getSlot("slot");
        else if(super.section.contains("slots")) slots = this.getSlots("slots");
        else throw new NullPointerException(String.format("No slot found at '%s'.", super.section.getCurrentPath()));

        return slots;
    }
}
