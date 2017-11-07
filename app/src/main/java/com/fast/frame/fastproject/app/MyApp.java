package com.fast.frame.fastproject.app;

import android.app.Application;

import com.fast.core.fast_core.app.Fast;
import com.fast.core.fast_core.interceptors.CacheViolenceInterceptor;
import com.fast.core.fast_core.utils.UrlUtils;

/**
 * Created by apple on 2017/10/21.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 一键配置核心moudle（fast-core）的配置参数
        Fast.init(this)
                .withInterceptor(new CacheViolenceInterceptor())
                .withApiHost(UrlUtils.BASE_URL)
                .withLoaderDelayedTime(500)
                .configure();
    }
}
