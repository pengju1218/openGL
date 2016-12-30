package com.openGL.csdn.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.AttributeSet;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/12/28.
 */

public class GLImageView extends BaseGL {


    public GLImageView(Context context) {
        super(context);
    }

    public GLImageView(Context context, AttributeSet attrs) {
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
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d("GLSurfaceViewTest", "surface created");

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
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("GLSurfaceViewTest", "surface changed: " + width + "x"
                + height);
        //gl.glViewport(0,0,width,height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glViewport(0, 0, GLImageView.this.getWidth(), GLImageView.this.getHeight());
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

    public int loadTexture(String fileName, GL10 gl) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContext().getAssets().open(
                    fileName));
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
        } catch (IOException e) {
            Log.d("TexturedRectangleTest",
                    "couldn't load asset 'bobrgb888.png'!");
            throw new RuntimeException("couldn't load asset '" + fileName
                    + "'");
        }
    }


}
