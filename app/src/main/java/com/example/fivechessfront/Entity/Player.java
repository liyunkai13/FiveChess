package com.example.fivechessfront.Entity;

public class Player {
    private final String name;
    private final boolean isHuman;
    private int pieceType;     // 棋子类型, 1为黑棋, 2为白棋

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
    public int getPieceType() {
        return pieceType;   // 1为黑棋, 2为白棋
    }
    public  void setPieceType(int pieceType) {
        this.pieceType = pieceType;
    }

    // 其他方法和逻辑
}
