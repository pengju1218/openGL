package com.openGL.csdn.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.openGL.csdn.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/12/28.
 */

public abstract class GLCanvas extends BaseGL {

    public GLCanvas(Context context) {
        super(context);
    }

    public GLCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    FloatBuffer vertices;
    FloatBuffer texture;
    ShortBuffer indices;
    int textureId;
    private float[] GVertices1 = new float[]{-240, -400.0f,
            240, -400.0f,
            -240, 400.0f,
            240, 400.0f};

    private float[] GVertices2 = new float[]{0.0f, 0.0f,
            480.0f, 0.0f,
            0.0f, 800.0f,
            480.0f, 800.0f};


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("GLSurfaceViewTest", "surface changed: " + width + "x"
                + height);
        //gl.glViewport(0,0,width,height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glViewport(0, 0, GLCanvas.this.getWidth(), GLCanvas.this.getHeight());
        textureId = loadTexture("ls.jpg", gl);
        //定义显示在屏幕上的什么位置(opengl 自动转换)
        // gl.glViewport(0, 0, GLImageView.this.getWidth(), GLImageView.this.getHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //gl.glOrthof(-240, 240, -400, 400, 1, -1);
        gl.glOrthof(0, 480, 0, 800, 0, 1);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        //绑定纹理ID
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
        // gl.glRotatef(1, 0, 1, 0);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 6,
                GL10.GL_UNSIGNED_SHORT, indices);
    }

    //定义一个int数组用来保存纹理
    private int[] TextureString = new int[1];

    public int loadTexture(String fileName, GL10 gl) {
        String s = "北京";

        Bitmap bitmap;

        // 构建Bitmap，它的width和height必须是2的n次方
        bitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 设置画布背景为透明，这样我们的纹理就只显示文字，而没有颜色背景
        canvas.drawColor(Color.TRANSPARENT);
        Paint p = new Paint();


        onDrawCanvas(canvas, p);

        // 到这里为止，所需Bitmap构建完成

        // 从这里开始生成纹理映射

        gl.glGenTextures(1, TextureString, 0);


        int textureIds[] = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        int textureId = textureIds[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        bitmap.recycle();
        return textureId;

    }


    public abstract void onDrawCanvas(Canvas canvas, Paint p);


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglconfig) {
        // 从这里开始生成所需Bitmap

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 3 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertices = byteBuffer.asFloatBuffer();

        vertices.put(GVertices2);


        ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(6 * 2);
        indicesBuffer.order(ByteOrder.nativeOrder());
        indices = indicesBuffer.asShortBuffer();
        indices.put(new short[]{0, 1, 2, 1, 2, 3});

        ByteBuffer textureBuffer = ByteBuffer.allocateDirect(4 * 3 * 4);
        textureBuffer.order(ByteOrder.nativeOrder());
        texture = textureBuffer.asFloatBuffer();
        texture.put(new float[]{0, 1f,
                1f, 1f,
                0f, 0f,
                1f, 0f});

        indices.position(0);
        vertices.position(0);
        texture.position(0);

        // 到这里纹理映射完成

        // 把我们的纹理映射地址传递给JNI

        //Native_SolarWind.DrawText(TextureString);

        // 初始化OpenglES场景设置
        //Native_SolarWind.onSurfaceCreated(gl10, eglconfig, 0, 0);

    }


    private void loadTexture(GL10 gl) {
        InputStream bitmapStream = null;
        Bitmap bitmap = null;
        try {
            // 打开图片资源流
            bitmapStream = getContext().getResources().openRawResource(R.raw.ie);
            // 解码图片生成 Bitmap 实例
            bitmap = BitmapFactory.decodeStream(bitmapStream);

            // 生成一个纹理对象，并将其ID保存到成员变量 texture 中
            int[] textures = new int[1];
            gl.glGenTextures(1, textures, 0);
            int texture = textures[0];

            // 将生成的空纹理绑定到当前2D纹理通道
            gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);

            // 设置2D纹理通道当前绑定的纹理的属性
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                    GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                    GL10.GL_REPEAT);

            // 将bitmap应用到2D纹理通道当前绑定的纹理中
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        } finally {
            // 释放资源
            // BTW: 期待 android 早日支持 Java 新的 try-with-resource 语法

            if (bitmap != null)
                bitmap.recycle();

            if (bitmapStream != null) {
                try {
                    bitmapStream.close();
                } catch (IOException e) {

                }
            }
        }
    }
/*
    private void loadTexture(GL10 gl) {
        InputStream bitmapStream = null;
        Bitmap bitmap = null;
        try {
            // 打开图片资源流
            bitmapStream = getContext().getResources().openRawResource(R.raw.ie);
            // 解码图片生成 Bitmap 实例
            bitmap = BitmapFactory.decodeStream(bitmapStream);

            // 生成一个纹理对象，并将其ID保存到成员变量 texture 中
            int[] textures = new int[1];
            gl.glGenTextures(1, textures, 0);
            int texture = textures[0];

            // 将生成的空纹理绑定到当前2D纹理通道
            gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);

            // 设置2D纹理通道当前绑定的纹理的属性
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                    GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                    GL10.GL_REPEAT);

            // 将bitmap应用到2D纹理通道当前绑定的纹理中
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        } finally {
            // 释放资源
            // BTW: 期待 android 早日支持 Java 新的 try-with-resource 语法

            if (bitmap != null)
                bitmap.recycle();

            if (bitmapStream != null) {
                try {
                    bitmapStream.close();
                } catch (IOException e) {

                }
            }
        }
    }*/


  /*  public void  invalidate(){

    }*/
}
