package com.fast.ui.fast_ui.tv.focusview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.fast.core.fast_core.utils.log.FastLogger;


/**
 * Created by apple on 2017/6/13.
 */

public class FocusTextView extends AppCompatTextView implements View.OnFocusChangeListener {

    private int mWidth;
    private int mHeight;

    public FocusTextView(Context context) {
        super(context);
        init();
    }

    public FocusTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setFocusable(true);


        setOnFocusChangeListener(this);
//        setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_framelayout));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        FastLogger.e("tag", "onFinishInflate");

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.bringToFront();

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 1.2f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 1.2f);
            scaleX.setDuration(300);
            scaleY.setDuration(300);
            scaleX.start();
            scaleY.start();
            setTextColor(Color.RED);

        } else {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.2f, 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.2f, 1.0f);
            scaleX.setDuration(300);
            scaleY.setDuration(300);
            scaleX.start();
            scaleY.start();


            setTextColor(Color.BLACK);


        }
    }
}
