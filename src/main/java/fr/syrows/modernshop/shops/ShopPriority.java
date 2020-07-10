package fr.syrows.modernshop.shops;

public enum ShopPriority {

    ADMIN(10), EXTREME(5), VERY_HIGH(4), HIGH(3), MEDIUM(2), NORMAL(1);

    private int power;

    ShopPriority(int power) {
        this.power = power;
    }

    public int getPower() {
        return this.power;
    }
}
