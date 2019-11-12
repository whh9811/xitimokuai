package com.example.myapplication.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.activity.Main1Activity;
import com.example.myapplication.fragment.CoursesFragment;
import com.example.myapplication.fragment.ExerciseFragment;
import com.example.myapplication.fragment.MessageFragment;
import com.example.myapplication.fragment.MySettingFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 4;
    private ExerciseFragment exerciseFragment;
    private CoursesFragment coursesFragment;
    private MySettingFragment mySettingFragment;
    private MessageFragment messageFragment;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        exerciseFragment = new ExerciseFragment();
        coursesFragment = new CoursesFragment();
        mySettingFragment = new MySettingFragment();
        messageFragment = new MessageFragment();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case Main1Activity.PAGE_ONE:
                fragment = coursesFragment;
                break;
            case Main1Activity.PAGE_TWO:
                fragment = exerciseFragment;
                break;
            case Main1Activity.PAGE_THREE:
                fragment = messageFragment;
                break;
            case Main1Activity.PAGE_FOUR:
                fragment = mySettingFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
