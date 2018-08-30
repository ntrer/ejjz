package com.shushang.aishangjia.activity;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;

public class PaperActivity extends BaseActivity{


    @Override
    public int setLayout() {
        return R.layout.activity_paper;
    }

    @Override
    public void init() {

    }

    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

}
