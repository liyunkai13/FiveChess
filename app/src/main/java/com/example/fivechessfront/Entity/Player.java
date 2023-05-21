package com.example.fivechessfront.Entity;

public class Player {
    private String name;
    private boolean isHuman;

    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
    }

    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return isHuman;
    }

    // 其他方法和逻辑
}
