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

    /**
     * Create a new Pagination with by specifying the number of elements
     * per page.
     *
     * @param elementsPerPage the number of elements a page will contain.
     */
    public Pagination(int elementsPerPage) {
        this.setElementsPerPage(elementsPerPage);
    }

    /**
     * Create a new Pagination with a list of elements and the number of
     * elements per page.
     *
     * @param elements a list of objects of type T to add to the pagination.
     * @param elementsPerPage the number of elements a page will contain.
     */
    public Pagination(List<T> elements, int elementsPerPage) {
        this(elementsPerPage);
        elements.forEach(this::addElement); // Adding each element using the method to create pages.
    }

    /**
     * Add a new element to the current pagination.
     *
     * @param element an object of type T.
     */
    public void addElement(T element) {

        this.elements.add(element); // Adding element to the list of elements.

        // If one more page is required, creating one.
        if(this.getRequiredPages() > this.countPages()) {

            // The created page contains one element which is the last.
            int index = this.elements.size() - 1;

            Page page = new Page(this.countPages() + 1, index, index + this.elementsPerPage);

            this.pages.add(page); // Adding page to the page list.
        }
    }

    /**
     * Remove an element of the pagination.
     *
     * @param element an object of type T.
     */
    public void removeElement(T element) {

        if(!this.elements.contains(element)) return; // Element no contained.

        this.elements.remove(element);

        int pages = this.countPages();

        // If there is more than 1 page and the number of pages is greater the one required,
        // removing the last page.
        if(pages > 1 && this.getRequiredPages() < pages) this.pages.remove(this.getLast());
    }

    /**
     * Check if an element is contained in the pagination.
     *
     * @param element the element to check.
     *
     * @return true if the element is contained or else false.
     */
    public boolean containsElement(T element) {
        return this.elements.contains(element);
    }

    /**
     * Returns the index of the element in the pagination. The first element is at
     * the index 0.
     *
     * @param element the element for which get the index.
     * @return the index of the element if it is contained or else -1.
     */
    public int indexOf(T element) {
        return this.elements.indexOf(element);
    }

    /**
     * Returns the last page of the pagination. A pagination always contains
     * a page, even if there is no element.
     *
     * @return an object of type Page.
     */
    public Page getLast() {
        return this.pages.get(this.pages.size() - 1);
    }

    /**
     * Returns the first page of the pagination. A pagination always contain
     * a page, even if there is no element.
     *
     * @return an object of type Page.
     */
    public Page getFirst() {
        return this.pages.get(0);
    }

    /**
     * Check if a page is contained in the pagination.
     *
     * @param page an object of type Page.
     * @return true if the page is contained or else false.
     */
    public boolean contains(Page page) {
        return this.pages.contains(page);
    }

    /**
     * Check if a page is the last page of the pagination.
     *
     * @param page an object of type Page.
     * @return true if the page the last or else false;
     */
    public boolean isLast(Page page) {
        return page.equals(this.getLast());
    }

    /**
     * Check if a page is the first page of the pagination.
     *
     * @param page an object of type Page.
     * @return true if the page is the first or else false.
     */
    public boolean isFirst(Page page) {
        return page.equals(this.getFirst());
    }

    /**
     * Check if there is a next page after the one specified.
     *
     * @param page an object of type Page.
     * @throws PaginationException if the page does not belong to the pagination.
     * @return true if there is a next page or else false.
     */
    public boolean hasNext(Page page) {

        if(!this.contains(page))
            throw new PaginationException("Page does not belong to the pagination.");

        return !this.isLast(page);
    }

    /**
     * Check if there is a previous page before the one specified.
     *
     * @param page an object of type Page.
     * @throws PaginationException if the page does not belong to the pagination.
     * @return true if there is a previous page or else false.
     */
    public boolean hasPrevious(Page page) {

        if(!this.contains(page))
            throw new PaginationException("Page does not belong to the pagination.");

        return !this.isFirst(page);
    }

    /**
     * Get the next page after the one specified.
     *
     * @param page an object of type Page.
     * @throws PaginationException if the page does not belong to the pagination.
     * @return an object of type Page if there is a next page or null.
     */
    public Page getNext(Page page) {

        if(!this.contains(page))
            throw new PaginationException("Page does not belong to the pagination.");

        return this.hasNext(page) ? this.pages.get(this.pages.indexOf(page) + 1) : null;
    }

    /**
     * Get the previous page before the one specified.
     *
     * @param page an object of type Page.
     * @throws PaginationException if the page does not belong to the pagination.
     * @return an object of type Page if there is a previous page or null.
     */
    public Page getPrevious(Page page) {

        if(!this.contains(page))
            throw new PaginationException("Page does not belong to the pagination.");

        return this.hasPrevious(page) ? this.pages.get(this.pages.indexOf(page) - 1) : null;
    }

    /**
     * Get the Page object by using its page number.
     *
     * @param number the page number. The index of the first page is 1.
     * @throws PaginationException if the specified number is out of bound.
     * It must be between 1 and the number of pages (include).
     * @return the page which corresponds to the specified number
     */
    public Page getPage(int number) {

        if(number < 1  || number > this.pages.size())
            throw new PaginationException(String.format("Page number must be between 1 and %d (include).", this.pages.size()));

        return this.pages.get(number - 1);
    }

    /**
     * Check if there is a page with the specified number.
     *
     * @param number the page number. Begins at 1.
     * @return true if there is a page with the specified number or else false.
     */
    public boolean hasPage(int number) {
        return number > 0 && number <= this.pages.size();
    }

    /**
     * Returns the total number of pages.
     *
     * @return an int.
     */
    public int countPages() {
        return this.pages.size();
    }

    /**
     * Returns all the pages.
     *
     * @return a list that contains all the pages.
     */
    public List<Page> getPages() {
        return new ArrayList<>(this.pages);
    }

    /**
     * Get the number of elements a page can contain.
     *
     * @return an int.
     */
    public int getElementsPerPage() {
        return this.elementsPerPage;
    }

    /**
     * Set the number of elements a page can contain.
     * This method will clear the old pages and generate new ones.
     * @param elementsPerPage
     */
    public void setElementsPerPage(int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
        this.generatePages();
    }

    /**
     * Get the total number of elements.
     *
     * @return an int.
     */
    public int countElements() {
        return this.elements.size();
    }

    /**
     * Get the number of required pages to paginate all the elements.
     *
     * @return an int.
     */
    private int getRequiredPages() {

        // Calculating the number of pages required to paginate all the elements.
        // Calculating by adding one (example: (elements / elementsPerPage) + 1)
        // doesn't work because if the division has not rest we'll have an extra empty page.
        return (int) Math.ceil((double) this.elements.size() / this.elementsPerPage);
    }

    /**
     * Regenerate all the pages.
     */
    private void generatePages() {

        List<T> elements = new ArrayList<>(this.elements); // Copying elements.

        this.pages.clear();
        this.elements.clear();

        elements.forEach(this::addElement); // Adding the elements to regenerate page.

        if(this.pages.size() == 0) this.pages.add(new Page(1, 0, this.elementsPerPage));
    }

    public class Page {

        private final int number, begin, end;

        private Page(int number, int begin, int end) {
            this.number = number;
            this.begin = begin;
            this.end = end;
        }

        /**
         * Get the elements contained in this page.
         *
         * @return a list of objects of type T.
         */
        public List<T> getElements() {

            List<T> allElements = Pagination.this.elements;

            // If the list is empty or the end index is greater than the
            // total number of elements, an empty list will be returned.
            int toIndex = Math.min(allElements.size(), this.end);

            return allElements.subList(this.begin, toIndex);
        }

        /**
         * Get the number of the page.
         *
         * @return an int.
         */
        public int getNumber() {
            return this.number;
        }

        /**
         * Check if an element belongs to this page.
         *
         * @param element the element to check.
         * @return true if the specified element belongs to the this page or else false.
         */
        public boolean belongsToPage(T element) {
            return this.getElements().contains(element);
        }
    }
}

