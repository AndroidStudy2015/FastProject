package com.fast.core.fast_core.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fast.core.fast_core.utils.dimen.DimenUtil;

/**
 * Created by apple on 2017/8/18.
 */

public class ImageUtils {

    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param placeholderResourceID
     */
    public static void loadImage(Context context, String url, ImageView imageView, int placeholderResourceID) {

        Glide.with(context).load(url).placeholder(placeholderResourceID).into(imageView);

    }
    /**
     * 加载网络图片，无占位图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {

        Glide.with(context).load(url).into(imageView);

    }

    /**
     * 加载本地图片，无占位图
     *
     * @param context
     * @param resourceID
     * @param imageView
     */
    public static void loadImage(Context context, int resourceID, ImageView imageView) {

        Glide.with(context).load(resourceID).into(imageView);

    }


    /**
     * 加载本地图片
     *
     * @param context
     * @param resourceID
     * @param imageView
     */
    public static void loadImage(Context context, int resourceID, ImageView imageView,int placeholderResourceID) {

        Glide.with(context).load(resourceID).placeholder(placeholderResourceID).into(imageView);

    }

    /**
     * 加载网络圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param cornerRadiusDP
     * @param placeholderResourceID
     */
    public static void loadRoundImage(final Context context, String url, final ImageView imageView, final float cornerRadiusDP, int placeholderResourceID) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Glide.with(context).load(url).asBitmap().placeholder(placeholderResourceID).override(layoutParams.width, layoutParams.height).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                //设置圆角角度
//                roundedDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDP, context.getResources().getDisplayMetrics()));
                roundedDrawable.setCornerRadius((float) DimenUtil.dip2px(context, cornerRadiusDP));
                imageView.setImageDrawable(roundedDrawable);
            }
        });
    }

    /**
     * 加载网络圆角图片，无占位图
     *
     * @param context
     * @param url
     * @param imageView
     * @param cornerRadiusDP
     */
    public static void loadRoundImage(final Context context, String url, final ImageView imageView, final float cornerRadiusDP) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Glide.with(context).load(url).asBitmap().override(layoutParams.width, layoutParams.height).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                //设置圆角角度
//                roundedDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDP, context.getResources().getDisplayMetrics()));
                roundedDrawable.setCornerRadius((float) DimenUtil.dip2px(context, cornerRadiusDP));
                imageView.setImageDrawable(roundedDrawable);
            }
        });
    }

    /**
     * 加载本地圆角图片
     *
     * @param context
     * @param resourceID
     * @param imageView
     * @param cornerRadiusDP
     * @param placeholderResourceID
     */
    public static void loadRoundImage(final Context context, int resourceID, final ImageView imageView, final float cornerRadiusDP, int placeholderResourceID) {

        Glide.with(context).load(resourceID).asBitmap().placeholder(placeholderResourceID).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                //设置圆角角度
                roundedDrawable.setCornerRadius((float) DimenUtil.dip2px(context, cornerRadiusDP));
                imageView.setImageDrawable(roundedDrawable);
            }
        });
    }

    /**
     * 加载网络圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param placeholderResourceID
     */

    public static void loadCircleImage(final Context context, String url, final ImageView imageView, int placeholderResourceID) {

        Glide.with(context).load(url).asBitmap().placeholder(placeholderResourceID).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                //设置圆角角度
                roundedDrawable.setCircular(true);
                imageView.setImageDrawable(roundedDrawable);

            }
        });
    }

    /**
     * 加载本地圆形图片
     *
     * @param context
     * @param resourceID
     * @param imageView
     * @param placeholderResourceID
     */

    public static void loadCircleImage(final Context context, int resourceID, final ImageView imageView, int placeholderResourceID) {

        Glide.with(context).load(resourceID).asBitmap().placeholder(placeholderResourceID).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedDrawable.setCircular(true);
                imageView.setImageDrawable(roundedDrawable);

            }
        });
    }

    public static int getScreenWidth(Context context) {

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int height = dm.heightPixels;
        return height;
    }


}



