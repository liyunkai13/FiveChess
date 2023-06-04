package com.example.fivechessfront.Enums;

public enum GameDifficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3),
    EXPERT(4),
    NIGHTMARE(5),
    TRAGIC(6),
    INSANE(7),
    EXTREME(8),
    DIVINE(9),
    IMPOSSIBLE(10);
    private final int value;

    private GameDifficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
