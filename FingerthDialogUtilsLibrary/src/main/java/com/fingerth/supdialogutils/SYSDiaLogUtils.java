package com.fingerth.supdialogutils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingerth.supdialogutils.progressbar.horizontal.HorizontalWithNumberProgressBar;
import com.fingerth.supdialogutils.utils.SupDialogStaticUtils;


/**
 * ======================================================
 * Created by Administrator able_fingerth on 2016/10/28.
 * <p/>
 * 版权所有，违者必究！
 * <详情描述/>
 */
public class SYSDiaLogUtils {


    ////1.成功  2.提示   3.錯誤  確認
    private static String loadingStr = "正在加載...";
    private static String successStr = "成功";
    private static String tipStr = "提示";
    private static String errorStr = "錯誤";
    private static String confirmStr = "確認";
    private static String cancelStr = "取消";
    private static ProgressDialog progressDialog;
    private static AlertDialog progressAlertDialog;
    private static HorizontalWithNumberProgressBar horizontalProgressBar;

    //4 取消 確認 對話框
    private static AlertDialog confirmDialog;

    public enum SYSDiaLogType {
        DefaultTpye,//默認的樣式，系統樣式
        IosType,//ios風格的
        HorizontalWithNumberProgressBar,  // 進度條
        RoundWidthNumberProgressBar,     // 圓形進度條
        ;
    }

    public enum SYSConfirmType {
        Success,//成功 綠色
        Tip,//提示  黃黃的
        Warning //警告 紅色
    }


    /**
     * 語言適配
     */
    public static void setLangStr(String loadingStr, String successStr, String tipStr, String errorStr, String confirmStr, String cancelStr) {
        SYSDiaLogUtils.loadingStr = loadingStr;
        SYSDiaLogUtils.successStr = successStr;
        SYSDiaLogUtils.tipStr = tipStr;
        SYSDiaLogUtils.errorStr = errorStr;
        SYSDiaLogUtils.confirmStr = confirmStr;
        SYSDiaLogUtils.cancelStr = cancelStr;
    }

    /**
     * 1.加載對話框部分
     */
    private static ProgressDialog pDialog;
    private static AlertDialog pAlertDialog;

    /**
     * @param context
     * @param canceledOnTouchOutside 遮罩下面控件点击是否可以點擊
     */
    private static void showDefaultProgressDialog(Activity context, SYSDiaLogType type, String title, String msg,
                                                  boolean canceledOnTouchOutside, boolean cancelable, DialogInterface.OnCancelListener listener) {
        if (context == null || context.isFinishing()) {
            return;
        }
        closeKeyboardHidden(context);
        initDialog();

        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        switch (type) {
            case IosType:
                AlertDialog.Builder progressBuilder = new AlertDialog.Builder(context, R.style.AlertDialog_Styles);
                View dialogView = View.inflate(context, R.layout.dialog_progress_view, null);
                LinearLayout dialog_progress_layout = (LinearLayout) dialogView.findViewById(R.id.dialog_progress_layout);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) dialog_progress_layout.getLayoutParams();
                params.width = SupDialogStaticUtils.getScreenWidth(context) * 4 / 9;
                params.height = SupDialogStaticUtils.getScreenWidth(context) * 4 / 9;
                dialog_progress_layout.setLayoutParams(params);
                TextView message_tv = (TextView) dialogView.findViewById(R.id.message_tv);
                if (TextUtils.isEmpty(msg)) {
                    message_tv.setVisibility(View.GONE);
                } else {
                    message_tv.setText(msg);
                }
                progressBuilder.setView(dialogView);
                pAlertDialog = progressBuilder.create();
                pAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
                pAlertDialog.setCancelable(cancelable);
                if (listener != null && cancelable) {
                    pAlertDialog.setOnCancelListener(listener);
                }
                pAlertDialog.show();
                break;
            case DefaultTpye:
            default:
                if (TextUtils.isEmpty(msg)) {
                    msg = loadingStr;
                }
                pDialog = ProgressDialog.show(context, title, msg);
                pDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
                pDialog.setCancelable(cancelable);
                if (listener != null && cancelable) {
                    pDialog.setOnCancelListener(listener);
                }
                break;
        }


    }

    public static void dismissProgress() {
        initDialog();
    }

    public static void showSystemProgressDialog(Activity context, String title, String msg, boolean canceledOnTouchOutside, DialogInterface.OnCancelListener listener) {
        showDefaultProgressDialog(context, SYSDiaLogType.DefaultTpye, title, msg, canceledOnTouchOutside, true, listener);
    }

    public static void showSystemProgressDialog(Activity context, String title, String msg, boolean canceledOnTouchOutside, boolean cancelable) {
        showDefaultProgressDialog(context, SYSDiaLogType.DefaultTpye, title, msg, canceledOnTouchOutside, cancelable, null);
    }

    public static void showSystemProgressDialog(Activity context, String title, String msg) {
        showDefaultProgressDialog(context, SYSDiaLogType.DefaultTpye, title, msg, false, true, null);
    }

    public static void showSystemProgressDialog(Activity context, String msg) {
        showDefaultProgressDialog(context, SYSDiaLogType.DefaultTpye, "", msg, false, true, null);
    }

    public static void showSystemProgressDialog(Activity context) {
        showDefaultProgressDialog(context, SYSDiaLogType.DefaultTpye, "", "", false, true, null);
    }

    public static void showProgressDialog(Activity context, SYSDiaLogType type, String msg, boolean canceledOnTouchOutside, DialogInterface.OnCancelListener listener) {
        showDefaultProgressDialog(context, type, "", msg, canceledOnTouchOutside, true, listener);
    }

    public static void showProgressDialog(Activity context, SYSDiaLogType type, String msg, boolean canceledOnTouchOutside, boolean cancelable) {
        showDefaultProgressDialog(context, type, "", msg, canceledOnTouchOutside, cancelable, null);
    }

    public static void showProgressDialog(Activity context, SYSDiaLogType type, String msg) {
        showDefaultProgressDialog(context, type, "", msg, false, true, null);
    }

    public static void showProgressDialog(Activity context, SYSDiaLogType type) {
        showDefaultProgressDialog(context, type, "", "", false, true, null);
    }


    /**
     * 隐藏软键盘
     **/
    private static void closeKeyboardHidden(Activity context) {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 新的對話框彈出之前，先把舊的清除掉
     */
    private static void initDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
        if (tDialog != null && tDialog.isShowing()) {
            tDialog.dismiss();
            tDialog = null;
        }
        if (pAlertDialog != null && pAlertDialog.isShowing()) {
            pAlertDialog.dismiss();
            pAlertDialog = null;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (progressAlertDialog != null && progressAlertDialog.isShowing()) {
            progressAlertDialog.dismiss();
            progressAlertDialog = null;
        }
        if (confirmDialog != null && confirmDialog.isShowing()) {
            confirmDialog.dismiss();
            confirmDialog = null;
        }
    }

    /**
     * 2.提示對話框部分
     */
    private static AlertDialog tDialog;

    private static void showAlertDialog(Activity context, String title, String msg, String confirmStr, int type, boolean canceledOnTouchOutside) {
        if (context == null || context.isFinishing()) {
            return;
        }
        closeKeyboardHidden(context);
        initDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog_Styles);
        View dialogView = View.inflate(context, R.layout.dialog_alert_view, null);
        LinearLayout dialog_view_layout = (LinearLayout) dialogView.findViewById(R.id.dialog_view_layout);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) dialog_view_layout.getLayoutParams();
        params.width = SupDialogStaticUtils.getScreenWidth(context) * 2 / 3;
        dialog_view_layout.setLayoutParams(params);
        ImageView dialog_icon = (ImageView) dialogView.findViewById(R.id.dialog_icon);

        TextView confirm = (TextView) dialogView.findViewById(R.id.confirm);
        if (TextUtils.isEmpty(confirmStr)) {
            confirmStr = SYSDiaLogUtils.confirmStr;
        }
        confirm.setText(confirmStr);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tDialog != null && tDialog.isShowing()) {
                    tDialog.dismiss();
                }
            }
        });
        //1.成功  2.提示   3.錯誤
        switch (type) {
            case 1:
                if (TextUtils.isEmpty(title)) {
                    title = SYSDiaLogUtils.successStr;
                }
                dialog_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.dialog_success));
                //selector_green
                confirm.setBackgroundResource(R.drawable.selector_green);
                //confirm.setBackground(context.getResources().getDrawable(R.drawable.selector_green));
                break;
            case 2:
                if (TextUtils.isEmpty(title)) {
                    title = SYSDiaLogUtils.tipStr;
                }
                dialog_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.dialog_tip));
                confirm.setBackgroundResource(R.drawable.selector_tip_color);
                //confirm.setBackground(context.getResources().getDrawable(R.drawable.selector_tip_color));
                break;
            case 3:
                if (TextUtils.isEmpty(title)) {
                    title = SYSDiaLogUtils.errorStr;
                }
                dialog_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.dialog_error));
                confirm.setBackgroundResource(R.drawable.selector_error_red);
                //confirm.setBackground(context.getResources().getDrawable(R.drawable.selector_error_red));
                break;
            default:
                break;
        }
        TextView title_tv = (TextView) dialogView.findViewById(R.id.title_tv);
        title_tv.setText(title);
        TextView message_tv = (TextView) dialogView.findViewById(R.id.message_tv);
        if (TextUtils.isEmpty(msg)) {
            message_tv.setVisibility(View.GONE);
        } else {
            message_tv.setText(msg);
        }


        builder.setView(dialogView);
        tDialog = builder.create();
        tDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        tDialog.show();

        //所需要的文件
        //<style name="AlertDialog_Styles" parent="Theme.AppCompat.Light.Dialog" />
    }

    public static void showSuccessDialog(Activity context, String title, String msg, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, title, msg, confirmStr, 1, canceledOnTouchOutside);
    }

    public static void showSuccessDialog(Activity context, String title, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, title, "", confirmStr, 1, canceledOnTouchOutside);
    }

    public static void showSuccessDialog(Activity context, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, "", "", confirmStr, 1, canceledOnTouchOutside);
    }

    public static void showSuccessDialog(Activity context, boolean canceledOnTouchOutside) {
        showAlertDialog(context, "", "", "", 1, canceledOnTouchOutside);
    }

    public static void showInfoDialog(Activity context, String title, String msg, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, title, msg, confirmStr, 2, canceledOnTouchOutside);
    }

    public static void showInfoDialog(Activity context, String title, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, title, "", confirmStr, 2, canceledOnTouchOutside);
    }

    public static void showInfoDialog(Activity context, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, "", "", confirmStr, 2, canceledOnTouchOutside);
    }

    public static void showInfoDialog(Activity context, boolean canceledOnTouchOutside) {
        showAlertDialog(context, "", "", "", 2, canceledOnTouchOutside);
    }

    public static void showErrorDialog(Activity context, String title, String msg, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, title, msg, confirmStr, 3, canceledOnTouchOutside);
    }

    public static void showErrorDialog(Activity context, String title, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, title, "", confirmStr, 3, canceledOnTouchOutside);
    }

    public static void showErrorDialog(Activity context, String confirmStr, boolean canceledOnTouchOutside) {
        showAlertDialog(context, "", "", confirmStr, 3, canceledOnTouchOutside);
    }

    public static void showErrorDialog(Activity context, boolean canceledOnTouchOutside) {
        showAlertDialog(context, "", "", "", 3, canceledOnTouchOutside);
    }


    /**
     * 3.進度條部分
     */
    private static void showDefaultProgressBar(Activity context, SYSDiaLogType type, String title, String msg,
                                               boolean canceledOnTouchOutside, boolean cancelable, DialogInterface.OnCancelListener listener) {
        if (context == null || context.isFinishing()) {
            return;
        }
        closeKeyboardHidden(context);
        initDialog();

        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        switch (type) {
            case HorizontalWithNumberProgressBar:
                AlertDialog.Builder progressBarBuilder = new AlertDialog.Builder(context, R.style.AlertDialog_Styles);
                View dialogView = View.inflate(context, R.layout.dialog_horizontal_progressbar_view, null);
                LinearLayout dialog_progress_layout = (LinearLayout) dialogView.findViewById(R.id.dialog_progress_layout);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) dialog_progress_layout.getLayoutParams();
                params.width = SupDialogStaticUtils.getScreenWidth(context) * 9 / 10;
                params.height = SupDialogStaticUtils.dp2px(context, 120);
                dialog_progress_layout.setLayoutParams(params);
                TextView message_tv = (TextView) dialogView.findViewById(R.id.message_tv);
                TextView title_tv = (TextView) dialogView.findViewById(R.id.title_tv);
                horizontalProgressBar = (HorizontalWithNumberProgressBar) dialogView.findViewById(R.id.horizontal_bar);
                if (TextUtils.isEmpty(title)) {
                    title_tv.setVisibility(View.GONE);
                } else {
                    title_tv.setText(title);
                }
                if (TextUtils.isEmpty(msg)) {
                    message_tv.setVisibility(View.GONE);
                } else {
                    message_tv.setText(msg);
                }
                progressBarBuilder.setView(dialogView);
                progressAlertDialog = progressBarBuilder.create();
                progressAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
                progressAlertDialog.setCancelable(cancelable);
                if (listener != null && cancelable) {
                    progressAlertDialog.setOnCancelListener(listener);
                }
                progressAlertDialog.show();
                break;
            case RoundWidthNumberProgressBar:
                AlertDialog.Builder roundProgressBarBuilder = new AlertDialog.Builder(context, R.style.AlertDialog_Styles);
                View rounddialogView = View.inflate(context, R.layout.dialog_round_progressbar_view, null);
                LinearLayout dialog_round_progress_layout = (LinearLayout) rounddialogView.findViewById(R.id.dialog_progress_layout);
                FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) dialog_round_progress_layout.getLayoutParams();
                params1.width = SupDialogStaticUtils.dp2px(context, 140);
                params1.height = SupDialogStaticUtils.dp2px(context, 140);
                dialog_round_progress_layout.setLayoutParams(params1);
                TextView messageTv = (TextView) rounddialogView.findViewById(R.id.message_tv);
                horizontalProgressBar = (HorizontalWithNumberProgressBar) rounddialogView.findViewById(R.id.round_bar);

                if (TextUtils.isEmpty(msg)) {
                    if (TextUtils.isEmpty(title)) {
                        messageTv.setVisibility(View.GONE);
                    } else {
                        messageTv.setText(title);
                    }
                } else {
                    messageTv.setText(msg);
                }
                roundProgressBarBuilder.setView(rounddialogView);
                progressAlertDialog = roundProgressBarBuilder.create();
                progressAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
                progressAlertDialog.setCancelable(cancelable);
                if (listener != null && cancelable) {
                    progressAlertDialog.setOnCancelListener(listener);
                }
                progressAlertDialog.show();
                break;
            case DefaultTpye:
            default:
                if (TextUtils.isEmpty(msg)) {
                    msg = loadingStr;
                }
                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  //设置样式
                progressDialog.setTitle(title); //设置Title
                progressDialog.setMessage(msg);
                progressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
                progressDialog.setCancelable(cancelable);
                if (listener != null && cancelable) {
                    progressDialog.setOnCancelListener(listener);
                }
                progressDialog.show();
                break;
        }

    }

    public static void showProgressBar(Activity context, String title, String msg, boolean canceledOnTouchOutside, DialogInterface.OnCancelListener listener) {
        showDefaultProgressBar(context, SYSDiaLogType.DefaultTpye, title, msg, canceledOnTouchOutside, true, listener);
    }

    public static void showProgressBar(Activity context, String title, String msg, boolean canceledOnTouchOutside, boolean cancelable) {
        showDefaultProgressBar(context, SYSDiaLogType.DefaultTpye, title, msg, canceledOnTouchOutside, cancelable, null);
    }

    public static void showProgressBar(Activity context, String title, String msg) {
        showDefaultProgressBar(context, SYSDiaLogType.DefaultTpye, title, msg, false, false, null);
    }

    public static void showProgressBar(Activity context, String msg) {
        showDefaultProgressBar(context, SYSDiaLogType.DefaultTpye, "", msg, false, false, null);
    }

    public static void showProgressBar(Activity context) {
        showDefaultProgressBar(context, SYSDiaLogType.DefaultTpye, "", "", false, false, null);
    }

    public static void setProgressBar(int progress) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setProgress(progress);
        }
        //horizontalProgressBar
        if (progressAlertDialog != null && progressAlertDialog.isShowing() && horizontalProgressBar != null) {
            horizontalProgressBar.setProgress(progress);
        }
    }

    public static int getProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            return progressDialog.getProgress();
        }
        //horizontalProgressBar
        if (progressAlertDialog != null && progressAlertDialog.isShowing() && horizontalProgressBar != null) {
            return horizontalProgressBar.getProgress();
        }
        return 0;
    }

    public static void showProgressBar(Activity context, SYSDiaLogType type, String title, String msg, boolean canceledOnTouchOutside, DialogInterface.OnCancelListener listener) {
        showDefaultProgressBar(context, type, title, msg, canceledOnTouchOutside, true, listener);
    }

    public static void showProgressBar(Activity context, SYSDiaLogType type, String title, String msg, boolean canceledOnTouchOutside, boolean cancelable) {
        showDefaultProgressBar(context, type, title, msg, canceledOnTouchOutside, cancelable, null);
    }

    public static void showProgressBar(Activity context, SYSDiaLogType type, String title, String msg) {
        showDefaultProgressBar(context, type, title, msg, false, false, null);
    }

    public static void showProgressBar(Activity context, SYSDiaLogType type, String msg) {
        showDefaultProgressBar(context, type, "", msg, false, false, null);
    }

    public static void showProgressBar(Activity context, SYSDiaLogType type) {
        showDefaultProgressBar(context, type, "", "", false, false, null);
    }

    /**
     * 4.確認 取消  對話框部分
     * public enum SYSConfirmType {
     * Success,//成功 綠色
     * Tip,//提示  黃黃的
     * Warning //警告 紅色
     * }
     */

    private static void showConfirmAlertDialog(Activity context, boolean isShowPic, SYSConfirmType picType,
                                               String title, String msg, String confirmStr, String cancelStr,
                                               boolean canceledOnTouchOutside, boolean cancelable,
                                               final ConfirmDialogListener confirmDialogListener) {
        if (context == null || context.isFinishing()) {
            return;
        }
        closeKeyboardHidden(context);
        initDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog_Styles);
        View dialogView = View.inflate(context, R.layout.dialog_confirm_alert_view, null);
        LinearLayout dialog_view_layout = (LinearLayout) dialogView.findViewById(R.id.dialog_view_layout);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) dialog_view_layout.getLayoutParams();
        params.width = SupDialogStaticUtils.getScreenWidth(context) * 2 / 3;
        dialog_view_layout.setLayoutParams(params);
        ImageView dialog_icon = (ImageView) dialogView.findViewById(R.id.dialog_icon);

        TextView confirm = (TextView) dialogView.findViewById(R.id.confirm);
        TextView cancel = (TextView) dialogView.findViewById(R.id.cancel);
        if (TextUtils.isEmpty(confirmStr)) {
            confirmStr = SYSDiaLogUtils.confirmStr;
        }
        if (TextUtils.isEmpty(cancelStr)) {
            cancelStr = SYSDiaLogUtils.cancelStr;
        }
        confirm.setText(confirmStr);
        cancel.setText(cancelStr);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmDialogListener != null) {
                    confirmDialogListener.onClickButton(false, true);
                }
                if (confirmDialog != null && confirmDialog.isShowing()) {
                    confirmDialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmDialogListener != null) {
                    confirmDialogListener.onClickButton(true, false);
                }
                if (confirmDialog != null && confirmDialog.isShowing()) {
                    confirmDialog.dismiss();
                }
            }
        });
        if (!isShowPic) {
            dialog_icon.setVisibility(View.GONE);
        } else {
            //1.成功  2.提示   3.錯誤
            switch (picType) {
                case Success:
                    if (TextUtils.isEmpty(title) && TextUtils.isEmpty(msg)) {
                        title = SYSDiaLogUtils.successStr;
                    }
                    dialog_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.dialog_success));
                    //selector_green
                    confirm.setBackgroundResource(R.drawable.selector_green);
                    //confirm.setBackground(context.getResources().getDrawable(R.drawable.selector_green));
                    break;
                case Tip:
                    if (TextUtils.isEmpty(title) && TextUtils.isEmpty(msg)) {
                        title = SYSDiaLogUtils.tipStr;
                    }
                    dialog_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.dialog_tip));
                    confirm.setBackgroundResource(R.drawable.selector_tip_color);
                    //confirm.setBackground(context.getResources().getDrawable(R.drawable.selector_tip_color));
                    break;
                case Warning:
                    if (TextUtils.isEmpty(title) && TextUtils.isEmpty(msg)) {
                        title = SYSDiaLogUtils.errorStr;
                    }
                    dialog_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.dialog_error));
                    confirm.setBackgroundResource(R.drawable.selector_error_red);
                    //confirm.setBackground(context.getResources().getDrawable(R.drawable.selector_error_red));
                    break;
                default:
                    dialog_icon.setVisibility(View.GONE);
                    break;
            }
        }
        TextView title_tv = (TextView) dialogView.findViewById(R.id.title_tv);
        if (TextUtils.isEmpty(title)) {
            title_tv.setVisibility(View.GONE);
        } else {
            title_tv.setText(title);
        }

        TextView message_tv = (TextView) dialogView.findViewById(R.id.message_tv);
        if (TextUtils.isEmpty(msg)) {
            message_tv.setVisibility(View.GONE);
        } else {
            message_tv.setText(msg);
        }

        builder.setView(dialogView);
        confirmDialog = builder.create();
        confirmDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        confirmDialog.setCancelable(cancelable);
        confirmDialog.show();

        //所需要的文件
        //<style name="AlertDialog_Styles" parent="Theme.AppCompat.Light.Dialog" />
    }

    public static void showConfirmDialog(Activity context, boolean isShowPic, SYSConfirmType picType,
                                         String title, String msg, String confirmStr, String cancelStr,
                                         boolean canceledOnTouchOutside, boolean cancelable,
                                         final ConfirmDialogListener confirmDialogListener) {
        showConfirmAlertDialog(context, isShowPic, picType, title, msg, confirmStr, cancelStr, canceledOnTouchOutside, cancelable, confirmDialogListener);
    }

    public static void showConfirmDialog(Activity context, boolean isShowPic, SYSConfirmType picType,
                                         String title, String msg,
                                         final ConfirmDialogListener confirmDialogListener) {
        showConfirmAlertDialog(context, isShowPic, picType, title, msg, "", "", false, true, confirmDialogListener);
    }

    public static void showConfirmDialog(Activity context, String title, String msg, String confirmStr, String cancelStr,
                                         boolean canceledOnTouchOutside, boolean cancelable,
                                         final ConfirmDialogListener confirmDialogListener) {
        showConfirmAlertDialog(context, false, SYSConfirmType.Success, title, msg, confirmStr, cancelStr, canceledOnTouchOutside, cancelable, confirmDialogListener);
    }

    public static void showConfirmDialog(Activity context, String title, String msg, String confirmStr, String cancelStr, ConfirmDialogListener confirmDialogListener) {
        showConfirmAlertDialog(context, false, SYSConfirmType.Success, title, msg, confirmStr, cancelStr, false, true, confirmDialogListener);
    }

    public static void showConfirmDialog(Activity context, String title, String msg, ConfirmDialogListener confirmDialogListener) {
        showConfirmAlertDialog(context, false, SYSConfirmType.Success, title, msg, "", "", false, true, confirmDialogListener);
    }

    public static void showConfirmDialog(Activity context, String msg, ConfirmDialogListener confirmDialogListener) {
        showConfirmAlertDialog(context, false, SYSConfirmType.Success, "", msg, "", "", false, true, confirmDialogListener);
    }


    public interface ConfirmDialogListener {
        void onClickButton(boolean clickLeft, boolean clickRight);
    }

//    private static void showCustomizeLoadingProgressDialog(Context context, boolean canceledOnTouchOutside) {
//        ProgressDialog mCustomizeLoadingProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
//        mCustomizeLoadingProgressDialog.setMessage(AppConstants.appStrMap.get(AppConstants.loading));
//        mCustomizeLoadingProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mCustomizeLoadingProgressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
//        mCustomizeLoadingProgressDialog.setCancelable(true);
//        mCustomizeLoadingProgressDialog.show();
//        Point size = new Point();
//        mCustomizeLoadingProgressDialog.getWindow().getWindowManager().getDefaultDisplay().getSize(size);
//        //记得用mProgressDialog来得到这个界面的大小，实际上不加就是得到当前监听器匿名类对象的界面宽度</span>
//
//        int width = size.x;//获取界面的宽度像素
//        int height = size.y;
//        WindowManager.LayoutParams params = mCustomizeLoadingProgressDialog.getWindow().getAttributes(); //一定要用mProgressDialog得到当前界面的参数对象，否则就不是设置ProgressDialog的界面了
//        params.alpha = 1.0f;//设置进度条背景透明度
//        params.height = height / 9;//设置进度条的高度
//        params.gravity = Gravity.BOTTOM;//设置ProgressDialog的重心
//        params.width = 2 * width / 3;//设置进度条的宽度
//        params.dimAmount = 0f;//设置半透明背景的灰度，范围0~1，系统默认值是0.5，1表示背景完全是黑色的,0表示背景不变暗，和原来的灰度一样
//        mCustomizeLoadingProgressDialog.getWindow().setAttributes(params);//把参数设置给进度条，注意，一定要先show出来才可以再设置，不然就没效果了，因为只有当界面显示出来后才可以获得它的屏幕尺寸及参数等一些信息
//    }
}
