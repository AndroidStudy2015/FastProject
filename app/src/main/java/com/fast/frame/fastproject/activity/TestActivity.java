package com.fast.frame.fastproject.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.fast.frame.fastproject.R;
import com.fast.ui.fast_ui.bottom_navigation.BottomNavigationView;
import com.fast.ui.fast_ui.bottom_navigation.BottomTabBean;

import java.util.LinkedHashMap;

/**
 * Created by apple on 2017/10/21.
 */

public class TestActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_Navigation_view);

//        1. 只要准备好底部tab图片、title和对应的Fragment，设置给mBottomNavigationView就ok了，所有的点击事件已经默认设好了
//        2. 目前设置了方法去改变具体底部栏目的图片、文字大小等属性，其实最好用自定义属性（懒得做了），或者可以直接打开bottom_tab去设置

        LinkedHashMap<BottomTabBean, Fragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, "主页0"), new Fragment1());
        items.put(new BottomTabBean(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, "主页1"), new Fragment2());
        items.put(new BottomTabBean(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, "主页2"), new Fragment3());

//        mBottomNavigationView.setIndexFragment(1);
//        mBottomNavigationView.setItemIconWidth(50);
//        mBottomNavigationView.setItemIconHeight(50);
//        mBottomNavigationView.setItemTitleSize(30);
//        mBottomNavigationView.setTitleSelectedColor(Color.BLUE);
//        mBottomNavigationView.setTitleUnselectedColor(Color.RED);
//        mBottomNavigationView.setFlContentWeight(8);
//        mBottomNavigationView.setLlBottomTabWeight(1);

        //Fragment的view加上背景色就没有重影效果了
        mBottomNavigationView.setItemData(items, savedInstanceState);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //重写走生命周期之前，记录当前的position，便于恢复上次的状态
        outState.putInt(BottomNavigationView.LAST_POSITION, mBottomNavigationView.getCurrentPostion());
//        ★★★★★★★★别忘了下下面的super.onSave，否则会报错
        super.onSaveInstanceState(outState);
    }
}
