package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ModifyNicknameActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editNickname;
    private ImageView clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nickname);
        initToolbar();
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            String oldNick = bundle.getString("nickname");
            editNickname.setText(oldNick);
        } else {
            editNickname.setText("");
        }
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNickname.setText("");
            }
        });
    }

    //加载选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info,menu);
        return true;
    }

    //菜单项的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                save();
                break;
            case R.id.item_cancel:
                ModifyNicknameActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        String nickname = editNickname.getText().toString();
        if (TextUtils.isEmpty(nickname)){
            Toast.makeText(ModifyNicknameActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(ModifyNicknameActivity.this,UserInfoActivity.class);
            intent.putExtra("nickname",nickname);
            setResult(RESULT_OK,intent);
            Toast.makeText(ModifyNicknameActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
            ModifyNicknameActivity.this.finish();
        }
    }

    private void initView() {
        editNickname = findViewById(R.id.edit_nickname);
        clear = findViewById(R.id.clear_nickname);
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("设置昵称");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyNicknameActivity.this.finish();
            }
        });
    }
}
