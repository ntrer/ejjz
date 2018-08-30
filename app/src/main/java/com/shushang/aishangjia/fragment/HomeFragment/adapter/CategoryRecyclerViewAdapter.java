package com.shushang.aishangjia.fragment.HomeFragment.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.Category;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/7/19.
 */

public class CategoryRecyclerViewAdapter extends BaseQuickAdapter<Category.DataBean,BaseViewHolder> {


    public CategoryRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<Category.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category.DataBean item) {
        helper.setText(R.id.num,item.getRegisterNum());
    }
}
