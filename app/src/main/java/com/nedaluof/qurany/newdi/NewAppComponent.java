package com.nedaluof.qurany.newdi;

import android.app.Application;

import com.nedaluof.qurany.NewApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by nedaluof on 8/25/2020.
 */
@Component(modules = {
        AndroidSupportInjectionModule.class
})
public interface NewAppComponent extends AndroidInjector<NewApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        NewAppComponent build();
    }

}
