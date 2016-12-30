package com.openGL.csdn.view;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLU;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.openGL.csdn.shape.Oval;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/12/28.
 */

public class OvalView extends BaseGL {


    public OvalView(Context context) {
        super(context);

    }

    public OvalView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    public void init() {
        super.init();
        this.setBackgroundColor(Color.parseColor("#30333333"));
    }

    Oval o = new Oval();

    //度到弧度的转换

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glLightModelx(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_FALSE);
        o.draw(gl);

    }

    public void setyAngle(float yAngle)
    {
        //b.setyAngle(yAngle);
        o.setyAngle(yAngle);
    }

    public void setzAngle(float zAngle)
    {
       // b.setzAngle(zAngle);
        o.setzAngle(zAngle);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 1.0f, 100.0f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(0, 0, 0, 0);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnable(GL10.GL_LIGHT0);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// 角度缩放比例
    private float mPreviousY;// 上次的触控位置Y坐标
    private float mPreviousX;// 上次的触控位置X坐标
    float yAngle = 0;// 绕y轴旋转的角度
    float zAngle = 0;// 绕z轴旋转的角度
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {   // 触摸事件的回调方法
        float x = e.getX();// 得到x坐标
        float y = e.getY();// 得到y坐标
        switch (e.getAction())
        {
            case MotionEvent.ACTION_MOVE:// 触控笔移动
                float dy = y - mPreviousY;// 计算触控笔Y位移
                float dx = x - mPreviousX;// 计算触控笔X位移
                yAngle += dx * TOUCH_SCALE_FACTOR;// 设置沿y轴旋转角度
                zAngle += dy * TOUCH_SCALE_FACTOR;// 设置沿z轴旋转角度

                requestRender();// 重绘画面
        }
        mPreviousY = y;// 记录触控笔位置
        mPreviousX = x;// 记录触控笔位置
        return true;// 返回true
    }
}
