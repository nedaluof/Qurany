package com.nedaluof.qurany.data.api;

import com.nedaluof.qurany.data.model.Reciters;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by nedaluof on 6/13/2020.
 */
public interface ApiService {

    /**
     * @return Observable Reciters obj
     */
    @GET("{language}.php")
    Observable<Reciters> getReciters(
            @Path("language") String language
    );

}
