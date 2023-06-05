package com.example.fivechessfront.Entity;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.util.Log;

import com.example.fivechessfront.Enums.GameType;
import com.example.fivechessfront.UIHelper.GameUIHelper;
import com.example.fivechessfront.utils.AI;
import com.example.fivechessfront.utils.Human;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Game {
    private Player player1;
    private Player player2;

    public Board getBoard() {
        return board;
    }

    private final Board board;
    private Player currentPlayer;
    private Player winner;
    private AiThread aiThread;
    private int turns = 0;
    private GameUIHelper helper;
    private GameHistory gameHistory;

    public Game(GameUIHelper helper,GameHistory gameHistory){
        board = new Board();
        this.helper = helper;
        this.gameHistory = gameHistory;
    }

    public void SetGameType(GameType type){
        switch(type) {
            case PlayerVsPlayer:
                player1 = new Human("player1",this);
                player2 = new Human("player2",this);
                break;
            case PlayerVsAi:
                player1 = new Human("player1",this);
                player2 = new AI(2,this);
                break;
        }
    }

    public void Start(){
        assignRandomColors();// 随机分配玩家的棋子颜色
        if (!getCurrentPlayer().isHuman()){
            Log.d("Game","开启了ai");
            StartAi();
        }
        if(player1.getPieceType()==1) gameHistory.setColor("执黑");//在数据库中插入棋子的颜色
        else gameHistory.setColor("执白");
    }

    public void Restart(){
        turns = 0;
        board.ResetBoard();
        helper.Invalidate();
        gameHistory.Clear();
        Start();
    }

    public void RunATurn(){
        currentPlayer.Drops();
        gameHistory.WriteProcess(currentPlayer.getIntention());
        helper.Invalidate();
        ContinueDetect(currentPlayer.getIntention());
    }

    public void PassIntention(int row,int col){
        currentPlayer.setIntention(new Position(col,row));
    }
    public void PassIntention(Position intention){
        currentPlayer.setIntention(intention);
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

    public void ContinueDetect(Position intention){
        if (!isGameOver(intention)) {
            // 如果游戏未结束，则切换玩家
            switchPlayer();
            helper.SetTurns(getTurns());
            if (!getCurrentPlayer().isHuman()){
                StartAi();
            }
        }
        else{
            GameFinish();
        }
    }

    public void GameFinish(){
        gameHistory.cnt = turns;
        gameHistory.name = player1.getName()+" vs "+player2.getName();
        if (GetWinner()==null) gameHistory.result = "平局";
        else if (GetWinner()==player1) gameHistory.result = "胜";
        else gameHistory.result = "负";
        //获取当前时间
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("MM/dd-HH:mm");
        gameHistory.DATE_FORMAT = f.format(date);
        gameHistory.SubmitToSql();
        helper.ShowDialog(GetWinner().getName(),t-> Restart());
    }

    public boolean isGameOver(Position position) {
        int row = position.row;
        int col = position.col;
        // 判断游戏是否结束的逻辑
        // 判断是否有一方获胜
        if (board.isFiveInLine(row, col)) {
            winner = currentPlayer;
            return true;
        }
        if (board.isFull()) {
            winner = null;
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
            AI ai = (AI) getCurrentPlayer();
            int[] move = ai.getBestMove(board);
            Position intention = new Position(move[1],move[0]);
            ai.game.PassIntention(intention);
            ai.game.RunATurn();
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

