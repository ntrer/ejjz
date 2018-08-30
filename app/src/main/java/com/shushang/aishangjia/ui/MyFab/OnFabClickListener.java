package com.shushang.aishangjia.ui.MyFab;

import android.support.design.widget.FloatingActionButton;

/**
 * Created by YD on 2018/8/3.
 */

public interface OnFabClickListener {

    /**
     * @param fab 按钮
     * @param tag 回调之前设置的tag
     */
    void onFabClick(FloatingActionButton fab, Object tag);
}