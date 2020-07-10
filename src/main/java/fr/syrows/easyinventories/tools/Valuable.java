package fr.syrows.easyinventories.tools;

public interface Valuable<E> {

    E getElement();

    void setElement(E element);
}
