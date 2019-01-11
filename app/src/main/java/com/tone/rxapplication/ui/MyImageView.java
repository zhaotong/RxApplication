package com.tone.rxapplication.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class MyImageView extends AppCompatImageView {


    private Context context;

    private int borderWidth = 8; // 边框宽度
    private int borderColor = Color.parseColor("#ccffffff"); // 边框颜色

    public static final int[] COLORS = {
            0xff44bb66,
            0xff55ccdd,
            0xffbb7733,
            0xffff6655,
            0xffffbb44,
            0xff44aaff
    };


    private int width;
    private int height;

    private Paint paint;
    private Path path; // 用来裁剪图片的ptah
    private RectF rectF; // 图片占的矩形区域


    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        rectF = new RectF();

        paint = new Paint();
        path = new Path();


        setScaleType(ScaleType.CENTER_CROP);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthM = MeasureSpec.getSize(widthMeasureSpec);
        int heightM = MeasureSpec.getSize(heightMeasureSpec);
        width = height = Math.min(heightM, widthM);
        setMeasuredDimension(width, height);
        rectF.set(0, 0, width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable == null) {

            // 使用图形混合模式来显示指定区域的图片
            canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
            drawText(canvas);

            // 恢复画布
            canvas.restore();
        } else {
            // 使用图形混合模式来显示指定区域的图片
            canvas.saveLayer(rectF, null, Canvas.ALL_SAVE_FLAG);

            super.onDraw(canvas);

            drawCircle(canvas);
            // 恢复画布
            canvas.restore();

            drawBorders(canvas);
        }
    }


    private void drawText(Canvas canvas) {
        float radius = Math.min(width, height) / 2.0f;
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(COLORS[0]);

        canvas.drawCircle(rectF.centerX(), rectF.centerY(), radius, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);


        Paint.FontMetrics metrics = paint.getFontMetrics();
        float txtHeight = metrics.descent - metrics.ascent;

        float dy = rectF.centerY() + txtHeight / 2;

        canvas.drawText(getDrawString("前面已经讲过，一个文字在界面中"), rectF.centerX(), dy, paint);
    }


    private String getDrawString(String txt) {
        if (TextUtils.isEmpty(txt))
            return "";

        int drawWidth = width - 10;
        String s = txt;
//        paint.reset();
        paint.setTextSize(60);
        float txtWidth = paint.measureText(s);
        int index = 0;
        while (drawWidth < txtWidth) {
            s = txt.substring(index, txt.length());
            txtWidth = paint.measureText(s);
            index++;
        }
        return s;
    }


    // 绘制圆形
    private void drawCircle(Canvas canvas) {
        float radius = Math.min(width, height) / 2.0f- borderWidth ;

//        path.reset();
//        path.addCircle(rectF.centerX(), rectF.centerY(), radius / 2, Path.Direction.CCW);


        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        c.drawCircle(rectF.centerX(), rectF.centerY(), radius , paint);


        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        canvas.drawPath(path, paint);
        canvas.drawBitmap(bm,0,0,paint);

    }

    // 绘制边框
    private void drawBorders(Canvas canvas) {
        if (borderWidth > 0) {
            float radius = Math.min(width, height) / 2.0f - borderWidth / 2f;

            paint.reset();
            paint.setAntiAlias(true);
            //setStrokeWidth这个方法，并不是往圆内侧增加圆环宽度的，而是往外侧增加一半，往内侧增加一半
            paint.setStrokeWidth(borderWidth);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setXfermode(null);

            path.reset();
            path.addCircle(rectF.centerX(), rectF.centerY(), radius, Path.Direction.CCW);
            canvas.drawPath(path, paint);
        }
    }

}
