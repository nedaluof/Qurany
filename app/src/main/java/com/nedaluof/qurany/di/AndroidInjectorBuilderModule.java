package com.nedaluof.qurany.di;

import com.nedaluof.qurany.ui.myreciters.MyRecitersFragment;
import com.nedaluof.qurany.ui.reciter.RecitersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nedaluof on 9/7/2020.
 */
@Module
public abstract class AndroidInjectorBuilderModule {

    @ContributesAndroidInjector
    abstract RecitersFragment contributesRecitersFragment();

    @ContributesAndroidInjector
    abstract MyRecitersFragment contributesMyRecitersFragment();
}
