package com.fast.ui.fast_ui.tv.tvrecyclerview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by liuyu on 17/2/8.
 * fix issue: RecyclerView 进行item定位的时候,item没有获取焦点
 */
public class TvGridLayoutManager1 extends GridLayoutManager {
    private Context context;
    private float MILLISECONDS_PER_INCH = 25f;
    private TvRecyclerView mTvRecyclerView;

    public TvGridLayoutManager1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TvGridLayoutManager1(Context context, int spanCount, TvRecyclerView tvRecyclerView) {
        super(context, spanCount);
        this.context = context;
        this.mTvRecyclerView = tvRecyclerView;

    }

    public TvGridLayoutManager1(Context context, int spanCount, int orientation, boolean reverseLayout, TvRecyclerView tvRecyclerView) {
        super(context, spanCount, orientation, reverseLayout);
        this.mTvRecyclerView = tvRecyclerView;

    }


    /**
     * Base class which scrolls to selected view in onStop().
     */
    abstract class GridLinearSmoothScroller extends LinearSmoothScroller {


        public GridLinearSmoothScroller(Context context) {
            super(context);
        }

        /**
         * 滑动完成后,让该targetPosition 处的item获取焦点
         */
        @Override
        protected void onStop() {
            super.onStop();
            View targetView = findViewByPosition(getTargetPosition());

            if (targetView != null) {
                targetView.requestFocus();
            }
            super.onStop();
        }

    }

    /**
     * RecyclerView的smoothScrollToPosition方法最终会执行本类（即：GridLayoutManager）的smoothScrollToPosition
     *
     * @param recyclerView
     * @param state
     * @param position
     */
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                       int position) {
        GridLinearSmoothScroller linearSmoothScroller =
                new GridLinearSmoothScroller(recyclerView.getContext()) {
                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return computeVectorForPosition(targetPosition);
                    }

                    //This returns the milliseconds it takes to
                    //scroll one pixel.
                    @Override
                    protected float calculateSpeedPerPixel
                    (DisplayMetrics displayMetrics) {
                        return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                        //返回滑动一个pixel需要多少毫秒
                    }

                    @Override
                    protected int calculateTimeForScrolling(int dx) {
                        return super.calculateTimeForScrolling(dx);
                    }
                };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    public PointF computeVectorForPosition(int targetPosition) {
        return super.computeScrollVectorForPosition(targetPosition);
    }


    /**
     * 设置Recycleview平滑滚动速度为慢速
     */
    public void setSpeedSlow() {
        //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
        //0.3f是自己估摸的一个值，可以根据不同需求自己修改
//        MILLISECONDS_PER_INCH = context.getResources().getDisplayMetrics().density * 0.3f;

//        废弃了上面的算法，因为采用了displayMetrics.densityDpi，而不是displayMetrics.density
        MILLISECONDS_PER_INCH = 30;
    }

    /**
     * 设置Recycleview平滑滚动速度为快速
     */
    public void setSpeedFast() {
//        MILLISECONDS_PER_INCH = context.getResources().getDisplayMetrics().density * 0.03f;
        MILLISECONDS_PER_INCH = 9;

    }

    /**
     * 设置Recycleview平滑滚动速度为极快
     */
    public void setRightNowFast() {
//        MILLISECONDS_PER_INCH = context.getResources().getDisplayMetrics().density * 0.01f;
        MILLISECONDS_PER_INCH = 3;

    }


//下面是为了解决长按方向键，焦点错乱问题

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {
        int fromPos = mTvRecyclerView.getCurrentFocusedChildPosition();
        int span = getSpanCount();

        if (getOrientation() == VERTICAL) {

            switch (direction) {//根据按键逻辑控制position
                case View.FOCUS_RIGHT:
                    fromPos++;
                    break;
                case View.FOCUS_LEFT:
                    fromPos--;
                    break;
                case View.FOCUS_UP:
                    fromPos = fromPos - span;
                    break;
                case View.FOCUS_DOWN:
                    fromPos = fromPos + span;

                    break;
            }
        } else if (getOrientation() == HORIZONTAL) {
            switch (direction) {//根据按键逻辑控制position
                case View.FOCUS_RIGHT:
                    fromPos = fromPos + span;
                    break;
                case View.FOCUS_LEFT:
                    fromPos = fromPos - span;
                    break;
                case View.FOCUS_UP:
                    fromPos--;
                    break;
                case View.FOCUS_DOWN:
                    fromPos++;


            }

            mTvRecyclerView.smoothScrollToPositionAndRequestFocus(fromPos);

        }
        return super.onInterceptFocusSearch(focused, direction);

    }


}