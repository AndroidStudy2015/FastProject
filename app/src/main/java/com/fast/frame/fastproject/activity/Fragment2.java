package com.fast.frame.fastproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fast.frame.fastproject.R;
import com.fast.ui.fast_ui.indexview.adapter.MyAdapter;
import com.fast.ui.fast_ui.indexview.view.IndexView;

import java.util.Arrays;

/**
 * Created by apple on 2017/10/21.
 */

public class Fragment2 extends Fragment {
    private String[] firstLetterArrays =
            {"定位", "热门",
                    "A", "B", "C", "D", "E", "F", "G",
                    "H", "I", "J", "K", "L", "M", "N",
                    "O", "P", "Q", "R", "S", "T", "U",
                    "V", "W", "X", "Y", "Z",
                    "#"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);

        IndexView indexView = (IndexView) view.findViewById(R.id.indexView);


//      1. set初始化城市集合
        indexView.setOriginalCityStrings(Arrays.asList(getResources().getStringArray(R.array.city_array)));
//      2.set右侧indexBar显示的内容
        indexView.setIndexBarData(firstLetterArrays,50);
        indexView.setIndexBarSizeAndGravityAndMargin(100, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER|Gravity.RIGHT,0,0,0,0);

//      3.设置A字符在firstLetterArrays中的索引
        indexView.setPositionA(2);
//      4.setAdapter,注意一定要传入排序后的城市列表
        indexView.setAdapter(new MyAdapter(getContext(),indexView.getSortCityList(),2));
        return view;
    }
}
