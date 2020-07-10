package fr.syrows.modernshop.shops.transactions.modules;

import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.shops.transactions.Transaction;
import fr.syrows.modernshop.shops.transactions.TransactionType;

public interface TransactionModules {

    void handle(Shop shop, Transaction transaction);

    TransactionType getType();
}
