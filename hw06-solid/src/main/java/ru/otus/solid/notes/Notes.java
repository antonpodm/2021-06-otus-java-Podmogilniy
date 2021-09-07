package ru.otus.solid.notes;

public enum Notes {
    FiftyRubles(50),
    OneHundredRubles(100),
    OneThousandRubles(1000),
    ThirtyRubles(30),
    FortyRubles(40),
    EightyRubles(80);

    private final int value;

    Notes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int compare(Notes n) {
        return Integer.compare(this.value, n.value);
    }
}
