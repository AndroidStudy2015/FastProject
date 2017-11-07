package com.fast.ui.fast_ui.tv.tvgridview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by apple on 2017/9/9.
 */

public class TvGridView extends RecyclerView {
    private boolean flag = false;

    public TvGridView(Context context) {
        super(context);
        init(context);
    }

    public TvGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {

    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int widthMode = View.MeasureSpec.getMode(widthSpec);
        int heightMode = View.MeasureSpec.getMode(heightSpec);
    }

    private int getFirstVisiblePosition() {
        int firstVisiblePos = 0;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                firstVisiblePos = ((LinearLayoutManager) layoutManager)
                        .findFirstVisibleItemPosition();
            }
        }
        return firstVisiblePos;
    }

    public void setWidthAndHeight(int width, int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        flag = true;
    }

    public int getTvRecyclerViewWidth() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        return layoutParams.width;

    }

    public int getTvRecyclerViewHeight() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        return layoutParams.height;

    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (!flag) {
//            其实本质原因是：如果你在xml布局中设置了，match_parent，会导致TvLayoutManager报错（长按方向键时候）
//            所以在这里让你代码里设置，你就必须去通过代码，去找到父布局宽高
//            throw new RuntimeException("你必须通过调用setWidthAndHeight(int width, int height)，设置TvRecyclerView的宽高 ");
        }
        super.setLayoutManager(layout);
    }



    /**
     * 这个方法作用是：突变滚动到某一个位置，并且让该位置的item获得焦点
     * 方法实现的说明：
     * 只是调用scrollToPosition，会滚动到这个位置，但是此时并没有获得到焦点
     * 还需要以当前显示的第一个条目为0点，计算出你想去的那个position，此时不是position了，是一个新计算的newPosition
     * 通过getChildAt(newPosition).requestFocus()得到焦点
     * <p>
     * <p>
     * scrollToPositionAndRequestFocus必须这样实现，因为TvGridLayoutManager只能满足smoothScrollToPositionAndRequestFocus的需求
     *
     * @param position
     */
    public void scrollToPositionAndRequestFocus(int position) {
        scrollToPosition(position);//突变滚到要显示的那一position
        int realFirstVisibleItemPosition = getRealFirstVisibleItemPosition();
        int newPosition = position - realFirstVisibleItemPosition;
        if (getChildAt(newPosition) != null) {
            //这里必须判空，因为上一步的realFirstVisibleItemPosition会执行多次，
            // 前几次的取值是错的，会导致这里为null
            getChildAt(newPosition).requestFocus();//再以当前可见位置第一个为0点计算要取得的焦点的位置
        }

    }

    /**
     * 得到目前第一个显示在屏幕上的条目的实际position（在总排序中的位置）
     *
     * @return
     */

    public int getRealFirstVisibleItemPosition() {
//        1.错误方法：((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
//        由于RecycleView复用的原因，上边得到的position永远是0

//        2.另外一个正确方法思路是，在adapter中设置tag为他的position，即：holder.itemView.setTag(position);
//        然后在这里获取tag，即：int tag = (int) getChildAt(0).getTag();

//        3.现在用的这个方法是，通过getChildAt(0)找到目前显示的第一个条目的view，
//        再通过getChildAdapterPosition（）方法，通过条目view，找到他的真实的position
        return getChildAdapterPosition(getChildAt(0));

    }

    // 返回当前TvRecyclerView中获得焦点的条目的position，
    // 如果当前获取焦点的view不是在TvRecyclerView内部，那么返回-1
    public int getCurrentFocusedChildPosition() {
        View focusedChild = getFocusedChild();
        if (focusedChild != null) {
            return getChildPosition(focusedChild);

        }
        return -1;
    }
}
