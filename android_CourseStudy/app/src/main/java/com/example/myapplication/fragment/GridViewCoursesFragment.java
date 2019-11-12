package com.example.myapplication.fragment;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.activity.CourseDetailActivity;
import com.example.myapplication.adapter.AdViewPagerAdapter;
import com.example.myapplication.adapter.CoursesGridViewAdapter;
import com.example.myapplication.adapter.CoursesRecylerAdapter;
import com.example.myapplication.entity.AdImage;
import com.example.myapplication.entity.Courses;
import com.example.myapplication.utils.HttpsUtil;
import com.example.myapplication.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class GridViewCoursesFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public static final int MSG_AD_ID = 1;

    private ViewPager viewPager;
    private TextView tvdesc;
    private LinearLayout llPoint;

    private List<AdImage> adImages;
    private List<ImageView> imageViews;
    private int lastPos;

    private GridView gvCourse;
    private List<Courses> courses;


    public GridViewCoursesFragment() {
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

//        initCourses();
        gvCourse = view.findViewById(R.id.gv_course);
//        update(courses);

//        loadCoursesByNet();
        loadCoursesByOkHttp();
        return view;
    }

    private void update(List<Courses> courses){
        CoursesGridViewAdapter adapter = new CoursesGridViewAdapter(getContext(),courses);
        gvCourse.setAdapter(adapter);
        gvCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Courses courses = (Courses) adapterView.getItemAtPosition(i);
                //跳转到课程详情界面
                //Toast.makeText(getContext(),"点击了:"+courses.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("course",courses);
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
                iv.setBackgroundResource(R.drawable.bgimage);
            } else if ("banner_2".equals(adImage.getImg())){
                iv.setBackgroundResource(R.drawable.bgimage);
            } else if ("banner_3".equals(adImage.getImg())){
                iv.setBackgroundResource(R.drawable.bgimage);
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

    private void loadCoursesByOkHttp(){
        // 1.创建一个Request对象，装载url,header等request头
        Request request = new Request.Builder()
                .url("https://www.fastmock.site/mock/ac528fb68a2bc0e8d96b5f1fad863e6b/course/chapter")
                .addHeader("Accept","application/json")
                .method("GET",null)
                .build();
        HttpsUtil.handleSSLHandshakeByOkHttp().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        String json = response.body().string();
                        final List<Courses> courses = JSON.parseArray(json,Courses.class);
                        //使用Handler的Message更新UI
//                        if (courses != null){
//                            Message msg = new Message();
//                            msg.what = 2;
//                            msg.obj = courses;
//                            courseHandler.sendMessage(msg);
//                        }
                        // 2.2 通过runOnUiThread回到主线程下更新UI
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                update(courses);
                            }
                        });
                    }
                }
            }
        });
    }
    private Handler courseHandler = new CourseHandler(this);
    private void loadCoursesByNet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = NetworkUtils.get("https://www.fastmock.site/mock/ac528fb68a2bc0e8d96b5f1fad863e6b/course/chapter");
                List<Courses> courses = JSON.parseArray(json,Courses.class);
                update(courses);
                if (courses != null){
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj= courses;

                }
            }
        }).start();
    }
    private static class CourseHandler extends Handler{
        private WeakReference<GridViewCoursesFragment> ref;

        public CourseHandler(GridViewCoursesFragment fragment){
            this.ref = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            GridViewCoursesFragment target = ref.get();
            if (msg.what == 2){
                target.update((List<Courses>) msg.obj);
            }
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
//                sendEmptyMessageDelayed(MSG_AD_ID, 5000);
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
                    Message msg = adHandler.obtainMessage();
                    msg.what = MSG_AD_ID;
                    adHandler.sendMessage(msg);
                }
            }
        }
    }
}
