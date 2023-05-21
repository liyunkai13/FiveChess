package com.example.fivechessfront.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.ChessboardView;
import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.R;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private ChessboardView chessboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 初始化玩家、棋盘和游戏
        Player player1 = new Player("Player 1", true);
        Player player2 = new Player("Player 2", false);
        Board board = new Board();
        game = new Game(player1, player2, board);

        /*初始化 ChessboardView*/
        chessboardView = findViewById(R.id.chessboard_view);
        // 设置 ChessboardView 的 Board 实例
        chessboardView.setBoard(board);
        // 设置 ChessboardView 的点击事件监听器
        chessboardView.setOnChessboardClickListener(new ChessboardView.OnChessboardClickListener() {
            @Override
            public void onChessboardClick(float x, float y) {
                // 处理点击坐标的逻辑
                Log.d("ChessboardView", "点击坐标：x = " + x + ", y = " + y);
            }
        });
        // 启动游戏
        game.startGame();

    }


}


