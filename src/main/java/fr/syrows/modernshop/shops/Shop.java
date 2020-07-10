package fr.syrows.modernshop.shops;

import fr.syrows.modernshop.shops.items.ShopItem;
import fr.syrows.modernshop.shops.owners.PlayerOwner;
import fr.syrows.modernshop.shops.owners.ShopOwner;
import fr.syrows.modernshop.shops.transactions.Transaction;
import fr.syrows.modernshop.shops.transactions.TransactionType;
import fr.syrows.modernshop.shops.transactions.modules.TransactionModules;
import fr.syrows.modernshop.utils.ShopUtils;
import fr.syrows.modernshop.utils.blocks.ChestUtils;
import fr.syrows.modernshop.utils.blocks.SignUtils;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

public class Shop {

    public static final String SHOP_ID_FORMAT = "%d_%d_%d_%s";

    private ShopOwner owner;
    private ShopLocation location;
    private ShopItem item;
    private ShopPriority priority;
    private ShopType type;

    private ArrayList<TransactionModules> modules;

    public Shop(Chest chest, ShopItem item) {

        this.owner = ShopOwner.NULL_OWNER;
        this.priority = ShopPriority.ADMIN;
        this.type = ShopType.ADMIN;
        this.item = item;
        this.location = new ShopLocation(chest.getLocation());
        this.modules = new ArrayList<>();
    }

    public Shop(Player owner, Chest chest, ShopItem item) {

        this.owner = new PlayerOwner(owner.getUniqueId());
        this.type = ShopType.PLAYER;
        this.priority = ShopPriority.NORMAL;
        this.item = item;
        this.location = new ShopLocation(chest.getLocation());
        this.modules = new ArrayList<>();
    }

    public String getIdentifier() {
        return ShopUtils.getIdentifier(this.location.getLocation());
    }

    public ShopOwner getOwner() {
        return this.owner;
    }

    public boolean hasOwner() {
        return !this.owner.isNull();
    }

    public ShopLocation getLocation() {
        return this.location;
    }

    public ShopItem getShopItem() {
        return this.item;
    }

    public ShopPriority getPriority() {
        return this.priority;
    }

    public void setPriority(ShopPriority priority) {
        this.priority = priority;
    }

    public ShopType getType() {
        return this.type;
    }

    public Sign getSign() {
        return ChestUtils.getFrontSign(this.getChest());
    }

    public Chest getChest() {
        return ChestUtils.getChestAt(this.location.getLocation());
    }

    public void handle(Transaction transaction) {

        TransactionType type = transaction.getType();

        if(!this.hasModule(type))
            throw new UnsupportedOperationException("Missing component to handle the action.");

        TransactionModules component = this.getModule(type);
        component.handle(this, transaction);
    }

    public boolean hasModule(TransactionType type) {
        return this.modules.stream().anyMatch(component -> component.getType() == type);
    }

    public void addModule(TransactionModules component) {

        if(this.hasModule(component.getType()))
            throw new UnsupportedOperationException("Component already registered.");

        this.modules.add(component);
    }

    public void removeModule(TransactionModules component) {

        this.modules.remove(component);
    }

    public TransactionModules getModule(TransactionType type) {

        Optional<TransactionModules> optional = this.modules.stream()
                .filter(component -> component.getType().equals(type))
                .findFirst();

        if(!optional.isPresent())
            throw new NullPointerException(String.format("Component %s not found.", type.name()));

        return optional.get();
    }
}
