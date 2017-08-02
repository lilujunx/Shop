package com.admin.exe.shop.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 0024/10/24.
 */

public class OldPriceTV extends TextView {
    private Paint mPaint=new Paint();

    public OldPriceTV(Context context) {
        super(context);

    }
    public OldPriceTV(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.RED);
        canvas.drawLine(0,this.getHeight()/2.0f,this.getWidth(),this.getHeight()/2.0f,mPaint);
    }
}
