/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.syrows.easyinventories.contents.pagination;

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
