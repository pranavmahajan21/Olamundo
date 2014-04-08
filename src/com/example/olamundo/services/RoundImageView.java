package com.example.olamundo.services;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.loopj.android.image.SmartImageView;

public class RoundImageView extends SmartImageView {
	 
	private static final int RADIUS = 32;
	    private Paint mPaint;
	    private Paint mSrcIn;
	    private RectF mRect;
	    
    public RoundImageView(Context context) {
        super(context);
//        setBackgroundColor(0xffffffff);
        mSrcIn = new Paint();
        mSrcIn.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new RectF();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Drawable dr = getDrawable();
        if (dr != null) {
            mRect.set(dr.getBounds());
            getImageMatrix().mapRect(mRect);
            mRect.offset(getPaddingLeft(), getPaddingTop());

            int rtc = canvas.saveLayer(mRect, null, Canvas.ALL_SAVE_FLAG);
            // draw DST
            canvas.drawRoundRect(mRect, RADIUS, RADIUS, mPaint);

            canvas.saveLayer(mRect, mSrcIn, Canvas.ALL_SAVE_FLAG);
            // draw SRC
            super.onDraw(canvas);
            canvas.restoreToCount(rtc);
        }
    }
}
