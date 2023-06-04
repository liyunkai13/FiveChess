package com.example.fivechessfront.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.R;
import com.example.fivechessfront.UIHelper.GameUIHelper;
import com.example.fivechessfront.View.ChessboardView;
import com.example.fivechessfront.utils.AI;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private Player player1;
    private Player player2;
    private Board board;
    private ChessboardView chessboardView;
    private TextView turnsView;
    private GameUIHelper helper;

    public void Init(){
        setContentView(R.layout.activity_game);
        turnsView = findViewById(R.id.turnsView);
        /*初始化 ChessboardView*/
        chessboardView = findViewById(R.id.chessboard_view);
        helper = new GameUIHelper(chessboardView,turnsView,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Init();
        // 初始化玩家、棋盘和游戏
        player1 = new Player("Player 1", true);  // 玩家1是人类玩家
        player2 = new AI("Ai", 2); // 玩家2是机器玩家
        board = new Board();
        game = new Game(player1, player2, board,helper);
        // 设置 ChessboardView 的 Board 实例
        chessboardView.setBoard(board);
        // 设置 ChessboardView 的点击事件监听器
        chessboardView.setOnChessboardClickListener((x, y, row, col) -> {
            if (game.getCurrentPlayer() == player1&& board.isLocValid(row, col)) {
                game.PlayerSet(row,col);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.Start();
    }
}


