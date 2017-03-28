package com.hsalf.menuanim.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.hsalf.menuanim.R;

public class CardColorLayout extends RelativeLayout {

    private static final String TAG = "CardColorLayout";

    private Paint mFillPaint = new Paint();

    public CardColorLayout(Context context) {
        super(context);
    }

    public CardColorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardColorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setWillNotDraw(false);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(ContextCompat.getColor(getContext(), R.color.cardColor));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, getMeasuredHeight() * 0.25f, getMeasuredWidth(), getMeasuredHeight(), mFillPaint);
    }
}
