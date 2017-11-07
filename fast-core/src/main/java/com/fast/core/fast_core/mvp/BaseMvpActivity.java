package com.fast.core.fast_core.mvp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * BaseMvpActivity主要是为了抽取出MvpActivity所共有的操作
 * 例如：
 * 1.onResume时要 调用: presenter.attach((V)this)---绑定
 * 2.onDestroy时要 调用: presenter.dettach()---解绑（防止Activity内存泄露）
 * 3.暴露一个方法初始化：presenter,以便上面绑定、解绑时使用
 * 4.暴露setContentViewResLayoutId()设置布局的方法
 * 5.暴露initView()方法
 * 6.暴露setListener()方法
 *
 * @param <V>
 * @param <P>
 */
public abstract class BaseMvpActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    public P presenter;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        1.设置布局ID
        setContentView(_getLayoutResId());

//      ★2.这里要初始化presenter，否则不能完成View和Presenter之间的绑定
        presenter = _initPresenter();
//        3.开始绑定Presenter
        if (null != presenter) {
            presenter.attach((V) this);
        }

//        4.layout的ID注入（findViewById）
        mUnbinder = ButterKnife.bind(this);
//      ★5.初始化页面的显示内容，必须在presenter.attach((V) this);之后
//      否则这是后在presenter里调用mView都是空，报错
        _onBindView(savedInstanceState);
//        6.设置监听事件
        _setListener();
    }

    /**
     * 初始化页面的显示内容，必须在presenter.attach((V) this);之后
     * 否则这是后在presenter里调用mView都是空，报错
     */
    protected abstract void _onBindView(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        if (null != presenter) {
            presenter.dettach();
        }
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
//        退出时候，做些垃圾回收，虽然不一定执行gc
        System.gc();
        System.runFinalization();
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
    protected abstract int _getLayoutResId();


    /**
     * 设置监听事件
     */
    protected abstract void _setListener();
//***************************************************TV长按方向键Recycleview失去焦点************************************************************

    public static final int EATKEYEVENT = 5;// 是否屏蔽按键的事件，控制按键的频率

    private static final int keyEventTime = 100; //最短的按键事件应该是在100ms
    private static boolean eatKeyEvent = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EATKEYEVENT:
                    eatKeyEvent = false;
                    break;
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            return super.dispatchKeyEvent(event);
        }

//如果按下的不是back和center，那么每隔keyEventTime = 270ms响应一次key事件，
// 主要是为了防止在Recyclerview里长按方向键焦点丢失
        if (eatKeyEvent) {
            return true;
        } else {
//            if (event.getRepeatCount() >= 2) {
                eatKeyEvent = true;
                mHandler.removeMessages(EATKEYEVENT);
                Message msg = mHandler.obtainMessage(EATKEYEVENT);
                mHandler.sendMessageDelayed(msg, keyEventTime);
//            }
        }
        return super.dispatchKeyEvent(event);


    }
//***************************************************TV长按方向键Recycleview失去焦点************************************************************

}
