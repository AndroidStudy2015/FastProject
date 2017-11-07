package com.fast.core.fast_core.interceptors;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by apple on 2017/10/27.
 * 暴力缓存拦截器
 * 无视服务器端返回的响应体的响应头，客户端强行修改，设置其缓存为最大60秒
 * 不建议这么做，
 *
 * 设置缓存方式有三个：
 * 1. 服务器端控制缓存策略，最好的情况，客户端只需要在okhttp里配置缓存地址就行了
 * 2. 客户端通过在请求头里配置缓存策略，服务器端拿到请求头后，将这个请求头配置到，对应的响应体的响应头里面
 * 3. 客户端说了算，为了方便自己灵活设置缓存策略（不同的url请求采取不同的策略），先配置请求头，然后在这里拿到注解（retrofit）上的请求头
 *    客户端通过拦截器得到原始的响应体后，直接修改响应头缓存为注解上配置的请求头
 *
 *    详情请看：http://www.jianshu.com/p/9c3b4ea108a7
 */

public class CacheViolenceInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
//        得到请求
        Request request = chain.request();
//        得到请求头名为"Cache-Control"的值
//        String cacheValue = request.header("Cache-Control");
        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
        String cacheValue = request.cacheControl().toString();
        Log.e("text",cacheValue);
//        执行请求，得到原生的响应体
        Response originResponse = chain.proceed(request);
        //设置缓存策略为你在请求头上设置的内容，例如60秒，并移除了pragma消息头，移除它的原因是因为pragma也是控制缓存的一个消息头属性
        return originResponse.newBuilder().removeHeader("pragma")
                .header("Cache-Control", cacheValue).build();


//**********************************************************************************************************************************

//        //设置缓存时间为60秒，并移除了pragma消息头，移除它的原因是因为pragma也是控制缓存的一个消息头属性
//        这种方式的话，所有的请求都会带上同样的缓存策略，不提倡
//        return originResponse.newBuilder().removeHeader("pragma")
//                .header("Cache-Control", "max-age=60").build();
    }
}
