package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Forget_pwd_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText iyusername,yname;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        initView();
        initToolbar();
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = iyusername.getText().toString();
                String secretProtect = yname.getText().toString();
                if (TextUtils.isEmpty(username)||TextUtils.isEmpty(secretProtect)){
                    Toast.makeText(Forget_pwd_Activity.this,"用户名或密保不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    savePref(secretProtect);
                    final ProgressDialog progressDialog = new ProgressDialog(Forget_pwd_Activity.this);
                    progressDialog.setMessage("保存中");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(Forget_pwd_Activity.this,"保存完毕",Toast.LENGTH_SHORT).show();
                        }
                    },5000);
                }
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("设置密保");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Forget_pwd_Activity.this.finish();
            }
        });
    }

    private void initView() {
        iyusername = findViewById(R.id.input_your_username);
        yname = findViewById(R.id.your_name);
        confirmBtn = findViewById(R.id.confirm_btn);
    }

    private void savePref(String secretProtect){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("secretProtect",secretProtect);
        editor.apply();
    }
}
