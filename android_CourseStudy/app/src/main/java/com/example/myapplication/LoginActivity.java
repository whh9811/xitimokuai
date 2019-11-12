package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MySettingFragment;
import com.example.myapplication.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText lgUsername,lgPassword;
    private Button btnLogin;
    private TextView registerNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lgUsername = findViewById(R.id.login_user_name);
        lgPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btn);
        registerNow = findViewById(R.id.register_now);
        registerNow.setOnClickListener(this);
        final Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        lgUsername.setText(username);
        initToolbar();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = lgUsername.getText().toString();
                String password = lgPassword.getText().toString();
                String spPwd = readPwd(username);
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                } else if (!spPwd.equals(MD5Utils.md5(password))){
                    Toast.makeText(LoginActivity.this,"密码或用户名无效",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(spPwd)){
                    Toast.makeText(LoginActivity.this,"请先注册",Toast.LENGTH_SHORT).show();
                } else {
                    saveLoginStatus(username,true);
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("登录中");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            progressDialog.dismiss();
                            String username = lgUsername.getText().toString();
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            //返回到我的页面
                            Intent intent1 = new Intent(LoginActivity.this, MySettingFragment.class);
                            intent1.putExtra("isLogin",true);
                            intent1.putExtra("loginUser",username);
                            setResult(RESULT_OK,intent1);
                            startActivity(intent1);
                            LoginActivity.this.finish();
                        }
                    },5000);
                }
            }

            private void saveLoginStatus(String username, boolean isLogin) {
                getSharedPreferences("data",MODE_PRIVATE)
                        .edit()
                        .putString("loginUser",username)
                        .putBoolean("isLogin",isLogin)
                        .apply();
            }

            private String readPwd(String username){
                SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
                return sp.getString(username,"");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null){
            String username = data.getStringExtra("username");
            lgUsername.setText(username);
//            lgUsername.setSelection(username.length());
        }
    }

    //    private void initData() {
//        String username = readPref();
//        if (!TextUtils.isEmpty(username)){
//            lgUsername.setText(username);
//        }
//    }
//
//    private String readPref() {
//        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
//        return sp.getString("username","");
//    }

    private void initToolbar(){
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }
    @Override
    public void onClick(View view) {
        //处理register的监听
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivityForResult(intent,1);
    }
}

