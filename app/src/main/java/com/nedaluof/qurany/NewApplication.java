package com.nedaluof.qurany;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by nedaluof on 8/25/2020.
 */
public class NewApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;/*DaggerAppComponent.builder().application(this).build();*/
    }
}
