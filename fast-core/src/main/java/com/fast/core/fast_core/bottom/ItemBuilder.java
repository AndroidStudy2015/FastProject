package com.fast.core.fast_core.bottom;

import java.util.LinkedHashMap;

/**
 * Created by apple
 */

public final class ItemBuilder {

    private final LinkedHashMap<BottomTabBean, BottomItemActivity> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bean, BottomItemActivity delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemActivity> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemActivity> build() {
        return ITEMS;
    }
}
