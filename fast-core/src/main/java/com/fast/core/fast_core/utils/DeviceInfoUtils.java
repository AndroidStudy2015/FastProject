package com.fast.core.fast_core.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.fast.core.fast_core.utils.log.FastLogger;

/**
 * Created by apple on 2017/10/11.
 */

public class DeviceInfoUtils {


    public static void printDeviceInfo(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 屏幕宽度（像素）
        int width = metric.widthPixels;
        // 屏幕高度（像素）
        int height = metric.heightPixels;
        // 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
        // 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL + ",\nSDK版本:"
                + android.os.Build.VERSION.SDK + ",\n系统版本:"
                + android.os.Build.VERSION.RELEASE + "\n屏幕宽度（像素）: " + width + "\n屏幕高度（像素）: " + height + "\n屏幕密度:  " + density + "\n屏幕密度DPI: " + densityDpi;
        FastLogger.e("System INFO", info);
    }
}
