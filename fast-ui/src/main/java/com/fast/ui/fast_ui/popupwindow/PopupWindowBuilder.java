package com.fast.ui.fast_ui.popupwindow;

import android.content.Context;
import android.view.View;

/**
 * Created by apple on 2017/9/25.
 */



public class PopupWindowBuilder {
    private Context mContext;
    private View mRootView;
    private Object mContentView;
    private int mContentViewWidth;
    private int mContentViewHeight;
    private boolean mOutsideTouchable;
    private AnimationStyle mAnimationStyle;
    private IBindPopupWindow mIBindPopupWindow;


    public PopupWindowBuilder() {

    }

    public final PopupWindowBuilder with(Context context) {
        this.mContext = context;
        return this;
    }

    public final PopupWindowBuilder rootView(View rootView) {
        this.mRootView = rootView;
        return this;
    }

    public final PopupWindowBuilder contetView(Object contentView) {
        this.mContentView = contentView;
        return this;
    }

    public final PopupWindowBuilder contetViewWidth(int contentViewWidth) {
        this.mContentViewWidth = contentViewWidth;
        return this;
    }


    public final PopupWindowBuilder contetViewHeight(int contentViewHeight) {
        this.mContentViewHeight = contentViewHeight;
        return this;
    }

    public final PopupWindowBuilder outsideTouchable(boolean outsideTouchable) {
        this.mOutsideTouchable = outsideTouchable;
        return this;
    }

    public final PopupWindowBuilder animationStyle(AnimationStyle animationStyle) {
        this.mAnimationStyle = animationStyle;
        return this;
    }

    public final PopupWindowBuilder iBindPopupWindow(IBindPopupWindow iBindPopupWindow) {
        this.mIBindPopupWindow = iBindPopupWindow;
        return this;
    }


    public final PopupWindowEngine build() {
        if (mContext == null) {
            throw new RuntimeException("请指定context");
        }
        if (mRootView == null) {
            throw new RuntimeException("请指定rootView");
        }
        if (mContentView == null) {
            throw new RuntimeException("请指定contentView");
        }
        if (mContentViewWidth == 0) {
            throw new RuntimeException("请指定contentViewWidth");
        }

        if (mContentViewHeight == 0) {
            throw new RuntimeException("请指定contentViewHeight");
        }


        return new PopupWindowEngine(mContext,
                mRootView,
                mContentView,
                mContentViewWidth,
                mContentViewHeight,
                mOutsideTouchable,
                mAnimationStyle,
                mIBindPopupWindow);
    }
}




