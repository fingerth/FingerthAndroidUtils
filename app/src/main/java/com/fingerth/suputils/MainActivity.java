package com.fingerth.suputils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fingerth.supdialogutils.SYSDiaLogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
