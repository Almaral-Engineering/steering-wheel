package com.example.smartnavi.sensors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class NeedleControl extends View {
    private final String LOG_TAG = getClass().getSimpleName();

    private Paint mWindmillPaint;
    private float mDegrees;
    private Bitmap mRotor;

    private Paint mCirclePaint;

    public NeedleControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 1000;
        int desiredHeight = 1000;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    private void init() {
        /*mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.GREEN);
        mCirclePaint.setStyle(Paint.Style.STROKE);*/

        mWindmillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWindmillPaint.setStyle(Paint.Style.FILL);
        mWindmillPaint.setColor(Color.BLACK);
        /*mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPaint.setStyle(Paint.Style.FILL);
        mArrowPaint.setColor(Color.YELLOW);
        mArrowPaint.setStrokeWidth(20f);*/
        mRotor = BitmapFactory.decodeResource(getResources(), R.drawable.needle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw rotor
        int h = 0;
        int w = 0;
        canvas.drawBitmap(mRotor, rotate(mRotor, h, w), mWindmillPaint);
        //canvas.drawCircle(mRotor.getWidth()/2,mRotor.getHeight()/2, 120, mCirclePaint);
        invalidate();
    }

    public Matrix rotate(Bitmap bm, int x, int y){
        Matrix mtx = new Matrix();
        mtx.postRotate(mDegrees, bm.getWidth() / 2, bm.getHeight() / 2);
        mtx.postTranslate(x, y);  //The coordinates where we want to put our bitmap
        return mtx;
    }

    public void setDegrees(float degrees) {
        mDegrees = degrees;
    }
}
