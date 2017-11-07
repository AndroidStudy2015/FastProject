package com.fast.core.fast_core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.fast.core.fast_core.app.Fast;
import com.fast.core.fast_core.utils.storage.FastPreference;

import java.util.Random;


/**
 * Created by a on 2016/11/6.
 */

public class PhoneUtils {
//下面的Fast.getApplicationContext()，其实就是需要传递一个Context，只不过在这个FAST框架里可以通过Fast.getApplicationContext()获取

    public static String getCurTime() {
        return System.currentTimeMillis() + "";
    }


    public static String getMobileType() {
        return "android";
    }


    private static PackageInfo getPackageInfo() {
        try {
            return Fast.getApplicationContext().getPackageManager().getPackageInfo(
                    Fast.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本名称
     */

    public static String getCurVersionName() {
        return getPackageInfo().versionName;
    }

    /**
     * 获取版本号
     */
    public static int getCurVersionCode() {
        return getPackageInfo().versionCode;
    }


    /**
     * 获取6位随机数字
     */
    public static String getRandomNuber() {

        Random rand = new Random();
        int result = rand.nextInt(999999);

        return "000000".substring((result + "").length()) + result;
    }


    /**
     * 方法描述：获取imsi码(获取SIM卡唯一标识)
     *
     * @return imsi码
     */
    public static String getImsi() {
        String imsi = FastPreference.getCustomAppProfile("imsi");

        if (imsi == "") {
            TelephonyManager phone = (TelephonyManager) Fast.getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imsi = phone.getSubscriberId();

            if (imsi != null && imsi != "") {
                FastPreference.addCustomAppProfile("imsi", imsi);

            } else {
                return "";
            }
        }
        return imsi;
    }

    /**
     * 方法描述：获取imei码，（获取手机设备唯一标示，手机序列号）
     * IMEI(International Mobile Equipment Identity)是国际移动设备身份码
     * 机顶盒获得的是"000000000000000"
     * @return imei
     */
    public static String getImei() {
        String imei = FastPreference.getCustomAppProfile("imei");
        if (imei == "") {
            TelephonyManager phone = (TelephonyManager) Fast.getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imei = phone.getDeviceId();

            if (imei != null && imei != "") {
                FastPreference.addCustomAppProfile("imei", imei);
            } else {
                return "";
            }
        }
        return imei;
    }

}
