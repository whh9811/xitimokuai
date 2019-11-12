package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.adapter.MyFragmentPagerAdapter;
import com.example.myapplication.fragment.CoursesFragment;
import com.example.myapplication.fragment.ExerciseFragment;
import com.example.myapplication.fragment.GridViewCoursesFragment;
import com.example.myapplication.fragment.MessageFragment;
import com.example.myapplication.fragment.MySettingFragment;
import com.example.myapplication.R;
import com.example.myapplication.fragment.PractiseFragment;

import java.security.acl.Group;

import static android.media.CamcorderProfile.get;

public class Main1Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private Toolbar toolbar;
    private RadioButton rbHome;
    private RadioButton rbFind;
    private RadioButton rbMy;
    private RadioButton rbWeb;
    private RadioGroup rgNav;
    private SparseArray<String> titles;
    private SparseArray<Fragment> fragments;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter mAdapter;

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        initView();
        initTitles();
//        initFragments();

    }

    /**
     * 根据按钮的id设置界面的标题
     * @param checkedId
     */
    private void setToolbar(int checkedId){
        if (checkedId == R.id.rb_my){
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle(titles.get(checkedId));
        }
    }

//    private void initFragments() {
//        //1.创建fragment列表
//        fragments = new SparseArray<>();
//        fragments.put(R.id.rb_my, MySettingFragment.newInstance());
//        fragments.put(R.id.rb_web, MessageFragment.newInstance());
//        fragments.put(R.id.rb_find, ExerciseFragment.newInstance("Activity向Fragment传值"));
//        fragments.put(R.id.rb_home, CoursesFragment.newInstance());
//        //加载默认的fragment
//        replaceFragment(fragments.get(R.id.rb_home));
//    }
//
//    private void replaceFragment(Fragment fragment) {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.replace(R.id.fragment_main,fragment);
//        ft.addToBackStack(null);
//        ft.commit();
//    }

    private void initView() {
        rbHome = findViewById(R.id.rb_home);
        rbFind = findViewById(R.id.rb_find);
        rbMy = findViewById(R.id.rb_my);
        rbWeb = findViewById(R.id.rb_web);
        rgNav = findViewById(R.id.radio_main);
        toolbar = findViewById(R.id.title_bar);
        rgNav.setOnCheckedChangeListener(this);
        viewPager = findViewById(R.id.vp_slide);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(this);
        toolbar.setVisibility(View.GONE);
        viewPager.setCurrentItem(PAGE_FOUR);


    }

    private void initTitles(){
        titles = new SparseArray<>();
        titles.put(R.id.rb_home,"课程");
        titles.put(R.id.rb_find,"习题");
        titles.put(R.id.rb_web,"资讯");
        titles.put(R.id.rb_my,"我的");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
       if (state == 2){
           int currentItemPosition = viewPager.getCurrentItem();
           switch (currentItemPosition){
               case PAGE_FOUR:
                   rbMy.setChecked(true);
                   setToolbar(R.id.rb_my);
                   break;
               case PAGE_TWO:
                   rbFind.setChecked(true);
                   setToolbar(R.id.rb_find);
                   break;
               case PAGE_THREE:
                    rbWeb.setChecked(true);
                    setToolbar(R.id.rb_web);
                    break;
               case PAGE_ONE:
                   rbHome.setChecked(true);
                   setToolbar(R.id.rb_home);
                   break;
           }
       }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_home:
                viewPager.setCurrentItem(PAGE_ONE);
                setToolbar(i);
                break;
            case R.id.rb_find:
                viewPager.setCurrentItem(PAGE_TWO);
                setToolbar(i);
                break;
            case R.id.rb_web:
                viewPager.setCurrentItem(PAGE_THREE);
                setToolbar(i);
                break;
            case R.id.rb_my:
                viewPager.setCurrentItem(PAGE_FOUR);
                setToolbar(i);
                break;

        }
    }
}
