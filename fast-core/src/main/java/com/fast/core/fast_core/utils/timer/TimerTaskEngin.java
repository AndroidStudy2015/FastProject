package com.fast.core.fast_core.utils.timer;

import java.util.Timer;

/**
 * Created by apple on 2017/9/23.
 */

public class TimerTaskEngin {

    public static TimerTaskEngin getInstance() {
        return new TimerTaskEngin();

    }
    Timer mTimer = new Timer();

    /**
     * 创建并且开启定时任务
     *
     * @param timerTask 定时任务
     * @param delay     延迟开始的时间
     * @param preiod    任务执行的间隔时间
     */
    public final void start(BaseTimerTask timerTask, long delay, long preiod) {
        mTimer.schedule(timerTask, delay, preiod);

    }

    /**
     * 结束定时任务
     */
    public void cancle() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

}
