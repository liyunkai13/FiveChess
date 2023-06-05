package com.example.fivechessfront.Entity;

public class Player {
    public final Game game;
    private final String name;
    private final boolean isHuman;
    private Position intention;
    private int pieceType;     // 棋子类型, 1为黑棋, 2为白棋

    public Player(String name, boolean isHuman, Game game) {
        this.name = name;
        this.isHuman = isHuman;
        this.game = game;
    }

    public void Drops(){
        if(game.getBoard().isLocValid(intention.row, intention.col)) {
            game.getBoard().placePiece(intention.row, intention.col, this);
        }
    }

    public String getName() {
        return name;
    }

    public void setIntention(Position intention) {
        this.intention = intention;
    }

    public Position getIntention() {
        return intention;
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

