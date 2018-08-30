package com.xys.libzxing.zxing.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import com.xys.libzxing.zxing.net.callback.IRequest;
import com.xys.libzxing.zxing.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.FileUtil;
import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;


/**
 * Created by YD on 2017/12/27.
 * 异步存储文件到本地
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... params) {

        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        //将得到的请求体转换为字节流
        final InputStream is = body.byteStream();
        //如果路径为空，设置默认路径
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        //如果后缀名为空，设置默认后缀名
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        //进行写入操作
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }

    }

    /**
     * 异步处理完成
     * @param file
     */
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null)
        {
            SUCCESS.onSuccess(file.getPath());

        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    /**
     * 自动安装apk
     * @param file
     */
    private void autoInstallApk(File file) {
        //如果得到的文件后缀名是aok，则开始自定安装
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install=new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        }

    }


}
