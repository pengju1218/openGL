package com.openGL.csdn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/12/30.
 */

public class Process extends GLCanvas {
    private Paint paint1=new Paint();
    private Paint paint2=new Paint();

    public Process(Context context) {
        super(context);

    }

    public Process(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onDrawCanvas(Canvas canvas) {
        float startX=20;
        float maringRight=20;
        float width=getMeasuredWidth()-startX-maringRight;

        paint1.setStrokeWidth(5);
        paint1.setColor(Color.RED);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeJoin(Paint.Join.ROUND);

        float starty=20;

        float stopX=startX+width*50/100;
        canvas.drawLine(startX,starty,stopX,starty,paint1);

        paint2.setStrokeWidth(5);
        float n_startX=stopX;
        paint2.setColor(Color.GREEN);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        float n_starty=20;

        float n_stopX=n_startX+width*(100-50)/100;
        canvas.drawLine(n_startX,n_starty,n_stopX,n_starty,paint2);
    }
}