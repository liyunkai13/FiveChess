package com.example.fivechessfront.Entity;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class Board implements Cloneable{
    public static final int SIZE = 9;
    private static final int EMPTY = 0;
    public int[][] boardArray;

    public Board() {
        boardArray = new int[SIZE][SIZE];
        // 初始化棋盘
    }

    public void placePiece(int row, int col,Player player) {
        // 放置棋子的逻辑
        boardArray[row][col] = player.getPieceType();   // 1为黑棋, 2为白棋
    }

    public boolean isLocValid(int row, int col) {
        // 检查指定位置是否有效的逻辑
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && boardArray[row][col] == EMPTY;
    }

    public void ResetBoard(){
        for (int i = 0; i < boardArray.length; i++) {
            Arrays.fill(boardArray[i], 0);
        }
    }

    //判断是否有五子连珠
    public boolean isFiveInLine(int row, int col) {
        int pieceType = boardArray[row][col];

        // 检查横向是否有五子连珠
        int count = 1;
        for (int i = 1; i <= 4; i++) {
            if (col - i >= 0 && boardArray[row][col - i] == pieceType) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (col + i < SIZE && boardArray[row][col + i] == pieceType) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true; // 横向连珠
        }

        // 检查纵向是否有五子连珠
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (row - i >= 0 && boardArray[row - i][col] == pieceType) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (row + i < SIZE && boardArray[row + i][col] == pieceType) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true; // 纵向连珠
        }

        // 检查左上到右下是否有五子连珠
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (row - i >= 0 && col - i >= 0 && boardArray[row - i][col - i] == pieceType) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (row + i < SIZE && col + i < SIZE && boardArray[row + i][col + i] == pieceType) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true; // 左上到右下连珠
        }

        // 检查右上到左下是否有五子连珠
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (row - i >= 0 && col + i < SIZE && boardArray[row - i][col + i] == pieceType) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (row + i < SIZE && col - i >= 0 && boardArray[row + i][col - i] == pieceType) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true; // 右上到左下连珠
        }

        return false; // 没有五子连珠
    }
    //判断是否平局
    public boolean isFull() {
        // 检查棋盘是否已满的逻辑
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++)
                if (boardArray[i][j] == EMPTY)
                    return false;
        }

        return true;
    }

    @NonNull
    @Override
    public Board clone() {
        try {
            Board board = (Board) super.clone();
            board.boardArray = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                System.arraycopy(boardArray[i], 0, board.boardArray[i], 0, SIZE);
            }
            return board;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    // 其他方法和逻辑
}

