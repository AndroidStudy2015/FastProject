package com.fast.ui.fast_ui.tv.focusview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by apple on 2017/6/13.
 */

public class FocusView extends RelativeLayout implements View.OnFocusChangeListener {

    private int mWidth;
    private int mHeight;
    private float mFloat = 1.07f;

    public FocusView(Context context) {
        super(context);
        init();
    }

    public FocusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setFocusable(true);

        setClipChildren(false);
        setClipToPadding(false);
        setOnFocusChangeListener(this);
//        setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_framelayout));
//        setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
//        ViewGroup.LayoutParams layoutParams = getLayoutParams();
//        layoutParams.width= (int) (mWidth*1.2f);
//        layoutParams.height= (int) (mHeight*1.2f);
//        setLayoutParams(layoutParams);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.bringToFront();

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, mFloat);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, mFloat);
            scaleX.setDuration(300);
            scaleY.setDuration(300);
            scaleX.start();
            scaleY.start();

//            setScaleX(1.2f);
//            setScaleY(1.2f);
            getChildAt(1).setVisibility(VISIBLE);
            getChildAt(2).setVisibility(INVISIBLE);
            if (getChildAt(3) instanceof TextView) {
                ((TextView) getChildAt(3)).setTextColor(Color.parseColor("#f0f0f0"));
            }

        } else {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", mFloat, 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", mFloat, 1.0f);
            scaleX.setDuration(300);
            scaleY.setDuration(300);
            scaleX.start();
            scaleY.start();

//            setScaleX(1.0f);
//            setScaleY(1.0f);
            getChildAt(1).setVisibility(INVISIBLE);
            getChildAt(2).setVisibility(VISIBLE);
            if (getChildAt(3) instanceof TextView) {

                ((TextView) getChildAt(3)).setTextColor(Color.parseColor("#55f0f0f0"));
            }


        }
    }
}
