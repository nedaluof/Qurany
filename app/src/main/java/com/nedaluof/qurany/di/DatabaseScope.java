package com.nedaluof.qurany.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by nedaluof on 6/26/2020.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseScope {
}
