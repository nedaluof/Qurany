package com.nedaluof.qurany.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.nedaluof.qurany.R;

/**
 * Created by nedaluof on 6/26/2020.
 */
public class CustomDownload extends View {

    private static final int STATE_PRE = 0;

    private static final int STATE_DOWNLOADING = 1;

    private static final int STATE_END = 2;

    private static final int STATE_RESET = 3;

    private static final int DEFAULT_LINE_COLOR = Color.WHITE;

    private static final int DEFAULT_BG_LINE_COLOR = 0xff3a3f45;

    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private static final int DEFAULT_LINE_WIDTH = 9;

    private static final int DEFAULT_BG_LINE_WIDTH = 9;

    private static final int DEFAULT_TEXT_SIZE = 14;

    private static final int DEFAULT_STATE = STATE_PRE;

    private static final int DEFAULT_RIPPLE_SPEED = 2;

    private static final int DEFAULT_DOWNLOAD_TIME = 2000;

    private static final DownloadUnit DEFAULT_DOWNLOAD_UNIT = DownloadUnit.B;

    private OnDownloadStateListener onDownloadStateListener;

    private int mCurrentState;

    private float mCurrentRippleX;

    private double mCurrentSize, mTotalSize;

    private int mTextSize;

    private int mDownloadTime;

    private DownloadUnit mUnit;

    private Paint mPaint, mBgPaint, mTextPaint;

    private Path mPath;

    private RectF mRectF ,mClipRectF;

    private float mFraction;

    private float mWidth, mHeight;

    private float mCenterX, mCenterY;

    private float mBaseLength, mCircleRadius, mBaseRippleLength;

    public static enum DownloadUnit {
        GB,
        MB,
        KB,
        B,
        NONE;

        DownloadUnit() {
        }
    }

    public CustomDownload(Context context) {
        super(context);
    }

    public CustomDownload(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomDownload);
        int lineColor = ta.getColor(R.styleable.CustomDownload_download_line_color, DEFAULT_LINE_COLOR);
        int bgLineColor = ta.getColor(R.styleable.CustomDownload_download_bg_line_color, DEFAULT_BG_LINE_COLOR);
        int textColor = ta.getColor(R.styleable.CustomDownload_download_text_color, DEFAULT_TEXT_COLOR);
        int lineWidth = ta.getInteger(R.styleable.CustomDownload_download_line_width, DEFAULT_LINE_WIDTH);
        int bgLineWidth = ta.getInteger(R.styleable.CustomDownload_download_bg_line_width, DEFAULT_BG_LINE_WIDTH);
        int textSize = ta.getInteger(R.styleable.CustomDownload_download_text_size, DEFAULT_TEXT_SIZE);
        ta.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(lineColor);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeWidth(bgLineWidth);
        mBgPaint.setColor(bgLineColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPath = new Path();

        mTextSize = textSize;
        mCurrentState = DEFAULT_STATE;
        mUnit = DEFAULT_DOWNLOAD_UNIT;
        mDownloadTime = DEFAULT_DOWNLOAD_TIME;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        mCircleRadius = mWidth * 5 / 12;
        mBaseLength = mCircleRadius / 3;
        mBaseRippleLength = 4.4f * mBaseLength / 12;
        mCurrentRippleX = mCenterX - mBaseRippleLength * 10;
        mRectF = new RectF(mCenterX - mCircleRadius, mCenterY - mCircleRadius, mCenterX + mCircleRadius, mCenterY + mCircleRadius);
        mClipRectF = new RectF(mCenterX - 6 * mBaseRippleLength, 0 , mCenterX + 6 * mBaseRippleLength , mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentState) {
            case STATE_PRE: //嗷~ 开始阶段：线弹起小球
                if (mFraction <= 0.4) {
                    canvas.drawCircle(mCenterX, mCenterY ,mCircleRadius, mBgPaint);
                    canvas.drawLine(mCenterX - mBaseLength, mCenterY, mCenterX, mCenterY + mBaseLength, mPaint);
                    canvas.drawLine(mCenterX, mCenterY + mBaseLength, mCenterX + mBaseLength ,mCenterY , mPaint);
                    canvas.drawLine(mCenterX , mCenterY + mBaseLength - 1.3f * mBaseLength / 0.4f * mFraction,
                            mCenterX, mCenterY - 1.6f * mBaseLength + 1.3f * mBaseLength / 0.4f * mFraction, mPaint);
                } else if(mFraction <= 0.6) {
                    canvas.drawCircle(mCenterX, mCenterY ,mCircleRadius, mBgPaint);
                    canvas.drawCircle(mCenterX, mCenterY -0.3f * mBaseLength , 2, mPaint);
                    canvas.drawLine(mCenterX - mBaseLength - mBaseLength * 1.2f / 0.2f * (mFraction - 0.4f), mCenterY, mCenterX, mCenterY + mBaseLength - mBaseLength / 0.2f * (mFraction - 0.4f), mPaint);
                    canvas.drawLine(mCenterX, mCenterY + mBaseLength - mBaseLength / 0.2f * (mFraction - 0.4f), mCenterX + mBaseLength + mBaseLength * 1.2f / 0.2f * (mFraction - 0.4f) ,mCenterY , mPaint);
                } else if (mFraction <= 1){
                    canvas.drawCircle(mCenterX, mCenterY ,mCircleRadius, mBgPaint);
                    canvas.drawCircle(mCenterX, mCenterY -0.3f * mBaseLength - (mCircleRadius - 0.3f * mBaseLength) / 0.4f * (mFraction - 0.6f), 2, mPaint);
                    canvas.drawLine(mCenterX - mBaseLength * 2.2f , mCenterY , mCenterX + mBaseLength * 2.2f, mCenterY, mPaint);
                } else {
                    canvas.drawCircle(mCenterX, mCenterY ,mCircleRadius, mBgPaint);
                    canvas.drawCircle(mCenterX, mCenterY - mCircleRadius - mBaseLength * 3 * (mFraction - 1), 3 ,mPaint);
                    canvas.drawLine(mCenterX - mBaseLength * 2.2f , mCenterY , mCenterX + mBaseLength * 2.2f, mCenterY, mPaint);
                }
                break;
            case STATE_DOWNLOADING: //嗷~ 下载阶段：波浪线与文字
                if (mFraction <= 0.2) {
                    mTextPaint.setTextSize(mTextSize / 0.2f * mFraction);
                }
                canvas.drawCircle(mCenterX, mCenterY ,mCircleRadius, mBgPaint);
                canvas.drawArc(mRectF, -90, 359.99f * mFraction, false ,mPaint);
                mPath.reset();
                mCurrentRippleX += DEFAULT_RIPPLE_SPEED;
                if (mCurrentRippleX > mCenterX - mBaseRippleLength * 6)
                    mCurrentRippleX = mCenterX - mBaseRippleLength * 10;
                mPath.moveTo(mCurrentRippleX , mCenterY);
                for (int i = 0; i< 4 ; i++) {
                    mPath.rQuadTo(mBaseRippleLength, -(1 - mFraction) * mBaseRippleLength, mBaseRippleLength * 2, 0);
                    mPath.rQuadTo(mBaseRippleLength, (1 - mFraction) * mBaseRippleLength, mBaseRippleLength * 2, 0);
                }
                canvas.save();
                canvas.clipRect(mClipRectF);
                canvas.drawPath(mPath, mPaint);
                canvas.restore();
                if (mUnit != DownloadUnit.NONE && mCurrentSize > 0) {
                    canvas.drawText(String.format("%.2f", mCurrentSize) + getUnitStr(mUnit), mCenterX , mCenterY + 1.4f * mBaseLength , mTextPaint);
                }
                break;
            case STATE_END: //嗷~ 结束阶段：线变勾
                canvas.drawCircle(mCenterX, mCenterY ,mCircleRadius, mPaint);
                if (mFraction <= 0.5) {
                    mTextPaint.setTextSize(mTextSize - mTextSize / 0.2f * mFraction);
                } else {
                    mTextPaint.setTextSize(0);
                }
                if (mUnit != DownloadUnit.NONE && mCurrentSize > 0) {
                    canvas.drawText(String.format("%.2f", mCurrentSize) + getUnitStr(mUnit), mCenterX , mCenterY + 1.4f * mBaseLength , mTextPaint);
                }
                canvas.drawLine(mCenterX - mBaseLength * 2.2f + mBaseLength * 1.2f * mFraction, mCenterY,
                        mCenterX - mBaseLength * 0.5f, mCenterY + mBaseLength * 0.5f * mFraction * 1.3f, mPaint);
                canvas.drawLine(mCenterX - mBaseLength * 0.5f, mCenterY + mBaseLength * 0.5f * mFraction * 1.3f,
                        mCenterX + mBaseLength * 2.2f - mBaseLength * mFraction, mCenterY - mBaseLength * mFraction * 1.3f, mPaint);
                break;
            case STATE_RESET:   //嗷~ 复位阶段：勾变箭头
                canvas.drawCircle(mCenterX, mCenterY ,mCircleRadius, mBgPaint);
                canvas.drawLine(mCenterX - mBaseLength, mCenterY,
                        mCenterX - mBaseLength * 0.5f + mBaseLength * 0.5f * mFraction, mCenterY + mBaseLength * 0.65f + mBaseLength * 0.35f * mFraction, mPaint);
                canvas.drawLine(mCenterX - mBaseLength * 0.5f + mBaseLength * 0.5f * mFraction, mCenterY + mBaseLength * 0.65f + mBaseLength * 0.35f * mFraction,
                        mCenterX + mBaseLength * 1.2f - mBaseLength * 0.2f * mFraction, mCenterY - 1.3f * mBaseLength + 1.3f * mBaseLength * mFraction, mPaint);
                canvas.drawLine(mCenterX - mBaseLength * 0.5f + mBaseLength * 0.5f * mFraction, mCenterY + mBaseLength * 0.65f + mBaseLength * 0.35f * mFraction,
                        mCenterX - mBaseLength * 0.5f + mBaseLength * 0.5f * mFraction,
                        mCenterY + mBaseLength * 0.65f - mBaseLength * 2.25f * mFraction, mPaint);
                break;
        }
    }

    public void start() {
        if (mCurrentState != STATE_PRE) {
            return;
        }
        mCurrentState = STATE_PRE;
        ValueAnimator preAnim = ValueAnimator.ofFloat(1.f, 100.f);
        preAnim.setDuration(1500);
        preAnim.setInterpolator(new OvershootInterpolator());
        preAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFraction = valueAnimator.getAnimatedFraction();
                invalidate();
            }
        });
        preAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentState = STATE_DOWNLOADING;
                downloadAnim();
            }
        });
        preAnim.start();
    }

    private void downloadAnim() {
        ValueAnimator downloadAnim = ValueAnimator.ofFloat(1.f, 100.f);
        downloadAnim.setDuration(mDownloadTime);
        downloadAnim.setInterpolator(new LinearInterpolator());
        downloadAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFraction = valueAnimator.getAnimatedFraction();
                if (mUnit != DownloadUnit.NONE && mTotalSize > 0)
                    mCurrentSize = mFraction * mTotalSize;
                invalidate();
            }
        });
        downloadAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentState = STATE_END;
                endAnim();
            }
        });
        downloadAnim.start();
    }

    private void endAnim() {
        ValueAnimator endAnim = ValueAnimator.ofFloat(1.f, 100.f);
        endAnim.setDuration(700);
        endAnim.setInterpolator(new OvershootInterpolator());
        endAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFraction = valueAnimator.getAnimatedFraction();
                invalidate();
            }
        });
        endAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFraction = 0;
                mCurrentState = STATE_RESET;
                if (onDownloadStateListener != null) {
                    onDownloadStateListener.onDownloadFinish();
                }
            }
        });
        endAnim.start();
    }

    public void reset() {
        if (mCurrentState != STATE_RESET) {
            return;
        }
        ValueAnimator resetAnim = ValueAnimator.ofFloat(1.f, 100.f);
        resetAnim.setDuration(500);
        resetAnim.setInterpolator(new OvershootInterpolator());
        resetAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFraction = valueAnimator.getAnimatedFraction();
                invalidate();
            }
        });
        resetAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFraction = 0;
                mCurrentState = STATE_PRE;
                if (onDownloadStateListener != null) {
                    onDownloadStateListener.onResetFinish();
                }
            }
        });
        resetAnim.start();
    }

    private String getUnitStr(DownloadUnit unit) {
        switch (unit) {
            case GB:
                return " gb";
            case MB:
                return " mb";
            case KB:
                return " kb";
            case B:
                return " b";
        }
        return " b";
    }

    public void setDownloadConfig(int downloadTime, double downloadFileSize, DownloadUnit unit) {
        mDownloadTime = downloadTime;
        mTotalSize = downloadFileSize;
        mUnit = unit;
    }

    public int getCurrentState() {
        return mCurrentState;
    }

    interface OnDownloadStateListener {

        void onDownloadFinish();

        void onResetFinish();
    }

    public void setOnDownloadStateListener(OnDownloadStateListener onDownloadStateListener) {
        this.onDownloadStateListener = onDownloadStateListener;
    }
}

