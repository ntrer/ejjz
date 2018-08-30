package com.shushang.aishangjia.fragment.MoneyFragment.refreshHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.MoneyCategory;
import com.shushang.aishangjia.Bean.MoneyPeople;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.SearchActivity;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.MoneyFragment.adapter.MoneyPeopleRecyclerViewAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.SwipeItemLayout;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by YD on 2017/12/31.
 * 处理请求数据，刷新数据
 */

public class MoneyDataRefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private boolean isFirst = true;
    private String merchantId="";
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final RecyclerView signpeopleRecyclerView;
    private MoneyPeopleRecyclerViewAdapter mMoneyPeopleRecyclerViewAdapter;
    private RelativeLayout mLoading;
    private TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5,mTextView6;
    private ImageView mSearch;
    List<MoneyCategory.DataBean> CateGoryData = new ArrayList<>();
    List<MoneyCategory.DataBean> refreshCategoryData = new ArrayList<>();
    List<MoneyPeople.DataListBean> SignPeopleData = new ArrayList<>();
    List<MoneyPeople.DataListBean> refreshSignPeopleData = new ArrayList<>();
    private String base_url= BaseUrl.BASE_URL;
    private Handler mHandler;
    private View mView;
    private Context mContext;
    private MainActivity mMainActivity;
    private int page=1;
    private String token_id;
    private String activity_id;
    private LinearLayout ll_nodata;
    public MoneyDataRefreshHandler(MainActivity mainActivity, SwipeRefreshLayout swipeRefreshLayout, RecyclerView signpeopleRecyclerView, RelativeLayout loading, Handler handler, LinearLayout llnodata) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.signpeopleRecyclerView = signpeopleRecyclerView;
        this.mLoading = loading;
        this.mHandler=handler;
        this.mContext=mainActivity;
        this.mMainActivity=mainActivity;
        this.ll_nodata=llnodata;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static MoneyDataRefreshHandler create(MainActivity mainActivity, SwipeRefreshLayout swipeRefreshLayout, RecyclerView signpeopleRecyclerView, RelativeLayout loading, Handler handler, LinearLayout llnodata) {
        return new MoneyDataRefreshHandler(mainActivity,swipeRefreshLayout, signpeopleRecyclerView, loading,handler,llnodata);
    }


    /**
     * 开始刷新，显示刷新进度
     */
    private void refresh(String merchant_id) {
        token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
        activity_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "activityId");
        if (merchant_id.equals("")) {
//            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "第一次", Toast.LENGTH_SHORT).show();
//            String categoryUrl = "https://raw.githubusercontent.com/ntrer/JSONApi/master/api10.json";
            String categoryUrl=base_url+"phoneApi/activityController.do?method=getSilversStatistics&token_id="+token_id+"&activity_id="+activity_id;
//            String signPelpleUrl = "https://raw.githubusercontent.com/ntrer/JSONApi/master/MoneyPeople.json";
            String signPelpleUrl = base_url+"phoneApi/activityController.do?method=getSilvers&token_id="+token_id+"&page=1"+"&rows=10"+"&activity_id="+activity_id;
            Log.d("signPelpleUrl",signPelpleUrl);
//            getCategoryData(categoryUrl);
            getSignPeopleData(signPelpleUrl,categoryUrl);
        } else {
//            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "第二次", Toast.LENGTH_SHORT).show();
//            String categoryUrl="https://raw.githubusercontent.com/ntrer/JSONApi/master/api10.json";
            String categoryUrl = base_url+"phoneApi/activityController.do?method=getSilversStatistics&token_id=" + token_id + "&activity_id=" + activity_id+"&merchant_id="+merchantId;
            String signPelpleUrl = base_url+"phoneApi/activityController.do?method=getSilvers&token_id="+token_id+"&page=1"+"&rows=10"+"&activity_id="+activity_id+"&merchant_id="+merchantId+"&condition=";
            Log.d("signPelpleUrl",signPelpleUrl);
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
                            Log.d("categoryUrl",response);
                            MoneyCategory moneyCategory = JSONUtil.fromJson(response, MoneyCategory.class);
                            if(moneyCategory.getRet().equals("200")){
                                MoneyCategory.DataBean data = moneyCategory.getData();
                                if(data!=null){
                                    mTextView1.setText(data.getTotalMerchant()+ "");
                                    mTextView2.setText(data.getTotalOrders() + "");
                                    mTextView3.setText(String.valueOf(Math.round(data.getTotalMoney())) + "");
                                    mTextView4.setText(String.valueOf(Math.round(data.getAverageMoney())));
                                    mTextView5.setText(data.getRefundOrders()+ "");
                                    mTextView6.setText(String.valueOf(data.getRefundMoney()) + "");
                                    mLoading.setVisibility(View.GONE);
                                }
                            }
                            else {
                                ToastUtils.showLong(""+moneyCategory.getMsg());
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
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！"+msg, Toast.LENGTH_LONG).show();
                        REFRESH_LAYOUT.setRefreshing(false);
                    }
                })
                .build()
                .get();
    }

    private void getSignPeopleData(String signPelpleUrl,final String categoryUrl) {
        REFRESH_LAYOUT.setRefreshing(true);
        Log.d("SignPeopleData",signPelpleUrl);
        RestClient.builder()
                .url(signPelpleUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("SignPeopleData",response);
                        REFRESH_LAYOUT.setRefreshing(false);
                        if (response != null) {
                            MoneyPeople moneyPeople = JSONUtil.fromJson(response, MoneyPeople.class);
                            if(moneyPeople.getRet().equals("200")){
                                getCategoryData(categoryUrl);
                                SignPeopleData = moneyPeople.getDataList();
//                            refreshSignPeopleData(SignPeopleData);
                                if(SignPeopleData.size()!=0){
                                    showTabData(SignPeopleData);
                                    ll_nodata.setVisibility(View.GONE);
                                }
                                else {
                                    showTabData(SignPeopleData);
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


    private void refreshSignPeopleData(List<MoneyPeople.DataListBean> SignPeopleData) {
        refreshSignPeopleData.clear();
        refreshSignPeopleData.addAll(SignPeopleData);
    }


    private void showTabData(List<MoneyPeople.DataListBean> signPeopleData){
        mView=View.inflate(MyApplication.getInstance().getApplicationContext(), R.layout.headerview5,null);
        mTextView1=mView.findViewById(R.id.num1);
        mTextView2=mView.findViewById(R.id.num2);
        mTextView3=mView.findViewById(R.id.num3);
        mTextView4=mView.findViewById(R.id.num4);
        mTextView5=mView.findViewById(R.id.num5);
        mTextView6=mView.findViewById(R.id.num6);
        mSearch=mView.findViewById(R.id.search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SearchActivity.class));
            }
        });
        mMoneyPeopleRecyclerViewAdapter = new MoneyPeopleRecyclerViewAdapter(R.layout.item_money2, signPeopleData,mHandler);
        mMoneyPeopleRecyclerViewAdapter.setOnLoadMoreListener(MoneyDataRefreshHandler.this, signpeopleRecyclerView);
        mMoneyPeopleRecyclerViewAdapter.addHeaderView(mView);
        //重复执行动画
        mMoneyPeopleRecyclerViewAdapter.isFirstOnly(false);
        signpeopleRecyclerView.setAdapter(mMoneyPeopleRecyclerViewAdapter);
        mLoading.setVisibility(View.GONE);
    }


    @Override
    public void onRefresh() {
        if (isFirst) {
            refresh("");
            signpeopleRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(MyApplication.getInstance().getApplicationContext()));
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
            url= base_url+"phoneApi/activityController.do?method=getSilvers&token_id="+token_id+"&page="+page+"&rows=10"+"&activity_id="+activity_id;

        }
        else {
            url=base_url+"phoneApi/activityController.do?method=getSilvers&token_id="+token_id+"&page="+page+"&rows=10"+"&activity_id="+activity_id+"&merchant_id="+merchantId+"&condition=";
        }
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            MoneyPeople moneyPeople = JSONUtil.fromJson(response, MoneyPeople.class);
                            if(moneyPeople.getRet().equals("200")){
                                if(page>moneyPeople.getIntmaxPage()){
                                    page=1;
                                    mMoneyPeopleRecyclerViewAdapter.loadMoreComplete();
                                    mMoneyPeopleRecyclerViewAdapter.loadMoreEnd();
                                }
                                else if(moneyPeople.getDataList().size()!=0){
                                    List<MoneyPeople.DataListBean> data = moneyPeople.getDataList();
                                    LoadMoreData(data);
                                    mMoneyPeopleRecyclerViewAdapter.loadMoreComplete();
                                }
                            }
                        }
                    }
                })
                .build()
                .get();

    }

    private void LoadMoreData(List<MoneyPeople.DataListBean> data) {
        mMoneyPeopleRecyclerViewAdapter.addData(data);
    }



}
