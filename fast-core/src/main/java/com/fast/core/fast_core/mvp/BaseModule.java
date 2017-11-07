package com.fast.core.fast_core.mvp;


/**
 * Created by a on 2016/8/25.
 * 抽象所有Modle的公共方法
 * 例如：
 * 1.请求网络数据
 * 2.请求数据库
 * 3.等等
 * 为什么BaseModel是抽象类呢，
 * 因为作为基类可能有其他具体的方法，所以不能是接口
 */
public abstract class BaseModule {
//    public RetrofitService mRetrofitService;

//    /**
//     * 让所有的Modle继承BaseModle后，都自带retrofitService
//     */
//    public BaseModule() {
//        mRetrofitService = RetrofitServiceConfig.retrofitService;
//    }
//
//    public  <T > Observable<T> callBackOnMainThread(Observable<T> tObservable) {
//        return tObservable
//                .onBackpressureBuffer()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
}
