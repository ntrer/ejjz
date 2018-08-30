package com.shushang.aishangjia.utils.ApiUtil;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by YD on 2018/8/10.
 */

public interface ApiService {

    @Multipart
    @POST("phoneApi/customerManager.do?method=managerSignin")
    Call<String> uploadMemberIcon(@Part("token_id") RequestBody token,@Part("qddz") RequestBody qddz,@Part("qdsj") RequestBody qdsj,@Part("qdlh") RequestBody qdlh,@Part MultipartBody.Part part);

    @Multipart
    @POST("phoneApi/customerManager.do?method=managerSignin")  //这里是自己post文件的地址
    Call<String> upload(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);


}
