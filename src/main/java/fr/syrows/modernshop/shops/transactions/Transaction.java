package fr.syrows.modernshop.shops.transactions;

import org.bukkit.entity.Player;

public abstract class Transaction {

    private Player player;
    private int amount;

    public Transaction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public abstract TransactionType getType();

    public Player getPlayer() {
        return this.player;
    }

    public int getAmount() {
        return this.amount;
    }
}
