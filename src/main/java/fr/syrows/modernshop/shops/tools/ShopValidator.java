package fr.syrows.modernshop.shops.tools;

import fr.syrows.modernshop.shops.ShopManager;
import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.shops.ShopType;
import fr.syrows.modernshop.shops.exceptions.ShopCreationException;
import fr.syrows.modernshop.shops.items.ShopItem;
import fr.syrows.modernshop.utils.blocks.ChestUtils;
import fr.syrows.modernshop.utils.blocks.SignUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.List;

import static fr.syrows.modernshop.shops.exceptions.ShopCreationException.ExceptionType.*;

public class ShopValidator {

    private ShopCreationData data;
    private ShopManager manager;

    public ShopValidator(ShopCreationData data, ShopManager manager) {
        this.data = data;
        this.manager = manager;
    }

    public void validatePermission() throws ShopCreationException {

        Player player = this.data.getPlayer();
        ShopType type = this.data.getShopType();

        if(!player.hasPermission(type.getPermission()))
            throw new ShopCreationException("No permission", NO_PERMISSION);
    }

    public void validateLimit() throws ShopCreationException {

        Player player = this.data.getPlayer();

        int limit = this.manager.getShopLimit(player);

        if(limit == -1) return;

        int shops = this.manager.countShops(player.getUniqueId());

        if(limit >= shops)
            throw new ShopCreationException("Limit reached.", SHOP_LIMIT_REACHED);
    }

    public void validateChest() throws ShopCreationException {

        Chest chest = SignUtils.getChestSignIsPlacedOn(this.data.getSign());

        if(ChestUtils.isDoubleChest(chest))
            throw new ShopCreationException("Double chest.", DOUBLE_CHEST);

        if(!ChestUtils.isChestEmpty(chest))
            throw new ShopCreationException("Chest not empty.", CHEST_NOT_EMPTY);

        Block above = chest.getBlock().getRelative(BlockFace.UP);

        if(above.getType() != Material.AIR)
            throw new ShopCreationException("Block above shop block.", BLOCK_ABOVE);
    }

    public void validateSign() throws ShopCreationException {

        Sign sign = this.data.getSign();

        if(!SignUtils.isWallSign(sign))
            throw new ShopCreationException("Sign not placed on a wall.", NOT_A_WALL_SIGN);

       if(!SignUtils.isPlacedOnChest(sign))
           throw new ShopCreationException("Sign not placed on a chest.", NOT_PLACED_ON_CHEST);

       Block above = sign.getBlock().getRelative(BlockFace.UP);

       if(above.getType() != Material.AIR)
           throw new ShopCreationException("Block above shop block.", BLOCK_ABOVE);

        Chest chest = SignUtils.getChestSignIsPlacedOn(sign);

        BlockFace front = ChestUtils.getFrontFace(chest);
        BlockFace attached = SignUtils.getAttachedFace(sign);

        if(!front.equals(attached.getOppositeFace()))
            throw new ShopCreationException("Sign not placed on the front face of the chest.", NOT_PLACED_ON_FRONT_FACE);
    }

    public void validateItem() throws ShopCreationException {

        ShopItem item = this.data.getShopItem();

        if(item == null)
            throw new NullPointerException("ShopItem is null.");

        Player player = this.data.getPlayer();

        List<Shop> shops = this.manager.getShops(player.getUniqueId());

        boolean used = shops.stream().anyMatch(shop -> shop.getShopItem().getIdentifier().equals(item.getIdentifier()));

        if(used)
            throw new ShopCreationException("ShopItem already used.", ITEM_ALREADY_USED);
    }
}
