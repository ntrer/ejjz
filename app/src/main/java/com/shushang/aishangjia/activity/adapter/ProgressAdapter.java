package com.shushang.aishangjia.activity.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.Progress;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/8/21.
 */

public class ProgressAdapter extends BaseQuickAdapter<Progress.DataListBean,BaseViewHolder> {

    public ProgressAdapter(int item_sheng, List<Progress.DataListBean> dataListBeen) {
        super(item_sheng, dataListBeen);
    }

    @Override
    protected void convert(BaseViewHolder helper, Progress.DataListBean item) {
        helper.setText(R.id.sheng_name,item.getDecorationProgressName());
    }
}
