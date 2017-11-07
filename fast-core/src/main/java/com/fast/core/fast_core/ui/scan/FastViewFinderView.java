package com.fast.core.fast_core.ui.scan;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Created by apple
 */

public class FastViewFinderView extends ViewFinderView {

    public FastViewFinderView(Context context) {
        this(context, null);
    }

    public FastViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        setLaserColor(Color.DKGRAY);//定义扫描激光颜色，但是不知道为什么出不来激光
        setBorderColor(Color.RED);//中间边界四个角的颜色，不需要聚焦到扫描框，也是可以扫描的
        setMaskColor(Color.argb(10,10,10,10));//中间四个角外侧的遮盖色
    }
}
