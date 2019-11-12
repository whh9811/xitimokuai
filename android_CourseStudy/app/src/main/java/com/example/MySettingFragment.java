package com.example;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MySettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MySettingFragment extends Fragment {
    private boolean isLogin;
    private Context mContext;
    private TextView tvUsername;
    private LinearLayout headLayout,historyLayout,settingLayout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;


    public MySettingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MySettingFragment newInstance() {
        MySettingFragment fragment = new MySettingFragment();
        //给fragment传参的方法
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //接收fragment参数
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    //初始化Fragment的xml界面上所有控件和数据，相当于Acticity的onCreate()作用
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //1获取fragment的
        this.mContext = getContext();
        this.isLogin = checkLoginStatus();
        View view = inflater.inflate(R.layout.fragment_my_setting, container, false);
        headLayout = view.findViewById(R.id.ll_head);
        tvUsername = view.findViewById(R.id.click_login);
        setUsername(isLogin);
        historyLayout = view.findViewById(R.id.list_history);
        settingLayout = view.findViewById(R.id.list_setting);
        //3设置事件监听
        headLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin){

                }else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent,1);
                }
            }
        });
        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin){

                }else {
                    Toast.makeText(mContext,"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin){
                    Intent intent = new Intent(mContext, SettingActivity.class);
                    startActivityForResult(intent,2);

                }else {
                    Toast.makeText(mContext,"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }

    private void setUsername(boolean isLogin) {
        if (isLogin){
            tvUsername.setText(readLoginInfo());
        } else {
            tvUsername.setText("点击登录");
        }
    }

    //获取登录状态
    private boolean checkLoginStatus(){
        SharedPreferences sp = mContext.getSharedPreferences("data",Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin",false);
    }

    private String readLoginInfo() {
        SharedPreferences sp = mContext.getSharedPreferences("data",Context.MODE_PRIVATE);
        return sp.getString("loginUser","");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data!=null) {
            isLogin = data.getBooleanExtra("isLogin", false);
            setUsername(isLogin);
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && data!=null){
            isLogin =data.getBooleanExtra("isLogin",false);
            setUsername(isLogin);
        }
    }
}
