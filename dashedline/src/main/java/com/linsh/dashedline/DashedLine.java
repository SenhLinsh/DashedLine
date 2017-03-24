package com.linsh.dashedline;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Senh Linsh on 17/3/20.
 */

public class DashedLine extends BaseDashedLine {

    private int mOrientation;

    public DashedLine(Context context) {
        super(context);
    }

    public DashedLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttr(TypedArray typedArray) {
        mOrientation = typedArray.getInt(R.styleable.DashedLine_dashOrientation, 0);
    }

    public void setOrientation(Orientation orientation) {
        switch (orientation) {
            case HORIZONTAL:
                mOrientation = 0;
                break;
            case VERTICAL:
                mOrientation = 1;
                break;
        }
    }

    @Override
    protected void onDrawDash(Canvas canvas, Paint paint, float strokeWidth) {
        int width = getWidth();
        int height = getHeight();

        mPaint.setStrokeWidth(mStrokeWidth != 0 ? mStrokeWidth : (mOrientation == 1 ? width : height));

        if (mOrientation == 1) { /// 垂直
            canvas.drawLine(width / 2, 0, width / 2, height, paint);
        } else { /// 水平
            canvas.drawLine(0, height / 2, width, height / 2, paint);
        }
    }

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
}
