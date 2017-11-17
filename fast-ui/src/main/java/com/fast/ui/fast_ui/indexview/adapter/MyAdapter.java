package com.fast.ui.fast_ui.indexview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bk.yd.fast_ui.R;
import com.fast.ui.fast_ui.indexview.utils.PinYinUtils;

import java.util.List;

/**
 * Created by apple on 2017/8/8.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LOCATION = 0;
    private static final int TYPE_RE_MEN = 1;
    private static final int TYPE_CITY_LIST = 2;
    Context context;
    List<String> mSortCityList;
    int positionA;

    /**
     *
     * @param context
     * @param mSortCityList 排序后的城市列表
     */
    public MyAdapter(Context context, List<String> mSortCityList, int positionA) {
        this.context = context;
        this.mSortCityList = mSortCityList;
        this.positionA=positionA;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_LOCATION:
                return new LocationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_location, parent, false));
            case TYPE_RE_MEN:
                return new ReMenViewHolder(LayoutInflater.from(context).inflate(R.layout.item_re_men, parent, false));
            case TYPE_CITY_LIST:
                return new CityListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_city, parent, false));
        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {


        if (position == 0) {
            return TYPE_LOCATION;
        } else if (position == 1) {
            return TYPE_RE_MEN;
        } else {
            return TYPE_CITY_LIST;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        int dataPosition = position - positionA;//2是因为多了额外的2个定位、热门
        if (holder instanceof CityListViewHolder) {

            CityListViewHolder cityListViewHolder = (CityListViewHolder) holder;
            String data = mSortCityList.get(dataPosition);
            String firstLetter = PinYinUtils.getPinYinFirstLetter(data);
            cityListViewHolder.title.setText(firstLetter);
            cityListViewHolder.tv.setText(data);


            if (dataPosition> 0) {
                String preData = mSortCityList.get(dataPosition - 1);

                String preFirstLetter = PinYinUtils.getPinYinFirstLetter(preData);


                if (firstLetter.equals(preFirstLetter)) {
                    cityListViewHolder.title.setVisibility(View.GONE);
                } else {
                    cityListViewHolder.title.setVisibility(View.VISIBLE);

                }


            } else {
                cityListViewHolder.title.setVisibility(View.VISIBLE);

            }
        }


    }

    @Override
    public int getItemCount() {
        return mSortCityList.size()+positionA;
    }


    class CityListViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView tv;

        public CityListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            tv = (TextView) itemView.findViewById(R.id.tvv);
        }
    }


    class LocationViewHolder extends RecyclerView.ViewHolder {

        public LocationViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ReMenViewHolder extends RecyclerView.ViewHolder {

        public ReMenViewHolder(View itemView) {
            super(itemView);
        }
    }
}
