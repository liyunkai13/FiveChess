package com.example.fivechessfront.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fivechessfront.Entity.Account;
import com.example.fivechessfront.Activity.MainActivity;
import com.example.fivechessfront.R;
import com.example.fivechessfront.utils.AccountManager;
import com.example.fivechessfront.utils.MyHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private String username;
    private String password;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        Button loginButton = findViewById(R.id.loginButton);
        pref = getSharedPreferences("data", MODE_PRIVATE);
        //获取sharePreferences中的账号密码
        username = pref.getString("username", "");
        password = pref.getString("password", "");
        usernameEditText.setText(username);
        passwordEditText.setText(password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                // 在数据库中验证账户信息
                boolean isValid = validateAccount(username, password);

                if (isValid) {

                    // 执行登录逻辑，获取账户对象
                    Log.d("LoginActivity", "账号密码正确");
                    Account account = new Account(username, password);
                    // 保存账户对象到 AccountManager
                    AccountManager.getInstance().setAccount(account);
                    //使用sharePreferences保存账号密码
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                    // 登录成功，跳转到主界面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号不存在或密码错误", Toast.LENGTH_SHORT).show();
                    Log.d("LoginActivity", "账号密码错误");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private boolean validateAccount(String username, String password) {
        // 在数据库中验证账户信息
        MyHelper myHelper = new MyHelper(this);
        List<Account> list = myHelper.selectByAccountAndPass(username, password);
        return list.size() > 0;
    }
}