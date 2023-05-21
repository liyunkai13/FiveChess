package com.example.fivechessfront.Entity;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;
    private Player currentPlayer;

    public Game(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        currentPlayer = player1;
    }

    public void startGame() {
        // 游戏开始的逻辑
    }

    public void switchPlayer() {
        // 切换玩家的逻辑
    }

    public void makeMove(int row, int col) {
        // 落子的逻辑
    }

    public boolean checkWin() {
        // 判断胜利条件的逻辑
        return false;
    }

    public void resetGame() {
        // 重置游戏的逻辑
    }

    public boolean isGameOver() {
        // 判断游戏是否结束的逻辑
        return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // 其他方法和逻辑
}
