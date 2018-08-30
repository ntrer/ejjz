package com.shushang.aishangjia.net;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by YD on 2018/8/10.
 */

public interface FileUploadService {

    @Multipart
    @POST("upload")
    Call<String> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
}
