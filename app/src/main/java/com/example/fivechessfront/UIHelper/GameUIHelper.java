package com.example.fivechessfront.UIHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

import com.example.fivechessfront.View.ChessboardView;

import java.util.function.Consumer;
import java.util.function.Function;

public class GameUIHelper {
    private ChessboardView chessboardView;
    private TextView turnsView;
    AlertDialog.Builder builder;

    public GameUIHelper(ChessboardView chessboardView, TextView turnsView, Context context) {
        this.chessboardView = chessboardView;
        this.turnsView = turnsView;
        builder = new AlertDialog.Builder(context);
    }
    public void AIAddTurns(int turns){
        turnsView.post(() -> turnsView.setText("当前回合数"+turns));
    }
    public void Invalidate(){
        chessboardView.invalidate();
        turnsView.invalidate();
    }
    public void ShowDialog(String winnerName, Consumer<Void> yesCallBack){
        AlertDialog dialog;
        builder.setTitle(winnerName+"获胜！").setMessage("是否要再来一局？");
        builder.setPositiveButton("是", (dialog1, id) -> {dialog1.dismiss();yesCallBack.accept(null);});
        builder.setNegativeButton("否", (dialog1, id) -> dialog1.dismiss());
        dialog = builder.create();
        dialog.show();
    }
    public void SetTurns(int turns){
        turnsView.setText("当前回合数"+turns);
    }
}
