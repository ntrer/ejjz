package com.shushang.aishangjia.fragment.SignFragment.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/8/4.
 */

public class SignAdapter extends BaseQuickAdapter<NewPeople.DataListBean,BaseViewHolder> {

    public SignAdapter(@LayoutRes int layoutResId, @Nullable List<NewPeople.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewPeople.DataListBean item) {
        helper.setText(R.id.people,item.getUsername());
        helper.setText(R.id.label,item.getMerchantName());
        helper.setText(R.id.date,item.getCjsj());
        helper.setText(R.id.phone,item.getPhone());
    }
}
