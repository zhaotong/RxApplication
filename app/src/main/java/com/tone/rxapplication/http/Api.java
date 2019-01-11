package com.tone.rxapplication.http;

import com.tone.rxapplication.entity.MovieTheather;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {
    @GET("/v2/movie/in_theaters")
    Observable<MovieTheather> getMovieTheaters();


// @POST("{url}")
// @Path(value = "url", encoded = true) String url
    @FormUrlEncoded
    @POST
    Observable<String> postDate(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> getDate(@Url String url, @FieldMap Map<String, String> map);

}
