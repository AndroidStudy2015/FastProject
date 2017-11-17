package com.fast.ui.fast_ui.indexview.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bk.yd.fast_ui.R;
import com.fast.ui.fast_ui.indexview.utils.City;
import com.fast.ui.fast_ui.indexview.utils.ComparatorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by apple on 2017/8/8.
 */

public class IndexView extends FrameLayout {

    private RecyclerView rcv;
    private LinearLayoutManager mManager;
    private RecyclerView.Adapter mMyAdapter;
    private int mIndexBarLetterTextSize = 50;

    public void setAdapter(RecyclerView.Adapter adapter) {
        mMyAdapter = adapter;
        rcv.setAdapter(mMyAdapter);

    }

    private LayoutInflater mInflater;
    private IndexBar mIndexBar;


    private List<City> mOriginalCityList = new ArrayList<>();

    public void setOriginalCityList(List<City> originalCityList) {
        mOriginalCityList = originalCityList;
        initDataList();

    }

    Context context;

    /**
     * 初始化的城市列表
     */
    private List<String> mOriginalCityStrings;
    /**
     * 按照首字母排序后的城市列表
     */
    private List<City> mSortCityList;

    public List<City> getSortCityList() {
        return mSortCityList;
    }

    public void setIndexBarData(String[] firstLetterArrays, int mIndexBarLetterTextSize) {
        this.firstLetterArrays = firstLetterArrays;
        this.mIndexBarLetterTextSize = mIndexBarLetterTextSize;
        initIndexBar(context, firstLetterArrays, mIndexBarLetterTextSize);
    }


    private String[] firstLetterArrays;

    public void setPositionA(int positionA) {
        this.positionA = positionA;
    }

    int positionA;

    private int mIndexBarGravity;
    private int mIndexBarWidth;
    private int mIndexBarHeight;

    public void setIndexBarSizeAndGravityAndMargin(int indexBarWidth, int indexBarHeight, int indexBarGravity, int left, int top, int right, int bottom) {
        mIndexBarWidth = indexBarWidth;
        mIndexBarHeight = indexBarHeight;
        mIndexBarGravity = indexBarGravity;
        LayoutParams layoutParams = new LayoutParams(mIndexBarWidth, mIndexBarHeight, mIndexBarGravity);
        layoutParams.setMargins(left, top, right, bottom);
        mIndexBar.setLayoutParams(layoutParams);
    }

    private FrameLayout mShowTextFrameLayout;
    private TextView mShowText;
    private FrameLayout mShowMovedTextFrameLayout;
    private TextView mMovedShowText;
    private LayoutParams mMovedLayoutParams;
    private int mIndexViewWidth;
    private int mIndexViewHeight;
    private final int CENTER_SHOW_TEXT = 1;
    private final int MOVED_SHOW_TEXT = 2;
    private int STYLE_SHOW_TEXT = 2;
    private int mShowMovedTextFrameLayoutWidth = 150;
    private int mShowMovedTextFrameLayoutHeight = 150;
    private int mShowMovedTextFrameLayoutRightMargin = 300;
    private int mShowTextSize = 30;
    private Random mRandom;
    private Drawable mBackground;
    private String mSortCityListFirstLetterToString;

    public IndexView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public IndexView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);

    }

    private void init(Context context) {
        this.context = context;
//        mInflater = LayoutInflater.from(context);
//        View view = mInflater.inflate(R.layout.index_view, this, true);
//        rcv = (RecyclerView) view.findViewById(R.id.recyclerView);


        initRecyclerview(context);
        if (STYLE_SHOW_TEXT == CENTER_SHOW_TEXT) {
            initShowText(context);
        }
        if (STYLE_SHOW_TEXT == MOVED_SHOW_TEXT) {
            initMovedShowText(context);
        }
    }

    private void initDataList() {

        mSortCityList = ComparatorUtils.sortCityList(mOriginalCityList);
        mSortCityListFirstLetterToString = ComparatorUtils.getSortCityListFirstLetterToString(mSortCityList);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mIndexViewWidth = w;
        mIndexViewHeight = h;
    }

    private void initMovedShowText(Context context) {
        mShowMovedTextFrameLayout = new FrameLayout(context);
        mShowMovedTextFrameLayout.setBackgroundResource(R.drawable.circle);
        mRandom = new Random();
        mBackground = mShowMovedTextFrameLayout.getBackground();

        mMovedShowText = new TextView(context);
        mMovedShowText.setTextSize(mShowTextSize);
        mMovedShowText.setTextColor(Color.WHITE);
        mShowMovedTextFrameLayout.addView(mMovedShowText, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        mMovedLayoutParams = new LayoutParams(mShowMovedTextFrameLayoutWidth, mShowMovedTextFrameLayoutHeight, Gravity.TOP | Gravity.RIGHT);
        mMovedLayoutParams.rightMargin = mShowMovedTextFrameLayoutRightMargin;
        mShowMovedTextFrameLayout.setLayoutParams(mMovedLayoutParams);
        addView(mShowMovedTextFrameLayout);

        mShowMovedTextFrameLayout.setVisibility(GONE);
    }

    private void initShowText(Context context) {
        mShowTextFrameLayout = new FrameLayout(context);
        mShowTextFrameLayout.setBackgroundColor(Color.argb(130, 100, 100, 100));
        mShowText = new TextView(context);
        mShowText.setTextSize(mShowTextSize);
        mShowTextFrameLayout.addView(mShowText, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        LayoutParams layoutParams = new LayoutParams(300, 300, Gravity.CENTER);
        addView(mShowTextFrameLayout, layoutParams);

        mShowTextFrameLayout.setVisibility(GONE);
    }

    private void initRecyclerview(Context context) {
        rcv = new RecyclerView(context);
        addView(rcv);
        mManager = new LinearLayoutManager(context);
        rcv.setLayoutManager(mManager);
    }

    private void initIndexBar(final Context context, final String[] firstLetterArrays, int mIndexBarLetterTextSize) {
        mIndexBar = new IndexBar(context, firstLetterArrays, mIndexBarLetterTextSize);
        mIndexBar.setBackgroundColor(Color.TRANSPARENT);

        mIndexBarWidth = 130;
        mIndexBarHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        mIndexBarGravity = Gravity.RIGHT | Gravity.CENTER;
        LayoutParams layoutParams = new LayoutParams(mIndexBarWidth, mIndexBarHeight, mIndexBarGravity);
        addView(mIndexBar, layoutParams);


        mIndexBar.setOnSelectIndexListener(new IndexBar.OnSelectIndexListener() {
            @Override
            public void onSelectIndex(int position) {
                mIndexBar.setBackgroundColor(Color.argb(120, 100, 100, 100));
                String letter = firstLetterArrays[position];
                if (STYLE_SHOW_TEXT == MOVED_SHOW_TEXT) {
                    mBackground.setColorFilter(Color.argb(255, mRandom.nextInt(255),
                            mRandom.nextInt(255),
                            mRandom.nextInt(255)),
                            PorterDuff.Mode.SRC_IN);//注意一定要用SRC_IN模式，否则的话为矩形
                } else if (STYLE_SHOW_TEXT == CENTER_SHOW_TEXT) {


                    if (letter.length() > 1) {
                        mShowText.setTextSize(mShowTextSize / 2);
                    } else {
                        mShowText.setTextSize(mShowTextSize);
                    }
                    mShowText.setText(letter);
                    mShowTextFrameLayout.setVisibility(VISIBLE);
                }

                moveToLetter1(position);
            }

            @Override
            public void onSelectAllTheTime(int position, float y) {
                String letter = firstLetterArrays[position];

                if (STYLE_SHOW_TEXT == MOVED_SHOW_TEXT) {


                    if (letter.length() > 1) {
                        mMovedShowText.setTextSize(mShowTextSize / 2);
                    } else {
                        mMovedShowText.setTextSize(mShowTextSize);
                    }
                    mMovedShowText.setText(letter);
                    mShowMovedTextFrameLayout.setVisibility(VISIBLE);

                    if (mIndexBarHeight == -1) {//设置为match_parent情况为-1，这里给设置最大尺寸
                        mIndexBarHeight = mIndexViewHeight;
                    }


//                    通用算法
                    float mIndexBarY = mIndexBar.getY();//mIndexBar在整个屏幕中起始点的Y坐标
                    float moveY = y + mIndexBarY - mShowMovedTextFrameLayoutHeight / 2;


//                    边界处，只是字符超出一半
                    if (moveY < mIndexBarY - mShowMovedTextFrameLayoutHeight / 2) {
                        moveY = mIndexBarY - mShowMovedTextFrameLayoutHeight / 2;
                    }

                    if (moveY > mIndexBarY + mIndexBarHeight - mShowMovedTextFrameLayoutHeight / 2) {
                        moveY = mIndexBarY + mIndexBarHeight - mShowMovedTextFrameLayoutHeight / 2;

                    }


//                    边界处，不超出
//                    if (moveY <= mIndexBarY) {
//                        moveY = mIndexBarY;
//                    }
//
//                    if (moveY >= mIndexBarY + mIndexBarHeight - mShowMovedTextFrameLayoutHeight) {
//                        moveY = mIndexBarY + mIndexBarHeight - mShowMovedTextFrameLayoutHeight;
//
//                    }


                    mShowMovedTextFrameLayout.setY(moveY);


                }
            }

            @Override
            public void onAnctionUp() {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (STYLE_SHOW_TEXT == CENTER_SHOW_TEXT) {
                            mShowTextFrameLayout.setVisibility(GONE);
                        }
                        if (STYLE_SHOW_TEXT == MOVED_SHOW_TEXT) {
                            mShowMovedTextFrameLayout.setVisibility(GONE);
                        }
                        mIndexBar.setBackgroundColor(Color.TRANSPARENT);
                    }
                }, 100);
            }
        });


    }


    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager 设置RecyclerView对应的manager
     * @param n       要跳转的位置
     */
    public void moveToPosition(LinearLayoutManager manager, int n) {
        rcv.stopScroll();
        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);
    }

    /**
     * 计算跳转到哪一个位置
     *
     * @param position
     */
    private void moveToLetter1(int position) {
        int index;

        if (position < positionA) {//这是A字母之前的索引
            index = position;
        } else {
            String letter = firstLetterArrays[position];

            index = mSortCityListFirstLetterToString.indexOf(letter);

            if (index == -1) {
                //index == -1表示字符串里不包含这个字母，也就是城市数据里没有以该字母开头的，那么就什么都不做
                return;
            }
            index = index + positionA;

        }

        moveToPosition(mManager, index);
    }


}

/*用法：

一. 在布局文件：
   <com.fast.ui.fast_ui.indexbar.view.IndexView
        android:id="@+id/indexView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
   />

 二. 代码里

 0.指定indexbar要显示的内容（首字母）
 private String[] firstLetterArrays =
            {"定位", "热门",
                    "A", "B", "C", "D", "E", "F", "G",
                    "H", "I", "J", "K", "L", "M", "N",
                    "O", "P", "Q", "R", "S", "T", "U",
                    "V", "W", "X", "Y", "Z",
            "#"};

    IndexView indexView = (IndexView) findViewById(R.id.indexView);

                List<City> mCitys = new ArrayList<>();
        //从网络获取城市数据，然后设置到 List<City> mCitys里面，因为后面设置数据需要 List<City> mCitys
                List<CityBean.ListBean> list = cityBean.getList();
                for (int i = 0; i < list.size(); i++) {
                    String name = list.get(i).getNAME();
                    String id = list.get(i).getId() + "";
                    String longitude = list.get(i).getLongitude() + "";
                    String latitude = list.get(i).getLatitude() + "";
                    mCitys.add(new City(name, id, longitude, latitude));//拼音会根据城市name内部自动生成
                        //这个City类，只有name是必须的，其他的根据情况定，这样做是为了解决城市有id等其他字段的情况
                }
//      1. set初始化城市集合
                mIndexView.setOriginalCityList(mCitys);
//      2.set右侧indexBar显示的内容
                mIndexView.setIndexBarData(firstLetterArrays, 50);
                mIndexView.setIndexBarSizeAndGravityAndMargin(100, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER | Gravity.RIGHT, 0, 0, 0, 0);

//      3.设置A字符在firstLetterArrays中的索引
                mIndexView.setPositionA(2);
//      4.setAdapter,注意一定要传入排序后的城市列表
                mIndexView.setAdapter(new MyAdapter(getContext(), mIndexView.getSortCityList(), 2));
* */