package com.example.myapplicationfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private TextView tvVerison; //控件对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        1.获取需要的组件
        tvVerison = findViewById(R.id.tv_version);
//        2.获取或设置控件的值
        String version = tvVerison.getText().toString();
        try{
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),0);
            tvVerison.setText("版本号：" + info.versionName);
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
//        3.跳转页面
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        };
        timer.schedule(task,5000);
    }
}
