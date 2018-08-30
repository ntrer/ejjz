package com.shushang.aishangjia.activity.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.Activity;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/8/7.
 */

public class ActivityAdapter extends BaseQuickAdapter<Activity.DataListBean,BaseViewHolder> {

    public ActivityAdapter(int item_sheng, List<Activity.DataListBean> dataListBeen) {
        super(item_sheng, dataListBeen);
    }

    @Override
    protected void convert(BaseViewHolder helper, Activity.DataListBean item) {
        helper.setText(R.id.tv_acname,item.getActivityName());
//        helper.setText(R.id.tv_acid,item.getActivityId());
//        helper.setText(R.id.tv_accode,String.valueOf(item.getActivityCode()));
    }
}
