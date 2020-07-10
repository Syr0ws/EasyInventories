package fr.syrows.modernshop.listeners;

import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.inventories.ShopDeleteInventory;
import fr.syrows.modernshop.inventories.provider.InventoryProvider;
import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.shops.ShopManager;
import fr.syrows.modernshop.shops.owners.ShopOwner;
import fr.syrows.modernshop.shops.tools.ShopDeletionTool;
import fr.syrows.modernshop.utils.blocks.ChestUtils;
import fr.syrows.modernshop.utils.parsers.MessageParser;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.List;
import java.util.Optional;

public class ShopBlockListeners implements Listener {

    private ShopPlugin plugin;
    private ShopManager manager;

    public ShopBlockListeners(ShopPlugin plugin) {
        this.plugin = plugin;
        this.manager = plugin.getShopManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Block block = event.getBlock();

        if(!this.manager.isShopBlock(block)) return;

        event.setCancelled(true);

        Optional<Shop> optional = this.manager.getShopAt(block.getLocation());

        if(!optional.isPresent())
            throw new UnsupportedOperationException("Shop block detected but cannot retrieve shop.");

        Player player = event.getPlayer();
        Shop shop = optional.get();

        if(this.canBreakShop(shop, player)) {

            ShopDeletionTool deletion = new ShopDeletionTool(player, this.manager, shop);

            InventoryProvider provider = this.plugin.getInventoryProvider();

            ShopDeleteInventory inventory = provider.getDeleteInventory(deletion);
            inventory.open(player);

        } else {

            FileConfiguration config = this.plugin.getConfig();
            ConfigurationSection section = config.getConfigurationSection("shop-messages");

            player.sendMessage(MessageParser.parseColors(section.getString("cannot-break-shop")));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        if(event.isCancelled()) return;

        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        if(this.isBlockAboveShop(block)) {

            event.setCancelled(true);

            FileConfiguration config = this.plugin.getConfig();
            ConfigurationSection section = config.getConfigurationSection("shop-messages");

            player.sendMessage(MessageParser.parseColors(section.getString("place-block-above")));
        }
    }

    @EventHandler
    public void onChestPlace(BlockPlaceEvent event) {

        if(event.isCancelled()) return;

        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        if(block.getType() != Material.CHEST) return;

        Chest chest = (Chest) block.getState();

        if(!this.isChestValid(chest)) {

            event.setCancelled(true);

            FileConfiguration config = this.plugin.getConfig();
            ConfigurationSection section = config.getConfigurationSection("shop-messages");

            player.sendMessage(MessageParser.parseColors(section.getString("double-chest-creation")));
        }
    }

    @EventHandler
    public void onBlockChange(BlockPhysicsEvent event) {

        Block block = event.getBlock();

        if(block.getType() == Material.AIR) return;

        if(this.isBlockAboveShop(block)) block.breakNaturally();
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {

        Block target = event.getBlockClicked().getRelative(event.getBlockFace());

        if(this.isBlockAboveShop(target)) {

            event.setCancelled(true);

            FileConfiguration config = this.plugin.getConfig();
            ConfigurationSection section = config.getConfigurationSection("shop-messages");

            event.getPlayer().sendMessage(MessageParser.parseColors(section.getString("place-liquid-above")));
        }
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {

        boolean blockAboveShop = this.isBlockAboveShop(event.getToBlock());

        event.setCancelled(blockAboveShop);
    }

    @EventHandler
    public void onPistonExpend(BlockPistonExtendEvent event) {

        List<Block> blocks = event.getBlocks();

        BlockFace direction = event.getDirection();

        boolean blockAboveShop;

        if(blocks.size() == 0) {

            Block piston = event.getBlock();
            Block pistonFront = piston.getRelative(direction);

            blockAboveShop = this.isBlockAboveShop(pistonFront);

        } else {

            blockAboveShop = blocks.stream()
                    .map(block -> block.getRelative(event.getDirection()))
                    .anyMatch(this::isBlockAboveShop);
        }
        event.setCancelled(blockAboveShop);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        List<Block> blocks = event.blockList();

        blocks.removeIf(block -> this.manager.isShopBlock(block));
    }

    private boolean isBlockAboveShop(Block block) {

        Block under = block.getRelative(BlockFace.DOWN);

        return this.manager.isShopBlock(under);
    }

    private boolean isChestValid(Chest placed) {

        if(!ChestUtils.isDoubleChest(placed)) return true;

        BlockFace[] faces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};

        for(BlockFace face : faces) {

            Block relative = placed.getBlock().getRelative(face);

            if(relative.getType() != Material.CHEST) continue;

            Chest chest = (Chest) relative.getState();

            if(!ChestUtils.isDoubleChest(chest)) continue;

            if(this.manager.isShopBlock(relative)) return false;
        }
        return true;
    }

    private boolean canBreakShop(Shop shop, Player player) {

        ShopOwner owner = shop.getOwner();

        if(player.hasPermission("modernshop.shops.delete.bypass")) return true;

        return !owner.isNull() && owner.getUUID().equals(player.getUniqueId());
    }
}
