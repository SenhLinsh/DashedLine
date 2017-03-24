package com.linsh.dashedline;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by Senh Linsh on 17/3/20.
 */

public class DashedLine extends BaseDashedLine {

    private Path mPath;
    private int mOrientation;

    public DashedLine(Context context) {
        super(context);
        init();
    }

    public DashedLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void initAttr(TypedArray typedArray) {
        mOrientation = typedArray.getInt(R.styleable.DashedLine_dashOrientation, 0);
    }

    private void init() {
        mPath = new Path();
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

        mPath.rewind();
        if (mOrientation == 1) { /// 垂直
            mPath.moveTo(width / 2, 0);
            mPath.lineTo(width / 2, height);
        } else { /// 水平
            mPath.moveTo(0, height / 2);
            mPath.lineTo(width, height / 2);
        }
        canvas.drawPath(mPath, paint);
    }

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
}
