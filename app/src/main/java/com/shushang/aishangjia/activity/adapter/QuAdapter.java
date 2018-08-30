package com.shushang.aishangjia.activity.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.Qu;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/8/7.
 */

public class QuAdapter extends BaseQuickAdapter<Qu.DataListBean,BaseViewHolder> {

    public QuAdapter(int item_sheng, List<Qu.DataListBean> dataListBeen) {
        super(item_sheng, dataListBeen);
    }

    @Override
    protected void convert(BaseViewHolder helper, Qu.DataListBean item) {
        helper.setText(R.id.sheng_name,item.getMingcheng());
    }
}
