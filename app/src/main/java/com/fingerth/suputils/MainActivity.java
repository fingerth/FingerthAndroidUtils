package com.fingerth.suputils;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fingerth.supdialogutils.SYSDiaLogUtils;

public class MainActivity extends AppCompatActivity {

    private ProgressBar horizontal_bar;
    private ProgressBar round_bar;
    private AsyncTaskProgress asyncTaskProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horizontal_bar = (ProgressBar) findViewById(R.id.horizontal_bar);
        round_bar = (ProgressBar) findViewById(R.id.round_bar);
    }

    public void onClick1(View view) {
        SYSDiaLogUtils.showSuccessDialog(this, false);
    }

    public void onClick2(View view) {
        SYSDiaLogUtils.showSuccessDialog(this, "操作成功", "恭喜你，操作成功了！", "OK", false);
    }

    public void onClick3(View view) {
        SYSDiaLogUtils.showInfoDialog(this, "操作提示", "很抱歉，你還不夠硬氣！", "取消", false);
    }

    public void onClick4(View view) {
        SYSDiaLogUtils.showErrorDialog(this, "錯誤警告", "很抱歉，你這次真的是錯了，請重新試試！", "取消", false);
    }

    public void onClick5(View view) {
        SYSDiaLogUtils.showSystemProgressDialog(this, "標題", "", false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "點擊消失", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick6(View view) {
        SYSDiaLogUtils.showProgressDialog(this, SYSDiaLogUtils.SYSDiaLogType.IosType, "加載中...", false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "點擊消失", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick7(View view) {
        SYSDiaLogUtils.showProgressBar(this, "", "", false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (SYSDiaLogUtils.getProgressBar() < 100) {
                    if (asyncTaskProgress != null) {
                        asyncTaskProgress.cancel(true);
                    }
                    SYSDiaLogUtils.showErrorDialog(MainActivity.this, "下載失敗", "由於你取消了下載，導致下載失敗！", "確定", false);
                }
            }
        });
        asyncTaskProgress = new AsyncTaskProgress();
        asyncTaskProgress.execute("");
    }

    public void onClick8(View view) {
        SYSDiaLogUtils.showProgressBar(this, SYSDiaLogUtils.SYSDiaLogType.HorizontalWithNumberProgressBar, "正在加載...");
        asyncTaskProgress = new AsyncTaskProgress();
        asyncTaskProgress.execute("");
    }

    public void onClick9(View view) {
        SYSDiaLogUtils.showProgressBar(this, SYSDiaLogUtils.SYSDiaLogType.RoundWidthNumberProgressBar, "正在加載...");
        asyncTaskProgress = new AsyncTaskProgress();
        asyncTaskProgress.execute("");
    }

    public void onClick10(View view) {
        SYSDiaLogUtils.showConfirmDialog(this, true, SYSDiaLogUtils.SYSConfirmType.Tip, "標題", "我是提示！", new SYSDiaLogUtils.ConfirmDialogListener() {
            @Override
            public void onClickButton(boolean clickLeft, boolean clickRight) {
                if (clickLeft) {
                    Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                } else if (clickRight) {
                    Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private class AsyncTaskProgress extends AsyncTask<String, Integer, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(String... params) {
            for (int i = 0; i < 100; i++) {
                SystemClock.sleep(50);
                publishProgress(i + 1);
            }
            return params;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //horizontal_bar.setProgress(values[0] % 100);
            SYSDiaLogUtils.setProgressBar(values[0] % 100);
            if (values[0] == 100) {
                SYSDiaLogUtils.dismissProgress();
                SYSDiaLogUtils.showSuccessDialog(MainActivity.this, false);
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}
