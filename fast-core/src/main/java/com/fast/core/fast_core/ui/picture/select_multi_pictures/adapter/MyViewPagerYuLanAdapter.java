package com.fast.core.fast_core.ui.picture.select_multi_pictures.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bk.ydtv.fast_core.R;
import com.fast.core.fast_core.utils.image.ImageUtils;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by apple on 2017/8/25.
 */

public class MyViewPagerYuLanAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> yuLanLists;

    public void setOnPhotoViewTapListener(OnPhotoViewTapListener onPhotoViewTapListener) {
        this.onPhotoViewTapListener = onPhotoViewTapListener;
    }

    OnPhotoViewTapListener onPhotoViewTapListener;

    public MyViewPagerYuLanAdapter(Context context, ArrayList<String> yuLanLists) {
        this.context = context;
        this.yuLanLists = yuLanLists;
    }

    @Override
    public int getCount() {
        return yuLanLists==null?0:yuLanLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = View
                .inflate(context, R.layout.item_viewpager, null);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                if (onPhotoViewTapListener!=null){
                    onPhotoViewTapListener.onPhotoViewTap();
                }
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });

        ImageUtils.loadImage(context, yuLanLists.get(position), photoView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    public interface OnPhotoViewTapListener{
        void onPhotoViewTap();
    }
}
