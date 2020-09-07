package com.nedaluof.qurany;

import com.nedaluof.qurany.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by nedaluof on 6/13/2020.
 */
public class QuranyApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .context(this)
                .build();
    }
}
