package com.qt.raderdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 *@date 2019/5/14
 *@desc 雷达扩散view
 *
 */

public class RadarView extends View {
    private int mWidth;
    private int mHeigth;
    private int mRadius;
    private int mCenterX;
    private int mCenterY;
    private int mColor;
    private int mAlpha;
    private boolean mIsAlpha;//是否需要渐变
    private int mSpace;//两个环之间的间距
    private int mSpeed;//速度
    private Paint mPaint;
    private int mNum=1;
    private int mCurrMaxRadius;//当前显示的最大半径
    private boolean mIsStart;
    private int mDelayMillis=60;
    private Bitmap mCenterBitmap;//中心图片

    private Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            if(mIsStart){
                invalidate();
                postDelayed(mRunnable,mDelayMillis);
            }
        }
    };



    public RadarView(Context context) {
        super(context);
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.radar_view, defStyleAttr, 0);
        mSpace=attributes.getInteger( R.styleable.radar_view_radar_view_space,150);
        mColor=attributes.getColor( R.styleable.radar_view_radar_view_color,0x80FF00FF);
        mSpeed=attributes.getInteger( R.styleable.radar_view_radar_view_speed,10);
        mIsAlpha=attributes.getBoolean(R.styleable.radar_view_radar_view_is_alpha,true);
        int imageId=attributes.getResourceId(R.styleable.radar_view_radar_view_center_image,0);
        if(imageId>0){
            mCenterBitmap= BitmapFactory.decodeResource(getResources(), imageId);
        }
        attributes.recycle();
        init();
    }

    private void init(){
        mPaint=new Paint();
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mAlpha=(mColor>>24)&0xFF;
    }

    public void setColor(int color){
        mColor=color;
        mPaint.setColor(mColor);
        mAlpha=(mColor>>24)&0xFF;
    }

    public void startSearch(){
        removeCallbacks(mRunnable);
        mIsStart=true;
        postDelayed(mRunnable,mDelayMillis);
    }

    public void stopSearch(){
        mIsStart=false;
        removeCallbacks(mRunnable);
    }




    @Override
    protected void onDraw(Canvas canvas) {
        if(mCurrMaxRadius>mRadius){
            mCurrMaxRadius-=mSpace;
        }
        mCurrMaxRadius+=mSpeed;
        int minRadius=0;
        if(mCenterBitmap!=null){
            minRadius=mCenterBitmap.getWidth()/2;
        }
        if(mIsAlpha) {
            for (int i = 0; i < mNum; i++) {
                int radius = mCurrMaxRadius - i * mSpace;
                if(radius>minRadius){
                    int colorGradient = mAlpha;
                    if (radius < mRadius) {
                        colorGradient = mAlpha * radius / mRadius;
                    }
                    colorGradient <<= 24;
                    mPaint.setColor(mColor - colorGradient);
                Log.d("RadarView","i="+i+" mAlpha="+Integer.toHexString(mAlpha).toUpperCase()+" radius="+radius+" colorGradient="+Integer.toHexString(colorGradient).toUpperCase()+" color="+Integer.toHexString(mColor - colorGradient).toUpperCase());
                    canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);
                }
            }
        }else{
            for (int i = 0; i < mNum; i++) {
                int radius = mCurrMaxRadius - i * mSpace;
                if(radius>minRadius){
                    canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);
                }
            }
        }
        if(minRadius>0){
            canvas.drawBitmap(mCenterBitmap,mCenterX-mCenterBitmap.getWidth()/2,mCenterY-mCenterBitmap.getHeight()/2,mPaint);
        }
    }






    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth=measureValue(widthMeasureSpec);
        mHeigth=measureValue(heightMeasureSpec);
        mRadius=Math.max(mWidth,mHeigth)/2;
        setMeasuredDimension(mWidth,mHeigth);
        mNum=mRadius/mSpace;
        mRadius=mNum*mSpace;
        mCenterX=mWidth/2;
        mCenterY=mHeigth/2;
    }

    private int measureValue(int value){
        int specMode=MeasureSpec.getMode(value);
        int specSize=MeasureSpec.getSize(value);
        int result=0;
        switch (specMode){
            case MeasureSpec.EXACTLY:
                result=specSize;
                break;
            case MeasureSpec.AT_MOST:
                result=specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                result=200;
                break;
        }
        return result;
    }
}
