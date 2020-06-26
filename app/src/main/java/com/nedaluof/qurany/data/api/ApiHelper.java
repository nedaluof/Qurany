package com.nedaluof.qurany.data.api;

import javax.inject.Inject;

/**
 * Created by nedaluof on 6/13/2020.
 */
public class ApiHelper {
    @Inject
    public ApiHelper() {
    }

    public ApiService getService() {
        return Client.getInstance().create(ApiService.class);
    }
}
