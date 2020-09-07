package com.nedaluof.qurany.di;

import android.app.Application;
import android.content.Context;

import com.nedaluof.qurany.QuranyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by nedaluof on 9/7/2020.
 */
@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AndroidInjectorBuilderModule.class,
                RoomModule.class

        })
public interface AppComponent extends AndroidInjector<QuranyApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }
}
