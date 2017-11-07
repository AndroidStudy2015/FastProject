package com.fast.core.fast_core.utils.timer;

import java.util.TimerTask;

/**
 * Created by apple on 2017/9/23.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerTaskListener mITimerTaskListener = null;

    public BaseTimerTask(ITimerTaskListener timerTaskListener) {
        this.mITimerTaskListener = timerTaskListener;
    }


    @Override
    public void run() {
        if (mITimerTaskListener != null) {
            mITimerTaskListener.onTimer();
        }
    }
}
