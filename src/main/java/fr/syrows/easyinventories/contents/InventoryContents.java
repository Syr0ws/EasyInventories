package fr.syrows.easyinventories.contents;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.AdvancedInventory;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;

public abstract class InventoryContents {

    private ClickableItem[][] contents;

    public InventoryContents(AdvancedInventory inventory) {

        int rows = inventory.getRows(), columns = inventory.getColumns();

        this.contents = new ClickableItem[rows][columns];
    }

    public abstract AdvancedInventory getInventory();

    public void setItem(int slot, ClickableItem item) {

        AdvancedInventory inventory = this.getInventory();

        // SlotValidator.validateSlot(this.inventory, slot);

        int row = SlotUtils.getRow(inventory.getSort(), slot);
        int column = SlotUtils.getColumn(inventory.getSort(), slot);

        this.contents[row][column] = item;
        this.update(slot);
    }

    public void setItem(int row, int column, ClickableItem item) {

        // SlotValidator.validateRow(this.inventory, row);
        // SlotValidator.validateColumn(this.inventory, column);

        this.contents[row - 1][column - 1] = item;
        this.update(row, column);
    }

    public void setItems(ClickableItem item, int... slots) {

        if(slots.length == 0)
            throw new IllegalArgumentException("No slots specified.");

        for(int slot : slots) this.setItem(slot, item);
    }

    public Optional<ClickableItem> getItem(int slot) {

        AdvancedInventory inventory = this.getInventory();

        // SlotValidator.validateSlot(this.inventory, slot);

        int row = SlotUtils.getRow(inventory.getSort(), slot);
        int column = SlotUtils.getColumn(inventory.getSort(), slot);

        ClickableItem item = this.contents[row][column];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    public Optional<ClickableItem> getItem(int row, int column) {

        // SlotValidator.validateRow(this.inventory, row);
        // SlotValidator.validateColumn(this.inventory, column);

        ClickableItem item =  this.contents[row - 1][column - 1];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    private void update(int slot) {

        AdvancedInventory inventory = this.getInventory();

        Optional<ClickableItem> optional = this.getItem(slot);
        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        inventory.getInventory().setItem(slot, stack);
    }

    private void update(int row, int column) {

        AdvancedInventory inventory = this.getInventory();

        int slot = SlotUtils.getSlot(inventory.getSort(), row, column);

        Optional<ClickableItem> optional = this.getItem(slot);

        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        inventory.getInventory().setItem(slot, stack);
    }

    public ClickableItem[][] getContents() {
        return Arrays.copyOf(this.contents, this.contents.length);
    }
}
