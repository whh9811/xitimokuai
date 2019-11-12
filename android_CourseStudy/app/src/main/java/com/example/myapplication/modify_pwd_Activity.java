package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.utils.MD5Utils;

public class modify_pwd_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText ifpwd,inpwd,inpwdAgain;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        initView();
        initToolbar();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = readLoginInfo();
                String firstPwd = ifpwd.getText().toString();
                String inputPwd = inpwd.getText().toString();
                String inputPwdAgain = inpwdAgain.getText().toString();
                String spPwd = readPwd(username);
                if (TextUtils.isEmpty(firstPwd)||TextUtils.isEmpty(inputPwdAgain)||TextUtils.isEmpty(inputPwd)){
                    Toast.makeText(modify_pwd_Activity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                } else if (!spPwd.equals(MD5Utils.md5(firstPwd))){
                    Toast.makeText(modify_pwd_Activity.this,"与原密码不相同",Toast.LENGTH_SHORT).show();
                } else if (!inputPwd.equals(inputPwdAgain)){
                    Toast.makeText(modify_pwd_Activity.this,"两次输入的密码必须一致",Toast.LENGTH_SHORT).show();
                } else {
                    savePref(username,MD5Utils.md5(inputPwdAgain));
                    Intent intent = new Intent(modify_pwd_Activity.this,LoginActivity.class);
                    startActivity(intent);
                    modify_pwd_Activity.this.finish();
                    Toast.makeText(modify_pwd_Activity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String readPwd(String username) {
        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        return sp.getString(username,"");
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_pwd_Activity.this.finish();
            }
        });
    }

    private String readLoginInfo() {
        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        return sp.getString("loginUser","");
    }

    private void savePref(String username,String password){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString(username,password);
        editor.apply();
    }
    private void initView() {
        ifpwd = findViewById(R.id.input_first_pwd);
        inpwd = findViewById(R.id.input_new_pwd);
        inpwdAgain = findViewById(R.id.input_new_pwd_again);
        saveButton = findViewById(R.id.save_btn);
    }
}
