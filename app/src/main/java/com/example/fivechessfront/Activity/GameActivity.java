package com.example.fivechessfront.Activity;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.GameHistory;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.R;
import com.example.fivechessfront.UIHelper.GameUIHelper;
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
    private TextView turnsView;
    private GameUIHelper helper;

    public MyHelper myHelper;
    private GameHistory gameHistory;

    public void Init(){
        setContentView(R.layout.activity_game);
        turnsView = findViewById(R.id.turnsView);
        /*初始化 ChessboardView*/
        chessboardView = findViewById(R.id.chessboard_view);
        helper = new GameUIHelper(chessboardView,turnsView,this);
        myHelper = new MyHelper(GameActivity.this,"mySQLite.db",null,2);//创建数据库
        gameHistory = new GameHistory();//创建游戏对局记录类
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
        if(player1.getPieceType()==1) gameHistory.setColor("BLACK");//在数据库中插入棋子的颜色
        else gameHistory.setColor("WHITE");
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

