package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.activity.ExerciseDetailActivity;
import com.example.myapplication.adapter.ExerciseAdapter;
import com.example.myapplication.entity.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param";

    private String mParam;

    private OnFragmentInteractionListener mListener;

    private RecyclerView tvExercise;
    private Context mContext;

    private List<Exercise> exerciseList = new ArrayList<>();

    public ExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter 1.
     * @return A new instance of fragment ExerciseFragment.
     */
    public static ExerciseFragment newInstance(String param) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {
        this.mContext = getContext();
        initPractise();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        tvExercise = view.findViewById(R.id.recyler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tvExercise.setLayoutManager(layoutManager);
        ExerciseAdapter adapter = new ExerciseAdapter(getActivity(),exerciseList);
        tvExercise.setAdapter(adapter);
        adapter.setmOnItemClickLIstener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            //设置监听器
            public void onItemClick(View view, int position) {
                Exercise exercise = exerciseList.get(position);
                Toast.makeText(getContext(),"clicked"+position,Toast.LENGTH_SHORT).show();
                //跳转到相应的章节习题
                Intent intent = new Intent(getContext(), ExerciseDetailActivity.class);
                intent.putExtra("id",exercise.getId());//用于识别那个xml文件
                intent.putExtra("title",exercise.getTitle());//用于设置详情的标题栏
                container.getContext().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"long clicked"+position,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private List<Exercise> initPractise() {
        if (exerciseList.size()<15) {
            Exercise exercise = new Exercise(1, "第一章 你的第一行Android代码", "共计五题", R.mipmap.circle);
            exerciseList.add(exercise);
            Exercise exercise1 = new Exercise(2, "第二章 探究活动", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise1);
            Exercise exercise2 = new Exercise(3, "第三章 UI开发的点点滴滴", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise2);
            Exercise exercise3 = new Exercise(4, "第四章 探究碎片", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise3);
            Exercise exercise4 = new Exercise(5, "第五章 详解广播机制", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise4);
            Exercise exercise5 = new Exercise(6, "第六章 详解持久化技术", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise5);
            Exercise exercise6 = new Exercise(7, "第七章 探究内容提供器", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise6);
            Exercise exercise7 = new Exercise(8, "第八章 运用手机多媒体", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise7);
            Exercise exercise8 = new Exercise(9, "第九章 使用网络技术", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise8);
            Exercise exercise9 = new Exercise(10, "第十章 探究服务 ", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise9);
            Exercise exercise10 = new Exercise(11, "第十一章 基于位置的服务", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise10);
            Exercise exercise11 = new Exercise(12, "第十二章 Material Design实战", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise11);
            Exercise exercise12 = new Exercise(13, "第十三章 你还应该掌握的高级技巧", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise12);
            Exercise exercise13 = new Exercise(14, "第十四章 开发库偶天气", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise13);
            Exercise exercise14 = new Exercise(15, "第十五章 将应用发布到360应用商店", "共计5题", R.mipmap.circle);
            exerciseList.add(exercise14);
        } else {
            return exerciseList;
        }
        return exerciseList;
    }

    private void initData() {
        exerciseList = new ArrayList<>();
        try {
            //1.从assets目录中获取资源的输入流
            InputStream input = getResources().getAssets().open("exercise_title.json");
            //2.将inputStream转为字符串
            String json = convert(input);
            //3.利用fastjson将字符串转为对象集合
            exerciseList = JSON.parseArray(json,Exercise.class);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
