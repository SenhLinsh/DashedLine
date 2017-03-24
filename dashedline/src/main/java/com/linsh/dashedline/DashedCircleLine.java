package com.linsh.dashedline;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by Senh Linsh on 17/3/20.
 */

public class DashedCircleLine extends BaseDashedLine {

    private RectF mRectF = new RectF();

    public DashedCircleLine(Context context) {
        super(context);
        init();
    }

    public DashedCircleLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void initAttr(TypedArray typedArray) {
        mDashEffect = new CircleDashEffect(mDashEffect.dashWidth, mDashEffect.dashGap, mDashEffect.offset);
    }

    private void init() {
    }

    @Override
    protected void onDrawDash(Canvas canvas, Paint paint, float strokeWidth) {

        int width = getWidth();
        int height = getHeight();
        int min = Math.min(width, height);

        if (strokeWidth < 0) {
            strokeWidth = 0;
        } else if (strokeWidth * 2f > min) {
            strokeWidth = min / 2f;
        }
        paint.setStrokeWidth(strokeWidth);

        float strokeOffset = strokeWidth / 2f;
        mRectF.top = (height - min) / 2f + strokeOffset;
        mRectF.left = (width - min) / 2f + strokeOffset;
        mRectF.bottom = (height + min) / 2f - strokeOffset;
        mRectF.right = (width + min) / 2f - strokeOffset;
        canvas.drawOval(mRectF, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(width, height);

        float strokeWidth = mStrokeWidth;
        if (strokeWidth < 0) {
            strokeWidth = 0;
        } else if (strokeWidth * 2f > min) {
            strokeWidth = min / 2f;
        }

        ((CircleDashEffect) mDashEffect).reCalculateDashEffect(width, height, strokeWidth);
    }


    protected class CircleDashEffect extends DashEffect {

        public CircleDashEffect(int dashWidth, int dashGap) {
            super(dashWidth, dashGap);
        }

        public CircleDashEffect(int dashWidth, int dashGap, int offset) {
            super(dashWidth, dashGap, offset);
        }

        private void reCalculateDashEffect(int width, int height, float strokeWidth) {
            float min = Math.min(width, height);

            float circleLength = (float) (Math.PI * (min - strokeWidth));
            int dashNum = Math.round(circleLength / (dashGap + dashWidth));
            float dashLength = circleLength / dashNum;
            float newDashWidth = dashLength * dashWidth / (dashGap + dashWidth);
            float newDashGap = dashLength * dashGap / (dashGap + dashWidth);

            dashEffect = new DashPathEffect(new float[]{newDashWidth, newDashGap}, offset);
        }
    }
}
