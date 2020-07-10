package fr.syrows.modernshop.shops.tools;

import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.events.ShopDeleteEvent;
import fr.syrows.modernshop.inventories.categories.impl.ShopViewer;
import fr.syrows.modernshop.inventories.provider.InventoryProvider;
import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.shops.ShopManager;
import fr.syrows.modernshop.shops.ShopType;
import fr.syrows.modernshop.utils.parsers.MessageParser;
import fr.syrows.modernshop.utils.parsers.ShopMessageParser;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class ShopDeletionTool {

    private Player remover;

    private ShopManager shopManager;
    private Shop shop;

    public ShopDeletionTool(Player remover, ShopManager shopManager, Shop shop) {
        this.remover = remover;
        this.shopManager = shopManager;
        this.shop = shop;
    }

    public void confirm() {

        ShopDeleteEvent event = new ShopDeleteEvent(this.remover, this.shop);

        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled()) return;

        this.shopManager.removeShop(this.shop);
        this.shop.getSign().getBlock().breakNaturally();

        this.setRemainingItems();
        this.sendDeletionMessage();
        this.updateViews();
    }

    public void cancel() {

        String message = this.getConfig().getString("shop-messages.deletion-cancelled");
        this.remover.sendMessage(MessageParser.parseColors(message));
    }

    private void updateViews() {

        ShopPlugin plugin = JavaPlugin.getPlugin(ShopPlugin.class);

        InventoryManager manager = plugin.getInventoryProvider().getInventoryManager();

        String identifier = "shop_inventory#" + this.shop.getShopItem().getIdentifier();

        manager.getViewers(identifier).stream()
                .map(viewer -> (ShopViewer.ShopInventory) manager.getOpenedInventory(viewer))
                .forEach(inv -> {
                    inv.getPagination().removeElement(this.shop);
                    inv.getContents().updatePageContents();
                });
    }

    private void setRemainingItems() {

    }

    private void sendDeletionMessage() {

        ConfigurationSection section = this.getConfig().getConfigurationSection("shop-messages.shop-deleted");

        String message;

        if(this.shop.getType() == ShopType.PLAYER) {

            UUID removerUUID = this.remover.getUniqueId();
            UUID ownerUUID = this.shop.getOwner().getUUID();

            if(removerUUID.equals(ownerUUID)) message = section.getString("self");
            else message = new ShopMessageParser(this.shop, section.getString("other")).parseOwner().get();

        } else message = section.getString("admin");

        this.remover.sendMessage(MessageParser.parseColors(message));
    }

    private FileConfiguration getConfig() {
        return JavaPlugin.getPlugin(ShopPlugin.class).getConfig();
    }

    public Player getRemover() {
        return this.remover;
    }

    public Shop getShop() {
        return this.shop;
    }
}
