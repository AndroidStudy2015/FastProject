package com.fast.frame.fastproject.activity;

import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by a on 2017/4/5.
 */
public class FragmentFactory {
    public final static int F1 = 1;
    public final static int F2 = 2;
    public final static int F3 = 3;

    private static HashMap<Integer, Fragment> mFragments = new HashMap<>();


    public static Fragment createFragment(int fragmentName) {
        // 从缓存中取出
        Fragment fragment = mFragments.get(fragmentName);
        if (fragment == null) {
            switch (fragmentName) {

                case F1:
                    fragment = new Fragment1();
                    break;

                case F2:
                    fragment = new Fragment2();

                    break;

                case F3:
                    fragment = new Fragment3();

                    break;
            }
            // 把frament加入到缓存中
            mFragments.put(fragmentName,fragment);
        }

        return fragment;
    }
}