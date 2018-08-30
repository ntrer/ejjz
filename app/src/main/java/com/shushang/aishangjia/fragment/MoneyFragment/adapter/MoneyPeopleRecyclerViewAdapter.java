package com.shushang.aishangjia.fragment.MoneyFragment.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.MoneyPeople;
import com.shushang.aishangjia.Bean.Quit;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.xys.libzxing.zxing.net.RestClient;
import com.xys.libzxing.zxing.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.List;

/**
 * Created by YD on 2018/7/19.
 */

public class MoneyPeopleRecyclerViewAdapter extends BaseQuickAdapter<MoneyPeople.DataListBean,BaseViewHolder> {

    private Handler mHandler;

    public MoneyPeopleRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<MoneyPeople.DataListBean> data, Handler handler) {
        super(layoutResId, data);
        this.mHandler=handler;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MoneyPeople.DataListBean item) {
        helper.setText(R.id.people,item.getUser_name());
        helper.setText(R.id.phone,item.getUser_phone());
        helper.setText(R.id.date,item.getXdsj());
        if(item.getMerchant_name()!=null){
            helper.setText(R.id.label,item.getMerchant_name().toString());
        }
        helper.setText(R.id.money,String.valueOf(item.getMoney())+"元");
        final String tokenId= PreferencesUtils.getString(mContext,"token_id");
        final String activity_id=PreferencesUtils.getString(mContext,"activityId");
        final String order_id=item.getOrder_id();
        helper.getView(R.id.quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=refundOrder&token_id="+tokenId+"&activity_id="+activity_id+"&order_id="+order_id;
                Log.d("quitUrl",url);
                RestClient.builder()
                        .url(url)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                if(response!=null){
                                    Log.d("quitUrl",response);
                                    Quit quit = JSONUtil.fromJson(response, Quit.class);
                                    if(quit.getRet().equals("200")){
                                        Message message=Message.obtain();
                                        message.what=2;
                                        mHandler.sendMessage(message);
                                    }
                                    else {
                                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "退单失败："+quit.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .build()
                        .get();
            }
        });
    }
}
