package com.fingerth.supdialogutils.progressbar.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.fingerth.supdialogutils.R;
import com.fingerth.supdialogutils.progressbar.horizontal.HorizontalWithNumberProgressBar;
import com.fingerth.supdialogutils.utils.SupDialogStaticUtils;

/**
 * ======================================================
 * Created by Administrator able_fingerth on 2017/7/13.
 * <p/>
 * 版权所有，违者必究！
 * <详情描述/>
 * 參考路徑：
 * http://blog.csdn.net/lmj623565791/article/details/43371299
 * 我自己有改善。
 */
public class RoundWidthNumberProgressBar extends HorizontalWithNumberProgressBar {

//    android:layout_width="90dp"
//    android:layout_height="90dp"
//    fingerth:progressbar_reached_color="#0EFF96"
//    fingerth:progressbar_reached_height="2dp"
//    fingerth:progressbar_text_color="#0EFF96"
//    fingerth:progressbar_text_size="16sp"
//    fingerth:progressbar_unreached_color="#dddddd"
//    fingerth:progressbar_unreached_height="1.5dp"
//    fingerth:radius="43dp"

//      <!--注意：當我把長寬寫成90dp時， 為了讓progressbar居中並且全部顯示，這時 radius + progressbar_reached_height 要等于45dp（）-->

    /**
     * mRadius of view
     */
    private int mRadius = SupDialogStaticUtils.dp2px(getContext(), 30);

    public RoundWidthNumberProgressBar(Context context) {
        this(context, null);
    }

    public RoundWidthNumberProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        //mReachedProgressBarHeight = (int) (mUnReachedProgressBarHeight * 2.5f);
        //這裡我處理下 mReachedProgressBarHeight 要大於 mUnReachedProgressBarHeight
        if (mUnReachedProgressBarHeight > mReachedProgressBarHeight) {
            mUnReachedProgressBarHeight = mReachedProgressBarHeight;
        }

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.RoundWidthNumberBar);
        mRadius = (int) ta.getDimension(
                R.styleable.RoundWidthNumberBar_radius, mRadius);
        ta.recycle();

        mTextSize = SupDialogStaticUtils.sp2px(getContext(), 14);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int paintWidth = Math.max(mReachedProgressBarHeight, mUnReachedProgressBarHeight);

        if (heightMode != MeasureSpec.EXACTLY) {
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() + mRadius * 2 + paintWidth);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }
        if (widthMode != MeasureSpec.EXACTLY) {
            int exceptWidth = (int) (getPaddingLeft() + getPaddingRight() + mRadius * 2 + paintWidth);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth, MeasureSpec.EXACTLY);
        }

        super.onMeasure(heightMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        String text = getProgress() + "%";
        // mPaint.getTextBounds(text, 0, text.length(), mTextBound);
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        mPaint.setStyle(Paint.Style.STROKE);
        // draw unreaded bar
        mPaint.setColor(mUnReachedBarColor);
        mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
        canvas.drawCircle(mRadius + mReachedProgressBarHeight / 2, mRadius + mReachedProgressBarHeight / 2, mRadius, mPaint);
        // draw reached bar
        mPaint.setColor(mReachedBarColor);
        mPaint.setStrokeWidth(mReachedProgressBarHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(mReachedProgressBarHeight / 2, mReachedProgressBarHeight / 2, mRadius * 2 + mReachedProgressBarHeight / 2, mRadius * 2 + mReachedProgressBarHeight / 2),
                270, sweepAngle, false, mPaint);
        // draw text
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight, mPaint);

        canvas.restore();

    }
}
