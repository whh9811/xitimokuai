package com.example.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ExerciseDetailAdapter;
import com.example.myapplication.entity.Practise;
import com.example.myapplication.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailActivity extends AppCompatActivity implements ExerciseDetailAdapter.OnSelectListener {
    private int id;
    private String title;

    private List<Practise> details;

    private RecyclerView lvdetails;
    private ExerciseDetailAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recyclerview);

        initToolbar();
        initData();
        initView();
    }

    private void initView() {
        lvdetails = findViewById(R.id.lv_detail);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        lvdetails.setLayoutManager(manager);
        adapter = new ExerciseDetailAdapter(details,this);
        lvdetails.setAdapter(adapter);
    }

    private void initData() {
        id = getIntent().getIntExtra("id",0);
        title = getIntent().getStringExtra("title");

        details = new ArrayList<>();
        try {
            InputStream is = getResources().getAssets().open("chapter"+id+".xml");
            details = IOUtils.getXmlContents(is);
            is.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("练习题");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciseDetailActivity.this.finish();
            }
        });

    }

    @Override
    public void OnselectA(int position, TextView ivA, TextView ivB, TextView ivC, TextView ivD) {
        Practise practise = details.get(position);
        if (practise.getAnswer()!= 1){
            practise.setSelect(1);
        } else {
            practise.setSelect(0);
        }
        switch (practise.getAnswer()){
            case 1:
                ivA.setText("");
                ivA.setBackgroundResource(R.mipmap.right);
                break;
            case 2:
                ivB.setText("");
                ivA.setText("");
                ivB.setBackgroundResource(R.mipmap.right);
                ivA.setBackgroundResource(R.mipmap.wrong);
                break;
            case 3:
                ivC.setText("");
                ivA.setText("");
                ivC.setBackgroundResource(R.mipmap.right);
                ivA.setBackgroundResource(R.mipmap.wrong);
                break;
            case 4:
                ivD.setText("");
                ivA.setText("");
                ivD.setBackgroundResource(R.mipmap.right);
                ivA.setBackgroundResource(R.mipmap.wrong);
                break;
        }
    }

    @Override
    public void OnselectB(int position, TextView ivA, TextView ivB, TextView ivC, TextView ivD) {
        Practise practise = details.get(position);
        if (practise.getAnswer()!= 2){
            practise.setSelect(2);
        } else {
            practise.setSelect(0);
        }
        switch (practise.getAnswer()){
            case 1:
                ivA.setText("");
                ivB.setText("");
                ivA.setBackgroundResource(R.mipmap.right);
                ivB.setBackgroundResource(R.mipmap.wrong);
                break;
            case 2:
                ivB.setText("");
                ivB.setBackgroundResource(R.mipmap.right);
                break;
            case 3:
                ivB.setText("");
                ivC.setText("");
                ivC.setBackgroundResource(R.mipmap.right);
                ivB.setBackgroundResource(R.mipmap.wrong);
                break;
            case 4:
                ivD.setText("");
                ivB.setText("");
                ivD.setBackgroundResource(R.mipmap.right);
                ivB.setBackgroundResource(R.mipmap.wrong);
                break;
        }
    }

    @Override
    public void OnselectC(int position, TextView ivA, TextView ivB, TextView ivC, TextView ivD) {
        Practise practise = details.get(position);
        if (practise.getAnswer()!= 3){
            practise.setSelect(3);
        } else {
            practise.setSelect(0);
        }
        switch (practise.getAnswer()){
            case 1:
                ivA.setText("");
                ivC.setText("");
                ivA.setBackgroundResource(R.mipmap.right);
                ivC.setBackgroundResource(R.mipmap.wrong);
                break;
            case 2:
                ivB.setText("");
                ivC.setText("");
                ivB.setBackgroundResource(R.mipmap.right);
                ivC.setBackgroundResource(R.mipmap.wrong);
                break;
            case 3:
                ivC.setText("");
                ivC.setBackgroundResource(R.mipmap.right);
                break;
            case 4:
                ivD.setText("");
                ivC.setText("");
                ivD.setBackgroundResource(R.mipmap.right);
                ivC.setBackgroundResource(R.mipmap.wrong);
                break;
        }
    }

    @Override
    public void OnselectD(int position, TextView ivA, TextView ivB, TextView ivC, TextView ivD) {
        Practise practise = details.get(position);
        if (practise.getAnswer()!= 4){
            practise.setSelect(4);
        } else {
            practise.setSelect(0);
        }
        switch (practise.getAnswer()){
            case 1:
                ivA.setText("");
                ivD.setText("");
                ivA.setBackgroundResource(R.mipmap.right);
                ivD.setBackgroundResource(R.mipmap.wrong);
                break;
            case 2:
                ivB.setText("");
                ivD.setText("");
                ivB.setBackgroundResource(R.mipmap.right);
                ivD.setBackgroundResource(R.mipmap.wrong);
                break;
            case 3:
                ivC.setText("");
                ivD.setText("");
                ivC.setBackgroundResource(R.mipmap.right);
                ivD.setBackgroundResource(R.mipmap.wrong);
                break;
            case 4:
                ivD.setText("");
                ivD.setBackgroundResource(R.mipmap.right);
                break;
        }
    }
}
