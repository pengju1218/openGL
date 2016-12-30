package com.openGL.csdn.view;

import android.content.Context;
import android.opengl.GLU;
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/12/29.
 */

public class LineView extends BaseGL {
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background color to black ( rgba ).
        //黑色背景
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        // 启用阴影平滑（不是必须的）
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        // 设置深度缓存
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        // 所作深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        // 对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

    }
    @Override
    public void onDrawFrame(GL10 gl) {

        DrawScene(gl);

    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        // 设置画面的大小
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        // 重置投影矩阵
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        // 设置画面比例
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
                100.0f);
        // Select the modelview matrix
        // 选择模型观察矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        // 重置模型观察矩阵
        gl.glLoadIdentity();
    }

    // 画线的坐标
    float vertexArray2[] = {
            -1.01f, -1.6f, 0.0f,
            1.01f, -1.6f, 0.0f,
           };

    // 画线
    public void DrawScene(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexArray2.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertex = vbb.asFloatBuffer();
        vertex.put(vertexArray2);
        vertex.position(0);

        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_LINES, 0, 2);
       /* int index = new Random().nextInt(4);
        switch (index) {

            case 1:
                gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_LINES, 0, 4);
                break;
            case 2:
                gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);
                break;
            case 3:
                gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
                break;
        }*/

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }
}
