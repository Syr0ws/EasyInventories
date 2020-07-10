package fr.syrows.easyinventories.configs.tools;

import fr.syrows.easyinventories.tools.StringParser;
import fr.syrows.easyinventories.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

public class InventoryItem {

    private ItemStack stack;
    private int[] slots;

    public InventoryItem(ItemStack stack, int[] slots) {
        this.stack = stack;
        this.slots = slots;
    }

    public InventoryItem(ItemStack stack, int slot) {
        this(stack, new int[]{slot});
    }

    public ItemStack getItemStack() {
        return this.stack;
    }

    public ItemStack getItemStack(StringParser parser) {
        return ItemUtils.parse(this.stack, parser);
    }

    public int[] getSlots() {
        return this.slots;
    }

    public int getSlot() {

        if(this.slots.length == 0)
            throw new IndexOutOfBoundsException("No available slot.");

        if(this.slots.length > 1)
            throw new IllegalStateException("There are several slots available.");

        return this.slots[0];
    }
}
