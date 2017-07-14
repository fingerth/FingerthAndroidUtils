package com.fingerth.supdialogutils.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class SupDialogStaticUtils {

    public static final String TAG = "SupDialogStaticUtils";

    private static int sysWidth = 0;
    private static int sysHeight = 0;

    /**
     * 获取手机的分比率，高和宽
     */
    private static void getScreen(Activity activity) {
        if (SupDialogStaticUtils.sysWidth <= 0 || SupDialogStaticUtils.sysHeight <= 0) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            SupDialogStaticUtils.sysWidth = dm.widthPixels;
            SupDialogStaticUtils.sysHeight = dm.heightPixels;
        }
    }

    public static int getScreenWidth(Activity activity) {
        getScreen(activity);
        return SupDialogStaticUtils.sysWidth;
    }

    public static int getScreenHeight(Activity activity) {
        getScreen(activity);
        return SupDialogStaticUtils.sysHeight;
    }

    public static int getStatusBarHeight(Context c) {
        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }


    public static int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());

    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
