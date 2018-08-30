package com.shushang.aishangjia.fragment.ScanFragment.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.SignList;
import com.shushang.aishangjia.R;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.List;

/**
 * Created by YD on 2018/8/4.
 */

public class ScanAdapter extends BaseQuickAdapter<SignList.DataListBean,BaseViewHolder> {

    public ScanAdapter(@LayoutRes int layoutResId, @Nullable List<SignList.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignList.DataListBean item) {
        String url=item.getZhaopian()+ "&token_id="+PreferencesUtils.getString(mContext,"token_id");
        Log.d("图片链接",url);
        Glide.with(mContext).load(url).override(ConvertUtils.dp2px(80),ConvertUtils.dp2px(80)).centerCrop().placeholder(R.mipmap.img).into((ImageView) helper.getView(R.id.touxiang));
        helper.setText(R.id.num,item.getTargetNum());
        helper.setText(R.id.label,item.getQddz());
        helper.setText(R.id.date,item.getQdsj());
        }
        }
