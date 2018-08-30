package com.shushang.aishangjia.utils.ApiUtil;

import com.shushang.aishangjia.base.BaseUrl;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YD on 2018/8/10.
 */

public class HttpRequestClient {

    public static final String TAG = "HttpRequestClientTAG";

    private static Retrofit retrofit;

    private static OkHttpClient getOkHttpClient() {

        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        return httpClientBuilder.build();
    }

    public static Retrofit getRetrofitHttpClient(){
        if(null == retrofit){
            synchronized (HttpRequestClient.class){
                if(null == retrofit){
                    retrofit = new Retrofit.Builder()
                            .client(getOkHttpClient())
                            .baseUrl(BaseUrl.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}

