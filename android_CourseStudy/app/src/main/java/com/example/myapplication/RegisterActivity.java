package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    //1.获取界面上的控件
    //2.button监听事件
    //3.处理点击事件
    //3.1获取控件的值
    //3.2检查数据的有效性
    //3.3将数据储存
    //3.4跳转到登陆页面
    private EditText etUsername,etPassword,etpswAgain;
    private Button btnRegister;
    private RadioButton rbtnMale;
    private RadioButton rbtnFemale;
    private RadioGroup rgSex;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rgSex = findViewById(R.id.group_sex);
        rbtnMale = findViewById(R.id.rbtn_male);
        rbtnFemale = findViewById(R.id.rbtn_female);
        //1
        initView();
        initToolbar();
        //2
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //3.1
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String pswAgain = etpswAgain.getText().toString();
                //获取选中的radio值
                String sex = "";
                int id = rgSex.getCheckedRadioButtonId();
                if (id == R.id.rbtn_male){
                    sex = rbtnMale.getText().toString();
                } else if (id == R.id.rbtn_female){
                    sex = rbtnFemale.getText().toString();
                }
                //3.2
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)||TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                } else if (!password.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this,"两次密码必须一致",Toast.LENGTH_SHORT).show();
                } else if (isExist(username)){
                    Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                }else {
                    savePref(username, MD5Utils.md5(password));
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setTitle("提示框");
                    dialog.setMessage("注册成功是否要跳转到登录界面");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String username = etUsername.getText().toString();
                            Intent intent = new Intent();
                            intent.putExtra("username",username);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
            }
        });

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
    }

    /**
     * 保存用户信息
     * @param username
     * @param password
     */
    private void savePref(String username, String password) {
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
//        editor.putString("username",username);
//        editor.putString("password",password);
//        editor.putString("sex",sex);
        editor.putString(username,password);
        editor.apply();
    }

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * return true 存在 false 不存在
     */
    private boolean isExist(String username){
        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        String pwd = sp.getString(username,"");
        return !TextUtils.isEmpty(pwd);
    }

    private void initView() {
        etUsername = findViewById(R.id.user_name);
        etPassword = findViewById(R.id.register_password);
        etpswAgain = findViewById(R.id.register_password_again);
        btnRegister = findViewById(R.id.register_btn);
    }
}
