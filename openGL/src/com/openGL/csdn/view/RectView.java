package com.openGL.csdn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/12/28.
 */

public class RectView extends BaseGL {


    public RectView(Context context) {
        super(context);

    }

    public RectView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 2 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertices = byteBuffer.asFloatBuffer();
        // 定义两个三角形的六个顶点
        vertices.put(new float[]{
                120.0f, 200.0f,
                360.0f, 200.0f,
                360.0f, 600.0f,

                120.0f, 200.0f,
                360.0f, 600.0f,
                120.0f, 600.0f
        });
        vertices.flip();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 3, 3);

    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        gl.glClearColor(0, 0, 0, 1);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glViewport(0, 0, 480, 800);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, 480, 0, 800, 1, -1);
    }

}
