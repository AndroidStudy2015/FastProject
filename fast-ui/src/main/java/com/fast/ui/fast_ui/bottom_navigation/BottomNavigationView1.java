//package com.fast.ui.fast_ui.bottom_navigation;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.support.annotation.ColorInt;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.widget.LinearLayoutCompat;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bk.yd.fast_ui.R;
//import com.fast.core.fast_core.utils.dimen.DimenUtil;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * Created by apple on 2017/10/21.
// *
// *
// * 这个不要了
// */
//
//public class BottomNavigationView1 extends LinearLayoutCompat implements View.OnClickListener {
//
//    private FrameLayout mFlContent;
//    private LinearLayout mLlBottomTab;
//    private FragmentManager mSupportFragmentManager;
//
//    /**
//     * framelayout的权重
//     */
//    private int mFlContentWeight = 9;
//    /**
//     * 底部导航栏的权重
//     */
//    private int mLlBottomTabWeight = 1;
//    /**
//     * tite选中后的颜色
//     */
//    @ColorInt
//    private int titleUnselectedColor = Color.parseColor("#ff0000");
//    /**
//     * tite未选中后的颜色
//     */
//    @ColorInt
//    private int titleSelectedColor = Color.parseColor("#0ff000");
//    /**
//     * 底部导航栏中的icon图标宽度，单位dp
//     */
//    private int itemIconWidth = 0;
//    /**
//     * 底部导航栏中的icon图标高度，单位dp
//     */
//    private int itemIconHeight = 0;
//    /**
//     * 底部导航栏中的title文字size，单位sp
//     */
//    private float itemTitleSize = 0;
//    /**
//     * 默认选中item位置
//     */
//    private int mIndexFragment = 0;
//
//    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
//    private final ArrayList<Fragment> ITEM_FRAGMENT = new ArrayList<>();
//
//    private void checkIsSetItemdate() {
//        if (isSetItemData) {
//            throw new RuntimeException("此方法必须在setItemData之前调用才生效");
//        }
//    }
//
//    // 所有的set方法放在setItemData之前调用才有效
//    public void setItemIconWidth(int itemIconWidth) {
//        checkIsSetItemdate();
//        this.itemIconWidth = itemIconWidth;
//
//    }
//
//
//    public void setItemIconHeight(int itemIconHeight) {
//        checkIsSetItemdate();
//
//        this.itemIconHeight = itemIconHeight;
//    }
//
//    public void setItemTitleSize(float itemTitleSize) {
//        checkIsSetItemdate();
//
//        this.itemTitleSize = itemTitleSize;
//    }
//
//    public void setTitleUnselectedColor(int titleUnselectedColor) {
//        checkIsSetItemdate();
//
//        this.titleUnselectedColor = titleUnselectedColor;
//    }
//
//    public void setTitleSelectedColor(int titleSelectedColor) {
//        checkIsSetItemdate();
//
//        this.titleSelectedColor = titleSelectedColor;
//    }
//
//    public void setIndexFragment(int indexFragment) {
//        checkIsSetItemdate();
//
//        mIndexFragment = indexFragment;
//    }
//
//
//    public void setFlContentWeight(int flContentWeight) {
//        mFlContentWeight = flContentWeight;
//        LinearLayout.LayoutParams mFlContentlayoutParams = (LinearLayout.LayoutParams) mFlContent.getLayoutParams();
//        mFlContentlayoutParams.weight = mFlContentWeight;
//        mFlContent.setLayoutParams(mFlContentlayoutParams);
//    }
//
//    public void setLlBottomTabWeight(int llBottomTabWeight) {
//        mLlBottomTabWeight = llBottomTabWeight;
//        LinearLayout.LayoutParams mLlBottomTablayoutParams = (LinearLayout.LayoutParams) mLlBottomTab.getLayoutParams();
//        mLlBottomTablayoutParams.weight = mLlBottomTabWeight;
//        mLlBottomTab.setLayoutParams(mLlBottomTablayoutParams);
//    }
//
//
//    public BottomNavigationView1(Context context) {
//        super(context);
//        init(context);
//    }
//
//    public BottomNavigationView1(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//
//    private void init(Context context) {
//        FragmentActivity activity = (FragmentActivity) context;
//        mSupportFragmentManager = activity.getSupportFragmentManager();
//        View view = inflate(context, R.layout.bottom_navigation, this);
//        mFlContent = (FrameLayout) view.findViewById(R.id.fl_content);
//        mLlBottomTab = (LinearLayout) view.findViewById(R.id.ll_bottom_tab);
//
//    }
//
//    private boolean isSetItemData = false;
//
//    public void setItemData(LinkedHashMap<BottomTabBean, Fragment> ITEMS) {
//        if (ITEMS == null || ITEMS.size() == 0) {
//
//            throw new RuntimeException("必须设置数据才有效果哟 LinkedHashMap<BottomTabBean, Fragment> ");
//        }
//        isSetItemData = true;
////        取出来对应的BottomTabBean（图片、文字）和Fragment的组合
//        for (Map.Entry<BottomTabBean, Fragment> item : ITEMS.entrySet()) {
//            final BottomTabBean key = item.getKey();
//            final Fragment value = item.getValue();
//            TAB_BEANS.add(key);
//            ITEM_FRAGMENT.add(value);
//        }
//
////          用取出来对应的BottomTabBean座位数据源，填充底部tab的内容
//        final int size = ITEMS.size();
//        for (int i = 0; i < size; i++) {
////            1.先把添加一个view到底部栏目
//            LayoutInflater.from(getContext()).inflate(R.layout.bottom_tab, mLlBottomTab);
////            2.拿到第一个tab的viewGroupo，这里必须为RelativeLayout，因为我不想做判断了
//            final RelativeLayout bottomTab = (RelativeLayout) mLlBottomTab.getChildAt(i);
////            3.设置每个item的点击事件
//            bottomTab.setTag(i);
//            bottomTab.setOnClickListener(this);
////            4.取出一个tab的内部的view，这里必须第一个是imagview，第二个是textview，因为写死了
//            final ImageView itemIcon = (ImageView) bottomTab.getChildAt(0);
//
//            if (itemIconWidth != 0 && itemIconHeight != 0) {
//                ViewGroup.LayoutParams itemIconLayoutParams = itemIcon.getLayoutParams();
//                itemIconLayoutParams.width = DimenUtil.dip2px(getContext(), itemIconWidth);
//                itemIconLayoutParams.height = DimenUtil.dip2px(getContext(), itemIconHeight);
//                itemIcon.setLayoutParams(itemIconLayoutParams);
//            }
//            final TextView itemTitle = (TextView) bottomTab.getChildAt(1);
//            if (itemTitleSize != 0) {
//                itemTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, itemTitleSize);
//            }
////            5.从BottomTabBean里取出对应的数据源
//            final BottomTabBean bean = TAB_BEANS.get(i);
//
//
////            6.开始初始化数据
//            if (i == mIndexFragment) {
////                默认选中设定的那个Fragment
//                selectFragment(ITEM_FRAGMENT.get(i),i);
//                itemIcon.setImageResource(bean.getSelectedIconResId());
//                itemTitle.setText(bean.getTitle());
//                itemTitle.setTextColor(titleSelectedColor);
//            } else {
//                itemIcon.setImageResource(bean.getUnselectedIconResId());
//                itemTitle.setText(bean.getTitle());
//                itemTitle.setTextColor(titleUnselectedColor);
//            }
//
//
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        final int tag = (int) v.getTag();
//        resetColor();
//        final RelativeLayout item = (RelativeLayout) v;
//        final ImageView itemIcon = (ImageView) item.getChildAt(0);
//        itemIcon.setImageResource(TAB_BEANS.get(tag).getSelectedIconResId());
//        final TextView itemTitle = (TextView) item.getChildAt(1);
//        itemTitle.setTextColor(titleSelectedColor);
//
//        selectFragment(ITEM_FRAGMENT.get(tag), tag);
//
//
//    }
//
//
//    private void resetColor() {
//
//        final int count = mLlBottomTab.getChildCount();
//        for (int i = 0; i < count; i++) {
//            final BottomTabBean bean = TAB_BEANS.get(i);
//            final RelativeLayout item = (RelativeLayout) mLlBottomTab.getChildAt(i);
//            final ImageView itemIcon = (ImageView) item.getChildAt(0);
//            itemIcon.setImageResource(bean.getUnselectedIconResId());
//            final TextView itemTitle = (TextView) item.getChildAt(1);
//            itemTitle.setTextColor(titleUnselectedColor);
//        }
//    }
//
//
//    private void selectFragment(Fragment fragment, int i) {
//        FragmentTransaction transaction = mSupportFragmentManager.beginTransaction();
//        hideAll(transaction);
//
//        if (!fragment.isAdded()) {
//            transaction.add(R.id.fl_content, fragment, i+"");
//
//        }
//
//        transaction.show(fragment).commit();
//
//    }
//
//
//    private void hideAll(FragmentTransaction transaction) {
//        for (int i = 0; i < ITEM_FRAGMENT.size(); i++) {
//            if (ITEM_FRAGMENT.get(i) != null) {
////            必须使用同一个transaction，跟add/show时候一样
////            同一个事物进行了所有的add/hide/show，之后统一commit就行了
////            中间不能commit事物，因为一个事物只能是提交一次，重复提交会报错
////            即：每一次点击，生成一个事物，操作完后，提交这个事物
//                transaction.hide(ITEM_FRAGMENT.get(i));
//            }
//
//        }
//
//    }
//
//
//}
