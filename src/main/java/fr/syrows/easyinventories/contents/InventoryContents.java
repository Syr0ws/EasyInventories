package fr.syrows.easyinventories.contents;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.validators.SlotValidator;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;

public abstract class InventoryContents {

    private ClickableItem[][] contents;

    public InventoryContents(SimpleInventory inventory) {

        int rows = inventory.getRows(), columns = inventory.getColumns();

        this.contents = new ClickableItem[rows][columns];
    }

    public abstract SimpleInventory getInventory();

    public void setItem(int slot, ClickableItem item) {

        SimpleInventory inventory = this.getInventory();

        SlotValidator.validateSlot(inventory, slot);

        int row = SlotUtils.getRow(inventory.getType(), slot);
        int column = SlotUtils.getColumn(inventory.getType(), slot);

        this.contents[row][column] = item;
        this.update(slot);
    }

    public void setItem(int row, int column, ClickableItem item) {

        SlotValidator.validatePosition(this.getInventory(), row, column);

        this.contents[row - 1][column - 1] = item;
        this.update(row, column);
    }

    public void setItems(ClickableItem item, int... slots) {

        if(slots.length == 0)
            throw new IllegalArgumentException("No slots specified.");

        for(int slot : slots) this.setItem(slot, item);
    }

    public Optional<ClickableItem> getItem(int slot) {

        SimpleInventory inventory = this.getInventory();

        SlotValidator.validateSlot(inventory, slot);

        int row = SlotUtils.getRow(inventory.getType(), slot);
        int column = SlotUtils.getColumn(inventory.getType(), slot);

        ClickableItem item = this.contents[row][column];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    public Optional<ClickableItem> getItem(int row, int column) {

        SlotValidator.validatePosition(this.getInventory(), row, column);

        ClickableItem item =  this.contents[row - 1][column - 1];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    private void update(int slot) {

        SimpleInventory inventory = this.getInventory();

        Optional<ClickableItem> optional = this.getItem(slot);
        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        inventory.getInventory().setItem(slot, stack);
    }

    private void update(int row, int column) {

        SimpleInventory inventory = this.getInventory();

        int slot = SlotUtils.getSlot(inventory.getType(), row, column);

        Optional<ClickableItem> optional = this.getItem(slot);

        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        inventory.getInventory().setItem(slot, stack);
    }

    public ClickableItem[][] getContents() {
        return Arrays.copyOf(this.contents, this.contents.length);
    }
}
