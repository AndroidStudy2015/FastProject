package com.fast.core.fast_core.bottom;

import android.widget.Toast;

import com.bk.ydtv.fast_core.R;
import com.fast.core.fast_core.app.Fast;
import com.fast.core.fast_core.mvp.BaseMvpActivity;
import com.fast.core.fast_core.mvp.BasePresenter;

/**
 * Created by apple
 *
 * 这个bottom貌似没用了，直接使用 BottomNavigationView
 */

public abstract class BottomItemActivity<V, P extends BasePresenter<V>>  extends BaseMvpActivity {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(this, "双击退出" + Fast.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
    }


}
