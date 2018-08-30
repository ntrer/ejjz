package com.shushang.aishangjia.activity.adapter;

import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.ActivityList;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by YD on 2018/8/8.
 */

public class ActivityListAdapter extends BaseQuickAdapter<ActivityList.DataListBean,BaseViewHolder> {

    public ActivityListAdapter(@LayoutRes int layoutResId, @Nullable List<ActivityList.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityList.DataListBean item) {
        Uri path1= Uri.parse("android.resource://" + MyApplication.getInstance().getPackageName() + "/" + R.mipmap.red);
        Uri path2= Uri.parse("android.resource://" + MyApplication.getInstance().getPackageName() + "/" + R.mipmap.yellow);
        Uri path3= Uri.parse("android.resource://" + MyApplication.getInstance().getPackageName() + "/" + R.mipmap.blue);
        if(item.getRoleType().equals("0")){
            Picasso.with(mContext).load(path1)
                    .into((ImageView) helper.getView(R.id.activity_img));
        }
        else if(item.getRoleType().equals("1")){
            Picasso.with(mContext).load(path2)
                    .into((ImageView) helper.getView(R.id.activity_img));
        }
        else if(item.getRoleType().equals("2")){
            Picasso.with(mContext).load(path3)
                    .into((ImageView) helper.getView(R.id.activity_img));
        }

        helper.setText(R.id.activity_name,item.getActivityName());
        helper.setText(R.id.user_type,item.getRoleName());
        helper.setText(R.id.start_time,item.getStartTime());
        helper.setText(R.id.end_time,item.getEndTime());

    }
//    Uri path= Uri.parse("android.resource://" + MyApplication.getInstance().getPackageName() + "/" + R.mipmap.azp);

}
