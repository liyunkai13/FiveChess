package com.example.fivechessfront.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.GameHistory;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.Enums.GameType;
import com.example.fivechessfront.R;
import com.example.fivechessfront.UIHelper.GameUIHelper;
import com.example.fivechessfront.View.ChessboardView;
import com.example.fivechessfront.utils.AI;
import com.example.fivechessfront.utils.MyHelper;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private ChessboardView chessboardView;
    private TextView turnsView;
    private GameUIHelper helper;
    private GameHistory gameHistory;

    public void Init(){
        setContentView(R.layout.activity_game);
        turnsView = findViewById(R.id.turnsView);
        /*初始化 ChessboardView*/
        chessboardView = findViewById(R.id.chessboard_view);
        helper = new GameUIHelper(chessboardView,turnsView,this);
        gameHistory = new GameHistory(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Init();
        //player2 = new AI("Ai", 2); // 玩家2是机器玩家
        game = new Game(helper,gameHistory);
        game.SetGameType(GameType.PlayerVsAi);
        // 设置 ChessboardView 的 Board 实例
        chessboardView.setBoard(game.getBoard());
        // 设置 ChessboardView 的点击事件监听器
        chessboardView.setOnChessboardClickListener((x, y, row, col) -> {
            // 处理点击坐标的逻辑
            Log.d("ChessboardView", "点击坐标：x = " + x + ", y = " + y);
            Log.d("ChessboardView", "点击格子：row = " + row + ", col = " + col);
            if (game.getCurrentPlayer().isHuman()) {
                game.PassIntention(row,col);
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

