package com.example.myapplication.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    private String[] imgUrl = {
            "https://source.unsplash.com/random/600x450",
            "https://source.unsplash.com/random/600x451",
            "https://source.unsplash.com/random/600x452"
    };

    public CustomPagerAdapter() {
        super();
    }

    //指定复用的判断逻辑，固定写法：view == object
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //当创建新的条目，又反回来，判断view是否可以被复用(即是否存在)
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //container 容器相当于用来存放imageView

        //从集合中获得图片
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(container.getContext())
                .load(imgUrl[position % imgUrl.length])
                .into(imageView);

        // 检查imageView是否已经添加到容器中
        ViewParent parent = imageView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(imageView);
        }
        //把图片添加到container中
        container.addView(imageView);

        //把图片返回给框架，用来缓存
        return imageView;
    }

    //销毁条目
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //  warning：不要在这里removeView
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    // 防止刷新时显示缓存数据
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}
