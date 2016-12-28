package com.openGL.csdn.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.openGL.csdn.shape.BackWall;
import com.openGL.csdn.shape.Dice;
import com.openGL.csdn.util.TextureManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 色子的渲染视图
 * @author Yue Jinbiao
 *
 */
public  class DiceSurfaceView extends  BaseGL{

	private float mPreviousX = 0;
	private float mPreviousY = 0;



	//90度角的正余弦
	private static final float NORMALS_COS = (float) Math.cos(Math.PI/2);
	private static final float NORMALS_SIN = (float)Math.sin(Math.PI/2);
	private static final int MSG_ROTATE_STOP = 1;


	private Handler handler = null;
	private Dice dice = null;
	private BackWall back = null;
	//转动时速度矢量
	private float rotateV = 0;
	//已旋转角度
	private float rotated = 0;
	//当前旋转轴
	private float axisX = 0;
	private float axisY = 0;
	private RotateTask rTask = null;

	public DiceSurfaceView(Context context) {
		super(context);
		// 设置渲染器，

		// 设置描绘方式，
		setAutoRender(false);
		this.requestRender();

		// 初始化数据
		dice = new Dice();
		back = new BackWall();
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				if(msg.what == MSG_ROTATE_STOP){
					DiceSurfaceView.this.setAutoRender(false);//设置非自动连续渲染
				}
			}
		};
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		//转换坐标方向；
		y = -y;

		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - mPreviousX;
			float dy = y - mPreviousY;
			onTouchMove(dx, dy);
		case MotionEvent.ACTION_DOWN:
//			Log.i("tg","touch down/" + x + "/" + y);
			this.mPreviousX = x;
			this.mPreviousY = y;
			break;
		case MotionEvent.ACTION_UP:
//			Log.i("tg","touch up/" + x + "/" + y);
			this.mPreviousX = 0;
			this.mPreviousY = 0;
			setAutoRender(true);
			startRotate();
			break;
		}
		this.requestRender();
		return true;
	}
	/**
	 * 设置是否自动连续渲染
	 * @param auto
	 */
	public void setAutoRender(boolean auto){
		// RENDERMODE_WHEN_DIRTY-有改变时重绘-需调用requestRender()
		// RENDERMODE_CONTINUOUSLY -自动连续重绘（默认）
		if(auto){
			setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		}else{
			setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}
	}

	//重置背景画
	public void resetBackground(int optionalBg){
		TextureManager.bgIndex = optionalBg;
		this.requestRender();
	}





		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//		Log.i("tg","Surface created.config/" + config);

			// Set the background frame color
			gl.glClearColor(0.3f, 0.3f, 0.4f, 0.7f);
			// 启用深度测试, 不启用时，不管远近，后画的会覆盖之前画的，
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// 启用顶点坐标数组
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);// 打开法线数组
			//初始化纹理
			TextureManager.initTexture(gl, DiceSurfaceView.this.getResources());
			initLight(gl);
			initMaterial(gl);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
//		Log.i("tg","Surface changed.。");
			//设置视窗
			gl.glViewport(0, 0, width, height);
			// 适应屏幕比例
			float ratio = (float) width / height;
			//设置矩阵为投射模式
			gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
			//重置矩阵
			gl.glLoadIdentity();                        // reset the matrix to its default state
			//设置投射椎体 // apply the projection matrix
			if(ratio < 1 ){
				gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);
			}else{
				gl.glFrustumf(-ratio, ratio, -1, 1, 4, 8);
//        	gl.glFrustumf(-ratio*1.5f, ratio*1.5f, -1*1.5f, 1*1.5f, 4, 8);
			}

		}

		@Override
		public void onDrawFrame(GL10 gl) {
//		Log.i("tg","draw a frame..");
			// 重画背景,  刷屏
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

			// 设置 GL_MODELVIEW(模型观察) 转换模式
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			// 重置矩阵，设置当前矩阵为单位矩阵，相当于渲染之前清屏
			gl.glLoadIdentity();

			// 使用GL_MODELVIEW 模式时, 必须设置视点
//		GLU.gluLookAt(gl, 3,3,3, 1f, 1f, 1f, 0f, 1.0f, 0f);
			GLU.gluLookAt(gl, 0, 0, 5, 0f, 0f, -1f, 0f, 1.0f, 0.0f);

			// 绘制背景墙
			gl.glPushMatrix();
			back.drawSelf(gl);
			gl.glPopMatrix();

			// 绘制色子
			gl.glPushMatrix();

			if(rotated != 0){
				RotateOnTouch(gl);
			}
			gl.glRotatef(45, 1, 1, 0);
			dice.drawSelf(gl);
			gl.glPopMatrix();

		}
		/**触摸后转动*/
		private void RotateOnTouch(GL10 gl){
			this.rotated += rotateV;
			gl.glRotatef(rotated, axisX, axisY, 0);
			if(rotateV>0){
//			Log.i("tg","GL rotateV/" + rotateV);
//			Log.i("tg","GL rotated/" + rotated + "/" + rotateV);
			}
		}
		/**
		 * 响应触摸移动
		 * @param dx
		 * @param dy
		 */
		public void onTouchMove(float dx,float dy){
			rotateV = Math.abs(dx) + Math.abs(dy);
//		Log.i("tg","GL rotateV/" + rotateV);
			rotated += rotateV;
			setAxisLine(dx,dy);
		}
		/**设置转轴线*/
		public void setAxisLine(float dx ,float dy){
			//x1 = x0 * cosB - y0 * sinB		y1 = x0 * sinB + y0 * cosB
			this.axisX = dx*NORMALS_COS - dy*NORMALS_SIN;
			this.axisY= dx*NORMALS_SIN + dy*NORMALS_COS;
		}
		/**启动旋转线程*/
		public void startRotate(){
			if(rTask != null){
				rTask.running = false;
			}
			rTask = new RotateTask();
			rTask.start();
		}
		/**
		 * 旋转线程类
		 *
		 */
		class RotateTask extends Thread{
			boolean running = true;
			@Override
			public void run() {
				while(running && rotateV > 0){
					if(rotateV>50){
						rotateV -= 7;
					}else if(rotateV>20){
						rotateV -= 3;
					}else{
						rotateV --;
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(rotateV<=0){
					handler.sendEmptyMessage(MSG_ROTATE_STOP);
				}
			}
		}

		/** 初始化灯光
		 * 定义各种类型光的光谱
		 * */
		private void initLight(GL10 gl) {
			gl.glEnable(GL10.GL_LIGHTING);		//打开照明总开关
			gl.glEnable(GL10.GL_LIGHT1);		// 打开1号灯

			// 环境光设置
			float[] ambientParams = { 0.7f, 0.7f, 0.7f, 1.0f };// 光参数 RGBA
			gl.glLightfv(GL10.GL_LIGHT1,		//光源序号
					GL10.GL_AMBIENT, 			//光照参数名-环境光
					ambientParams, 				//参数值
					0							//偏移
			);
			// 散射光设置
			float[] diffuseParams = { 0.7f, 0.7f, 0.7f, 1.0f };// 光参数 RGBA
			gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseParams, 0);
			// 反射光设置
			float[] specularParams = { 1f, 1f, 1f, 1.0f };// 光参数 RGBA
			gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularParams, 0);
			//光源位置
			float[] positionParams = { 0,0,9,1 };
			gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParams, 0);
			//聚光灯方向
			float[] directionParams = {0,0,-1};
			gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPOT_DIRECTION , directionParams, 0);
			//聚光角度（0-90）度
			gl.glLightf(GL10.GL_LIGHT1, GL10.GL_SPOT_CUTOFF , 30);
			//聚光程度（0-128）实现聚焦
			gl.glLightf(GL10.GL_LIGHT1, GL10.GL_SPOT_EXPONENT  , 10);
		}

		/** 初始化材质
		 * 定义平面对各种类型光的反射光谱
		 * */
		private void initMaterial(GL10 gl) {
			//控制环境光在平面上的反射光光谱
			float ambientMaterial[] = { 0.4f, 0.5f, 0.6f, 0.3f };
			gl.glMaterialfv(
					GL10.GL_FRONT_AND_BACK, //反射面，正面，反面，或两面（android)只支持两面
					GL10.GL_AMBIENT,		//反射光类型，环境光
					ambientMaterial, 		//反射参数值
					0						//偏移
			);
			//控制反射散射光
			float diffuseMaterial[] = { 0.7f, 0.6f, 0.7f, 0.8f };
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
					diffuseMaterial, 0);
			//控制反射光
			float specularMaterial[] = { 0.9f, 0.9f, 0.9f, 0.8f };
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
					specularMaterial, 0);
			//对高光的反射指数（0-128）值越大光的散射越小
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 120f);
		}


}
