package com.fast.frame.fastproject.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fast.core.fast_core.bottom.BottomItemActivity;
import com.fast.core.fast_core.mvp.BasePresenter;
import com.fast.core.fast_core.net.RestClient;
import com.fast.core.fast_core.net.callback.ISuccess;
import com.fast.core.fast_core.utils.UrlUtils;
import com.fast.core.fast_core.utils.log.FastLogger;
import com.fast.frame.fastproject.R;

import java.util.WeakHashMap;

import static com.fast.frame.fastproject.activity.FragmentFactory.F1;


public class HomeActivity extends BottomItemActivity<HomeActivity,BasePresenter<HomeActivity>> implements View.OnClickListener{
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private FragmentManager mFragmentManager;
    private Fragment mFragment1;
    private Fragment mFragment2;
    private Fragment mFragment3;

    @Override
    protected void _onBindView(Bundle savedInstanceState) {

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        /*
        * Fragment重叠原因一
        * 1.Activity横竖屏切换(或者后台的Activity由于内存不足,被回收),导致Activity重走生命周期
        * 2.但是FragmentManager会保存之前的Fragment,当页面恢复时,会把这些Fragment都显示出来
        * 3.此时,在Activity的onCreate里面你再次new Fragment时并且add了一次,会导致Fragment的重叠
        * 4.解决方法是第一次时直接new，第二次页面恢复后，要使用mFragmentManager通过Tag来取得，
        * 第一次和第二次的判断标志就是savedInstanceState是否为null（内存回收Activity时，会把数据保存在savedInstanceState里）
        * */


//        ★全局用一个mFragmentManager
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mFragment1 = FragmentFactory.createFragment(F1);
//           ★ 为什么第一个fragment在添加的时候用的是replace而不是add?
//            防止回退到首页的时候显示Activity的空的FrameLayout（这是指有回退栈情况，没回退栈用add也不会出现空白，因为已经直接退出了）（★★★其实最还是，直接在默认第一个加载的Fragment不要加入回退栈就好了）
            selectFragment1(mFragment1);
        } else {
//            先使用mFragmentManager通过Tag来取得，只要他add过，就给他添加了Tag
//            否则直接重写创建一个Fragment的话,会导致重叠
            mFragment1 = mFragmentManager.findFragmentByTag(Fragment1.class.getName());
            if (mFragment1 == null) {
                mFragment1 = FragmentFactory.createFragment(F1);

            }
            mFragment2 = mFragmentManager.findFragmentByTag(Fragment2.class.getName());
            if (mFragment2 == null) {
//                有可能获得为空，因为你还没与把mFragment2添加，还没有给其设置Tag，所以这里为空
                mFragment2 = FragmentFactory.createFragment(FragmentFactory.F2);

            }

            mFragment3 = mFragmentManager.findFragmentByTag(Fragment3.class.getName());
            if (mFragment3 == null) {
                mFragment3 = FragmentFactory.createFragment(FragmentFactory.F3);

            }


            position = savedInstanceState.getInt("position");
            switch (position) {
                case 1:
                    selectFragment(mFragment1);
                    break;

                case 2:
                    selectFragment(mFragment2);
                    break;

                case 3:
                    selectFragment(mFragment3);
                    break;
            }

        }




















        WeakHashMap<String, Object> params = new WeakHashMap<>();

        params.put("para.size", 999);
        params.put("mechanicalCode", "11111111");

        RestClient.builder()
                .params(params)
                .url(UrlUtils.URL_GET_HOT_SHOW)
                .loader(this).success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                FastLogger.json("tagccccc",response);
            }
        }).build().get();

    }

    @Override
    public BasePresenter _initPresenter() {
        return null;
    }

    @Override
    protected int _getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void _setListener() {

    }





    //记录Fragment的位置
    private int position = 1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //记录当前的position
        outState.putInt("position", position);
        Log.e("Ccc", position + "dfssf");
//        ★★★★★★★★别忘了下下面的super.onSave，否则会报错
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                if (mFragment1 == null) {
                    mFragment1 = FragmentFactory.createFragment(F1);
                }
                selectFragment(mFragment1);
//                    记录下当前是哪一个Fragment，
//                    方便重新走Activity生命周期时，获取其死亡时的位置
                position = 1;
                break;

            case R.id.tv2:
                if (mFragment2 == null) {
                    mFragment2 = FragmentFactory.createFragment(FragmentFactory.F2);
                }
                selectFragment(mFragment2);
                position = 2;

                break;

            case R.id.tv3:
                if (mFragment3 == null) {
                    mFragment3 = FragmentFactory.createFragment(FragmentFactory.F3);
                }
                selectFragment(mFragment3);
                position = 3;

                break;
        }
    }

    private void selectFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAll(transaction);

        if (!fragment.isAdded()) {
            transaction.add(R.id.fl, fragment, fragment.getClass().getName());
//            transaction.addToBackStack(null);

        }
        Log.e("qqq",mFragmentManager.getBackStackEntryCount()+"qqq");

        transaction.show(fragment).commit();
        Log.e("qqq",mFragmentManager.getBackStackEntryCount()+"hoe");

    }

    private void selectFragment1(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        transaction.replace(R.id.fl, fragment, fragment.getClass().getName()).commit();



    }

    private void hideAll(FragmentTransaction transaction) {
        if (mFragment1 != null) {
//            必须使用同一个transaction，跟add/show时候一样
//            同一个事物进行了所有的add/hide/show，之后统一commit就行了
//            中间不能commit事物，因为一个事物只能是提交一次，重复提交会报错
//            即：每一次点击，生成一个事物，操作完后，提交这个事物
            transaction.hide(mFragment1);
        }

        if (mFragment2 != null) {
            transaction.hide(mFragment2);
        }

        if (mFragment3 != null) {
            transaction.hide(mFragment3);
        }
    }

}
