package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.entity.User;
import com.example.myapplication.service.impl.UserService;
import com.example.myapplication.service.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {
    //定义所需变量
    private User user;
    private UserService userService;
    private String username;
    //定义控件对象
    private Toolbar toolbar;
    private LinearLayout fLayout, sLayout, tLayout, forthLayout, fifthLayout;
    private TextView usernameContent, nicknameContent, sexContent, signatureContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initData();
        initView();
        final String[] sexArr = new String[]{"男", "女"};
        initToolbar();
        //从数据库，网络或上一个界面的数据
        tLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick = nicknameContent.getText().toString();
                Intent intent = new Intent(UserInfoActivity.this, ModifyNicknameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nickname", nick);
                bundle.putString("title", "设置昵称");
                bundle.putInt("flag", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        forthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sex = sexContent.getText().toString();

                List<String> sexs = Arrays.asList(sexArr);
                int selected = sexs.indexOf(sex);
                final AlertDialog.Builder dialog = new AlertDialog.Builder(UserInfoActivity.this);
                dialog.setTitle("性别");
                dialog.setCancelable(false);
                dialog.setSingleChoiceItems(sexArr, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sexContent.setText(sexArr[i]);
                        user.setSex(sexArr[i]);
                        userService.modify(user);
                        saveToInternal(user);
                        savePrivateExStorage(user);
                        savePublicExStorage(user);
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });
        fifthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1获取已有内容
                //2根据需要传递数据到下一个activity
                //3启动下一个页面
                String sign = signatureContent.getText().toString();
                Intent intent = new Intent(UserInfoActivity.this, ModifySignatureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("signature", sign);
                bundle.putString("title", "设置签名");
                bundle.putInt("flag", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });
    }

    private void initView() {
        fLayout = findViewById(R.id.first_layout);
        sLayout = findViewById(R.id.second_layout);
        tLayout = findViewById(R.id.third_layout);
        forthLayout = findViewById(R.id.forth_layout);
        fifthLayout = findViewById(R.id.fifth_layout);

        usernameContent = findViewById(R.id.username_content);
        nicknameContent = findViewById(R.id.nickname_content);
        sexContent = findViewById(R.id.sex_content);
        signatureContent = findViewById(R.id.signature_content);

        usernameContent.setText(username);
        nicknameContent.setText(user.getNickname());
        sexContent.setText(user.getSex());
        signatureContent.setText(user.getSignature());
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoActivity.this.finish();
            }
        });
    }

    private static final String FILE_NAME = "userinfo.txt";

    private void saveToInternal(User user) {
        try {
            FileOutputStream out = this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(JSON.toJSONString(user));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取文件内容
    private User readFromInternal() {
        User user = null;
        try {
            FileInputStream in = this.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data, User.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void savePrivateExStorage(User user) {
        try {
            File file = new File(getExternalFilesDir(""), FILE_NAME);
            FileOutputStream out = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(JSON.toJSONString(user));
            writer.flush();
            writer.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int REQUEST_READ_USERINFO = 101;
    private static final int REQUEST_WRITE_USERINFO = 102;

    private void savePublicExStorage(User user) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_USERINFO);
                return;
            }
        }
        saveUserInfo(user);
    }

    private User readPrivateExStorage() {
        User user = null;
        try {
            File file = new File(getExternalFilesDir(""), FILE_NAME);
            if (!file.exists()) {
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data, User.class);
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "申请权限被拒绝，无法执行操作", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == REQUEST_READ_USERINFO) {
            user = readUserinfo();
        } else if (requestCode == REQUEST_WRITE_USERINFO) {
            saveUserInfo(user);
        }
    }

    private User readUserinfo() {
        User user = null;
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);
        try {
            FileInputStream in = new FileInputStream(file);
            int length = in.available();
            byte[] data = new byte[length];
            int len = in.read(data);
            user = JSON.parseObject(data,User.class);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"读取失败",Toast.LENGTH_SHORT).show();
        }
        return user;
    }

    private void saveUserInfo(User user) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);
            if (!file.exists()) {
                return;
            }
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data, User.class);
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User readPublicExStorage() {
        User user = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_READ_USERINFO);
                return user;
            }
        }
        return readUserinfo();
    }

    private void initData() {
        username = readLoginInfo();
        userService = new UserServiceImpl(this);
        user = userService.get(username);
        user = readFromInternal();
        user = readPrivateExStorage();
        user = readPublicExStorage();
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setNickname("123");
            user.setSex("男");
            user.setSignature("123");
            userService.save(user);
            saveToInternal(user);
            savePrivateExStorage(user);
            savePublicExStorage(user);
        }
    }


    private String readLoginInfo() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        return sp.getString("loginUser", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //对空数据返回异常做判断
        if (data == null || resultCode != RESULT_OK) {
            Toast.makeText(UserInfoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            String nickname = data.getStringExtra("nickname");
            if (!TextUtils.isEmpty(nickname)) {
                nicknameContent.setText(nickname);
                user.setNickname(nickname);
            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            String signature = data.getStringExtra("signature");
            if (!TextUtils.isEmpty(signature)) {
                signatureContent.setText(signature);
                user.setSignature(signature);
            }
        }
        userService.modify(user);
        saveToInternal(user);
        savePrivateExStorage(user);
        savePublicExStorage(user);
    }
}
