package com.fast.core.fast_core.mvp;

/**
 * BasePresenter主要是为了抽取出Presenter所共有的操作
 * 例如：
 * 1.每个Presenter都需要在与之对应的View显示时（onResume）产生一种联系，即：绑定
 * 2.每个Presenter都需要在与之对应的View在销毁时（onDestroy），解除这一种联系（避免View的内存泄露），即：解绑
 * 3.等等，还可能会有其他的共有操作，都可以抽象到这里BasePresenter
 * @param <V>  因为要传入一个View，让BasePresenter绑定，所以必须传递一个参数
 */
public abstract class BasePresenter<V> {

    public V mView;

    public void attach(V mView){
        this.mView = mView;
    }

    public void dettach(){
     mView = null;
     }



}
