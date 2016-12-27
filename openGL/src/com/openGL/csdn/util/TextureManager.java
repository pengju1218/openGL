package com.openGL.csdn.util;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import com.openGL.csdn.R;

/**
 * 纹理管理者
 * @author Yue Jinbiao
 *
 */
public class TextureManager {

	//纹理索引号
	public static final int TEXTURE_INDEX_DICE = 0;
	public static final int TEXTURE_INDEX_BG00 = 1;
	public static final int TEXTURE_INDEX_BG01 = 2;
	public static final int TEXTURE_INDEX_BG02 = 3;
	//纹理资源id
	private static int[] textureSrcs = {R.drawable.dice_map, R.drawable.bg00,R.drawable.bg01,R.drawable.bg02};
	//纹理id存储
	private static int[] textureIds = new int[textureSrcs.length];
	
	private static GL10 gl = null;
	private static Resources res = null;

	//背景画索引 0-2；
	public static int bgIndex = 0;
	
	/**
	 * 取得指定索引的纹理id
	 * @param index
	 * @return
	 */
	public static int getTextureId(int index){
//		Log.i("tg","TextureManager/getTextureId/" + textureIds[index]);
		if(textureIds[index] <= 0){
			Log.i("tg","TextureManager/getTextureId/" + textureIds[index]);
			gl.glGenTextures(1, textureIds, index);
			bindTexture(gl,res,index);
		}
		return textureIds[index];
	}
	/**初始化纹理*/
	public static void initTexture( GL10 gl, Resources res) {

		TextureManager.gl = gl;
		TextureManager.res = res;
		//获取未使用的纹理对象ID
		gl.glGenTextures(1, textureIds, TEXTURE_INDEX_DICE);
		bindTexture(gl,res,TEXTURE_INDEX_DICE);
		//获取未使用的纹理对象ID
		gl.glGenTextures(1, textureIds, bgIndex + 1);
		bindTexture(gl,res,bgIndex + 1);

		
//		for(int i=0;i<textureIds.length;i++){
//			bindTexture(gl,res,i);
//		}

	}
	/**
	 * 为纹理id绑定纹理。
	 * @param gl
	 * @param res
	 * @param index
	 */
	private static void bindTexture(GL10 gl,Resources res,int index){
//		Log.i("tg","TextureManager/initTexture/" + textureIds[i]);
		//绑定纹理对象
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[index]);
		//设置纹理控制，指定使用纹理时的处理方式
		//缩小过滤：一个像素代表多个纹素。
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, 	//纹理目标
				GL10.GL_TEXTURE_MIN_FILTER,			//纹理缩小过滤
				GL10.GL_NEAREST								//使用距离当前渲染像素中心最近的纹素
				);
		//放大过滤：一个像素是一个纹素的一部分。
		//放大过滤时，使用距离当前渲染像素中心，最近的4个纹素加权平均值，也叫双线性过滤。
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);		//
		//设置纹理贴图方式，指定对超出【0,1】的纹理坐标的处理方式
		//左下角是【0,0】，右上角是【1,1】，横向是S维，纵向是T维。android以左上角为原点
		//S维贴图方式：重复平铺
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);
		//T维贴图方式：重复平铺
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);
		bindBitmap(index,res);
	}
	/**
	 * 为纹理绑定位图
	 * @param index
	 * @param res
	 */
	private static void bindBitmap(int index,Resources res){
		Bitmap bitmap = null;
		InputStream is = res.openRawResource(textureSrcs[index]);
		try {
			bitmap = BitmapFactory.decodeStream(is);
		} finally {
			if(is != null){
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//为纹理对象指定位图
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		//释放bitmap对象内存，像素数据仍存在，不影响使用。
		bitmap.recycle();
	}
}
