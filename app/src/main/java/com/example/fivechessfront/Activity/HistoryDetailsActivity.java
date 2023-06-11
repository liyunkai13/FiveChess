package com.example.fivechessfront.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.Entity.GameHistory;
import com.example.fivechessfront.R;
import com.example.fivechessfront.View.ChessboardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryDetailsActivity extends AppCompatActivity {
    private GameHistory gameHistory;
    private ChessboardView chessboardView;
    private Board board;
    private List<int[]> Intentions;
    private TextView textView;
    private ImageButton btnBack;
    private ImageButton whetherPlay;
    private ImageButton btnNext;
    private Boolean isPlaying;
    //落子间隔时长
    private static final int DROP_INTERVAL = 500;
    //当前落子的下标
    private int currentIndex = 0;

    public void Init() {
        setContentView(R.layout.activity_history_details);
        //接收一个GameHistory对象,获取
        gameHistory = (GameHistory) getIntent().getSerializableExtra("gameHistory");
        Log.d("gameHistory", gameHistory.toString());
        Intentions = splitStringIntoArrays(gameHistory.getProcess());
        for (int[] array : Intentions) {
            System.out.println(Arrays.toString(array));
        }
        chessboardView = findViewById(R.id.chessboard_view);
        board = new Board();
        textView = findViewById(R.id.turnsView);
        btnBack = findViewById(R.id.backButton);
        whetherPlay = findViewById(R.id.pauseButton);
        btnNext = findViewById(R.id.forwardButton);
        isPlaying = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        Init();
        chessboardView.setBoard(board);
        // 设置 btnBack 的点击事件监听器
        btnBack.setOnClickListener(v -> {
            cancelDrop();
        });
        //设置 whetherPlay 的点击事件监听器
        whetherPlay.setOnClickListener(v -> {
            if (isPlaying) {
                isPlaying = false;
                whetherPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play));
            } else {
                isPlaying = true;
                Thread thread = new AutoDropThread();
                thread.start();
                whetherPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.suspend));
            }
        });
        //设置 btnNext 的点击事件监听器
        btnNext.setOnClickListener(v -> {
            nextDrop();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new AutoDropThread();
        thread.start();
    }
    //将gameHistory中的process字符串分割成int数组,即单次落子的坐标
    public static List<int[]> splitStringIntoArrays(String string) {
        List<int[]> arrays = new ArrayList<>();
        for (int i = 0; i < string.length(); i += 2) {
            String substring = string.substring(i, Math.min(i + 2, string.length()));
            if (substring.length() == 2 &&substring.matches("\\d+")) {
                int[] array = new int[2];
                array[0] = Character.getNumericValue(substring.charAt(0));
                array[1] = Character.getNumericValue(substring.charAt(1));
                arrays.add(array);
            }
        }
        return arrays;
    }
    //棋盘遍历Intentions中的坐标自动落子的线程,每次落子间隔DROP_INTERVAL,黑子先行
    private class AutoDropThread extends Thread {
        @Override
        public void start() {
            super.start();
            isPlaying = true;
        }
        @Override
        public void run() {
            super.run();
            while (isPlaying&&currentIndex<Intentions.size()) {
                Log.d("currentIndex", String.valueOf(currentIndex));
                int[] intention = Intentions.get(currentIndex);
                board.placePiece(intention[0], intention[1],currentIndex % 2 == 0 ? 1 : 2);
                chessboardView.postInvalidate();
                textView.post(() -> textView.setText("回合数："+currentIndex));
                currentIndex++;
                try {
                    Thread.sleep(DROP_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            whetherPlay.setImageDrawable(ContextCompat.getDrawable(HistoryDetailsActivity.this, R.drawable.play));
        }
    }

    //取消上一个落子,并停止落子
    public void cancelDrop() {
        if (currentIndex > 0) {
            int[] intention = Intentions.get(currentIndex-1);
            board.cancelPiece(intention[0], intention[1]);
            currentIndex--;
            isPlaying = false;
            whetherPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play));
            chessboardView.postInvalidate();
            textView.setText("回合数："+currentIndex);
        }
    }
    //下一个落子
    public void nextDrop() {
        if (currentIndex < Intentions.size()) {
            int [] intention = Intentions.get(currentIndex);
            board.placePiece(intention[0],intention[1],currentIndex % 2 == 0 ? 1 : 2);
            chessboardView.postInvalidate();
            currentIndex++;
            textView.setText("回合数："+currentIndex);
        }

    }

}