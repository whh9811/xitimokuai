package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.Utils.MD5Utils;
import com.example.login.Utils.StatusUtils;


public class loginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setImmersionMode(this);
        setContentView(R.layout.activity_login);

        StatusUtils.initToolbar(this, "登录", true, false);  // 初始化toolbar

        initView();  // 初始化界面控件
        initData();  // 初始化传入的数据
    }

    private void initData() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (!TextUtils.isEmpty(username)) {
            etUsername.setText(username);
        }
    }

    private void initView() {
        etUsername = findViewById(R.id.username_edit);
        etPassword = findViewById(R.id.userpassword_edit);

        TextView tvRegister = findViewById(R.id.register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, signinActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Button btnLogin = findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        password = MD5Utils.md5(password);
        String spPwd = readPwd(username);

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(loginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(loginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(spPwd)) {
            Toast.makeText(loginActivity.this, "请先注册", Toast.LENGTH_SHORT).show();
        } else if (!spPwd.equals(password)) {
            Toast.makeText(loginActivity.this, "输入的密码不正确", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            saveLoginStatus(username, true);

            // 返回到我的界面
            Intent intent = new Intent();
            intent.putExtra("isLogin", true);
            intent.putExtra("loginUser", username);
            setResult(RESULT_OK, intent);
            loginActivity.this.finish();
        }
    }

    /**
     * 保存登录的状态和用户名
     * @param username
     * @param isLogin
     */
    private void saveLoginStatus(String username, boolean isLogin) {
        getSharedPreferences("userInfo", MODE_PRIVATE)
                .edit()
                .putString("loginUser", username)
                .putBoolean("isLogin", isLogin)
                .apply();
    }

    private String readPwd(String username) {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        return sp.getString(username, "");
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String username = data.getStringExtra("username");
            etUsername.setText(username);
//                etUsername.setSelection(username.length());
        }
    }

    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}
