package ru.otus.solid.notes;

import ru.otus.solid.interfaces.INote;

import java.util.Objects;

public class TenRubles implements INote {

    private final int value = 10;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenRubles tenRubles = (TenRubles) o;
        return value == tenRubles.getValue();
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
