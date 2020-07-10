package fr.syrows.modernshop.shops;

public enum ShopType {

    ADMIN("AdminShop", "modernshop.shops.create.admin"), PLAYER("Shop", "modernshop.shops.create.player");

    private String header, permission;

    ShopType(String header, String permission) {
        this.header = header;
        this.permission = permission;
    }

    public String getHeader() {
        return String.format("[%s]", this.header);
    }

    public String getPermission() {
        return this.permission;
    }

    public static ShopType getTypeByHeader(String header) {

        for(ShopType type : ShopType.values()) {

            if(type.getHeader().equalsIgnoreCase(header)) return type;
        }
        return null;
    }
}
