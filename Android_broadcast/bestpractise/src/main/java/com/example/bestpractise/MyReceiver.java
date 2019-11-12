package com.example.bestpractise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");
        Toast.makeText(context,"other模块的MyCustomReceiver接收到："+name,Toast.LENGTH_SHORT).show();
    }
}
