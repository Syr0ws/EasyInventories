package fr.syrows.modernshop.utils;

import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.utils.blocks.SignUtils;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

public class ShopUtils {

    public static String getIdentifier(Location location) {

        String format = Shop.SHOP_ID_FORMAT;

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        String world = location.getWorld().getName();

        return String.format(format, x, y, z, world);
    }

    public static Location getTeleportLocation(Shop shop) {

        Sign sign = shop.getSign();
        Location loc = sign.getLocation();

        double x = loc.getBlockX() + 0.5;
        double y = loc.getBlockY();
        double z = loc.getBlockZ() + 0.5;

        float yaw = 0f;

        switch (SignUtils.getFacing(sign)) {
            case NORTH:
                yaw = 180f;
                break;
            case SOUTH:
                yaw = 0f;
                break;
            case EAST:
                yaw = -90f;
                break;
            case WEST:
                yaw = 90f;
                break;
        }
        return new Location(loc.getWorld(), x, y, z, yaw, 0f);
    }
}
