package com.example.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.activity.CourseDetailActivity;
import com.example.myapplication.adapter.AdViewPagerAdapter;
import com.example.myapplication.adapter.CoursesRecylerAdapter;
import com.example.myapplication.entity.AdImage;
import com.example.myapplication.entity.Courses;
import com.example.myapplication.entity.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public static final int MSG_AD_ID = 1;
    private ViewPager viewPager;
    private TextView tvdesc;
    private LinearLayout llPoint;

    private List<AdImage> adImages;
    private List<ImageView> imageViews;
    private int lastPos;

    private RecyclerView recyclerView;
    private List<Courses> courses;


    public CoursesFragment() {
        // Required empty public constructor
    }

    private static CoursesFragment fragment;
    public static CoursesFragment newInstance(){
        if (fragment == null){
            fragment = new CoursesFragment();
        }
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        
        initAdData();
        initAdView(view);
        initIndicator(view);

        lastPos = 0;
        llPoint.getChildAt(0).setEnabled(true);
        tvdesc.setText(adImages.get(0).getDesc());
        viewPager.setAdapter(new AdViewPagerAdapter(imageViews));

        adHandler = new AdHandler(viewPager);
//        adHandler.sendEmptyMessageDelayed(MSG_AD_ID,5000);
        new AdslideThread().start();

        initCourses();
        initCoursesView(view);
        return view;
    }

    private void initCoursesView(View view) {
        recyclerView = view.findViewById(R.id.rv_courses);

        CoursesRecylerAdapter adapter = new CoursesRecylerAdapter(courses);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CoursesRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               Courses course = courses.get(position);
               //跳转到课程详情页面
                //Toast.makeText(getContext(),"点击了："+course.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("course",course);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initIndicator(View view) {
        llPoint = view.findViewById(R.id.ll_point);

        View pointView;
        for (int i = 0; i<adImages.size();i++){
            pointView = new View(getContext());
            pointView.setBackgroundResource(R.drawable.indicator_bg);
            pointView.setEnabled(false);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16,16);
            if (i != 0){
                params.leftMargin = 10;
            }
            llPoint.addView(pointView,params);
        }
    }

    private void initAdView(View view) {
        tvdesc = view.findViewById(R.id.tv_desc);

        viewPager = view.findViewById(R.id.vp_banner);
        viewPager.addOnPageChangeListener(this);

        imageViews = new ArrayList<>();
        for (int i = 0;i<adImages.size();i++){
            AdImage adImage = adImages.get(i);

            ImageView iv = new ImageView(getContext());
            if ("banner_1".equals(adImage.getImg())){
                iv.setBackgroundResource(R.drawable.bilibili);
            } else if ("banner_2".equals(adImage.getImg())){
                iv.setBackgroundResource(R.drawable.bilibili2);
            } else if ("banner_3".equals(adImage.getImg())){
                iv.setBackgroundResource(R.drawable.bilibili4);
            }
            imageViews.add(iv);
        }

        //监听触屏事件，按下后取消所有的消息，抬起则恢复
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        adHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        //删除CourseFragment
                        adHandler.removeCallbacksAndMessages(null);
                        adHandler.sendEmptyMessageDelayed(CoursesFragment.MSG_AD_ID,5000);
                        view.performClick();//解决onTouch和onClick事件的冲突
                        break;
                }
                return true;
            }
        });
    }

    private void initCourses(){
        courses = new ArrayList<>();
        try {
            //1.从assets目录中获取资源的输入流
            InputStream input = getResources().getAssets().open("chapter_intro.json");
            //2.将inputStream转为字符串
            String json = convert(input,"UTF-8");
            //3.利用fastjson将字符串转为对象集合
            courses = JSON.parseArray(json, Courses.class);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String convert(InputStream is,String encode){
        try{
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,encode));
            while ((line = reader.readLine())!= null){
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private void initAdData() {
        adImages = new ArrayList<>();
        for (int i = 0; i<3;i++){
            AdImage adImage = new AdImage();
            adImage.setId(i+1);
            switch (i){
                case 0:
                    adImage.setImg("banner_1");
                    adImage.setDesc("新一代Apple Watch发布");
                    break;
                case 1:
                    adImage.setImg("banner_2");
                    adImage.setDesc("寒武纪发布AI芯片");
                    break;
                case 2:
                    adImage.setImg("banner_3");
                    adImage.setDesc("Google发布AI语音助手");
                    break;
                default:
                    break;
            }
            adImages.add(adImage);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int currentPos = position%adImages.size();
        tvdesc.setText(adImages.get(currentPos).getDesc());

        llPoint.getChildAt(lastPos).setEnabled(false);
        llPoint.getChildAt(currentPos).setEnabled(true);
        lastPos = currentPos;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private AdHandler adHandler;
    private static class AdHandler extends Handler {
        private WeakReference<ViewPager> reference;

        public AdHandler(ViewPager viewPager) {
            reference = new WeakReference<>(viewPager);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ViewPager viewPager = reference.get();
            if (viewPager == null) {
                return;
            }
            if (msg.what == MSG_AD_ID) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                sendEmptyMessageDelayed(MSG_AD_ID, 5000);
            }
        }
    }
    /**
     * 使用多线程实现广告自动切换
     */
    private class AdslideThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (adHandler != null){
                    adHandler.sendEmptyMessage(MSG_AD_ID);
                }
            }
        }
    }
}
