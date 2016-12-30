package com.openGL.csdn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/29.
 */

public class TextView1 extends GLCanvas {
    private String text = "";
    private TextPaint paint = new TextPaint();
    private float textSize = 10.0f;
    private int CurrentTextColor = Color.GRAY;
    private StaticLayout layout = null;
    private int lineCount = 0;

    public TextView1(Context context) {
        super(context);

    }

    public TextView1(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    public void onDrawCanvas(Canvas canvas) {


        String s = " 一个朋友跟我说，在感情里，信“总是深情留不住，偏偏套路得人心”的人，认为现在的痴情不值钱，是一种很白痴的表现，“认真了，就等于输了”，有技巧的撩妹子才是恋爱中的精髓，要懂得拿捏的准度，在这过程中，要知道你所“追求”的是什么。“追求”的时候要怎么说甜言蜜语，做事的时候该怎么有男人的霸道，吵架的时候要用什么方式去哄对方。";
/*
        // 设置字体、字体大小和字体颜色
        String familyName = "楷体";
        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/楷体.ttf");
        //Typeface font = Typeface.create(familyName, Typeface.BOLD);
        paint.setColor(Color.GREEN);
        paint.setTypeface(typeFace);
        paint.setTextSize(20);
        String autoTxt = autoSplitText(s, p);
        // 在Bitmap上绘制文字
        //canvas.drawText(s, 0, 60, paint);
        drawText(canvas,layout);*/
        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/楷体.ttf");
        layout = new StaticLayout(text, paint, 480, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        paint.setColor(Color.GREEN);
        paint.setTextSize(12.0F);
        paint.setAntiAlias(true);

        paint.setTypeface(typeFace);


        // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
        canvas.save();
        canvas.translate(0, 0);//从100，100开始画
       // layout.draw(canvas);

        drawText(canvas,layout);
        lineCount = layout.getLineCount();
        canvas.restore();//别忘了restore*/

    }

    public void drawText(Canvas canvas ,StaticLayout layout){


    }


    public void setText(String text) {

        this.text = text;
        if (this.text != null && !"".equals(this.text)) {
            invalidate();
        }


    }

    public TextPaint getPaint() {
        return paint;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        paint.setTextSize(textSize);
        invalidate();
    }


    public int getCurrentTextColor() {
        return CurrentTextColor;
    }

    public float getTextSize() {
        return textSize;
    }


    public String getText() {
        return text;
    }


    public int getLineCount() {
        return lineCount;
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    private String autoSplitText(String text, Paint tvPaint) {
        final float tvWidth = TextView1.this.getWidth() - TextView1.this.getPaddingLeft() - TextView1.this.getPaddingRight(); //控件可用宽度

        //将原始文本按行拆分
        String[] rawTextLines = text.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!text.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        return sbNewText.toString();
    }
}
