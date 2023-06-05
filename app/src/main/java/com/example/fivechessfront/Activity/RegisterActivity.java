package com.example.fivechessfront.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fivechessfront.Entity.Account;
import com.example.fivechessfront.R;
import com.example.fivechessfront.utils.MyHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (password.equals(confirmPassword)) {
                    // 注册成功，保存账户信息到数据库
                    saveAccount(username, password);

                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    // 跳转到登录界面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveAccount(String username, String password) {
        // 将账户信息保存到数据库
        MyHelper myHelper = new MyHelper(this);
        myHelper.insertNewAccount(new Account(username, password));
        Log.d("RegisterActivity", "saveAccount: " + username + " " + password);
    }
}
