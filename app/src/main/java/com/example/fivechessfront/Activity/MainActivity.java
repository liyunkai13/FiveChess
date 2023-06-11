package com.example.fivechessfront.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.fivechessfront.Entity.GameHistory;
import com.example.fivechessfront.Enums.GameDifficulty;
import com.example.fivechessfront.R;
import com.example.fivechessfront.View.CreateRoomDialog;

public class MainActivity extends AppCompatActivity {

    private Button startAiGameButton;
    private Button startPeopleGameButton;
    private Button startInternetGameButton;
    private Button showHistoryButton;
    private Button exitButton;
    private Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPeopleGameButton = findViewById(R.id.startPeopleGameButton);
        startAiGameButton = findViewById(R.id.startAiGameButton);
        startInternetGameButton = findViewById(R.id.createRoomButton);
        showHistoryButton = findViewById(R.id.showHistoryButton);
        exitButton = findViewById(R.id.exitButton);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        ArrayAdapter<GameDifficulty> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GameDifficulty.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);
        GameDifficulty selectedDifficulty = (GameDifficulty) difficultySpinner.getSelectedItem();
        startAiGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取选择的难度值
                GameDifficulty selectedDifficulty = (GameDifficulty) difficultySpinner.getSelectedItem();
                // 创建Intent并传递选择的难度值
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("difficulty", selectedDifficulty.getValue());
                intent.putExtra("mode", "ai");
                // 启动GameActivity
                startActivity(intent);
            }
        });
        startPeopleGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("mode", "people");
                startActivity(intent);
            }
        });
        startInternetGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示对话框，输入房间号
                CreateRoomDialog dialog = new CreateRoomDialog(MainActivity.this);
                dialog.setTitle("创建房间");
                dialog.setConfirm("确定");
                dialog.setCancel("取消");
                dialog.setHint("房间号");
                dialog.setOnDialogListener(new CreateRoomDialog.OnDialogListener() {
                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                    }
                    @Override
                    public void onPositiveClick() {
                        //TODO: 看看这里可不可以直接跳转到GameActivity，传递roomID参数
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra("roomID", dialog.getRoomID());
                        intent.putExtra("mode", "internet");
                        // 启动GameActivity
                        startActivity(intent);

                    }
                });
                dialog.show();
            }
        });
        showHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到历史界面
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前Activity
                System.exit(0); // 退出应用程序
            }
        });
    }

}
