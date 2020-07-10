package fr.syrows.modernshop.shops.transactions.modules.impl;

import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.shops.transactions.Transaction;
import fr.syrows.modernshop.shops.transactions.TransactionType;
import fr.syrows.modernshop.shops.transactions.modules.TransactionModules;

public class Purchase implements TransactionModules {

    private double price;

    public Purchase(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {

        if(this.price <= 0)
            throw new IllegalArgumentException("Price must be greater than 0.");

        this.price = price;
    }

    @Override
    public void handle(Shop shop, Transaction transaction) {

    }

    @Override
    public TransactionType getType() {
        return TransactionType.PURCHASE;
    }
}
