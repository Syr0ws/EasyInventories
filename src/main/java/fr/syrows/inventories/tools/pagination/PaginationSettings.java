package fr.syrows.inventories.tools.pagination;

public class PaginationSettings {

    private int beginSlot, endSlot;

    private PaginationSettings(Builder builder) {
        this.beginSlot = builder.beginSlot;
        this.endSlot = builder.endSlot;
    }

    public int getBeginSlot() {
        return this.beginSlot;
    }

    public int getEndSlot() {
        return this.endSlot;
    }

    public int countSlots() {
        return (this.endSlot - this.beginSlot) + 1;
    }

    public static class Builder {

        private int beginSlot, endSlot;

        public Builder withBeginSlot(int slot) {
            this.beginSlot = slot;
            return this;
        }

        public Builder withEndSlot(int slot) {
            this.endSlot = slot;
            return this;
        }

        public PaginationSettings getSettings() {
            return new PaginationSettings(this);
        }
    }
}
