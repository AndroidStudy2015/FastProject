package com.fast.core.fast_core.net;


import android.os.Environment;

import com.fast.core.fast_core.app.Fast;

import java.io.File;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by apple on 2017/9/17.
 */

public class RestCreator {


    private static final class ParamsHolder {

        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = Fast.getApiHost();


        public static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .build();
    }

    private static final class OKHttpHolder {


        private static final long cacheSize = 1024 * 1024 * 20;// 缓存文件最大限制大小20M
        private static String cacheDirectory = Environment.getExternalStorageDirectory() + "/okttpcaches"; // 设置缓存文件路径
        private static Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //

        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        //        配置文件里的所有拦截器
        private static final ArrayList<Interceptor> INTERCEPTORS = Fast.getInterceptor();

        //          把配置文件里的所有拦截器，全部初始化到okhttp里
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addNetworkInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS) // 设置连接超时时间
                .writeTimeout(8, TimeUnit.SECONDS)// 设置写入超时时间
                .readTimeout(8, TimeUnit.SECONDS)// 设置读取数据超时时间
                .retryOnConnectionFailure(true)// 设置进行连接失败重试
                .cache(cache)// 设置缓存
                .build();
    }

    private static final class RestServiceHolder {

        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
