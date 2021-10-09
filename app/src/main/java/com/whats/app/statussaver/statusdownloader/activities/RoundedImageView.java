package com.whats.app.statussaver.statusdownloader.activities;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;


public class RoundedImageView extends androidx.appcompat.widget.AppCompatImageView {


    public RoundedImageView(Context context) {
        super(context);
    }


    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float radius = 25.0f;


        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);


        super.onDraw(canvas);
    }
}