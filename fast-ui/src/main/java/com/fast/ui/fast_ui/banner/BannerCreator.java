package com.fast.ui.fast_ui.banner;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

/**
 * Created by apple on 2017/9/19.
 */

public class BannerCreator {

    //     https://github.com/youth5201314/banner
    public static void setDefault(Banner banner,
                                  ArrayList<String> imagsUrls,
                                  ArrayList<String> titles,
                                  OnBannerListener clickListener) {


        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imagsUrls);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //显示圆形指示器和标题（水平显示）
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        banner.setOnBannerListener(clickListener);

    }

}
