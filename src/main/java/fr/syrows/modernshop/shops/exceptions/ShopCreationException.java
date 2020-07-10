package fr.syrows.modernshop.shops.exceptions;

public class ShopCreationException extends Exception {

    private ExceptionType type;

    public ShopCreationException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public ExceptionType getType() {
        return this.type;
    }

    public enum ExceptionType {

        NO_PERMISSION, NOT_A_WALL_SIGN, BLOCK_ABOVE, NOT_PLACED_ON_CHEST, DOUBLE_CHEST, CHEST_NOT_EMPTY, NOT_PLACED_ON_FRONT_FACE, SHOP_LIMIT_REACHED, ITEM_ALREADY_USED;
    }
}
