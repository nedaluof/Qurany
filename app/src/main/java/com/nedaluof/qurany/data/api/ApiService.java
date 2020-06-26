package com.nedaluof.qurany.data.api;

import com.nedaluof.qurany.data.model.Languages;
import com.nedaluof.qurany.data.model.Reciters;

import java.util.List;

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


    /**
     * @param url
     * @return Call<ResponseBody>
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String url);
}
