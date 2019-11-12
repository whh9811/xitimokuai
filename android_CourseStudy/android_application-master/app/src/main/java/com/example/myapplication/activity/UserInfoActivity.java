package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.entity.User;
import com.example.myapplication.service.impl.UserService;
import com.example.myapplication.service.impl.UserServiceImpl;

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
    private LinearLayout fLayout,sLayout,tLayout,forthLayout,fifthLayout;
    private TextView usernameContent,nicknameContent,sexContent,signatureContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initData();
        initView();
        final String[] sexArr = new String[]{"男","女"};
        initToolbar();
        //从数据库，网络或上一个界面的数据
        tLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick = nicknameContent.getText().toString();
                Intent intent = new Intent(UserInfoActivity.this,ModifyNicknameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nickname",nick);
                bundle.putString("title","设置昵称");
                bundle.putInt("flag",1);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
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
                Intent intent = new Intent(UserInfoActivity.this,ModifySignatureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("signature",sign);
                bundle.putString("title","设置签名");
                bundle.putInt("flag",1);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });
    }

    private void initView() {
        fLayout =findViewById(R.id.first_layout);
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
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoActivity.this.finish();
            }
        });
    }

    private void initData() {
        username = readLoginInfo();
        userService = new UserServiceImpl(this);
        user =userService.get(username);
        if (user == null){
            user = new User();
            user.setUsername(username);
            user.setNickname("123");
            user.setSex("男");
            user.setSignature("123");
            userService.save(user);
        }
    }

    private String readLoginInfo() {
        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        return sp.getString("loginUser","");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //对空数据返回异常做判断
        if (data == null || resultCode != RESULT_OK){
            Toast.makeText(UserInfoActivity.this,"未知错误",Toast.LENGTH_SHORT).show();
        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK && data!=null) {
            String nickname = data.getStringExtra("nickname");
            if (!TextUtils.isEmpty(nickname)) {
                nicknameContent.setText(nickname);
                user.setNickname(nickname);
            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && data!=null){
            String signature =data.getStringExtra("signature");
            if (!TextUtils.isEmpty(signature)) {
                signatureContent.setText(signature);
                user.setSignature(signature);
            }
        }
        userService.modify(user);
    }
}
