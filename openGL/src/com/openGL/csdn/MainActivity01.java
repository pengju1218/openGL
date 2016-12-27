package com.openGL.csdn;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.openGL.csdn.view.CubeView;
import com.openGL.csdn.view.EarthView;
import com.openGL.csdn.view.TextView;

public class MainActivity01 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
		EarthView baseGL=new EarthView(this);
		//baseGL.setText("我们都是中国人");
		setContentView(baseGL);

	}

}
