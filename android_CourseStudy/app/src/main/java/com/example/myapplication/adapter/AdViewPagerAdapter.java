package com.example.myapplication.adapter;

import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.example.myapplication.fragment.CoursesFragment.MSG_AD_ID;

public class AdViewPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;

    public AdViewPagerAdapter(List<ImageView> imageViews) {
        super();
        this.imageViews = imageViews;
    }

    public AdViewPagerAdapter() {
        this(null);
        imageViews = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //防止刷新时显示缓存数据
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    /**
     * 返回数据集的真实容量大小
     */
    public int getSize() {
        return imageViews.size();
    }

    //指定复用的逻辑判断，固定写法：view==object
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //container 容器相当于用来存放imageView

        //从集合中获取图片
        ImageView imageView = imageViews.get(position % imageViews.size());

        //检查imageView是否已经添加到容器中
        ViewParent parent = imageView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(imageView);
        }
        //把图片添加到container中
        container.addView(imageView);
        //把图片返回给框架，用来缓存
        return imageView;
    }
}

