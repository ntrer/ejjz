package com.xys.libzxing.zxing.net.download;

import android.os.AsyncTask;
import com.xys.libzxing.zxing.net.RestCreator;
import com.xys.libzxing.zxing.net.callback.IError;
import com.xys.libzxing.zxing.net.callback.IFailure;
import com.xys.libzxing.zxing.net.callback.IRequest;
import com.xys.libzxing.zxing.net.callback.ISuccess;
import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YD on 2017/12/27.
 * 进行下载处理
 */

public class DownloadHandler {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String url,
                           IRequest request,
                           String downDir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    /**
     * 处理下载请求
     */
    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService()
                .download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                            //得到请求体
                            final ResponseBody responseBody=response.body();
                            //存储下载的文件,因为是边下边存，所以要异步处理
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            //THREAD_POOL_EXECUTOR 异步线程池，多个任务在线程池中并发执行
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,responseBody,NAME);
                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                            else {
                                if (ERROR != null) {
                                    ERROR.onError(response.code(), response.message());
                                }
                            }
                            //清除参数，为下次请求准备
                            RestCreator.getParams().clear();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                            RestCreator.getParams().clear();
                        }
                    }
                });


    }


}
