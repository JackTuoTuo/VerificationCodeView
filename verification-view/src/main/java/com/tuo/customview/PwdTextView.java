package com.tuo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 *
 * 作者：Tuo on 2018/4/13 16:35
 * 邮箱：839539179@qq.com
 */

public class PwdTextView extends AppCompatTextView {


    private float radius;

    private boolean hasPwd;

    public PwdTextView(Context context) {
        this(context, null);
    }

    public PwdTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PwdTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (hasPwd) {
            // 画一个黑色的圆
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
        }
    }


    public void clearPwd() {
        this.hasPwd = false;
        invalidate();
    }


    public void drawPwd(float radius) {
        this.hasPwd = true;
        if (radius == 0) {
            this.radius = getWidth() / 4;
        } else {
            this.radius = radius;
        }
        invalidate();
    }

}
