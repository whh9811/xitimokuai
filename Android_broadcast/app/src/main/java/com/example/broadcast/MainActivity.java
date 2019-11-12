package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyCustomReceiver myCustomReceiver;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //发送标准广播按钮的事件监听和处理
        Button btnNormal = findViewById(R.id.btn_normal);
        btnNormal.setOnClickListener(this);
//        btnNormal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction("com.example.broadcast.MY_NORMAL_BROADCAST");
//                intent.putExtra("name","标准自定义广播");
//                sendBroadcast(intent);
//            }
//        });
        //发送有序广播按钮的事件监听和处理
        Button btnOrdered = findViewById(R.id.btn_ordered);
        btnOrdered.setOnClickListener(this);
//        btnOrdered.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction("com.example.broadcast.MY_ORDERED_BROADCAST");
//                intent.putExtra("name","有序自定义广播");
//                sendOrderedBroadcast(intent,null);
//            }
//        });

        //发送本地广播按钮的事件监听和处理
        Button btnLocal = findViewById(R.id.btn_local);
        btnLocal.setOnClickListener(this);
//        btnLocal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent("com.example.broadcast.MY_LOCAL_BROADCAST");
//                intent.putExtra("name","本地自定义广播");
//                manager.sendBroadcast(intent);
//            }
//        });
        myCustomReceiver = new MyCustomReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.broadcast.MY_ORDERED_BROADCAST");
        filter.addAction("com.example.broadcast.MY_NORMAL_BROADCAST");
        filter.addAction("com.example.broadcast.MY_LOCAL_BROADCAST");
//        this.registerReceiver(myCustomReceiver,filter);
        manager.registerReceiver(localReceiver,filter);
    }
    private NetworkReceiver networkReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        //动态注册系统广播的接收器
        if (networkReceiver == null){
            networkReceiver = new NetworkReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkReceiver,filter);

        //动态注册自定义广播的接收器，标准和有序
        if (myCustomReceiver == null){
            myCustomReceiver = new MyCustomReceiver();
        }
        filter = new IntentFilter();
        filter.addAction("com.example.broadcast.MY_NORMAL_BROADCAST");
        filter.addAction("com.example.broadcast.MY_ORDERED_BROADCAST");
        filter.setPriority(100);
        registerReceiver(myCustomReceiver,filter);

        //动态注册本地广播
        if (localReceiver == null){
            localReceiver = new LocalReceiver();
        }
        filter = new IntentFilter();
        filter.addAction("com.example.broadcast.MY_LOCAL_BROADCAST");
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(localReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消注销
        if (networkReceiver != null){
            unregisterReceiver(networkReceiver);
        }
        if (myCustomReceiver != null){
            unregisterReceiver(myCustomReceiver);
        }
        if (localReceiver != null){
            manager.unregisterReceiver(localReceiver);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_normal:
                Intent intent = new Intent();
                intent.setAction("com.example.broadcast.MY_NORMAL_BROADCAST");
                intent.putExtra("name","标准自定义广播");
                sendBroadcast(intent);
                break;
            case R.id.btn_local:
                intent = new Intent("com.example.broadcast.MY_LOCAL_BROADCAST");
                intent.putExtra("name","本地自定义广播");
                manager.sendBroadcast(intent);
                break;
            case R.id.btn_ordered:
                intent = new Intent();
                intent.setAction("com.example.broadcast.MY_ORDERED_BROADCAST");
                intent.putExtra("name","有序自定义广播");
                sendOrderedBroadcast(intent,null);
                break;
        }
    }
}
