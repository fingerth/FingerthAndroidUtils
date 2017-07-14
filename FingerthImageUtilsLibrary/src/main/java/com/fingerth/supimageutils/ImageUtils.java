package com.fingerth.supimageutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


/**
 * ======================================================
 * Created by Administrator able_fingerth on 2017/7/13.
 * <p/>
 * 版权所有，违者必究！
 * <详情描述/>
 */
public class ImageUtils {

    public static Drawable getSpecifyTheColorImageDrawable(Context context, int drawableId, String hexStr) {
        Bitmap baseBitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        // 1.获取一个与baseBitmap大小一致的可编辑的空图片
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(), baseBitmap.getHeight(), baseBitmap.getConfig());
        // 2.使用Bitmap对象创建画布Canvas, 然后创建画笔Paint。
        Canvas canvas = new Canvas(afterBitmap);
        Paint paint = new Paint();

        //#B82231
        float progressR = 0xb8;
        float progressG = 0x22;
        float progressB = 0x31;
        if (hexStr.length() == 7) {
            String r = hexStr.substring(1, 3);
            String g = hexStr.substring(3, 5);
            String b = hexStr.substring(5, 7);
            progressR = Integer.valueOf(r, 16);
            progressG = Integer.valueOf(g, 16);
            progressB = Integer.valueOf(b, 16);
        }

        // 根据SeekBar定义RGBA的矩阵, 通过修改矩阵第五列颜色的偏移量改变图片的颜色
        float[] src = new float[]{
                1, 0, 0, 0, progressR,
                0, 1, 0, 0, progressG,
                0, 0, 1, 0, progressB,
                0, 0, 0, 1, 0};

        // 3.定义ColorMatrix，并指定RGBA矩阵
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(src);
        // 4.使用ColorMatrix创建一个ColorMatrixColorFilter对象, 作为画笔的滤镜, 设置Paint的颜色
        paint.setColorFilter(new ColorMatrixColorFilter(src));
        // 5.通过指定了RGBA矩阵的Paint把原图画到空白图片上
        canvas.drawBitmap(baseBitmap, new Matrix(), paint);
        return new BitmapDrawable(afterBitmap);
    }
}
