package com.openGL.csdn.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.AttributeSet;

import com.openGL.csdn.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 立方体
 * Created by Administrator on 2016/12/27.
 */

public class CubeView extends BaseGL {

    public static Bitmap mBitmap;
    public CubeView(Context context) {
        super(context);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img);
    }

    public CubeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img);
    }
    boolean mSwitch = true;
    float xrot, yrot;
    float xspeed, yspeed;
    float z = -5.0f;


    //光线参数
    FloatBuffer lightAmbient = FloatBuffer.wrap(new float[]{0.5f,0.5f,0.5f,1.0f});
    FloatBuffer lightDiffuse = FloatBuffer.wrap(new float[]{1.0f,1.0f,1.0f,1.0f});
    FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{0.0f,0.0f,2.0f,1.0f});

    FloatBuffer normalsBuf;
    FloatBuffer verticesBuf;
    FloatBuffer texCoordsBuf;
    ByteBuffer indicesBuf;

    int filter = 1;
    int[] texture;
    float[] vertices = {
            -1f,-1f,1f,
            1f,-1f,1f,
            1f,1f,1f,
            -1f,1f,1f,

            -1f,-1f,-1f,
            -1f,1f,-1f,
            1f,1f,-1f,
            1f,-1f,-1f,

            -1f,1f,-1f,
            -1f,1f,1f,
            1f,1f,1f,
            1f,1f,-1f,

            -1f,-1f,-1f,
            1f,-1f,-1f,
            1f,-1f,1f,
            -1f,-1f,1f,

            1f,-1f,-1f,
            1f,1f,-1f,
            1f,1f,1f,
            1f,-1f,1f,

            -1f,-1f,-1f,
            -1f,-1f,1f,
            -1f,1f,1f,
            -1f,1f,-1f,

    };

    float[] normals = {
            0,0,1f,
            0,0,1f,
            0,0,1f,
            0,0,1f,

            0,0,1f,
            0,0,1f,
            0,0,1f,
            0,0,1f,

            0,1f,0,
            0,1f,0,
            0,1f,0,
            0,1f,0,

            0,-1f,0,
            0,-1f,0,
            0,-1f,0,
            0,-1f,0,

            1f,0,0,
            1f,0,0,
            1f,0,0,
            1f,0,0,

            -1f,0,0,
            -1f,0,0,
            -1f,0,0,
            -1f,0,0,
    };

    float[] texCoords = {
            1f,0,0,0,0,1f,1f,1f,
            0,0,0,1f,1f,1f,1f,0,
            1f,1f,1f,0,0,0,0,1f,
            0,1f,1f,1f,1f,0,0,0,
            0,0,0,1f,1f,1f,1f,0,
            1f,0,0,0,0,1f,1f,1f,
    };

    byte[] indices = {
            0,1,3,2,
            4,5,7,6,
            8,9,11,10,
            12,13,15,14,
            16,17,19,18,
            20,21,23,22,
    };

    @Override
    public void onDrawFrame(GL10 gl)  {
        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();
        //使能光照
        gl.glEnable(GL10.GL_LIGHTING);

        ////////////////
        gl.glTranslatef(0.0f, 0.0f, z);

        //设置旋转
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);

        //设置纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[filter]);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuf);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuf);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordsBuf);

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        //绘制四边形
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 24,  GL10.GL_UNSIGNED_BYTE, indicesBuf);

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        //修改旋转角度
        xrot+=0.3f;
        yrot+=0.2f;

        //混合开关
        if (mSwitch){
            gl.glEnable(GL10.GL_BLEND);         // 打开混合
            gl.glDisable(GL10.GL_DEPTH_TEST);   // 关闭深度测试
        }
        else{
            gl.glDisable(GL10.GL_BLEND);        // 关闭混合
            gl.glEnable(GL10.GL_DEPTH_TEST);    // 打开深度测试
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)  {
        float ratio = (float) width / height;
        //设置OpenGL场景的大小
        gl.glViewport(0, 0, width, height);
        //设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //重置投影矩阵
        gl.glLoadIdentity();
        // 设置视口的大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        // 选择模型观察矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 重置模型观察矩阵
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)  {
        gl.glDisable(GL10.GL_DITHER);

        // 告诉系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        // 黑色背景
        gl.glClearColor(0, 0, 0, 0);

        gl.glEnable(GL10.GL_CULL_FACE);
        // 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //设置光线,,1.0f为全光线，a=50%
        gl.glColor4f(1.0f,1.0f,1.0f,0.5f);
        // 基于源象素alpha通道值的半透明混合函数
        gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE);

        //纹理相关
        IntBuffer textureBuffer = IntBuffer.allocate(3);
        gl.glGenTextures(3, textureBuffer);
        texture = textureBuffer.array();

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);

        //深度测试相关
        gl.glClearDepthf(1.0f);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        //设置环境光
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);
        //设置漫射光
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);
        //设置光源位置
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);
        //开启一号光源
        gl.glEnable(GL10.GL_LIGHT1);
        //开启混合
        gl.glEnable(GL10.GL_BLEND);

        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        nbb.order(ByteOrder.nativeOrder());
        normalsBuf = nbb.asFloatBuffer();
        normalsBuf.put(normals);
        normalsBuf.position(0);

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        verticesBuf = vbb.asFloatBuffer();
        verticesBuf.put(vertices);
        verticesBuf.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length*4);
        tbb.order(ByteOrder.nativeOrder());
        texCoordsBuf = tbb.asFloatBuffer();
        texCoordsBuf.put(texCoords);
        texCoordsBuf.position(0);

        indicesBuf = ByteBuffer.allocateDirect(indices.length);
        indicesBuf.put(indices);
        indicesBuf.position(0);

        mSwitch = false;
    }
}
