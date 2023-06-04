package com.example.fivechessfront.Activity;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.GameHistory;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.R;
import com.example.fivechessfront.View.ChessboardView;
import com.example.fivechessfront.utils.AI;
import com.example.fivechessfront.utils.MyHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private Player player1;
    private Player player2;
    private Board board;
    private ChessboardView chessboardView;
    private AiThread aiThread;
    private TextView turnsView;

    public MyHelper myHelper;
    private GameHistory gameHistory;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        myHelper = new MyHelper(GameActivity.this,"mySQLite.db",null,2);//创建数据库
        gameHistory = new GameHistory();//创建游戏对局记录类

        turnsView = (TextView) findViewById(R.id.turnsView);
        // 初始化玩家、棋盘和游戏
        player1 = new Player("Player 1", true);  // 玩家1是人类玩家
        player2 = new AI("Ai", 2); // 玩家2是机器玩家
        board = new Board();
        game = new Game(player1, player2, board);
        game.assignRandomColors(); // 随机分配玩家的棋子颜色

        if(player1.getPieceType()==1) gameHistory.setColor("BLACK");//在数据库中插入棋子的颜色
        else gameHistory.setColor("WHITE");

        /*初始化 ChessboardView*/
        chessboardView = findViewById(R.id.chessboard_view);
        // 设置 ChessboardView 的 Board 实例
        chessboardView.setBoard(board);
        // 设置 ChessboardView 的点击事件监听器

        chessboardView.setOnChessboardClickListener(new ChessboardView.OnChessboardClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChessboardClick(float x, float y, int row, int col) {
                // 处理点击坐标的逻辑
                Log.d("ChessboardView", "点击坐标：x = " + x + ", y = " + y);
                Log.d("ChessboardView", "点击格子：row = " + row + ", col = " + col);
                if (game.getCurrentPlayer() == player1&& board.isLocValid(row, col)) {
                    //用字符串记录坐标，因为始终黑子先走，所以用字符串即可，便于后期复现
                    gameHistory.setProcess(gameHistory.getProcess() + row);
                    gameHistory.setProcess(gameHistory.getProcess() + col);

                    // 当前玩家是玩家1且游戏未结束时，执行下棋逻辑
                    board.placePiece(row, col, player1);
                    chessboardView.invalidate(); // 更新棋盘显示
                    if (!game.isGameOver(row, col)) {
                        // 如果游戏未结束，则切换玩家
                        game.switchPlayer();
                        turnsView.setText("当前回合数"+game.getTurns());
                        if (!game.getCurrentPlayer().isHuman()){
                            // 如果当前玩家是机器玩家，则开始Ai线程
                            aiThread = new AiThread();
                            aiThread.start();
                        }
                    }
                    //myHelper.insertGameData(account.getName(), gameHistory.getResult(),
                    //        gameHistory.getColor(),gameHistory.getProcess(),0,gameHistory.getDATE_FORMAT());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!game.getCurrentPlayer().isHuman()){
            // 如果当前玩家是机器玩家，则开始Ai线程
            aiThread = new AiThread();
            aiThread.start();
        }
    }

    private class AiThread extends Thread {
        @Override
        public void start() {
            super.start();
        }
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            super.run();
            // Ai线程的逻辑
            Log.d("AiThread", "Ai线程开始运行");
            int[] move = ((AI) game.getCurrentPlayer()).getBestMove(board);
            board.placePiece(move[0], move[1], game.getCurrentPlayer());

            gameHistory.setProcess(gameHistory.getProcess() + move[0]);//记录坐标
            gameHistory.setProcess(gameHistory.getProcess() + move[1]);//记录坐标
            chessboardView.invalidate(); // 更新棋盘显示
            if (!game.isGameOver(move[0], move[1])) {
                // 如果游戏未结束，则切换玩家
                Log.d("AiThread", "Ai线程结束运行");
                game.switchPlayer();
                turnsView.post(new Runnable() {
                    @Override
                    public void run() {
                        turnsView.setText("当前回合数"+game.getTurns());
                    }
                });
                //gameHistory.set
            }
        }
    }


}

/*
在游戏开始时，账户信息应该是已经确定的，所以其中的Account可以设置为全局静态类，方便后续取信息；
对于本局游戏的结束，可以使用AlertDialog对话框；在弹出对话框之前，需要添加以下代码
    //记录比赛结果
    if(game.getWinner()==player1) gameHistory.setResult("WIN");//对局胜利
    else gameHistory.setResult("LOSE");//对局失利
    //记录结束的时间
    Date et = new Date();//建议设置为全局
    SimpleDateFormat format1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    String str = format1.format(et);
    gameHistory.setDATE_FORMAT(str);

    //
* */

