package fr.syrows.easyinventories.tools.pagination;

import java.util.Arrays;

public class PaginationSettings {

    private int beginRow, beginColumn, endRow, endColumn;
    private int[] blacklisted;

    private PaginationSettings(Builder builder) {

        this.beginRow = builder.beginRow;
        this.beginColumn = builder.beginColumn;

        this.endRow = builder.endRow;
        this.endColumn = builder.endColumn;

        this.blacklisted = builder.blacklisted;
    }

    public int getBeginRow() {
        return this.beginRow;
    }

    public int getBeginColumn() {
        return this.beginColumn;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public int getEndColumn() {
        return this.endColumn;
    }

    public int countSlots() {
        return ((this.endRow - this.beginRow + 1) * (this.endColumn - this.beginColumn + 1)) - this.blacklisted.length;
    }

    public int[] getBlacklisted() {
        return Arrays.copyOf(this.blacklisted, this.blacklisted.length);
    }

    public static class Builder {

        private int beginRow, beginColumn, endRow, endColumn;
        private int[] blacklisted;

        public Builder values(int beginRow, int beginColumn, int endRow, int endColumn) {

            if(beginRow > endRow)
                throw new IllegalArgumentException("Begin row must be lower or equals than end row.");

            if(beginColumn > endColumn)
                throw new IllegalArgumentException("Begin column must be lower or equals than end column.");

            this.beginRow = beginRow;
            this.beginColumn = beginColumn;
            this.endRow = endRow;
            this.endColumn = endColumn;

            return this;
        }

        public Builder blacklist(int[] slots) {
            this.blacklisted = slots;
            return this;
        }

        public PaginationSettings getSettings() {
            return new PaginationSettings(this);
        }
    }
}
