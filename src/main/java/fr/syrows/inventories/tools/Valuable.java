package fr.syrows.inventories.tools;

public interface Valuable<E> {

    E getElement();

    void setElement(E element);
}
