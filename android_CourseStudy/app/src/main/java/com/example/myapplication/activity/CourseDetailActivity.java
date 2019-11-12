package com.example.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CourseDetailAdapter;
import com.example.myapplication.adapter.RecordRecyclerAdapter;
import com.example.myapplication.entity.CourseDetail;
import com.example.myapplication.entity.Courses;
import com.example.myapplication.entity.Records;
import com.example.myapplication.service.impl.RecordService;
import com.example.myapplication.service.impl.RecordServiceImpl;
import com.example.myapplication.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {
    private List<CourseDetail> courseDetails = new ArrayList<>();

    private Toolbar toolbar;
    private ImageView preIVImg;
    private VideoView videoView;
    private RecyclerView rvVideo;
    private TextView jjContent;

    private Courses courses;
    private MediaController controller;
    private MediaPlayer mediaPlayer;
    private String title;
    private CourseDetailAdapter courseDetailAdapter;

    private Records records;
    private RecordService recordService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        //1.接收从上一个界面传递的Bundle对象
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            courses =(Courses)bundle.getSerializable("course");
        }

        title = courses.getTitle();

        initData();
        initView();
        initToolbar();
        loadFirstFrame();
    }

    //加载视频的首帧图像
    private void loadFirstFrame(){
        Bitmap bitmap = null;

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.video101);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this,uri);
        bitmap = retriever.getFrameAtTime();
        preIVImg.setImageBitmap(bitmap);
    }

    private void initData() {
        //1.接收从上一个界面传递的Bundle对象
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            courses =(Courses)bundle.getSerializable("course");
        }
        //2.从json文件中获取视频的描述数据
        courseDetails = new ArrayList<>();
        try {
            //2.1获取json文件中的所有数据集合
            InputStream is = getResources().getAssets().open("courses.json");
            String json = IOUtils.convert(is,"UTF-8");
            courseDetails = JSON.parseArray(json,CourseDetail.class);
            //2.2筛选出course的chapterId对应的视频集合
            Iterator<CourseDetail> it = courseDetails.iterator();
            while (it.hasNext()){
                CourseDetail courseDetail = it.next();
                if (courseDetail.getChapterId()!=courses.getId()){
                    it.remove();
                }
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        preIVImg = findViewById(R.id.pre_iv_img);
        videoView = findViewById(R.id.vv_video);
        controller = new MediaController(this);
        videoView.setMediaController(controller);

        jjContent = findViewById(R.id.jj_content);
        jjContent.setText(courses.getIntro());
        rvVideo = findViewById(R.id.rv_video);

        courseDetailAdapter = new CourseDetailAdapter(courseDetails);
        RecyclerView recyclerView = findViewById(R.id.rv_video);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(courseDetailAdapter);
        courseDetailAdapter.setOnItemClickListener(new CourseDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //设置选中项，并通过notifyDataSetChanged()跟新UI
                courseDetailAdapter.setSelected(position);
                courseDetailAdapter.notifyDataSetChanged();

                recordService = new RecordServiceImpl(CourseDetailActivity.this);
                records = new Records();
                records.setUsername(readLoginInfo());
                records.setTitle(title);
                recordService.save(records);

                //获取Video对象的数据，并初始化VideoView
                CourseDetail courseDetail = courseDetails.get(position);
                if (videoView.isPlaying()){
                    videoView.stopPlayback();
                }
                if (TextUtils.isEmpty(courseDetail.getVideoPath())){
                    Toast.makeText(CourseDetailActivity.this,"本地没有此视频，暂时无法播放",Toast.LENGTH_SHORT).show();
                    return;
                }
                //播放视频
                videoView.setVisibility(View.VISIBLE);
                preIVImg.setVisibility(View.GONE);
                String uri = "android.resource://" + getPackageName() + "/" +R.raw.video101;
                videoView.setVideoPath(uri);
                videoView.start();
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseDetailActivity.this.finish();
            }
        });
    }
    private List<CourseDetail> initCoursesDetail() {
        for (int i = 0;i<1;i++) {
            CourseDetail courseDetail = new CourseDetail("Android系统简介");
            courseDetails.add(courseDetail);
            CourseDetail courseDetail1 = new CourseDetail("Android体系结构");
            courseDetails.add(courseDetail1);
        }
        return courseDetails;
    }

    private String readLoginInfo() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        return sp.getString("loginUser", "");
    }
}
