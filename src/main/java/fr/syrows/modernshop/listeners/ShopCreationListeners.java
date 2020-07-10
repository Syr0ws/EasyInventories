package fr.syrows.modernshop.listeners;

import fr.syrows.easyinventories.events.SimpleInventoryCloseEvent;
import fr.syrows.easyinventories.tools.CloseReason;
import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.inventories.categories.impl.ShopItemSelector;
import fr.syrows.modernshop.shops.ShopType;
import fr.syrows.modernshop.shops.exceptions.ShopCreationException;
import fr.syrows.modernshop.shops.tools.ShopCreationData;
import fr.syrows.modernshop.shops.tools.ShopCreationTool;
import fr.syrows.modernshop.shops.tools.ShopValidator;
import fr.syrows.modernshop.utils.parsers.MessageParser;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.HashMap;

public class ShopCreationListeners implements Listener {

    private ShopPlugin plugin;

    private HashMap<Player, ShopCreationData> data = new HashMap<>();

    public ShopCreationListeners(ShopPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        String header = event.getLine(0);
        ShopType type = ShopType.getTypeByHeader(header);

        if(type == null) return;

        Player player = event.getPlayer();
        Sign sign = (Sign) event.getBlock().getState();

        ShopItemSelector selector = this.plugin.getInventoryProvider().getItemSelector(this.plugin.getShopCategoryManager());
        ShopCreationData data = new ShopCreationData(player, sign, type, selector);
        ShopValidator validator = new ShopValidator(data, this.plugin.getShopManager());

        try {

            validator.validatePermission();
            validator.validateLimit();
            validator.validateSign();
            validator.validateChest();

            this.data.put(player, data);
            this.openItemSelector(player, selector);

        } catch (ShopCreationException e) {

            event.setCancelled(true);
            event.getBlock().breakNaturally();

            String message = ShopCreationTool.getErrorMessage(e.getType());
            player.sendMessage(MessageParser.parseColors(message));
        }
    }

    @EventHandler
    public void onInventoryClose(SimpleInventoryCloseEvent event) {

        if(event.getReason() != CloseReason.CLOSE_ALL) return;

        Player player = event.getPlayer();

        if(!this.data.containsKey(player)) return;

        ShopCreationData data = this.data.get(player);

        this.data.remove(player);

        if(data.getShopItemSelector().getSelectedItem() != null) {

            ShopCreationTool tool = new ShopCreationTool(this.plugin.getShopManager(), data);
            tool.createShop();

        } else {

            FileConfiguration config = this.plugin.getConfig();
            String message = config.getString("general-messages.item-selection-cancelled");

            player.sendMessage(MessageParser.parseColors(message));
            data.getSign().getBlock().breakNaturally();
        }
    }

    private void openItemSelector(Player player, ShopItemSelector selector) {

        FileConfiguration config = this.plugin.getConfig();
        player.sendMessage(MessageParser.parseColors(config.getString("general-messages.select-item")));

        selector.openInventory(player);
    }
}
