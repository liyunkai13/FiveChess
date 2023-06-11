package com.example.fivechessfront.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.GameHistory;
import com.example.fivechessfront.Enums.GameType;
import com.example.fivechessfront.Enums.PlayerType;
import com.example.fivechessfront.Network.Client;
import com.example.fivechessfront.R;
import com.example.fivechessfront.UIHelper.GameUIHelper;
import com.example.fivechessfront.View.ChessboardView;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private ChessboardView chessboardView;
    private TextView turnsView;
    private TextView nameView;
    private GameUIHelper helper;
    private GameHistory gameHistory;

    public void Init() {
        setContentView(R.layout.activity_game);
        turnsView = findViewById(R.id.turnsView);
        nameView = findViewById(R.id.nameView);
        /*初始化 ChessboardView*/
        chessboardView = findViewById(R.id.chessboard_view);
        helper = new GameUIHelper(chessboardView, turnsView, nameView,this);
        gameHistory = new GameHistory(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Client client = Client.getInstance();
        super.onCreate(savedInstanceState);
        Init();
        game = new Game(helper, gameHistory, this);
        //player2 = new AI("Ai", 2); // 玩家2是机器玩家
        String mode = getIntent().getStringExtra("mode");
        int difficultyValue = getIntent().getIntExtra("difficulty", 1);
        int roomNum = getIntent().getIntExtra("roomID", 114);
        switch (mode) {
            case "ai":
                game.SetGameType(GameType.PlayerVsAi,difficultyValue);
                break;
            case "people":
                game.SetGameType(GameType.PlayerVsPlayer,0);
                break;
            case "internet":
                game.SetGameType(GameType.PlayerVsInternet,0);
                client.Start();
                client.JoinRoom(roomNum);
                break;
        }
        // 设置 ChessboardView 的 Board 实例
        chessboardView.setBoard(game.getBoard());
        // 设置 ChessboardView 的点击事件监听器
        chessboardView.setOnChessboardClickListener((x, y, row, col) -> {
            // 处理点击坐标的逻辑
            Log.d("ChessboardView", "点击坐标：x = " + x + ", y = " + y);
            Log.d("ChessboardView", "点击格子：row = " + row + ", col = " + col);
            if (game.getCurrentPlayer().getPlayerType() == PlayerType.Human) {
                game.PassIntention(row, col);
                game.RunATurn();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.Start();
    }
}


