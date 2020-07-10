package fr.syrows.modernshop.utils.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.MaterialData;

public class ChestUtils {

    public static Chest getChestAt(Location location) {

        Block block = location.getBlock();
        BlockState state = block.getState();

        if(!(state instanceof Chest))
            throw new IllegalArgumentException("Block is not a chest.");

        return (Chest) state;
    }

    public static boolean isDoubleChest(Chest chest) {

        InventoryHolder holder = chest.getInventory().getHolder();

        return holder instanceof DoubleChest;
    }

    public static boolean isChestEmpty(Chest chest) {

        Inventory inventory = chest.getBlockInventory();

        for(int i = 0; i < inventory.getSize(); i++) {

            if(inventory.getItem(i) != null) return false;
        }
        return true;
    }

    public static BlockFace getFrontFace(Chest chest) {

        MaterialData data = chest.getData();

        org.bukkit.material.Chest material = (org.bukkit.material.Chest) data;

        return material.getFacing();
    }

    public static Sign getFrontSign(Chest chest) {

        BlockFace frontFace = getFrontFace(chest);
        Block frontBlock = chest.getBlock().getRelative(frontFace);

        return frontBlock.getType() == Material.WALL_SIGN ? (Sign) frontBlock.getState() : null;
    }
}
