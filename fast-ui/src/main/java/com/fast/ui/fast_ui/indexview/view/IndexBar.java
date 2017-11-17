package com.fast.ui.fast_ui.indexview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by apple on 2017/8/8.
 */

public class IndexBar extends View {

    private Paint mPaint;
    /**
     * 平均每个字母占据的高度，这个平均高度不一定等于mTextSize，也可以是mTextSize+上下两个间隙
     */
    private float mPerHeight;
    /**
     * 绘制的字母的高度，默认为平均每个字母占据的高度mPerHeight
     */
    private float mTextSize;
    /**
     * IndexBar控件宽度
     */
    private int mWidth;
    /**
     * IndexBar控件高度
     */
    private int mHeight;
    /**
     * 要绘制的首字母的纵坐标位置
     */
    private float mFirstLetterY;


    private String[] firstLetterArrays;
    private float mTextSizeScale = 0.7f;
    //

    public void setOnSelectIndexListener(OnSelectIndexListener onSelectIndexListener) {
        mOnSelectIndexListener = onSelectIndexListener;
    }

    private OnSelectIndexListener mOnSelectIndexListener;

    public IndexBar(Context context) {
        super(context);

        init(context);
    }

    public IndexBar(Context context, String[] firstLetterArrays,int mTextSize) {
        this(context);
        this.firstLetterArrays = firstLetterArrays;
        this.mTextSize=mTextSize;

    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setAlpha(100);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mPerHeight = mHeight * 1.0f / firstLetterArrays.length;
//        mTextSize = mPerHeight * mTextSizeScale;
        mPaint.setTextSize(mTextSize);

    }


    @Override
    protected void onDraw(Canvas canvas) {
//        为什么*0.9，这与drawText方法中高度基线有关系，忘了那几个基线的关系了，所以*0.9意思一下
        mFirstLetterY = (mPerHeight / 2 + mTextSize / 2) * 0.9f;//必须在这里重置一次，因为失去焦点后，再次显示会回调onDraw，而此时的mInitHeignt已经很大了


//        下面是绘制，首字母，字母A之前的文字需要单独处理，字母A~Z循环处理，字母Z之后的也要单独处理
        for (int i = 0; i < firstLetterArrays.length; i++) {
            canvas.drawText(firstLetterArrays[i], mWidth / 2, mFirstLetterY, mPaint);
            mFirstLetterY = mFirstLetterY + mPerHeight;
        }
    }

    int shang;//商
    int lastShang = -100;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mPaint.setAlpha(255);

//              用触摸点的Y坐标除以每个字母的高度，得到的商，用以判断手指触摸的是哪个字母
                shang = (int) (event.getY() / mPerHeight);
                if (shang >= firstLetterArrays.length) {//为了防止IndexView角标越界，因为最后的一个字母后面还有空间可以触摸（这个空间是由于取整数，小数点累计造成的），导致shang=mcount，从而角标越界
                    shang = firstLetterArrays.length - 1;
                }
                if (shang < 0) {
                    shang = 0;
                }


                mOnSelectIndexListener.onSelectIndex(shang);

                mOnSelectIndexListener.onSelectAllTheTime(shang, event.getY());

                lastShang = shang;
                break;

            case MotionEvent.ACTION_MOVE:
                mPaint.setAlpha(255);

                shang = (int) (event.getY() / mPerHeight);
                if (shang >= firstLetterArrays.length) {//为了防止IndexView角标越界
                    shang = firstLetterArrays.length - 1;
                }
                if (shang < 0) {
                    shang = 0;
                }

//                如果商和上次的商是一样的，证明你一直在一个字母的高度范围内move，就不要触发回调了
                if (lastShang != shang) {
                    mOnSelectIndexListener.onSelectIndex(shang);
                }
                mOnSelectIndexListener.onSelectAllTheTime(shang, event.getY());
                lastShang = shang;


                break;

            case MotionEvent.ACTION_UP:
                mPaint.setAlpha(100);


                mOnSelectIndexListener.onAnctionUp();
                break;
        }

        return true;//只有down事件返回了true，接下来的move、up才能响应事件
    }

    interface OnSelectIndexListener {
        /**
         * 当手指选中1个字母时，在一个字母的区间范围内，只调用1次，提高效率
         * （例如：文本内容、文本圈颜色在自己范围内不要变化，对应的列表moveToPosition方法只调用一次）
         *
         * @param position
         */
        void onSelectIndex(int position);

        /**
         * 当手指触摸indexBar时，一直不停地回调此方法，主要是为了让指示文本圈随着手指一直移动
         *
         * @param position
         * @param y
         */
        void onSelectAllTheTime(int position, float y);

        /**
         * 当手指松开时，回调此方法，用于隐藏指示文本，indexBar回复透明状态
         */
        void onAnctionUp();
    }
}
