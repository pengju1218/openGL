package com.openGL.csdn.shape;

import com.openGL.csdn.bean.Constant;
import com.openGL.csdn.util.TextureManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
/**
 * 色子类
 * @author Yue Jinbiao
 *
 */
public class Dice {
	private int vertexCount = 36;
	/** 顶点坐标数据缓冲 */
	private FloatBuffer mVertexBuffer;
	/** 顶点法向量数据缓冲 */
	private FloatBuffer mNormalBuffer;
	/** 顶点纹理数据缓冲，存储每个顶点在位图中的坐标 */
	private FloatBuffer mTextureBuffer;

	/**色子类*/
	public Dice() {
		initDataBuffer();
	}
	/**初始化定点数据缓冲区*/
	private void initDataBuffer(){
		float[] vertices = Constant.VERTEX_COORD;
		float[] normals = Constant.NORMALS_COORD;
		float[] texST = Constant.TEXTURE_COORD;		//new float[cpTexST.length];	//常量数组的内容可变，这里要拷贝

		// vertices.length*4是因为一个Float四个字节
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mVertexBuffer = vbb.asFloatBuffer();// 转换为float型缓冲
		mVertexBuffer.put(vertices);// 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);// 设置缓冲区起始位置
		
		ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length * 4);
		nbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mNormalBuffer = nbb.asFloatBuffer();// 转换为int型缓冲
		mNormalBuffer.put(normals);// 向缓冲区中放入顶点着色数据
		mNormalBuffer.position(0);// 设置缓冲区起始位置
		
		ByteBuffer tbb = ByteBuffer.allocateDirect(texST.length * 4);
		tbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mTextureBuffer = tbb.asFloatBuffer();// 转换为int型缓冲
		mTextureBuffer.put(texST);// 向缓冲区中放入顶点着色数据
		mTextureBuffer.position(0);// 设置缓冲区起始位置
	}
	/**绘制色子*/
	public void drawSelf(GL10 gl) {
//		Log.i("tg","to draw dice..");

		// 为画笔指定顶点坐标数据
		gl.glVertexPointer(3, 				// 每个顶点的坐标数量为3 xyz
				GL10.GL_FLOAT,			 	// 顶点坐标值的类型为 GL_FIXED
				0, 										// 连续顶点坐标数据之间的间隔
				mVertexBuffer 					// 顶点坐标数据
		);

		// 为画笔指定顶点法向量数据
		gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);

		// 开启纹理贴图
		gl.glEnable(GL10.GL_TEXTURE_2D);
		// 允许使用纹理ST坐标缓冲
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		// 指定纹理ST坐标缓冲
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
		// 绑定当前纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureManager.getTextureId(TextureManager.TEXTURE_INDEX_DICE));

		// 绘制图形 , 以三角形方式填充
		gl.glDrawArrays(GL10.GL_TRIANGLES, 	0, 	vertexCount );
	}
}
