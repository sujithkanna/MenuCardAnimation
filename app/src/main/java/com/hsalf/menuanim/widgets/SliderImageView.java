package com.hsalf.menuanim.widgets;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

public class SliderImageView extends CardView implements ValueAnimator.AnimatorUpdateListener {

    private static final String TAG = "SliderImageView";

    private Bitmap mBitmapImage;
    private float mAnimateFactor = 0f;
    private Rect mSrcBounds = new Rect();
    private Rect mDrawBounds = new Rect();
    private Paint mBitmapPaint = new Paint();
    private IntEvaluator mIntEvaluator = new IntEvaluator();
    private ValueAnimator mValueAnimator = new ValueAnimator();

    public SliderImageView(Context context) {
        super(context);
        init();
    }

    public SliderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SliderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mValueAnimator.setDuration(2000);
        mValueAnimator.setFloatValues(0, 1);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // mValueAnimator.start();
    }

    public void setImageBitmap(Bitmap bitmap) {
        mBitmapImage = bitmap;
        invalidate();
    }

    public void setAnimationFactor(float factor) {
        if (factor < 0 || factor > 1) {
            return;
        }
        mAnimateFactor = 1 - factor;
        invalidate();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        Log.i(TAG, "X: " + x);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mSrcBounds.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mDrawBounds.set(0, 0, mSrcBounds.right, mSrcBounds.bottom);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapImage != null) {
            int width = mBitmapImage.getWidth() - getMeasuredWidth();
            mSrcBounds.left = mIntEvaluator.evaluate(mAnimateFactor, 0, width);
            mSrcBounds.right = getMeasuredWidth() + mSrcBounds.left;
            canvas.drawBitmap(mBitmapImage, mSrcBounds, mDrawBounds, mBitmapPaint);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        setAnimationFactor(animation.getAnimatedFraction());
    }
}
