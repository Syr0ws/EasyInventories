package fr.syrows.modernshop.utils.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.material.Directional;

public class SignUtils {

    public static final int HEADER_INDEX = 0;

    public static boolean isWallSign(Sign sign) {
        return sign.getType() == Material.WALL_SIGN;
    }

    public static BlockFace getAttachedFace(Sign sign) {

        if(!isWallSign(sign))
            throw new IllegalArgumentException("Sign is not placed on a wall.");

        Directional directional = (Directional) sign.getData();

        return directional.getFacing().getOppositeFace();
    }

    public static boolean isPlacedOnChest(Sign sign) {

        if(!isWallSign(sign)) return false;

        BlockFace attachedFace = getAttachedFace(sign);
        Block block = sign.getBlock().getRelative(attachedFace);

        return block.getType() == Material.CHEST;
    }

    public static Chest getChestSignIsPlacedOn(Sign sign) {

        if(!isWallSign(sign))
            throw new IllegalArgumentException("Sign is not placed on a wall.");

        if(!isPlacedOnChest(sign))
            throw new IllegalArgumentException("Sign is not placed on a chest.");

        BlockFace attachedFace = getAttachedFace(sign);
        Block block = sign.getBlock().getRelative(attachedFace);

        return (Chest) block.getState();
    }

    public static BlockFace getFacing(Sign sign) {

        Directional directional = (Directional) sign.getData();

        return directional.getFacing().getOppositeFace();
    }
}
