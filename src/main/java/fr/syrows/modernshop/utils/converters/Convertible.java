package fr.syrows.modernshop.utils.converters;

public interface Convertible<T> {

    Class<? extends T> getType();
}
