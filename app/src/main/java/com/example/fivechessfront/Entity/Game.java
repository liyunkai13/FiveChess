package com.example.fivechessfront.Entity;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.util.Log;

import com.example.fivechessfront.UIHelper.GameUIHelper;
import com.example.fivechessfront.utils.AI;

import java.util.Random;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Board board;
    private Player currentPlayer;
    private Player winner;
    private AiThread aiThread;
    private int turns = 0;
    private GameUIHelper helper;
    private GameHistory gameHistory;

    public Game(Player player1, Player player2, Board board,GameUIHelper helper,GameHistory gameHistory){
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.helper = helper;
        this.gameHistory = gameHistory;
        assignRandomColors(); // 随机分配玩家的棋子颜色
    }

    public void Start(){
        assignRandomColors();// 随机分配玩家的棋子颜色
        if (!getCurrentPlayer().isHuman()){
            StartAi();
        }
    }

    public void Restart(){
        turns = 0;
        board.ResetBoard();
        helper.Invalidate();
        Start();
    }

    /**
     * 随机分配玩家的棋子颜色，黑棋先手
     */
    private void assignRandomColors() {
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
    private void switchPlayer() {
        turns++;
        if (currentPlayer == player1) {
            currentPlayer = player2;
            Log.d("currentPlayer", "player2");
        } else {
            currentPlayer = player1;
            Log.d("currentPlayer", "player1,该你啦！");
        }
    }

    public void PlayerSet(int row, int col){
        // 当前玩家是玩家1且游戏未结束时，执行下棋逻辑
        board.placePiece(row, col, player1);
        gameHistory.setProcess(gameHistory.getProcess() +row);//记录坐标
        gameHistory.setProcess(gameHistory.getProcess() + col);//记录坐标
        helper.Invalidate(); // 更新棋盘显示
        if (!isGameOver(row, col)) {
            // 如果游戏未结束，则切换玩家
            switchPlayer();
            helper.SetTurns(getTurns());
            if (!getCurrentPlayer().isHuman()){
                StartAi();
            }
        }
        else{
            helper.ShowDialog(GetWinner().getName(),t-> Restart());
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

    public void StartAi(){
        aiThread = new AiThread();
        aiThread.start();
    }

    private class AiThread extends Thread {
        @Override
        public void start() {
            super.start();
        }
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            Looper.prepare();
            super.run();
            // Ai线程的逻辑
            int[] move = ((AI) getCurrentPlayer()).getBestMove(board);
            board.placePiece(move[0], move[1], getCurrentPlayer());
            gameHistory.setProcess(gameHistory.getProcess() + move[0]);//记录坐标
            gameHistory.setProcess(gameHistory.getProcess() + move[1]);//记录坐标
            helper.Invalidate(); // 更新棋盘显示
            if (!isGameOver(move[0], move[1])) {
                switchPlayer(); // 如果游戏未结束，则切换玩家
                helper.AIAddTurns(getTurns());
            }
            else{
                helper.ShowDialog(GetWinner().getName(),t-> Restart());
            }
            Looper.loop();
        }
    }

    public Player GetWinner(){return winner;}

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurns() {
        return turns;
    }

    // 其他方法和逻辑
}

