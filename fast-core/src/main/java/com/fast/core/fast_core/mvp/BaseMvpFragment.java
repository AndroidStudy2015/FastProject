package com.fast.core.fast_core.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * BaseMvpActivity主要是为了抽取出MvpActivity所共有的操作
 * 例如：
 * 1.onResume时要 调用: presenter.attach((V)this)---绑定
 * 2.onDestroy时要 调用: presenter.dettach()---解绑（防止Activity内存泄露）
 * 3.暴露一个方法初始化：presenter,以便上面绑定、解绑时使用
 * 4.暴露setContentViewResLayoutId()设置布局的方法
 * 5.暴露_onBindView()方法
 * 6.暴露setListener()方法
 *
 * @param <V>
 * @param <P>
 */
public abstract class BaseMvpFragment<V, P extends BasePresenter<V>> extends Fragment {

    public P presenter;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        1.设置布局ID
        View rootView = null;

        if (_getResId() instanceof Integer) {
            rootView = inflater.inflate((Integer) _getResId(), container, false);
        } else if (_getResId() instanceof View) {
            rootView = (View) _getResId();
        }


//      ★2.这里要初始化presenter，否则不能完成View和Presenter之间的绑定
        presenter = _initPresenter();
//        3.开始绑定Presenter
        if (null != presenter) {
            presenter.attach((V) this);
        }


//      ★4.初始化页面的显示内容，必须在presenter.attach((V) this);之后
//      否则这是后在presenter里调用mView都是空，报错
        if (rootView != null) {
            //5.layout的ID注入（findViewById）
            mUnbinder = ButterKnife.bind(this, rootView);
            _onBindView(savedInstanceState, rootView);
        }
//        6.设置监听事件
        _setListener();

        return rootView;
    }

    /**
     * 初始化页面的显示内容，必须在presenter.attach((V) this);之后
     * 否则这是后在presenter里调用mView都是空，报错
     */
    protected abstract void _onBindView(Bundle savedInstanceState, View rootView);


    @Override
    public void onDestroy() {
        if (null != presenter) {
            presenter.dettach();
        }
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    /**
     * 这里一定要new Presenter（）初始化之
     * 否则不能完成View和Presenter之间的绑定
     *
     * @return
     */
    public abstract P _initPresenter();

    /**
     * 返回布局资源ID
     *
     * @return
     */
    protected abstract Object _getResId();


    /**
     * 设置监听事件
     */
    protected abstract void _setListener();


}
