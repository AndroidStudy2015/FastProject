package com.fast.core.fast_core.app;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by apple on 2017/9/15.
 */

public final class Fast {

    //     1.init，一般会传递一个context作为初始化，同时把他存在了map里面，以后方便获取
    public static Configurator init(Context context) {

        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT.name(), context.getApplicationContext());

        return Configurator.getInstace();
    }

    public static HashMap<String, Object> getConfigurations() {

        return Configurator.getInstace().getFastConfigs();
    }

    public static Context getApplicationContext(){
        return Configurator.getInstace().getConfiguation(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static String getApiHost(){
        return Configurator.getInstace().getConfiguation(ConfigKeys.API_HOST);
    }

    public static ArrayList<Interceptor> getInterceptor(){
        return Configurator.getInstace().getConfiguation(ConfigKeys.INTERCEPTOR);
    }

    public static int getLoadDelayedTime() {

        return Configurator.getInstace().getConfiguation(ConfigKeys.LOAD_DELAYED_TIME);

    }
}


/*用法示例：
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
}*/