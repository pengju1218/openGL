package com.openGL.csdn;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.openGL.csdn.view.CoverFlowOpenGL;
import com.openGL.csdn.view.GLCanvas;
import com.openGL.csdn.view.GLImageView;
import com.openGL.csdn.view.LineView;
import com.openGL.csdn.view.OvalView;
import com.openGL.csdn.view.RectView;
import com.openGL.csdn.view.TextView1;
import com.openGL.csdn.view.TriangleView;
import com.openGL.csdn.view.text.AlignTextView;

public class MainActivity01 extends Activity {
    private static int[] SAMPLE_IMAGES = new int[]{
            R.drawable.gallery_photo_1,
            R.drawable.gallery_photo_2,
            R.drawable.gallery_photo_3,
            R.drawable.gallery_photo_4,
            R.drawable.gallery_photo_5,
            R.drawable.gallery_photo_6,
            R.drawable.gallery_photo_7,
            R.drawable.gallery_photo_8
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //Toast.makeText(MainActivity01.this, (String) msg.obj, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        /*CoverFlowOpenGL baseGL=new CoverFlowOpenGL(this);
		//baseGL.setText("我们都是中国人");
		baseGL.setCoverFlowListener(new CoverFlowOpenGL.CoverFlowListener() {
			@Override
			public int getCount(CoverFlowOpenGL view) {
				return SAMPLE_IMAGES.length;
			}

			@Override
			public Bitmap getImage(CoverFlowOpenGL anotherCoverFlow, int position) {
				return BitmapFactory.decodeResource(getResources(), SAMPLE_IMAGES[position]);
			}

			@Override
			public void tileOnTop(CoverFlowOpenGL view, int position) {
				// you can control what will happen when one image is in middle
				mHandler.obtainMessage(0, String.format("Image %d is on top.", position)).sendToTarget();
			}

			@Override
			public void topTileClicked(CoverFlowOpenGL view, int position) {
				// you can control what will happen when the image in middle is clicked
				mHandler.obtainMessage(0, String.format("Image %d is clicked", position)).sendToTarget();
			}
		});

		//mCoverFlow.setSelection(0);
		baseGL.setBackgroundTexture(R.drawable.bg);*/

        //DiceSurfaceView baseGL=new DiceSurfaceView(this);
/*
		final  LuckyPanView baseGL=new LuckyPanView(this);
		baseGL.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!baseGL.isStart())
				{
					//mStartBtn.setImageResource(R.drawable.stop);
					baseGL.luckyStart(1);
				} else
				{
					if (!baseGL.isShouldEnd())

					{
						//mStartBtn.setImageResource(R.drawable.start);
						baseGL.luckyEnd();
					}
				}
			}
		});*/

/*
        CoverFlowOpenGL baseGL = new CoverFlowOpenGL(this);
        baseGL.setCoverFlowListener(new CoverFlowOpenGL.CoverFlowListener() {
            @Override
            public int getCount(CoverFlowOpenGL view) {
                return SAMPLE_IMAGES.length;
            }

            @Override
            public Bitmap getImage(CoverFlowOpenGL anotherCoverFlow, int position) {
                return BitmapFactory.decodeResource(getResources(), SAMPLE_IMAGES[position]);
            }

            @Override
            public void tileOnTop(CoverFlowOpenGL view, int position) {
                // you can control what will happen when one image is in middle
                mHandler.obtainMessage(0, String.format("Image %d is on top.", position)).sendToTarget();
            }

            @Override
            public void topTileClicked(CoverFlowOpenGL view, int position) {
                // you can control what will happen when the image in middle is clicked
                mHandler.obtainMessage(0, String.format("Image %d is clicked", position)).sendToTarget();
            }
        });
        baseGL.setSelection(0);*/
        String s = " 一个朋友跟我说，在感情里，信“总是深情留不住，偏偏套路得人心”的人，认为现在的痴情不值钱，是一种很白痴的表现，“认真了，就等于输了”，有技巧的撩妹子才是恋爱中的精髓，要懂得拿捏的准度，在这过程中，要知道你所“追求”的是什么。“追求”的时候要怎么说甜言蜜语，做事的时候该怎么有男人的霸道，吵架的时候要用什么方式去哄对方。";

        AlignTextView baseGL=new AlignTextView(this);
        baseGL.setText(s);
        setContentView(baseGL);

    }




}
