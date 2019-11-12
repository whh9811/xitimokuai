package com.example.bestpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register();
    }

    private void register(){
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.broadcast.MY_NORMAL_BROADCAST");
        filter.addAction("com.example.broadcast.MY_ORDERED_BROADCAST");
        filter.setPriority(50);
        this.registerReceiver(myReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消注销
        if (myReceiver != null){
            unregisterReceiver(myReceiver);
        }
    }
}
