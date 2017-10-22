package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.moyuchen.mvpuser.R;

/**
 * User: 张亚博
 * Date: 2017-09-20 23:32
 * Description：
 */
public class VPadapter extends PagerAdapter {
    private String[] images;
    private Context context;

    public VPadapter(String[] images, Context context) {
        this.images = images;
        this.context = context;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        if (view==null) {
            view=View.inflate(context,R.layout.photoview,null);
        }

        PhotoView photoview = view.findViewById(R.id.photoview);
        Glide.with(context).load(images[position]).into(photoview);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



}
