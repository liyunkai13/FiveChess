package com.example.fivechessfront.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.ChessboardView;
import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.R;
import com.example.fivechessfront.utils.AI;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private Player player1;
    private Player player2;
    private Board board;
    private ChessboardView chessboardView;
    private AiThread aiThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 初始化玩家、棋盘和游戏
        player1 = new Player("Player 1", true);  // 玩家1是人类玩家
        player2 = new AI("Ai", 2); // 玩家2是机器玩家
        board = new Board();
        game = new Game(player1, player2, board);
        game.assignRandomColors(); // 随机分配玩家的棋子颜色
        /*初始化 ChessboardView*/
        chessboardView = findViewById(R.id.chessboard_view);
        // 设置 ChessboardView 的 Board 实例
        chessboardView.setBoard(board);
        // 设置 ChessboardView 的点击事件监听器
        chessboardView.setOnChessboardClickListener(new ChessboardView.OnChessboardClickListener() {
            @Override
            public void onChessboardClick(float x, float y, int row, int col) {
                // 处理点击坐标的逻辑
                Log.d("ChessboardView", "点击坐标：x = " + x + ", y = " + y);
                Log.d("ChessboardView", "点击格子：row = " + row + ", col = " + col);
                if (game.getCurrentPlayer() == player1&& board.isLocValid(row, col)) {
                    // 当前玩家是玩家1且游戏未结束时，执行下棋逻辑
                    board.placePiece(row, col, player1);
                    chessboardView.invalidate(); // 更新棋盘显示
                    if (!game.isGameOver(row, col)) {
                        // 如果游戏未结束，则切换玩家
                        game.switchPlayer();
                        if (!game.getCurrentPlayer().isHuman()){
                            // 如果当前玩家是机器玩家，则开始Ai线程
                            aiThread = new AiThread();
                            aiThread.start();
                        }


                    }
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
        @Override
        public void run() {
            super.run();
            // Ai线程的逻辑
            Log.d("AiThread", "Ai线程开始运行");
            int[] move = ((AI) game.getCurrentPlayer()).getBestMove(board);
            board.placePiece(move[0], move[1], game.getCurrentPlayer());
            chessboardView.invalidate(); // 更新棋盘显示
            if (!game.isGameOver(move[0], move[1])) {
                // 如果游戏未结束，则切换玩家
                Log.d("AiThread", "Ai线程结束运行");
                game.switchPlayer();
            }
        }
    }


}


