package com.fast.ui.fast_ui.bottom_navigation;

import android.support.annotation.DrawableRes;

/**
 * Created by apple
 */

public final class BottomTabBean {
    @DrawableRes
    private final int SELECTED_ICON_RES_ID;
    @DrawableRes
    private final int UNSELECTED_ICON_RES_ID;
    private final String TITLE;

    public int getSelectedIconResId() {
        return SELECTED_ICON_RES_ID;
    }

    public int getUnselectedIconResId() {
        return UNSELECTED_ICON_RES_ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public BottomTabBean(int SELECTED_ICON_RES_ID, int UNSELECTED_ICON_RES_ID, String TITLE) {
        this.SELECTED_ICON_RES_ID = SELECTED_ICON_RES_ID;
        this.UNSELECTED_ICON_RES_ID = UNSELECTED_ICON_RES_ID;
        this.TITLE = TITLE;
    }
}
