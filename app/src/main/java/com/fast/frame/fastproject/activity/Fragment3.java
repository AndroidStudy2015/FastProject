package com.fast.frame.fastproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fast.frame.fastproject.R;
import com.fast.ui.fast_ui.popupwindow.AnimationStyle;
import com.fast.ui.fast_ui.popupwindow.PopupWindowEngine;

/**
 * Created by apple on 2017/10/21.
 */

public class Fragment3 extends Fragment {

    private Button btscan;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment3, container, false);
        btscan = (Button) mView.findViewById(R.id.bt);

//        扫描
        btscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //用法示例
                //构建
                PopupWindowEngine popupWindowEngine = PopupWindowEngine
                        .builder()
                        .with(getContext())
                        .outsideTouchable(true)
                        .animationStyle(AnimationStyle.BOTTOM_ENTER_BOTTOM_EXIT)
                        .rootView(mView)

                        .contetView(R.layout.ccc)
                        .contetViewWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                        .contetViewHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .build();
                popupWindowEngine.showInParent(Gravity.CENTER,0,0);//显示popWindow
                popupWindowEngine.dismissPopupWindow();//关闭popWindow

//                CommonUtils.startActivity(getActivity(), SimpleScannerBarActivity.class);
//                CallbackManager.getInstance()
//                        .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
//                            @Override
//                            public void executeCallback(@Nullable String args) {
//                                Toast.makeText(getContext(), "得到的二维码是" + args, Toast.LENGTH_LONG).show();
//                            }
//                        });
            }
        });



        return mView;
    }
}
