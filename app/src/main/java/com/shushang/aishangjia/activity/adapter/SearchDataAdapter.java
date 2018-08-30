package com.shushang.aishangjia.activity.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.MoneyPeople;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/7/25.
 */

public class SearchDataAdapter extends BaseQuickAdapter<MoneyPeople.DataListBean,BaseViewHolder> {

    public SearchDataAdapter(@LayoutRes int layoutResId, @Nullable List<MoneyPeople.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MoneyPeople.DataListBean item) {
        helper.setText(R.id.people,item.getUser_name());
        helper.setText(R.id.phone,item.getUser_phone());
        helper.setText(R.id.date,item.getXdsj());
        helper.setText(R.id.money,String.valueOf(item.getMoney())+"元");

    }
}
