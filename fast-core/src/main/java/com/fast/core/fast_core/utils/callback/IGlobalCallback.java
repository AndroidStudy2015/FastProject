package com.fast.core.fast_core.utils.callback;

import android.support.annotation.Nullable;

/**
 * Created by apple
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
