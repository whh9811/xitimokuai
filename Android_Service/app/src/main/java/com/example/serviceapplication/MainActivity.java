package com.example.serviceapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvStartService, tvStopService, tvbindService, tvtieService,
            tvStartforeService, tvStopforeService, tvStartIntentService, tvStart,
            tvPause, tvStop;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initView();
    }

    private void initView() {
        tvStartService = findViewById(R.id.tv_start_service);
        tvStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvStopService = findViewById(R.id.tv_stop_service);
        tvStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvbindService = findViewById(R.id.tv_bind_service);
        tvbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  
            }
        });
        tvtieService = findViewById(R.id.tv_tie_service);
        tvtieService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvStartforeService = findViewById(R.id.tv_start_foreservice);
        tvStartforeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvStopforeService = findViewById(R.id.tv_stop_foreservice);
        tvStopforeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvStartIntentService = findViewById(R.id.tv_intentService);
        tvStartIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvStart = findViewById(R.id.tv_start);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvPause = findViewById(R.id.tv_pause);
        tvPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvStop = findViewById(R.id.tv_stop);
        tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("ServiceDemo");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
    }

    public void onClick(View view) {
    }
}
