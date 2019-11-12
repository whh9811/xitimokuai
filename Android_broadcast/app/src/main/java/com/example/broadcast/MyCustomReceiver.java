package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyCustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");
        Toast.makeText(context,"本模块的MyCustomReceiver接收到："+name,Toast.LENGTH_SHORT).show();

        if (name.contains("有序")){
            Toast.makeText(context,"本模块的MyCustomReceiver接收:"+name,Toast.LENGTH_SHORT).show();
            abortBroadcast();//取消有序广播
        }
    }
}
