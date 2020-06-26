package fr.syrows;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Test implements Listener {

    private EasyInventoriesPlugin plugin;

    public Test(EasyInventoriesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(event.getItem() == null || event.getItem().getType() != Material.STONE) return;
    }
}
