package com.fast.core.fast_core.utils.timer;

/**
 * Created by apple on 2017/9/23.
 */

public interface ITimerTaskListener {
    /**
     * 具体要执行的定时任务
     * 此方法在子线程中
     * 变化UI的话，请切换到主线程
     */
    void onTimer();
}
