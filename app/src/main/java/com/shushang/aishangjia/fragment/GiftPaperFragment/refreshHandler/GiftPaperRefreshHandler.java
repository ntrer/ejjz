package com.shushang.aishangjia.fragment.GiftPaperFragment.refreshHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.GiftPaper;
import com.shushang.aishangjia.Bean.MoneyCategory;
import com.shushang.aishangjia.Bean.SignProple;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.GiftPaperFragment.adapter.GiftPaperRecyclerViewAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by YD on 2018/8/6.
 */

public class GiftPaperRefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private boolean isFirst = true;
    private String merchantId="";
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final RecyclerView signpeopleRecyclerView;
    private GiftPaperRecyclerViewAdapter mGiftPaperRecyclerViewAdapter;
    private RelativeLayout mLoading;
    private TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5,mTextView6;
    List<MoneyCategory.DataBean> CateGoryData = new ArrayList<>();
    List<MoneyCategory.DataBean> refreshCategoryData = new ArrayList<>();
    List<SignProple.DataListBean> SignPeopleData = new ArrayList<>();
    List<SignProple.DataListBean> refreshSignPeopleData = new ArrayList<>();
    private String base_url= BaseUrl.BASE_URL;
    private Handler mHandler;
    private View mView;
    private String token_id;
    private String activity_id;
    private Context mContext;
    private LinearLayout ll_nodata;
    private MainActivity mMainActivity;
    private int page=1;
    public GiftPaperRefreshHandler(MainActivity mainActivity, SwipeRefreshLayout swipeRefreshLayout, RecyclerView signpeopleRecyclerView, RelativeLayout loading, LinearLayout llnodata) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.signpeopleRecyclerView = signpeopleRecyclerView;
        this.mLoading = loading;
        this.ll_nodata=llnodata;
        this.mContext=mainActivity;
        this.mMainActivity=mainActivity;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static GiftPaperRefreshHandler create(MainActivity mainActivity, SwipeRefreshLayout swipeRefreshLayout, RecyclerView signpeopleRecyclerView, RelativeLayout loading, LinearLayout llnodata) {
        return new GiftPaperRefreshHandler(mainActivity,swipeRefreshLayout, signpeopleRecyclerView, loading,llnodata);
    }


    /**
     * 开始刷新，显示刷新进度
     */
    private void refresh(String merchant_id) {
        token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
        activity_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "activityId");
        if (merchant_id.equals("")) {
            String categoryUrl=base_url+"phoneApi/activityController.do?method=getCouponCount"+"&activity_id="+activity_id+"&token_id="+token_id;
            Log.d("categoryUrl",categoryUrl);
            String signPelpleUrl = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSigninCustomers&token_id="+token_id+"&page=1"+"&rows=10"+"&activity_id="+activity_id;
//            getCategoryData(categoryUrl);
            getSignPeopleData(signPelpleUrl,categoryUrl);
        } else {
//            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "第二次", Toast.LENGTH_SHORT).show();
//            String categoryUrl="https://raw.githubusercontent.com/ntrer/JSONApi/master/api10.json";
            String categoryUrl=base_url+"phoneApi/activityController.do?method=getCouponCount&merchant_id="+merchant_id+"&activity_id="+activity_id+"&token_id="+token_id;
            Log.d("categoryUrl",categoryUrl);
            String signPelpleUrl = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSigninCustomers&token_id="+token_id+"&merchant_id="+merchant_id+"&page=1"+"&rows=10"+"&activity_id="+activity_id;
//            getCategoryData(categoryUrl);
            getSignPeopleData(signPelpleUrl,categoryUrl);
        }

    }

    private void getCategoryData(String categoryUrl) {
        REFRESH_LAYOUT.setRefreshing(true);
        RestClient.builder()
                .url(categoryUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        isFirst = false;
                        REFRESH_LAYOUT.setRefreshing(false);
                        if (response != null) {
//                            Log.d("GiftPaper",response);
                            GiftPaper giftPaper = JSONUtil.fromJson(response, GiftPaper.class);
                            mTextView1.setText(String.valueOf(giftPaper.getData().getRegisterNum()));
                            mTextView2.setText(String.valueOf(giftPaper.getData().getCouponNum()));
                            mTextView3.setText(String.valueOf(Math.round(giftPaper.getData().getRate()*100)+"%"));
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        REFRESH_LAYOUT.setRefreshing(false);
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！"+msg, Toast.LENGTH_LONG).show();
                        REFRESH_LAYOUT.setRefreshing(false);
                    }
                })
                .build()
                .get();
    }

    private void getSignPeopleData(String signPelpleUrl, final String categoryUrl) {
        REFRESH_LAYOUT.setRefreshing(true);
        Log.d("SignPeopleData",signPelpleUrl);
        RestClient.builder()
                .url(signPelpleUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        REFRESH_LAYOUT.setRefreshing(false);
                        if (response != null) {
                           Log.i("SignPeopleList",response);
                            SignProple moneyPeople = JSONUtil.fromJson(response, SignProple.class);
                            if(moneyPeople.getRet().equals("200")){
                                getCategoryData(categoryUrl);
                                SignPeopleData = moneyPeople.getDataList();
//                            refreshSignPeopleData(SignPeopleData);
                                if(SignPeopleData.size()!=0){
                                    shouTabData(SignPeopleData);
                                    ll_nodata.setVisibility(View.GONE);
                                }
                                else {
                                    shouTabData(SignPeopleData);
                                    ll_nodata.setVisibility(View.VISIBLE);
                                }

                            }
                            else if(moneyPeople.getRet().equals("101")||moneyPeople.getRet().equals("201")){
                                Toast.makeText(mContext, "token失效了", Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(mContext,"token_id",null);
                                startActivity(new Intent(mMainActivity, LoginActivity2.class));
                                mMainActivity.finish();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        REFRESH_LAYOUT.setRefreshing(false);
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                        REFRESH_LAYOUT.setRefreshing(false);
                    }
                })
                .build()
                .get();
    }



    private void refreshSignPeopleData(List<SignProple.DataListBean> SignPeopleData) {
        refreshSignPeopleData.clear();
        refreshSignPeopleData.addAll(SignPeopleData);
    }


    private void shouTabData(List<SignProple.DataListBean> signPeopleData){
        mView=View.inflate(MyApplication.getInstance().getApplicationContext(), R.layout.headerview1,null);
        mTextView1=mView.findViewById(R.id.num1);
        mTextView2=mView.findViewById(R.id.num2);
        mTextView3=mView.findViewById(R.id.num3);
        mGiftPaperRecyclerViewAdapter = new GiftPaperRecyclerViewAdapter(R.layout.item_sign, signPeopleData);
        mGiftPaperRecyclerViewAdapter.setOnLoadMoreListener(GiftPaperRefreshHandler.this, signpeopleRecyclerView);
        mGiftPaperRecyclerViewAdapter.addHeaderView(mView);
        //重复执行动画
        mGiftPaperRecyclerViewAdapter.isFirstOnly(false);
        signpeopleRecyclerView.setAdapter(mGiftPaperRecyclerViewAdapter);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        if (isFirst) {
            refresh("");
        }  else if(merchantId.equals("")){
            refresh("");
        }
        else if(merchantId.equals("100")){
            refresh("");
        }
        else {
//            Toast.makeText(MyApplication.getInstance().getApplicationContext(), merchantId, Toast.LENGTH_SHORT).show();
            refresh(merchantId);
        }
    }

    public void getTab(String allData) {
        REFRESH_LAYOUT.setRefreshing(true);
        merchantId = allData;
        refresh("");
    }


    public void switchData(String merchant_id) {
        REFRESH_LAYOUT.setRefreshing(true);
        merchantId = merchant_id;
        refresh(merchant_id);
    }


    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }


    private void loadMore() {
        String url=null;
        page=page+1;
        if(merchantId.equals("100")||merchantId.equals("")){
            url= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSigninCustomers&token_id="+token_id+"&page="+page+"&rows=10"+"&activity_id="+activity_id;

        }
        else {
            url=BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSigninCustomers&token_id="+token_id+"&merchant_id="+merchantId+"&page="+page+"&rows=10"+"&activity_id="+activity_id;
        }
        Log.i("Gift",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("SignPeopleList",response);
                        SignProple moneyPeople = JSONUtil.fromJson(response, SignProple.class);
                        if(moneyPeople.getRet().equals("200")){
                            if(page>moneyPeople.getIntmaxPage()) {
                                page=1;
                                mGiftPaperRecyclerViewAdapter.loadMoreComplete();
                                mGiftPaperRecyclerViewAdapter.loadMoreEnd();
                            }
                            else if(moneyPeople.getDataList().size()!=0){
                                List<SignProple.DataListBean> data = moneyPeople.getDataList();
                                LoadMoreData(data);
                                mGiftPaperRecyclerViewAdapter.loadMoreComplete();
                            }
                        }
                        else {
                            mGiftPaperRecyclerViewAdapter.loadMoreComplete();
                        }
                    }
                })
                .build()
                .get();

    }

    private void LoadMoreData(List<SignProple.DataListBean> data) {
        mGiftPaperRecyclerViewAdapter.addData(data);
    }


}
