package com.tone.rxapplication.http;

import com.google.gson.Gson;
import com.tone.rxapplication.entity.MovieTheather;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {

    private static HttpUtil httpUtil;

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private final String BASE_URL = "https://api.douban.com";

    public static HttpUtil getInstance() {
        if (httpUtil == null)
            synchronized (HttpUtil.class){
                if (httpUtil == null)
                    httpUtil = new HttpUtil();
            }
        return httpUtil;
    }

    private HttpUtil() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }


    public Observable<MovieTheather> getMovie() {
        return retrofit.create(Api.class).getMovieTheaters();
    }


    public <T> Observable<T> postData(String url, Map<String, String> map, final Class<T> clazz){
        return retrofit
                .create(Api.class)
                .postDate(url,map)
                .flatMap(new Function<String, Observable<T>>() {
                    @Override
                    public Observable<T> apply(String s) throws Exception {
                        Gson gson =new Gson();
                        T obj = gson.fromJson(s, clazz);
                        return Observable.just(obj);
                    }
                });
    }

}
