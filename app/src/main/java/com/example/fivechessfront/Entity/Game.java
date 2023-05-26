package com.example.fivechessfront.Entity;

import android.util.Log;

import java.util.Random;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Board board;
    private Player currentPlayer;
    private Player winner;
    private int turns = 0;

    public Game(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }
    /**
     * 随机分配玩家的棋子颜色，黑棋先手
     */
    public void assignRandomColors() {
        // 随机分配玩家的棋子颜色的逻辑, 1为黑棋, 2为白棋
        //黑子先手
        Random random = new Random();
        if (random.nextBoolean()) {
            player1.setPieceType(1);
            player2.setPieceType(2);
            currentPlayer = player1;
            Log.d("黑子先行", "player1");

        } else {
            player1.setPieceType(2);
            player2.setPieceType(1);
            currentPlayer = player2;
            Log.d("黑子先行", "player2");
        }
    }

    /**
     * 切换落子玩家
     */
    public void switchPlayer() {
        turns++;
        if (currentPlayer == player1) {
            currentPlayer = player2;
            Log.d("currentPlayer", "player2");
        } else {
            currentPlayer = player1;
            Log.d("currentPlayer", "player1,该你啦！");
        }
    }

    public boolean isGameOver(int row, int col) {
        // 判断游戏是否结束的逻辑
        // 判断是否有一方获胜
        if (board.isFiveInLine(row, col)) {
            winner = currentPlayer;
            if (winner == player1) {
                Log.d("GameOver", "你获胜啦！");
            } else {
                Log.d("GameOver", "很遗憾，胜败乃兵家常事~ ");
            }
            return true;
        }
        if (board.isFull()) {
            winner = null;
            Log.d("GameOver", "平局啦！"); // 平局
            return true;
        }

        return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurns() {
        return turns;
    }

    // 其他方法和逻辑
}

