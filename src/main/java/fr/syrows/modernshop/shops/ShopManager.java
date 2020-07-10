package fr.syrows.modernshop.shops;

import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.logs.CustomLogger;
import fr.syrows.modernshop.shops.exceptions.ShopCreationException;
import fr.syrows.modernshop.shops.items.ShopItem;
import fr.syrows.modernshop.shops.tools.ShopCreationData;
import fr.syrows.modernshop.shops.tools.ShopValidator;
import fr.syrows.modernshop.utils.ShopUtils;
import fr.syrows.modernshop.utils.blocks.SignUtils;
import fr.syrows.modernshop.utils.parsers.ShopMessageParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ShopManager {

    private final CustomLogger logger;

    private ShopPlugin plugin;
    private ShopPersistence persistence;

    private HashMap<String, Shop> shops = new HashMap<>();

    public ShopManager(ShopPlugin plugin) {
        this.plugin = plugin;
        this.persistence = new ShopPersistence(plugin);
        this.logger = ShopPlugin.getCustomLogger();
    }

    public void init() {

        this.createShopFolder();
        this.loadShops();
    }

    public Shop createShop(ShopCreationData data) throws ShopCreationException {

        this.validateShop(data);

        ShopItem item = data.getShopItem();
        ShopType type = data.getShopType();

        Chest chest = SignUtils.getChestSignIsPlacedOn(data.getSign());

        Shop shop = type == ShopType.PLAYER ? new Shop(data.getPlayer(), chest, item) : new Shop(chest, item);

        ShopPriority priority = this.getPriority(shop);
        shop.setPriority(priority);

        return shop;
    }

    public void registerShop(Shop shop) throws IOException {

        this.shops.put(shop.getIdentifier(), shop);
        this.persistence.saveShop(shop);
    }

    public void removeShop(Shop shop) {

        try {

            this.persistence.removeShop(shop);

        } catch (IOException e) {

            e.printStackTrace();
        }
        this.shops.remove(shop.getIdentifier());
    }

    public void teleportToShop(Shop shop, Player player) {

        Location location = ShopUtils.getTeleportLocation(shop);

        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("shop-messages.teleportation");

        String message;

        if(shop.getType() == ShopType.ADMIN) message = section.getString("admin");
        else if(shop.getOwner().getUUID().equals(player.getUniqueId())) message = section.getString("self");
        else message = new ShopMessageParser(shop, section.getString("other")).parseOwner().get();

        this.plugin.getTeleportationManager().teleport(player, location, message);
    }

    private void validateShop(ShopCreationData data) throws ShopCreationException {

        ShopValidator validator = new ShopValidator(data, this);

        validator.validatePermission();
        validator.validateSign();
        validator.validateChest();

        if(data.getShopType() == ShopType.PLAYER) {

            validator.validateLimit();
            validator.validateItem();
        }
    }

    private void createShopFolder() {

        try {

            this.persistence.createShopFolder();

        } catch (IOException e) {

            e.printStackTrace();

            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
    }

    private void loadShops() {

        try {

            long begin = System.currentTimeMillis();

            ArrayList<Shop> shops = this.persistence.loadShops();

            shops.forEach(shop -> this.shops.put(shop.getIdentifier(), shop));

            long end = System.currentTimeMillis();

            this.logger.broadcast(Level.INFO, String.format("%d shops were loaded. Took %d ms.", shops.size(), end - begin));

        } catch (IOException e) {

            e.printStackTrace();

            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
    }

    public int countShops(UUID uuid) {

        return (int) this.shops.values().stream()
                .filter(shop -> !shop.getOwner().isNull() && shop.getOwner().getUUID().equals(uuid))
                .count();
    }

    public int getShopLimit(Player player) {

        if(player.hasPermission("modernshop.limit.bypass")) return -1;

        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("shop-groups");

        Optional<Integer> optional = section.getKeys(false).stream()
                .filter(key -> player.hasPermission("modernshop.group." + key) || key.equals("default"))
                .map(key -> section.getInt(key + ".limit"))
                .max(Integer::compare);

        return optional.filter(limit -> limit >= 1).orElse(1);
    }

    public ShopPriority getPriority(Shop shop) {

        if(shop.getType() == ShopType.ADMIN) return ShopPriority.ADMIN;

        Player owner = shop.getOwner().getPlayer();

        if(owner == null)
            throw new NullPointerException("Shop owner must be connected to do this action.");

        return this.getPriorityFor(owner);
    }

    public ShopPriority getPriorityFor(Player player) {

        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("shop-groups");

        Optional<ShopPriority> optional = section.getKeys(false).stream()
                .filter(key -> player.hasPermission("modernshop.group." + key) || key.equals("default"))
                .map(key -> ShopPriority.valueOf(section.getString(key + ".priority")))
                .max(Comparator.comparingInt(ShopPriority::getPower));

        return optional.orElse(ShopPriority.NORMAL);
    }

    public List<Shop> getShops(UUID uuid) {

        return this.shops.values().stream()
                .filter(shop -> !shop.getOwner().isNull() && shop.getOwner().getUUID().equals(uuid))
                .collect(Collectors.toList());
    }

    public List<Shop> getShops(ShopItem item) {

        String identifier = item.getIdentifier();

        return this.shops.values().stream()
                .filter(shop -> shop.getShopItem().getIdentifier().equals(identifier))
                .collect(Collectors.toList());
    }

    public List<Shop> getShopsByPriority(ShopItem item) {

        String identifier = item.getIdentifier();

        return this.shops.values().stream()
                .filter(shop -> shop.getShopItem().getIdentifier().equals(identifier))
                .sorted((s1, s2) -> Integer.compare(s2.getPriority().getPower(), s1.getPriority().getPower()))
                .collect(Collectors.toList());
    }

    public boolean exists(Shop shop) {
        return this.shops.containsKey(shop.getIdentifier());
    }

    public Collection<Shop> getShops() {
        return this.shops.values();
    }

    public boolean isShopBlock(Block block) {

        BlockState state = block.getState();

        if(state instanceof Chest) return this.isShopChest((Chest) state);
        else if(state instanceof Sign) return this.isShopSign((Sign) state);
        else return false;
    }

    public boolean isShopChest(Chest chest) {
        return this.getShopAt(chest.getLocation()).isPresent();
    }

    public boolean isShopSign(Sign sign) {

        if(!SignUtils.isWallSign(sign)) return false;

        if(!SignUtils.isPlacedOnChest(sign)) return false;

        Chest chest = SignUtils.getChestSignIsPlacedOn(sign);

        Optional<Shop> optional = this.getShopAt(chest.getLocation());

        return optional.map(shop -> shop.getSign().equals(sign)).orElse(false);

    }

    public Optional<Shop> getShopAt(Location location) {

        World world = location.getWorld();
        Block block = world.getBlockAt(location);

        if(block.getType() == Material.WALL_SIGN) {

            Sign sign = (Sign) block.getState();

            if(!SignUtils.isPlacedOnChest(sign)) return Optional.empty();

            Chest chest = SignUtils.getChestSignIsPlacedOn(sign);

            location = chest.getLocation();
        }
        String identifier = ShopUtils.getIdentifier(location);

        return Optional.ofNullable(this.shops.getOrDefault(identifier, null));
    }

    public ShopPersistence getPersistence() {
        return this.persistence;
    }
}
