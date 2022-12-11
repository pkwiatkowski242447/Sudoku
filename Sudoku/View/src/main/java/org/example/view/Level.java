package org.example.view;

public enum Level {
    Easy(40),
    Medium(50),
    Hard(60);
    private int value;

    Level(int value) {
        value = this.value;
    }

    public int getValue() {
        return value;
    }
}
