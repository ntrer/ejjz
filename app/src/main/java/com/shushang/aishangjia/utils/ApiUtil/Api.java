package com.shushang.aishangjia.utils.ApiUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YD on 2018/8/10.
 */

public class Api {
    private static ApiService SERVICE;
    private static final String BASE_URL = "http://192.168.0.55:8899/";

    public static ApiService getDefault() {
        if(SERVICE == null) {

            OkHttpClient client = new OkHttpClient.Builder().build();
            SERVICE = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build().create(ApiService.class);
        }

        return SERVICE;
    }


}
