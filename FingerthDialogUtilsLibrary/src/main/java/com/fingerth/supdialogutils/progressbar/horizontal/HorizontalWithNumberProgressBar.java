package com.fingerth.supdialogutils.progressbar.horizontal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.fingerth.supdialogutils.R;
import com.fingerth.supdialogutils.utils.SupDialogStaticUtils;

/**
 * ======================================================
 * Created by Administrator able_fingerth on 2017/7/13.
 * <p/>
 * 版权所有，违者必究！
 * <详情描述/>
 * 參考路徑：
 * http://blog.csdn.net/lmj623565791/article/details/43371299
 */
public class HorizontalWithNumberProgressBar extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0XFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACHED_COLOR = 0xFFd3d6da;
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_SIZE_TEXT_OFFSET = 10;

    /**
     * painter of all drawing things
     */
    protected Paint mPaint = new Paint();
    /**
     * color of progress number
     */
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    /**
     * size of text (sp)
     */
    protected int mTextSize = SupDialogStaticUtils.sp2px(getContext(), DEFAULT_TEXT_SIZE);

    /**
     * offset of draw progress
     */
    protected int mTextOffset = SupDialogStaticUtils.dp2px(getContext(), DEFAULT_SIZE_TEXT_OFFSET);

    /**
     * height of reached progress bar
     */
    protected int mReachedProgressBarHeight = SupDialogStaticUtils.dp2px(getContext(), DEFAULT_HEIGHT_REACHED_PROGRESS_BAR);

    /**
     * color of reached bar
     */
    protected int mReachedBarColor = DEFAULT_TEXT_COLOR;
    /**
     * color of unreached bar
     */
    protected int mUnReachedBarColor = DEFAULT_COLOR_UNREACHED_COLOR;
    /**
     * height of unreached progress bar
     */
    protected int mUnReachedProgressBarHeight = SupDialogStaticUtils.dp2px(getContext(), DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
    /**
     * view width except padding
     */
    protected int mRealWidth;

    protected boolean mIfDrawText = true;

    protected static final int VISIBLE = 0;

    public HorizontalWithNumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalWithNumberProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setHorizontalScrollBarEnabled(true);

        obtainStyledAttributes(attrs);

        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

    }

    /**
     * get the styled attributes
     *
     * @param attrs
     */
    private void obtainStyledAttributes(AttributeSet attrs) {
        // init values from custom attributes
        final TypedArray attributes = getContext().obtainStyledAttributes(
                attrs, R.styleable.HorizontalWithNumberProgressBar);

        mTextColor = attributes
                .getColor(R.styleable.HorizontalWithNumberProgressBar_progressbar_text_color,
                        DEFAULT_TEXT_COLOR);
        mTextSize = (int) attributes.getDimension(
                R.styleable.HorizontalWithNumberProgressBar_progressbar_text_size,
                mTextSize);

        mReachedBarColor = attributes
                .getColor(
                        R.styleable.HorizontalWithNumberProgressBar_progressbar_reached_color,
                        mTextColor);
        mUnReachedBarColor = attributes
                .getColor(
                        R.styleable.HorizontalWithNumberProgressBar_progressbar_unreached_color,
                        DEFAULT_COLOR_UNREACHED_COLOR);
        mReachedProgressBarHeight = (int) attributes
                .getDimension(
                        R.styleable.HorizontalWithNumberProgressBar_progressbar_reached_height,
                        mReachedProgressBarHeight);
        mUnReachedProgressBarHeight = (int) attributes
                .getDimension(
                        R.styleable.HorizontalWithNumberProgressBar_progressbar_unreached_height,
                        mUnReachedProgressBarHeight);
        mTextOffset = (int) attributes
                .getDimension(
                        R.styleable.HorizontalWithNumberProgressBar_progressbar_text_offset,
                        mTextOffset);

        int textVisible = attributes
                .getInt(R.styleable.HorizontalWithNumberProgressBar_progressbar_text_visibility,
                        VISIBLE);
        if (textVisible != VISIBLE) {
            mIfDrawText = false;
        }
        attributes.recycle();
    }

    /**
     * 刚才不是出onDraw里面写写就行了么，为什么要改onMeasure呢，主要是因为我们所有的属性比如进度条宽度让用户自定义了，所以我们的测量也得稍微变下。
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY) {

            float textHeight = (mPaint.descent() + mPaint.ascent());
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() + Math
                    .max(Math.max(mReachedProgressBarHeight,
                            mUnReachedProgressBarHeight), Math.abs(textHeight)));

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 宽度我们不变，所以的自定义属性不涉及宽度，高度呢，只考虑不是EXACTLY的情况（用户明确指定了，我们就不管了），根据padding和进度条宽度算出自己想要的，如果非EXACTLY下，我们进行exceptHeight封装，传入给控件进行测量高度。
     * 测量完，就到我们的onDraw了~~~
     * <p>
     * 其实核心方法就是onDraw了，但是呢，onDraw也很简单，绘制线、绘制文本、绘制线，结束。
     */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        //画笔平移到指定paddingLeft， getHeight() / 2位置，注意以后坐标都为以此为0，0
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        boolean noNeedBg = false;
        //当前进度和总值的比例
        float radio = getProgress() * 1.0f / getMax();
        //已到达的宽度
        float progressPosX = (int) (mRealWidth * radio);
        //绘制的文本
        String text = getProgress() + "%";

        //拿到字体的宽度和高度
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        //如果到达最后，则未到达的进度条不需要绘制
        if (progressPosX + textWidth > mRealWidth) {
            progressPosX = mRealWidth - textWidth;
            noNeedBg = true;
        }

        // 绘制已到达的进度
        float endX = progressPosX - mTextOffset / 2;
        if (endX > 0) {
            mPaint.setColor(mReachedBarColor);
            mPaint.setStrokeWidth(mReachedProgressBarHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        // 绘制文本
        if (mIfDrawText) {
            mPaint.setColor(mTextColor);
            canvas.drawText(text, progressPosX, -textHeight, mPaint);
        }

        // 绘制未到达的进度条
        if (!noNeedBg) {
            float start = progressPosX + mTextOffset / 2 + textWidth;
            mPaint.setColor(mUnReachedBarColor);
            mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingRight() - getPaddingLeft();
    }
}
