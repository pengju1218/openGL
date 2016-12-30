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

public class TriangleView extends BaseGL {


    public TriangleView(Context context) {
        super(context);

    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    /*第一种写法
    private float[] mTriangleArray = {
            0f, 1f, 0f,
            -1f, -1f, 0f,
            1f, -1f, 0f
    };
    //三角形各顶点颜色(三个顶点)
    private float[] mColor = new float[]{
            1, 1, 0, 1,
            0, 1, 1, 1,
            1, 0, 1, 1
    };
    private FloatBuffer mTriangleBuffer;
    private FloatBuffer mColorBuffer;


    @Override
    public void init() {
        super.init();

    }


    @Override
    public void onDrawFrame(GL10 gl) {


        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();

        // 允许设置顶点
        //GL10.GL_VERTEX_ARRAY顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 允许设置颜色
        //GL10.GL_COLOR_ARRAY颜色数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        //将三角形在z轴上移动
        gl.glTranslatef(0f, 0.0f, -2.0f);

        // 设置三角形
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);
        // 设置三角形颜色
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
        // 绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);


        // 取消颜色设置
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        // 取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        //绘制结束
        gl.glFinish();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
        gl.glViewport(0, 0, width, height);
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 重置投影矩阵
        gl.glLoadIdentity();
        // 设置视口的大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置白色为清屏
        gl.glClearColor(1, 1, 1, 1);



        //点相关s
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(mTriangleArray.length * 4);
        //以本机字节顺序来修改此缓冲区的字节顺序
        bb.order(ByteOrder.nativeOrder());
        mTriangleBuffer = bb.asFloatBuffer();
        //将给定float[]数据从当前位置开始，依次写入此缓冲区
        mTriangleBuffer.put(mTriangleArray);
        //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
        mTriangleBuffer.position(0);


        //颜色相关
        ByteBuffer bb2 = ByteBuffer.allocateDirect(mColor.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        mColorBuffer = bb2.asFloatBuffer();
        mColorBuffer.put(mColor);
        mColorBuffer.position(0);

    }*/


    FloatBuffer vertices;
    ShortBuffer indices;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d("GLSurfaceViewTest", "surface created");

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 3 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertices = byteBuffer.asFloatBuffer();
/*        vertices.put(new float[]{0f, 0f, 0f,
                320f, 0f, 0f,
                160f, 480f, 0f});*/

        vertices.put(new float[]{-160f, -240f, 0f,
                160f, -240f, 0f,
                0f, 240.0f ,0f});
/*
        vertices.put( new float[] { -160f, -240f,
                160f, -240f,
                0f, 240.0f});*/
        ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(3 * 2);
        indicesBuffer.order(ByteOrder.nativeOrder());
        indices = indicesBuffer.asShortBuffer();
        indices.put(new short[]{0, 1, 2});
        //indices.flip() == indices.position(0)
        indices.flip();
        vertices.flip();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("GLSurfaceViewTest", "surface changed: " + width + "x"
                + height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //定义显示在屏幕上的什么位置(opengl 自动转换)
        gl.glViewport(0, 0, TriangleView.this.getWidth(), TriangleView.this.getHeight());
        // gl.glViewport(50, 50,430, 550);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //设置视锥体的大小，一个很扁的长方体
        //gl.glOrthof(0, 320, 0, 480, 0, 1);//  left, right bottom top zNear zFar
          gl.glOrthof(-160, 160, -240, 240, 1, -1);
        //颜色设置为红色
        gl.glColor4f(1, 0, 0, 1);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
        //画出这个三角形
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 3,
                GL10.GL_UNSIGNED_SHORT, indices);
    }


}
