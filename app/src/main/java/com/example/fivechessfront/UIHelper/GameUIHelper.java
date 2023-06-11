package com.example.fivechessfront.UIHelper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.example.fivechessfront.Dialog.CustomDialog;
import com.example.fivechessfront.View.ChessboardView;

import java.util.function.Consumer;

public class GameUIHelper {
    private ChessboardView chessboardView;
    private TextView turnsView;
    private TextView nameView;
    AlertDialog.Builder builder;
    private Context context;

    public GameUIHelper(ChessboardView chessboardView, TextView turnsView,TextView nameView, Context context) {
        this.chessboardView = chessboardView;
        this.turnsView = turnsView;
        this.context = context;
        this.nameView = nameView;
        builder = new AlertDialog.Builder(context);
    }
    public void Invalidate(){
        chessboardView.invalidate();
        turnsView.invalidate();
    }

    public void showInfoDialog(String result, String info, String cancelText, View.OnClickListener cancelOnClick, String confirmText,
                                  View.OnClickListener confirmOnClick) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle(result);
        builder.setButtonCancel(cancelText, (View.OnClickListener) cancelOnClick);
        builder.setButtonConfirm(confirmText, (View.OnClickListener) confirmOnClick);

        CustomDialog customDialog = builder.create();
        customDialog.show();
    }

    public void ShowDialog(String winnerName, Consumer<Void> yesCallBack){
        AlertDialog dialog;
        builder.setTitle(winnerName+"获胜！").setMessage("是否要再来一局？");
        builder.setPositiveButton("是", (dialog1, id) -> {dialog1.dismiss();yesCallBack.accept(null);});
        builder.setNegativeButton("否", (dialog1, id) -> dialog1.dismiss());
        dialog = builder.create();
        dialog.show();
    }
    @SuppressLint("SetTextI18n")
    public void SetTurns(int turns){
        if (Looper.myLooper() == Looper.getMainLooper()) { // UI主线程
            turnsView.setText("回合数："+turns);
        } else { // 非UI主线程
            turnsView.post(() -> turnsView.setText("回合数："+turns));
        }
    }
    @SuppressLint("SetTextI18n")
    public void SetName(String name){
        if (Looper.myLooper() == Looper.getMainLooper()) { // UI主线程
            nameView.setText(name+"行动中...");
        } else { // 非UI主线程
            nameView.post(() -> nameView.setText(name+"行动中..."));
        }
    }
}
