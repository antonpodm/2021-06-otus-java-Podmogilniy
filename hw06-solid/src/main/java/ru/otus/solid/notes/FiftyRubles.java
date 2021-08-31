package ru.otus.solid.notes;

import ru.otus.solid.interfaces.INote;

import java.util.Objects;

public class FiftyRubles implements INote {

    private final int value = 50;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiftyRubles that = (FiftyRubles) o;
        return value == that.getValue();
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
