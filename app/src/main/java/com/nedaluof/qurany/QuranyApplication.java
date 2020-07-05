package com.nedaluof.qurany;

import android.app.Application;

import com.nedaluof.qurany.di.components.AppComponent;
import com.nedaluof.qurany.di.components.DaggerAppComponent;
import com.nedaluof.qurany.di.modules.ApplicationModule;
import com.nedaluof.qurany.di.modules.DatabaseModule;

/**
 * Created by nedaluof on 6/13/2020.
 */
public class QuranyApplication extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();
        component.inject(this);
    }

    public AppComponent getComponent() {
        return ((QuranyApplication)this.getApplicationContext()).component;
    }
    /*public AppComponent getComponent() {
        return component;
    }*/
}
