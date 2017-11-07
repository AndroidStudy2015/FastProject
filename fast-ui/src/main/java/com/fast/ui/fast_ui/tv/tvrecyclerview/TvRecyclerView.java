package com.fast.ui.fast_ui.tv.tvrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.fast.core.fast_core.utils.log.FastLogger;

/**
 * Created by apple on 2017/10/1.
 */

public class TvRecyclerView extends RecyclerView {


    private int mSpanCount;
    private int mTotalCount;

    public TvRecyclerView(Context context) {
        super(context);
    }

    public TvRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);

        GridLayoutManager gridLayoutManager = (GridLayoutManager) layout;
        mSpanCount = gridLayoutManager.getSpanCount();


    }

    @Override
    public void setAdapter(Adapter adapter) {
        //获取item的总数
        mTotalCount = adapter.getItemCount();
        super.setAdapter(adapter);


    }

    /**
     * TvRecyclerView平滑地滑动到指定position的item，并且让这个item获取焦点
     * <p>
     * 看似简单地调用了smoothScrollToPosition，实际上如果在TvGridLayoutManager内部不处理的话
     * smoothScrollToPosition只会是滚动到目标位置，不会让该条目获取焦点
     * <p>
     * 方法二：跟下面的scrollToPositionAndRequestFocus一样的思路，先滑到位置，再找到目前现实的首位真实的position
     * 在计算出newPosition，让其获得焦点也是可以的
     *
     * @param position
     */
    public void smoothScrollToPositionAndRequestFocus(int position) {
//        直接调用下面的这句话，会平滑地滑动到指定item，但是该item不会获取焦点
//        为达到这个目的，需要这个TvRecyclerView设置一个TvGridLayoutManager，
//        这个TvGridLayoutManager内部重写GridLinearSmoothScroller方法，得到了获取焦点的目的
        smoothScrollToPosition(position);
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


    public boolean onRightEgde() {
        View focusedChild = getFocusedChild();
        if (focusedChild != null) {
            if ((getChildPosition(focusedChild) + 1) % mSpanCount == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean onLeftEgde() {
        View focusedChild = getFocusedChild();
        if (focusedChild != null) {
            if (getChildPosition(focusedChild) % mSpanCount == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean onBottomEgde() {
        View focusedChild = getFocusedChild();
        if (focusedChild != null) {

            if (getChildPosition(focusedChild) >= mTotalCount - mSpanCount) {
                FastLogger.e("pos", getChildPosition(focusedChild) + "getChildPosition(focusedChild)");
                FastLogger.e("pos", mTotalCount + "gmTotalCount");
                FastLogger.e("pos", mSpanCount + "mSpanCount");
                return true;
            }
        }
        return false;
    }

    public boolean onTopEgde() {
        View focusedChild = getFocusedChild();
        if (focusedChild != null) {
            if (getChildPosition(focusedChild) < mSpanCount) {
                return true;
            }
        }
        return false;
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
