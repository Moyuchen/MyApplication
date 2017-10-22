package com.moyuchen.mvpuser.Owner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * User: 张亚博
 * Date: 2017-10-13 19:00
 * Description：
 */
public class TransluteScrollView extends ScrollView {
    private OnScrollChangedListener mOnScrollChangedListener;

    public TransluteScrollView(Context context) {
        super(context);
    }

    public TransluteScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransluteScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setmOnScrollChangedListener(OnScrollChangedListener mOnScrollChangedListener) {
        this.mOnScrollChangedListener = mOnScrollChangedListener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }
}
