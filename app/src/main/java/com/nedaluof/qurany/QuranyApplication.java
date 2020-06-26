package com.nedaluof.qurany;

import android.app.Application;

import com.nedaluof.qurany.di.components.AppComponent;
import com.nedaluof.qurany.di.components.DaggerAppComponent;

/**
 * Created by nedaluof on 6/13/2020.
 */
public class QuranyApplication extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public AppComponent getComponent() {
        return component;
    }
}
