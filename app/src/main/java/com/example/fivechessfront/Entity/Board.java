package com.example.fivechessfront.Entity;

public class Board {
    private static final int SIZE = 15;
    private static final int EMPTY = 0;
    private static final int PLAYER1_PIECE = 1;
    private static final int PLAYER2_PIECE = 2;
    public int[][] boardArray;

    public Board() {
        boardArray = new int[SIZE][SIZE];
        // 初始化棋盘
    }

    public void placePiece(int row, int col, int playerPiece) {
        // 放置棋子的逻辑
    }

    public boolean isCellEmpty(int row, int col) {
        // 检查指定位置是否为空的逻辑
        return false;
    }

    public boolean isCellValid(int row, int col) {
        // 检查指定位置是否有效的逻辑
        return false;
    }

    // 其他方法和逻辑
}

