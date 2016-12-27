package com.openGL.csdn.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.openGL.csdn.R;
import com.openGL.csdn.inter.IOpenGLDemo;
import com.openGL.csdn.shape.Ball;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/12/27.
 */

public class EarthView extends BaseGL  implements IOpenGLDemo{
    private Ball ball;
    public EarthView(Context context) {
        super(context);
        setOpenGLDemo(this);
        this.setOnTouchListener(onTouchListener);
    }

    public EarthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOpenGLDemo(this);
        this.setOnTouchListener(onTouchListener);

    }


    private IOpenGLDemo openGLDemo;

    public void setOpenGLDemo(IOpenGLDemo openGLDemo) {
        this.openGLDemo = openGLDemo;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

        gl.glShadeModel(GL10.GL_SMOOTH);

        gl.glClearDepthf(1.0f);

        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        if (openGLDemo != null) {
            openGLDemo.initLight(gl);
            openGLDemo.initObject(gl);
        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (openGLDemo != null) {
            openGLDemo.DrawScene(gl);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }

    @Override
    public void DrawScene(GL10 gl) {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);



        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        ball.drawSelf(gl);
        gl.glPopMatrix();
    }



    @Override
    public void initLight(GL10 gl) {
        gl.glEnable(GL10.GL_LIGHTING);
        initWhiteLight(gl, GL10.GL_LIGHT0, 0.5f, 0.5f, 0.5f);
    }

    @Override
    public void initObject(GL10 gl) {
        ball = new Ball(5, initTexture(gl, R.drawable.logo));
    }




    /**
     * 设置白色灯光
     *
     * @param gl
     * @param cap
     * @param posX
     * @param posY
     * @param posZ
     */
    public void initWhiteLight(GL10 gl, int cap, float posX, float posY, float posZ) {
        gl.glEnable(cap);// 打开cap号灯

        // 环境光设置
        float[] ambientParams = { 1.0f, 1.0f, 1.0f, 1.0f };// 光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams, 0);

        // 散射光设置
        float[] diffuseParams = { 1.0f, 1.0f, 1.0f, 1.0f };// 光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams, 0);

        // 反射光设置
        float[] specularParams = { 1f, 1f, 1f, 1.0f };// 光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams, 0);

        float[] positionParams = { posX, posY, posZ, 1 };
        gl.glLightfv(cap, GL10.GL_POSITION, positionParams, 0);
    }

    /**
     * 由一个bitmap 创建一个纹理
     *
     * bitmap的大小限制：
     *
     * @param gl
     * @param resourceId
     * @return
     */
    public int initTexture(GL10 gl, int resourceId) {
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        int currTextureId = textures[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        InputStream is = this.getResources().openRawResource(resourceId);
        Bitmap bitmapTmp;
        try {
            bitmapTmp = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle();
        return currTextureId;
    }

    /**
     * 初始化白色材质
     *
     * 材质为白色时什么颜色的光照在上面就将体现出什么颜色
     *
     * @param gl
     */
    private void initMaterial(GL10 gl) {

        // 环境光为白色材质
        float ambientMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial, 0);

        // 散射光为白色材质
        float diffuseMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial, 0);

        // 高光材质为白色
        float specularMaterial[] = { 1f, 1f, 1f, 1.0f };
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial, 0);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
    }



    private OnTouchListener onTouchListener = new OnTouchListener() {
        float lastX, lastY;

        private int mode = 0; // 触控点的个数

        float oldDist = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = 1;
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    mode += 1;

                    oldDist = caluDist(event);

                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    mode -= 1;
                    break;

                case MotionEvent.ACTION_UP:
                    mode = 0;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode >= 2) {
                        float newDist = caluDist(event);
                        if (Math.abs(newDist - oldDist) > 2f) {
                            zoom(newDist, oldDist);
                        }
                    } else {
                        float dx = event.getRawX() - lastX;
                        float dy = event.getRawY() - lastY;

                        float a = 180.0f / 320;
                        ball.mAngleX += dx * a;
                        ball.mAngleY += dy * a;
                    }
                    break;
            }

            lastX = (int) event.getRawX();
            lastY = (int) event.getRawY();
            return true;
        }
    };

    public void zoom(float newDist, float oldDist) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float px = displayMetrics.widthPixels;
        float py = displayMetrics.heightPixels;

        ball.zoom += (newDist - oldDist) * (ball.maxZoom - ball.minZoom) / Math.sqrt(px * px + py * py) / 4;

        if (ball.zoom > ball.maxZoom) {
            ball.zoom = ball.maxZoom;
        } else if (ball.zoom < ball.minZoom) {
            ball.zoom = ball.minZoom;
        }
    }

    public float caluDist(MotionEvent event) {
        float dx = event.getX(0) - event.getX(1);
        float dy = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }






}
