package com.example.myapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.adapter.PractiseAdapter;
import com.example.myapplication.entity.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PractiseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PractiseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PractiseFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private OnFragmentInteractionListener mListener;

    private ListView tvPractise;

    private List<Exercise> exercises = new ArrayList<>();

    private Context mContext;

    public PractiseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter 1.
     * @return A new instance of fragment PractiseFragment.
     */
    public static PractiseFragment newInstance(String param) {
        PractiseFragment fragment = new PractiseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    //    String[] data = {"软件1711","软件1721","软件1731","软件1741","软件1751","软件1761","软件1771","软件1781","软件1791"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initPractise();//初始化练习数据
//        initData();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_practise, container, false);
        //增加获取控件的语句与Activity中的OnCreate()功能类似
        //1获取列表控件
        tvPractise = view.findViewById(R.id.list_view);
        //2创建集合类控件需要的Adapter数据适配器
        PractiseAdapter adapter = new PractiseAdapter(getActivity(), exercises);
        //3设置ListView的Adapter
        tvPractise.setAdapter(adapter);
        //4ListView中的item事件监听
        tvPractise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise exercise = (Exercise) adapterView.getItemAtPosition(i);
            }
        });
        return view;
    }

    private void initData() {
        exercises = new ArrayList<>();
        try {
            //1.从assets目录中获取资源的输入流
            InputStream input = getResources().getAssets().open("exercise_title.json");
            //2.将inputStream转为字符串
            String json = convert(input);
            //3.利用fastjson将字符串转为对象集合
            exercises=JSON.parseArray(json,Exercise.class);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private List<Exercise> initPractise() {
        Exercise exercise = new Exercise(1, "第一章 你的第一行Android代码", "共计五题", R.mipmap.circle);
        exercises.add(exercise);
        Exercise exercise1 = new Exercise(2, "第二章 探究活动", "共计5题", R.mipmap.circle);
        exercises.add(exercise1);
        Exercise exercise2 = new Exercise(3, "第三章 UI开发的点点滴滴", "共计5题", R.mipmap.circle);
        exercises.add(exercise2);
        Exercise exercise3 = new Exercise(4, "第四章 探究碎片", "共计5题", R.mipmap.circle);
        exercises.add(exercise3);
        Exercise exercise4 = new Exercise(5, "第五章 详解广播机制", "共计5题", R.mipmap.circle);
        exercises.add(exercise4);
        Exercise exercise5 = new Exercise(6, "第六章 详解持久化技术", "共计5题", R.mipmap.circle);
        exercises.add(exercise5);
        Exercise exercise6 = new Exercise(7, "第七章 探究内容提供器", "共计5题", R.mipmap.circle);
        exercises.add(exercise6);
        Exercise exercise7 = new Exercise(8, "第八章 运用手机多媒体", "共计5题", R.mipmap.circle);
        exercises.add(exercise7);
        Exercise exercise8 = new Exercise(9, "第九章 使用网络技术", "共计5题", R.mipmap.circle);
        exercises.add(exercise8);
        Exercise exercise9 = new Exercise(10, "第十章 探究服务 ", "共计5题", R.mipmap.circle);
        exercises.add(exercise9);
        Exercise exercise10 = new Exercise(11, "第十一章 基于位置的服务", "共计5题", R.mipmap.circle);
        exercises.add(exercise10);
        Exercise exercise11 = new Exercise(12, "第十二章 Material Design实战", "共计5题", R.mipmap.circle);
        exercises.add(exercise11);
        Exercise exercise12 = new Exercise(13, "第十三章 你还应该掌握的高级技巧", "共计5题", R.mipmap.circle);
        exercises.add(exercise12);
        Exercise exercise13 = new Exercise(14, "第十四章 开发库偶天气", "共计5题", R.mipmap.circle);
        exercises.add(exercise13);
        Exercise exercise14 = new Exercise(15, "第十五章 将应用发布到360应用商店", "共计5题", R.mipmap.circle);
        exercises.add(exercise14);
        return exercises;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public static String convert(InputStream is){
        try{
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
