package com.example.fivechessfront.Entity;

import com.example.fivechessfront.Enums.PlayerType;

public class Player {
    public final Game game;
    private String name;
    private final PlayerType playerType;
    private Position intention;
    private int pieceType;     // 棋子类型, 1为黑棋, 2为白棋

    public Player(String name, PlayerType playerType, Game game) {
        this.name = name;
        this.playerType = playerType;
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
    public void setName(String name) {
        this.name = name;
    }

    public void setIntention(Position intention) {
        this.intention = intention;
    }

    public Position getIntention() {
        return intention;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
    public int getPieceType() {
        return pieceType;   // 1为黑棋, 2为白棋
    }
    public  void setPieceType(int pieceType) {
        this.pieceType = pieceType;
    }

    // 其他方法和逻辑
}

