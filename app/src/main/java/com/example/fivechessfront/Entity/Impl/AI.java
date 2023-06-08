package com.example.fivechessfront.Entity.Impl;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.Enums.PlayerType;

import java.util.ArrayList;
import java.util.List;

public class AI extends Player {
    private int maxDepth; // 最大搜索深度

    public AI(int maxDepth, Game game) {
        super("AI", PlayerType.AI,game);
        this.maxDepth = maxDepth;
    }

    public int[] getBestMove(Board board) {
        //此函数初始调用是ai的回合
        int[] bestMove = { -1, -1 };
        int bestScore = Integer.MIN_VALUE;

        List<int[]> possibleMoves = generatePossibleMoves(board);
        //Ai遍历，走出第一步
        for (int[] move : possibleMoves) {
            int row = move[0];
            int col = move[1];

            Board newBoard = board.clone(); // 创建棋盘副本
            newBoard.placePiece(row, col, this); // 在副本上执行落子

            boolean isGameOver = isGameOver(newBoard, row, col);
            int score;
            if (isGameOver) {
                score = evaluateGameOver(newBoard, row, col);
                //这行函数处理的内容有两种情况:1.Ai一步获胜，返回Integer.MAX_VALUE 2.平局返回0（总不能落完子自己就立马输了吧）
            } else {
                //游戏没有结束，该到虚拟的最小化玩家回合了
                score = minimax(newBoard, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            }

            if (score > bestScore) {
                bestScore = score;
                bestMove[0] = row;
                bestMove[1] = col;
            }
        }

        return bestMove;
    }



    private int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 ) {
            return evaluate(board);
        }
        List<int[]> possibleMoves = generatePossibleMoves(board);

        if (maximizingPlayer) {
            int maxScore = Integer.MIN_VALUE;
            for (int[] move : possibleMoves) {
                int row = move[0];
                int col = move[1];

                Board newBoard = board.clone(); // 创建棋盘副本
                newBoard.placePiece(row, col, this); // 在副本上执行落子
                boolean isGameOver = isGameOver(newBoard, row, col);
                int score;
                if (isGameOver) {
                    score = evaluateGameOver(newBoard, row, col);
                    //这行函数处理的内容有两种情况:1.Ai一步获胜，返回Integer.MAX_VALUE 2.平局返回0（总不能落完子自己就立马输了吧）
                    return score;
                } else {
                    //游戏没有结束，该到虚拟的最小化玩家回合了
                    score = minimax(newBoard, depth - 1, alpha, beta, false);
                    maxScore = Math.max(maxScore, score);
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) {
                        break; // 剪枝
                    }
                }
            }
            return maxScore;

        } else {
            int minScore = Integer.MAX_VALUE;
            for (int[] move : possibleMoves) {
                int row = move[0];
                int col = move[1];

                Board newBoard = board.clone(); // 创建棋盘副本
//                newBoard.placePiece(row, col, getOpponentPieceType()); // 在副本上执行落子
                newBoard.boardArray[row][col] = 9;      // 在副本上执行落子,随便设置一个值，反正不是Ai的棋子和实际玩家的棋子

                boolean isGameOver = isGameOver(newBoard, row, col);
                int score;
                if (isGameOver) {
                    score = evaluateGameOver(newBoard, row, col);
                    //这行函数处理的内容有两种情况:1.最小化玩家一步获胜，返回Integer.MIN_VALUE 2.平局返回0（总不能落完子自己就立马输了吧）
                    return score;
                } else {
                    //游戏没有结束，该到Ai玩家回合了
                    score = minimax(newBoard, depth - 1, alpha, beta, true);
                    minScore = Math.min(minScore, score);
                    beta = Math.min(beta, score);
                    if (beta <= alpha) {
                        break; // 剪枝
                    }

                }

            }
            return minScore;

        }
    }



    private List<int[]> generatePossibleMoves(Board board) {
        // 生成所有可能的落子位置
        List<int[]> possibleMoves = new ArrayList<>();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.boardArray[row][col] == 0) {
                    int[] move = { row, col };
                    possibleMoves.add(move);
                }
            }
        }
        return possibleMoves;
    }
    private int evaluateGameOver(Board board, int row, int col) {
        // 模拟 Game 类的 isGameOver() 方法的评估逻辑
        if (board.isFiveInLine(row, col)) {
            // 处理获胜情况的评估
            if (board.boardArray[row][col] == this.getPieceType()) {
                // AI 获胜
                return Integer.MAX_VALUE;
            } else {
                // 对手获胜
                return Integer.MIN_VALUE;
            }
        } else {
            // 处理平局情况的评估
            return 0;
        }
    }

    private boolean isGameOver(Board board, int row, int col) {
        // 模拟 Game 类的 isGameOver() 方法逻辑，满足条件则返回 true
        return board.isFiveInLine(row, col) || board.isFull();
    }

    private int evaluate(Board board) {
        int score = 0;

        // 棋盘的连续棋子数评估
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {-1, 1}}; // 水平、垂直、对角线方向
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.boardArray[row][col] != 0) {
                    int pieceType = board.boardArray[row][col];
                    for (int[] direction : directions) {
                        int count = 1;
                        int r = row + direction[0];
                        int c = col + direction[1];
                        while (r >= 0 && r < Board.SIZE && c >= 0 && c < Board.SIZE && board.boardArray[r][c] == pieceType) {
                            count++;
                            r += direction[0];
                            c += direction[1];
                        }
                        score += evaluateLine(count, pieceType);
                    }
                }
            }
        }

        // 棋盘控制区域评估
        int centerRow = Board.SIZE / 2;
        int centerCol = Board.SIZE / 2;
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                int distance = Math.abs(row - centerRow) + Math.abs(col - centerCol);
                if (board.boardArray[row][col] == this.getPieceType()) {
                    score += (Board.SIZE - distance); // 根据离中心的距离给予权重
                } else if (board.boardArray[row][col] ==9) {
                    score -= (Board.SIZE - distance); // 根据离中心的距离减分
                }
            }
        }

        // 棋盘空位的数量评估
        int emptyCount = 0;
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.boardArray[row][col] == 0) {
                    emptyCount++;
                }
            }
        }
        score += emptyCount; // 根据剩余空位数量给予加分

        return score;
    }

    private int evaluateLine(int count, int pieceType) {
        if (count >= 5) {
            if (pieceType == getPieceType()) {
                return 1000; // AI获胜，给予较高的分数
            } else {
                return -1000; // 对手获胜，给予较低的分数
            }
        } else if (count == 4) {
            if (pieceType == getPieceType()) {
                return 100; // AI形成四子连珠，给予一定的分数
            } else {
                return -100; // 对手形成四子连珠，给予一定的分数
            }
        } else {
            return 0; // 其他情况不给予分数
        }
    }



}

