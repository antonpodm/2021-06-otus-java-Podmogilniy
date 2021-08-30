package ru.otus.solid.notes;

import ru.otus.solid.interfaces.INote;

import java.util.Objects;

public class OneThousandRubles implements INote {

    private final int value = 1000;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneThousandRubles that = (OneThousandRubles) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int getValue() {
        return value;
    }
}
