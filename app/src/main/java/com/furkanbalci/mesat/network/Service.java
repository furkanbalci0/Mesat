package com.furkanbalci.mesat.network;


import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Service API.
 */
public class Service {

    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.234:9000/")
            .client(Service.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    /**
     * Gets retrofit.
     *
     * @return Retrofit.
     */
    @NonNull
    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
