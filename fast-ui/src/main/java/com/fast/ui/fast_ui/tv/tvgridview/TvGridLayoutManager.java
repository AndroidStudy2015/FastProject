package com.fast.ui.fast_ui.tv.tvgridview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.fast.core.fast_core.utils.log.FastLogger;


/**
 * Created by apple on 2017/9/12.
 */

public class TvGridLayoutManager extends GridLayoutManager {
    CurrentPositionCallback mCurrentPositionCallback;

    public void setCurrentPositionCallback(CurrentPositionCallback currentPositionCallback) {
        mCurrentPositionCallback = currentPositionCallback;
    }

    private int mCurrentFocusPosition = 0;
    private TvGridView mTvGridView;
    private int mRecyclerViewHeight;
    private int mRowCount;
    private View upFocusView;
    private View leftFocusView;
    private View downFocusView;
    private View rightFocusView;

    public void setRightFocusView(View rightFocusView) {
        this.rightFocusView = rightFocusView;
    }

    public void setDownFocusView(View downFocusView) {
        this.downFocusView = downFocusView;
    }

    public void setUpFocusView(View upFocusView) {
        this.upFocusView = upFocusView;
    }

    public void setLeftFocusView(View leftFocusView) {
        this.leftFocusView = leftFocusView;
    }

    public TvGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TvGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public TvGridLayoutManager(Context context, int spanCount, int rowCount, int orientation, boolean reverseLayout, TvGridView tvGridView) {
        super(context, spanCount, orientation, reverseLayout);
        this.mTvGridView = tvGridView;
        this.mRowCount = rowCount;
        mRecyclerViewHeight = mTvGridView.getTvRecyclerViewHeight();
    }

    public int getCurrentFoucsPosition() {
        return mCurrentFocusPosition;

    }

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {

        if (getOrientation() == VERTICAL) {
            int count = getItemCount();//获取item的总数
            int fromPos = getPosition(focused);//★★★★★★★★当前焦点的位置 因为这里如果RecycleView设置match_parent会报下面的错误,所以记住如果要设置match_parent，就到代码里设置好了
            // java.lang.ClassCastException: com.example.apple.tvdemo99.view.MyFrameLayout cannot be cast to android.support.v7.widget.RecyclerView★★★★★
//        ★★★
            int lastVisibleItemPos = findLastVisibleItemPosition();//最新的已显示的Item的位置
            int span = getSpanCount();
            int firstVisibleItemPos = findFirstVisibleItemPosition();//最新的已显示的Item的位置
            switch (direction) {//根据按键逻辑控制position
                case View.FOCUS_RIGHT:
                    fromPos++;//如果grid是横向的，这里是fromPos + span，一般不用横向的，所以不处理了
                    break;
                case View.FOCUS_LEFT:
                    fromPos--;//如果grid是横向的，这里是fromPos - span

                    break;
                case View.FOCUS_UP:

                    fromPos = fromPos - span;

                    break;
                case View.FOCUS_DOWN:
                    fromPos = fromPos + span;

                    break;
            }
//       ****************** 边界焦点处理逻辑******************
//        if (fromPos < 0 || fromPos >= count) {
//            //如果下一个位置<0,或者超出item的总数，则返回当前的View，即焦点不动
//            return focused;
//        } //需要注释掉，不然如果这个Recycleview前面有其他要获取焦点的view，你就获取不到了
            //如果下一个位置大于最新的已显示的item，即下一个位置的View没有显示，则滑动到那个位置，让他显示，就可以获取焦点了
//       ****************** 边界焦点处理逻辑******************

            if (fromPos > lastVisibleItemPos) {//当按下方向键时（主要是down键），如果将要达到的位置大于可见目前最后一个的位置
                if (fromPos >= count) {
//                return view;//返回哪一个view就给谁焦点

                } else {//让mRecyclerView向下滑动半个屏幕
                    if (direction == View.FOCUS_DOWN) {
                        mTvGridView.scrollBy(0, mRecyclerViewHeight / mRowCount);//这个值过大（例如划过全部一屏幕，现在是半屏幕）会报异常，参看http://www.cnblogs.com/monodin/p/3675040.html
//                   recyclerview 你滑到了第3栏目，他还是把显示在屏幕上的第一个view的position设为0，你要是用fromPos，就会空指针
//                    int i = fromPos % span+span;
//                    getChildAt(i).requestFocus();
                        FastLogger.e("ccc", "fromPos" + fromPos + "///firstVisibleItemPos" + firstVisibleItemPos + "///lastVisibleItemPos" + lastVisibleItemPos);
                    }
                }
            }


            if (direction == View.FOCUS_UP) {
                if (fromPos < 0) {
                    return upFocusView;
                }
                if (fromPos < firstVisibleItemPos) {//当按下方向键时（主要是up键），如果将要达到的位置小于可见目前最后一个的位置，就向上滑动半屏幕
                    mTvGridView.scrollBy(0, -mRecyclerViewHeight / mRowCount);

                }
            }
            if (mCurrentPositionCallback != null) {
                if (fromPos>count){
                    fromPos-=span;
                }
                mCurrentPositionCallback.getCurrentPosition(fromPos);
            }


        }


        return super.onInterceptFocusSearch(focused, direction);
    }


    public interface CurrentPositionCallback {
        void getCurrentPosition(int mCurrentFocusPosition);
    }
}
