package com.xys.libzxing.zxing.net;


import com.xys.libzxing.zxing.net.callback.IError;
import com.xys.libzxing.zxing.net.callback.IFailure;
import com.xys.libzxing.zxing.net.callback.IRequest;
import com.xys.libzxing.zxing.net.callback.ISuccess;
import com.xys.libzxing.zxing.net.callback.RequestCallbacks;
import com.xys.libzxing.zxing.net.download.DownloadHandler;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by YD on 2017/12/25.
 */

public class RestClient {
    /**
     * 请求参数
     */
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    /**
     * 请求链接
     */
    private final String URL;
    /**
     * 下载路径
     */
    private final String DOWNLOAD_DIR;
    /**
     * 下载文件后缀名
     */
    private final String EXTENSION;
    /**
     * 下载文件名
     */
    private final String NAME;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final File FILE;


    /**
     * 接受通过建造者模式得到的参数
     * @param url
     * @param params
     * @param request
     * @param success
     * @param failure
     * @param error
     * @param body
     */
    RestClient(String url,
               Map<String, Object> params,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               File file) {
        this.URL = url;
        PARAMS.putAll(params);
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
    }

    /**
     * 通过建造者模式来创建请求
     * @return
     */
    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }


    /**
     * get请求
     */
    public final void get() {
        request(HttpMethod.GET);
    }

    /**
     * post请求
     */
    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    /**
     * put请求
     */
    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    /**
     * delete请求
     */
    public final void delete() {
        request(HttpMethod.DELETE);
    }

    /**
     * 上传请求
     */
    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, FAILURE, ERROR)
                .handleDownload();
    }


    /**
     * 开始请求数据
     * @param method
     */
    private void request(HttpMethod method) {
        //得到数据接口
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        /**
         * 根据具体的请求方法，返回对应的请求
         */
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }

    }

    /**
     * 得到请求后的回调
     * @return
     */
    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR
        );
    }


}
