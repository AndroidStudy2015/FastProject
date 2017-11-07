package com.fast.core.fast_core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by a on 2016/12/19.
 */

public class CommonUtils {

    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }


    public static void startActivity(Activity activity, Class<?> cls, Pair<View, String>... sharedElements) {
        Intent intent = new Intent(activity, cls);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                            sharedElements)
                            .toBundle());
        } else {
            activity.startActivity(intent);

        }
    }

    public static void startActivity(Activity activity, Intent intent, Pair<View, String>... sharedElements) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                            sharedElements)
                            .toBundle());
        } else {
            activity.startActivity(intent);

        }
    }






    public static void startActivity(Activity activity, Class<?> cls, View view, String shareName) {
        Intent intent = new Intent(activity, cls);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, shareName)
                            .toBundle());
        }
    }

    public static void toast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

    }

    // 将字符串转为时间戳
    public static long getTime(String user_time, String format) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;
    }


}
