package edu.niit.android.course;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.niit.android.course.utils.MD5Utils;
import edu.niit.android.course.utils.StatusUtils;

public class ForgetpsdActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "ForgetpsdActivity";
    // 1. 获取界面上的控件
    // 2. button的点击事件的监听
    // 3. 处理点击事件
    // 3.1 获取控件的值
    // 3.2 检查数据的有效性
    // 3.3 将注册信息存储
    // 3.4 跳转到登录界面
    private EditText etPwdBefore, etPassword, etPwdAgain;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setImmersionMode(this);
        setContentView(R.layout.activity_forgetpsd);

        // 初始化标题栏
        StatusUtils.initToolbar(this, "注册", true, false);
        // 1. 获取界面上的控件
        initView();
        // 2. button的点击事件的监听
        btnSave.setOnClickListener(this);
    }

    private void initView() {
        etPwdBefore = findViewById(R.id.et_pwd_before);
        etPassword = findViewById(R.id.et_password);
        etPwdAgain = findViewById(R.id.et_pwd_again);
        btnSave = findViewById(R.id.btn_save);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveter();
                break;
        }
    }

    private void saveter() {
        // 3.1
        String pwdBefore = etPwdBefore.getText().toString();
        String password = etPassword.getText().toString();
        String pwdAgain = etPwdAgain.getText().toString();
        // 3.2
        if (TextUtils.isEmpty(pwdBefore)) {
            Toast.makeText(ForgetpsdActivity.this, "原始密码不能为空",
                    Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(pwdAgain)) {
            Toast.makeText(ForgetpsdActivity.this, "新密码不能为空",
                    Toast.LENGTH_SHORT).show();
        } else if (!password.equals(pwdAgain)) {
            Toast.makeText(ForgetpsdActivity.this, "两次密码必须一致",
                    Toast.LENGTH_SHORT).show();
        } else {
            // 注册成功之后
            savePref(MD5Utils.md5(password));
            Intent intent = new Intent();
            intent.putExtra("password", password);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 保存注册密码
     * @param password 密码，类型String
     */
    private void savePref(String password) {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", password);
//        editor.putString(password);
        editor.apply();
        Log.d(TAG, password);

    }

}
