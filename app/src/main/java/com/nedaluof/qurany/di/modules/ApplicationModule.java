package com.nedaluof.qurany.di.modules;

import android.app.Application;
import android.content.Context;

import com.nedaluof.qurany.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nedaluof on 6/26/2020.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application app) {
        application = app;
    }

    @Singleton
    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return application;
    }
}
