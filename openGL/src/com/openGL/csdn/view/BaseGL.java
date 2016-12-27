package com.openGL.csdn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Window;
import android.view.WindowManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/12/27.
 */

public class BaseGL extends GLSurfaceView implements GLSurfaceView.Renderer {


    public BaseGL(Context context) {
        super(context);
        init();
        this.setRenderer(this);
    }

    public BaseGL(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        this.setRenderer(this);
    }


    public void init(){
       // this.setBackgroundColor(Color.RED);
        //透明
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //GLES20.glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        //GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

      // GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
