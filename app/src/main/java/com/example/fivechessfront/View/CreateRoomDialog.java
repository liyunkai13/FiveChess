package com.example.fivechessfront.View;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.fivechessfront.R;

public class CreateRoomDialog extends AlertDialog {
    private TextView textViewTitle;
    private EditText editTextInput;
    private Button buttonCancel, buttonConfirm;
    private String title;
    private String hint;
    private String cancel;
    private String confirm;
    private String RoomID;
    public OnDialogListener OnDialogListener;
    private final Context context;

    public CreateRoomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_room);
        initView();
        initEvent();
    }
    //初始化界面
    private void initView() {
        textViewTitle = findViewById(R.id.textViewTitle);
        editTextInput = findViewById(R.id.editTextInput);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonConfirm = findViewById(R.id.buttonConfirm);
    }
    //初始化数据
    private void initData() {
        //如果标题不为空，设置标题
        if (title != null) {
            textViewTitle.setText(title);
        }
        //如果提示内容不为空，设置提示内容
        if (hint != null) {
            editTextInput.setHint(hint);
        }
        //如果取消按钮文字不为空，设置取消按钮文字
        if (cancel != null) {
            buttonCancel.setText(cancel);
        }
        //如果确认按钮文字不为空，设置确认按钮文字
        if (confirm != null) {
            buttonConfirm.setText(confirm);
        }
    }
    //初始化点击事件监听
    private void initEvent() {
        //初始化EditText事件,随输入内容变化更新RoomID
        editTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                RoomID = s.toString();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RoomID = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                RoomID = s.toString();
            }
        });
        //设置取消按钮点击事件监听器
        buttonCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnDialogListener != null)
                    OnDialogListener.onNegativeClick();
            }
        });
        //设置确认按钮点击事件监听器
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnDialogListener != null)
                    OnDialogListener.onPositiveClick();
            }
        });
    }
    //重写show方法，在show时初始化数据
    @Override
    public void show() {
        super.show();
        initData();
    }

    //实现dialog的点击事件接口
    public interface OnDialogListener {
        void onNegativeClick();
        void onPositiveClick();
    }
    //设置dialog的点击事件监听器
    public void setOnDialogListener(OnDialogListener OnDialogListener) {
        this.OnDialogListener = OnDialogListener;
    }
    //获取输入框内容
    public String getRoomID() {
        return RoomID;
    }
    //设置标题
    public void setTitle(String title) {
        this.title = title;
    }
    //设置输入框提示语
    public void setHint(String hint) {
        this.hint = hint;
    }
    //设置取消按钮文字
    public void setCancel(String cancel) {
        this.cancel = cancel;
    }
    //设置确认按钮文字
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

}
