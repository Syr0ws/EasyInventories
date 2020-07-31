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

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {

    private final List<Page> pages = new ArrayList<>();
    private final List<T> elements = new ArrayList<>();

    private int elementsPerPage;

    public Pagination(List<T> elements, int elementsPerPage) {

        this.elementsPerPage = elementsPerPage;
        this.addPage(new Page(1, 0, this.elementsPerPage));

        elements.forEach(this::addElement);
    }

    public void addElement(T element) {

        this.elements.add(element);

        int required = (int) Math.ceil((double) this.elements.size() / this.elementsPerPage);

        if(required > this.countPages()) {

            int index = this.elements.size() - 1;

            Page page = new Page(this.countPages() + 1, index, index + this.elementsPerPage);

            this.pages.add(page);
        }
    }

    public void removeElement(T element) {

        if(!this.elements.contains(element)) return;

        this.elements.remove(element);

        int required = (int) Math.ceil((double) this.elements.size() / this.elementsPerPage);

        if(required < this.countPages()) this.pages.remove(this.getLast());
    }

    public boolean containsElement(T element) {
        return this.elements.contains(element);
    }

    public int indexOf(T element) {
        return this.elements.indexOf(element);
    }

    public Page getLast() {
        return this.pages.get(this.pages.size() - 1);
    }

    public Page getFirst() {
        return this.pages.get(0);
    }

    public boolean contains(Page page) {
        return this.pages.contains(page);
    }

    public boolean isLast(Page page) {
        return page == this.getLast();
    }

    public boolean isFirst(Page page) {
        return page == this.getFirst();
    }

    public boolean hasNext(Page page) {
        return !this.isLast(page);
    }

    public boolean hasPrevious(Page page) {
        return !this.isFirst(page);
    }

    public Page getNext(Page page) {
        return this.hasNext(page) ? this.pages.get(this.pages.indexOf(page) + 1) : null;
    }

    public Page getPrevious(Page page) {
        return this.hasPrevious(page) ? this.pages.get(this.pages.indexOf(page) - 1) : null;
    }

    public Page getPage(int number) {

        Page page;

        if(number <= 0) page =  this.pages.get(1);
        else if(number > this.pages.size()) page = this.pages.get(this.pages.size() - 1);
        else page = this.pages.get(number - 1);

        return page;
    }

    public boolean hasPage(int number) {
        return number > 0 && number <= this.pages.size();
    }

    public int countPages() {
        return this.pages.size();
    }

    public List<Page> getPages() {
        return new ArrayList<>(this.pages);
    }

    public int getElementsPerPage() {
        return this.elementsPerPage;
    }

    public int countElements() {
        return this.elements.size();
    }

    private void addPage(Page page) {
        this.pages.add(page);
    }

    public class Page {

        private final int number, begin, end;

        private Page(int number, int begin, int end) {
            this.number = number;
            this.begin = begin;
            this.end = end;
        }

        public List<T> getElements() {

            List<T> elements = Pagination.this.elements;

            if(elements.size() == 0) return new ArrayList<>();
            else return elements.subList(this.begin, Math.min(elements.size(), this.end));
        }

        public int getNumber() {
            return this.number;
        }

        public int getBeginIndex() {
            return this.begin;
        }

        public int getEndIndex() {
            return this.end;
        }
    }
}

