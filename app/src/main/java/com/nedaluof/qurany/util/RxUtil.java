package com.nedaluof.qurany.util;

import io.reactivex.disposables.Disposable;

/**
 * Created by nedaluof on 6/13/2020.
 */
public class RxUtil {
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
