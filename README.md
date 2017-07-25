## 使用方法
- Step 1. Add the JitPack repository to your build file
- Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
- Step 2. Add the dependency
```
dependencies {
	        compile 'com.github.fingerth:FingerthAndroidUtils:1.0.3'
	}
```
	

## 使用 ##
- 代码1
 
```
SYSDiaLogUtils.showSuccessDialog(this, false);
```

- 效果图1 


![效果图](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_01.jpg)


----------


- 代码2

```
SYSDiaLogUtils.showSuccessDialog(this, "操作成功", "恭喜你，操作成功了！", "OK", false);
```

- 效果图2

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_02.jpg)

----------
- 代码3

```
 SYSDiaLogUtils.showInfoDialog(this, "操作提示", "很抱歉，你還不夠硬氣！", "取消", false);
```

- 效果图3

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_03.jpg)

----------
- 代码4

```
 SYSDiaLogUtils.showErrorDialog(this, "錯誤警告", "很抱歉，你這次真的是錯了，請重新試試！", "取消", false);
```

- 效果图4

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_04.jpg)

----------
- 代码5

```
 SYSDiaLogUtils.showSystemProgressDialog(this, "標題", "", false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "點擊消失", Toast.LENGTH_SHORT).show();
            }
        });
```

- 效果图5

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_05.jpg)

----------
- 代码6

```
SYSDiaLogUtils.showProgressDialog(this, SYSDiaLogUtils.SYSDiaLogType.IosType, "加載中...", false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "點擊消失", Toast.LENGTH_SHORT).show();
            }
        });
```

- 效果图6

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_06.jpg)

----------
- 代码7

```
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
```

- 效果图7

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_07.jpg)

----------
- 代码8

```
SYSDiaLogUtils.showProgressBar(this, SYSDiaLogUtils.SYSDiaLogType.HorizontalWithNumberProgressBar, "正在加載...");
        asyncTaskProgress = new AsyncTaskProgress();
        asyncTaskProgress.execute("");
```

- 效果图8

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_08.jpg)

----------
- 代码9

```
 SYSDiaLogUtils.showProgressBar(this, SYSDiaLogUtils.SYSDiaLogType.RoundWidthNumberProgressBar, "正在加載...");
        asyncTaskProgress = new AsyncTaskProgress();
        asyncTaskProgress.execute("");
```

- 效果图9

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_09.jpg)

----------
- 代码10

```
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
```

- 效果图10

![这里写图片描述](https://github.com/fingerth/FingerthAndroidUtils/blob/master/pic/pro_10.jpg)


----------
- AsyncTaskProgress 的代码

```
private AsyncTaskProgress asyncTaskProgress;

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
```
## 四、小结一下 ##
- 对话框，我一般都用系统提供的了，自定义bug太多，没有Google提供的稳定。
- 其实我还写了很多方法，代码很简单，入门级。
