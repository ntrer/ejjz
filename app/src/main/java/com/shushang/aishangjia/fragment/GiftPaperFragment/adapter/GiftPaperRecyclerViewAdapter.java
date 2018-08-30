package com.shushang.aishangjia.fragment.GiftPaperFragment.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.SignProple;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/7/19.
 */

public class GiftPaperRecyclerViewAdapter extends BaseQuickAdapter<SignProple.DataListBean,BaseViewHolder> {


    public GiftPaperRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<SignProple.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SignProple.DataListBean item) {
        if(item!=null){
            helper.setText(R.id.date,item.getQdsj());
            helper.setText(R.id.people,item.getUser_name());
            helper.setText(R.id.phone,item.getUser_phone());
            helper.setText(R.id.label,item.getMerchant_name());
            if(item.getLqsuccess()!=null){
                helper.getView(R.id.getPaper).setVisibility(View.VISIBLE);
            }
            else {
                helper.getView(R.id.getPaper).setVisibility(View.GONE);
            }
        }
    }
}
