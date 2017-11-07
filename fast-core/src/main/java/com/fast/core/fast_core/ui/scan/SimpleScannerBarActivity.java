package com.fast.core.fast_core.ui.scan;

import android.app.Activity;
import android.os.Bundle;

import com.bk.ydtv.fast_core.R;
import com.fast.core.fast_core.utils.callback.CallbackManager;
import com.fast.core.fast_core.utils.callback.CallbackType;
import com.fast.core.fast_core.utils.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * 二维码扫描github地址：https://github.com/dm77/barcodescanner
 * 当汉字数量少的时候，会编码错我，zbr是日本人写的
 * 迂回的一个解决方案是，让二维码包含比较多的汉字，客户端识别后去截取有用部分
 * 最终的解决方案，我还没搞定
 *
 * 可以自定义扫描Activity的布局
 *
 */
/*用法示例：
    CommonUtils.startActivity(getActivity(),SimpleScannerBarActivity.class);
    CallbackManager.getInstance()
    .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
             @Override
             public void executeCallback(@Nullable String args) {
            Toast.makeText(getContext(), "得到的二维码是" + args, Toast.LENGTH_LONG).show();
      }
    });*/
public class SimpleScannerBarActivity extends Activity implements ZBarScannerView.ResultHandler {
    private ScanView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

//        if (mScannerView == null) {
//            mScannerView = new ScanView(SimpleScannerBarActivity.this);
//        }
//        setContentView(mScannerView);

        setContentView(R.layout.activity_scan);
        mScannerView = (ScanView) findViewById(R.id.scanview);
        mScannerView.setAutoFocus(true);
        mScannerView.setResultHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScannerView != null) {
//            mScannerView. setFlash(true);//设置闪光灯
            mScannerView.startCamera();              // Start camera on resume

        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mScannerView != null) {
            mScannerView.stopCameraPreview();//关闭摄像头预览
            mScannerView.stopCamera();//关闭摄像头
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        final IGlobalCallback<String> callback = CallbackManager
                .getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            finish();
            callback.executeCallback(rawResult.getContents());
        }
    }
}