package fr.syrows;

import fr.syrows.inventories.interfaces.EasyInventory;
import fr.syrows.inventories.interfaces.impl.FastInventory;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Test implements Listener {

    private EasyInventoriesPlugin plugin;

    public Test(EasyInventoriesPlugin plugin) {
        this.plugin = plugin;
    }

    public EasyInventory getInventory() {

        FastInventory inventory = new FastInventory.Builder(this.plugin.getDefaultManager())
                .withIdentifier("my_inventory")
                .withTitle("&eInventory")
                .withSize(54)
                .get();

        this.setContents(inventory);

        return inventory;
    }

    public void setContents(EasyInventory inventory) {

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(event.getItem() == null || event.getItem().getType() != Material.STONE) return;

        this.getInventory().open(event.getPlayer());
    }
}
