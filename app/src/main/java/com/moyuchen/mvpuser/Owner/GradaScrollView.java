package com.moyuchen.mvpuser.Owner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by 张乔君 on 2017/10/12.
 */

public class GradaScrollView extends ScrollView {
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {

        void onScrollChanged(GradaScrollView scrollView, int x, int y,
                             int oldx, int oldy);

    }

    private ScrollViewListener scrollViewListener = null;
    public GradaScrollView(Context context) {
        super(context);
    }

    public GradaScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradaScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(scrollViewListener!=null){
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
}
