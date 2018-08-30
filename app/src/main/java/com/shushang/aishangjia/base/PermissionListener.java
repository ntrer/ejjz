package com.shushang.aishangjia.base;

import java.util.List;

/**
 * Created by YD on 2018/7/22.
 */

public interface PermissionListener {

    void onGranted();//已授权

    void onDenied(List<String> deniedPermission);//未授权

}
