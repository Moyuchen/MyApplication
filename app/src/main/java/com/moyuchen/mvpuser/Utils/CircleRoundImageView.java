package com.moyuchen.mvpuser.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.moyuchen.mvpuser.R;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/09/27
 * Description:
 */
@SuppressLint("AppCompatCustomView")
public class CircleRoundImageView extends ImageView {

    private Paint mPaint;

    private boolean isCircle;

    private int mWidth;

    private int mHeight;

    private int mRadius;//圆半径

    private RectF mRect;//矩形

    private BitmapShader mBitmapShader;//图形渲染

    private Matrix mMatrix;


    public static final int DEFAUT_ROUND_RADIUS = 10;//默认圆角大小

    public CircleRoundImageView(Context context) {
        this(context, null);
    }

    public CircleRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context,attrs);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleRoundImageView);
        isCircle = typedArray.getBoolean(R.styleable.CircleRoundImageView_circle, true);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.CircleRoundImageView_radius, 10);

        typedArray.recycle();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//去锯齿
        mMatrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 如果是绘制圆形，则强制宽高大小一致
        if (isCircle) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (null == getDrawable()) {
            return;
        }
        setBitmapShader();
        if (isCircle) {
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        } else  {
            mPaint.setColor(Color.RED);
            canvas.drawRoundRect(mRect, mRadius, mRadius, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect = new RectF(0, 0, getWidth(), getHeight());
    }

    /**
     * 设置BitmapShader
     */
    private void setBitmapShader() {
        Drawable drawable = getDrawable();
        if (null == drawable) {
            return;
        }
        Bitmap bitmap = drawableToBitmap(drawable);
        // 将bitmap作为着色器来创建一个BitmapShader
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (isCircle) {
            // 拿到bitmap宽或高的小值
            int bSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
            scale = mWidth * 1.0f / bSize;

        } else  {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);

    }

    /**
     * drawable转bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
