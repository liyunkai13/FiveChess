package com.example.fivechessfront.Enums;

public enum GameDifficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3);
    private final int value;

    private GameDifficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
