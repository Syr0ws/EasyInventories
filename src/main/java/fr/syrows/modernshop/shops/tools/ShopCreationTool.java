package fr.syrows.modernshop.shops.tools;

import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.modernshop.inventories.categories.impl.ShopViewer;
import fr.syrows.modernshop.shops.ShopManager;
import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.events.ShopCreateEvent;
import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.shops.ShopType;
import fr.syrows.modernshop.shops.exceptions.ShopCreationException;
import fr.syrows.modernshop.utils.parsers.MessageParser;
import fr.syrows.modernshop.utils.parsers.ShopMessageParser;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class ShopCreationTool {

    private ShopManager shopManager;
    private ShopCreationData data;

    public ShopCreationTool(ShopManager shopManager, ShopCreationData data) {
        this.shopManager = shopManager;
        this.data = data;
    }

    public Shop createShop() {

        Shop shop = null;

        try {

            shop = this.shopManager.createShop(this.data);

            ShopCreateEvent event = new ShopCreateEvent(this.data.getPlayer(), shop);

            Bukkit.getPluginManager().callEvent(event);

            if(event.isCancelled()) return null;

            this.shopManager.registerShop(shop);
            this.setSignLines(shop);

            String message = this.getConfig().getString("shop-creation-messages.created");
            this.data.getPlayer().sendMessage(MessageParser.parseColors(message));

            this.updateViews(shop);

        } catch (ShopCreationException e) {

            String message = getErrorMessage(e.getType());

            this.data.getSign().getBlock().breakNaturally();
            this.data.getPlayer().sendMessage(MessageParser.parseColors(message));

        } catch (IOException e) {

            e.printStackTrace();

            this.data.getSign().getBlock().breakNaturally();

            String message = this.getConfig().getString("shop-creation-messages.error");

            this.data.getPlayer().sendMessage(MessageParser.parseColors(message));
        }
        return shop;
    }

    private void setSignLines(Shop shop) {

        ConfigurationSection section = this.getConfig().getConfigurationSection("shop-signs");

        String key = shop.getType().name().toLowerCase();

        ConfigurationSection lines = section.getConfigurationSection(key);

        Sign sign = shop.getSign();

        for(int index = 0; index < 4; index++) {

            String line = lines.getString(Integer.toString(index));

            ShopMessageParser parser = new ShopMessageParser(shop, line);

            if(shop.getType() == ShopType.PLAYER) parser.parseOwner();

            sign.setLine(index, parser.get());
        }
        sign.update();
    }

    private void updateViews(Shop shop) {

        ShopPlugin plugin = JavaPlugin.getPlugin(ShopPlugin.class);

        InventoryManager manager = plugin.getInventoryProvider().getInventoryManager();

        String identifier = "shop_inventory#" + shop.getShopItem().getIdentifier();

        System.out.println(manager.getViewers(identifier).size());

        manager.getViewers(identifier).stream()
                .map(viewer -> (ShopViewer.ShopInventory) manager.getOpenedInventory(viewer))
                .forEach(inv -> {
                    inv.getPagination().addElement(shop);
                    inv.getContents().updatePageContents();
                });
    }

    private FileConfiguration getConfig() {
        return JavaPlugin.getPlugin(ShopPlugin.class).getConfig();
    }

    public static String getErrorMessage(ShopCreationException.ExceptionType type) {

        FileConfiguration config = JavaPlugin.getPlugin(ShopPlugin.class).getConfig();
        ConfigurationSection section = config.getConfigurationSection("shop-creation-messages");

        switch (type) {
            case NO_PERMISSION:
                return section.getString("no-permission");
            case SHOP_LIMIT_REACHED:
                return section.getString("shop-limit-reached");
            case NOT_A_WALL_SIGN:
                return section.getString("not-a-wall-sign");
            case NOT_PLACED_ON_CHEST:
                return section.getString("not-placed-on-chest");
            case DOUBLE_CHEST:
                return section.getString("double-chest");
            case CHEST_NOT_EMPTY:
                return section.getString("chest-not-empty");
            case NOT_PLACED_ON_FRONT_FACE:
                return section.getString("not-placed-on-front");
            case ITEM_ALREADY_USED:
                return section.getString("item-already-used");
            case BLOCK_ABOVE:
                return section.getString("block-above");
            default:
                throw new IllegalArgumentException(String.format("Type '%s' doesn't have error message.", type.name()));
        }
    }
}
