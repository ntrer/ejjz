package com.shushang.aishangjia.fragment.HomeFragment.adapter;

import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.SignProple;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/7/19.
 */

public class SignPeopleRecyclerViewAdapter extends BaseQuickAdapter<SignProple.DataListBean,BaseViewHolder> {

    private Handler mHandler;

    public SignPeopleRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<SignProple.DataListBean> data,Handler handler) {
        super(layoutResId, data);
        this.mHandler=handler;
    }

    @Override
    protected void convert(BaseViewHolder helper, SignProple.DataListBean item) {
        if(item!=null){
            helper.setText(R.id.date,item.getQdsj());
            helper.setText(R.id.people,item.getUser_name());
            helper.setText(R.id.phone,item.getUser_phone());
            helper.setText(R.id.label,item.getMerchant_name());
        }
    }
}
