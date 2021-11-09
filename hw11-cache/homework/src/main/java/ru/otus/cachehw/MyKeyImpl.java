package ru.otus.cachehw;

import java.util.Objects;

public class MyKeyImpl<T> implements MyKey<T>{

    private final T key;

    public MyKeyImpl(T key) {
        this.key = key;
    }

    @Override
    public T get() {
        return key;
    }

    @Override
    public String toString() {
        return "MyKeyImpl{" +
                "key=" + key +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyKeyImpl<?> myKey = (MyKeyImpl<?>) o;
        return Objects.equals(key, myKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
