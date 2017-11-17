package com.fast.frame.fastproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fast.core.fast_core.net.RestClient;
import com.fast.core.fast_core.net.callback.ISuccess;
import com.fast.core.fast_core.utils.UrlUtils;
import com.fast.frame.fastproject.R;
import com.fast.frame.fastproject.bean.CityBean;
import com.fast.ui.fast_ui.indexview.adapter.MyAdapter;
import com.fast.ui.fast_ui.indexview.utils.City;
import com.fast.ui.fast_ui.indexview.view.IndexView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
    private List<City> mCitys;
    private IndexView mIndexView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);

        mIndexView = (IndexView) view.findViewById(R.id.indexView);

        mCitys = new ArrayList<>();
        RestClient.builder().url(UrlUtils.URL_GET_CITY_LIST).success(new ISuccess() {
            @Override
            public void onSuccess(String response) {

                Gson gson = new Gson();
                CityBean cityBean = gson.fromJson(response, CityBean.class);
                List<CityBean.ListBean> list = cityBean.getList();
                for (int i = 0; i < list.size(); i++) {
                    String name = list.get(i).getNAME();
                    String id = list.get(i).getId() + "";
                    String longitude = list.get(i).getLongitude() + "";
                    String latitude = list.get(i).getLatitude() + "";
                    mCitys.add(new City(name, id, longitude, latitude));

                }

                //      1. set初始化城市集合
//        indexView.setOriginalCityStrings(Arrays.asList(getResources().getStringArray(R.array.city_array)));
                mIndexView.setOriginalCityList(mCitys);
//      2.set右侧indexBar显示的内容
                mIndexView.setIndexBarData(firstLetterArrays, 50);
                mIndexView.setIndexBarSizeAndGravityAndMargin(100, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER | Gravity.RIGHT, 0, 0, 0, 0);

//      3.设置A字符在firstLetterArrays中的索引
                mIndexView.setPositionA(2);
//      4.setAdapter,注意一定要传入排序后的城市列表
                mIndexView.setAdapter(new MyAdapter(getContext(), mIndexView.getSortCityList(), 2));
            }
        }).build().getWithCache();


        return view;
    }


}
