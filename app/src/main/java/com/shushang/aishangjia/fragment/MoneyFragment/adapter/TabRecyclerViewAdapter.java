package com.shushang.aishangjia.fragment.MoneyFragment.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.TabList;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/7/19.
 */

public class TabRecyclerViewAdapter extends BaseQuickAdapter<TabList.DataListBean,BaseViewHolder> {

    //先声明一个int成员变量
    private int thisPosition;
    //再定义一个int类型的返回值方法
    public int getthisPosition() {
        return thisPosition;
    }

    //其次定义一个方法用来绑定当前参数值的方法
    //此方法是在调用此适配器的地方调用的，此适配器内不会被调用到
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public TabRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<TabList.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int positions) {
        super.onBindViewHolder(holder, positions);
        if (positions == getthisPosition()) {
            holder.getView(R.id.tab_name).setEnabled(true);
        } else {
            holder.getView(R.id.tab_name).setEnabled(false);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, TabList.DataListBean item) {
       helper.setText(R.id.tab_name,item.getMerchant_name());
    }
}
