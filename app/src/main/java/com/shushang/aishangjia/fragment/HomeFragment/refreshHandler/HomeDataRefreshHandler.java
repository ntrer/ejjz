package com.shushang.aishangjia.fragment.HomeFragment.refreshHandler;

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
import com.shushang.aishangjia.Bean.Category;
import com.shushang.aishangjia.Bean.SignProple;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.SignPeopleRecyclerViewAdapter;
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
 * Created by YD on 2017/12/31.
 * 处理请求数据，刷新数据
 */

public class HomeDataRefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private boolean isFirst = true;
    private String merchantId="";
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final RecyclerView signpeopleRecyclerView;
    private SignPeopleRecyclerViewAdapter mSignPeopleRecyclerViewAdapter;
    private RelativeLayout mLoading;
    private MainActivity mMainActivity;
    private TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5;
    List<Category.DataBean> CateGoryData = new ArrayList<>();
    List<Category.DataBean> refreshCategoryData = new ArrayList<>();
    List<SignProple.DataListBean> SignPeopleData = new ArrayList<>();
    List<SignProple.DataListBean> refreshSignPeopleData = new ArrayList<>();
    private Handler mHandler;
    private View mView;
    private LinearLayout ll_nodata;
    private int page=1;
    private String token_id;
    private String activity_id;
    private Context mContext;
    public HomeDataRefreshHandler(MainActivity mainActivity, SwipeRefreshLayout swipeRefreshLayout, RecyclerView signpeopleRecyclerView, RelativeLayout loading, Handler handler, LinearLayout llnodata) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.signpeopleRecyclerView = signpeopleRecyclerView;
        this.mLoading = loading;
        this.mHandler=handler;
        this.ll_nodata=llnodata;
        this.mContext=mainActivity;
        this.mMainActivity=mainActivity;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static HomeDataRefreshHandler create(MainActivity mainActivity, SwipeRefreshLayout swipeRefreshLayout, RecyclerView signpeopleRecyclerView, RelativeLayout loading, Handler handler, LinearLayout llnodata) {
        return new HomeDataRefreshHandler(mainActivity,swipeRefreshLayout, signpeopleRecyclerView, loading,handler,llnodata);
    }


    /**
     * 开始刷新，显示刷新进度
     */
    private void refresh(String merchant_id) {
         token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
         activity_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "activityId");
        if (merchant_id.equals("")) {
//            String categoryUrl = "https://raw.githubusercontent.com/ntrer/JSONApi/master/api10.json";
            String categoryUrl= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getCustomerStatistics&token_id="+token_id+"&activity_id="+activity_id;
            String signPelpleUrl = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSigninCustomers&token_id="+token_id+"&page=1"+"&rows=10"+"&activity_id="+activity_id;
//            getCategoryData(categoryUrl);
            getSignPeopleData(signPelpleUrl,categoryUrl);
        } else {
//            String categoryUrl="https://raw.githubusercontent.com/ntrer/JSONApi/master/api10.json";
            String categoryUrl = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getCustomerStatistics&token_id=" + token_id + "&merchant_id=" + merchant_id + "&activity_id=" + activity_id;
            String signPelpleUrl = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSigninCustomers&token_id="+token_id+"&page=1"+"&rows=10"+"&activity_id="+activity_id+"&merchant_id="+merchant_id+"&condition=";
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
                        Log.d("categoryUrl",response);
                        isFirst = false;
                        REFRESH_LAYOUT.setRefreshing(false);
                        if (response != null) {
                            Category category = JSONUtil.fromJson(response, Category.class);
                            if(category.getData()!=null){
                                Category.DataBean data = category.getData();
                                mTextView1.setText(data.getTotalCustomer() + "");
                                mTextView2.setText(data.getUnregisterNum() + "");
                                mTextView3.setText(data.getRegisterNum() + "");
                                mTextView4.setText(String.valueOf(Math.round(data.getRegisterRate()*100) + "%"));
                                mTextView5.setText(String.valueOf(Math.round(Float.parseFloat(data.getTotalMerchant()+""))));
                                mLoading.setVisibility(View.GONE);
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
        Log.d("signPelpleUrl",signPelpleUrl);
        RestClient.builder()
                .url(signPelpleUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("signPelpleUrl",response);
                        REFRESH_LAYOUT.setRefreshing(false);
                        if (response != null) {
                            SignProple signProple = JSONUtil.fromJson(response, SignProple.class);
                            if(signProple.getRet().equals("200")){
                                getCategoryData(categoryUrl);
                                if(signProple.getDataList()!=null){
                                    SignPeopleData = signProple.getDataList();
//                                refreshSignPeopleData(SignPeopleData);
                                    if(SignPeopleData.size()!=0){
                                        showTabData(SignPeopleData);
                                        ll_nodata.setVisibility(View.GONE);
                                    }
                                    else {
                                        showTabData(SignPeopleData);
                                        ll_nodata.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            else if(signProple.getRet().equals("101")||signProple.getRet().equals("201")){
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

    private void showTabData(List<SignProple.DataListBean> signPeopleData){
        mView=View.inflate(MyApplication.getInstance().getApplicationContext(), R.layout.headerview4,null);
        mTextView1=mView.findViewById(R.id.num1);
        mTextView2=mView.findViewById(R.id.num2);
        mTextView3=mView.findViewById(R.id.num3);
        mTextView4=mView.findViewById(R.id.num4);
        mTextView5=mView.findViewById(R.id.num5);
        mSignPeopleRecyclerViewAdapter = new SignPeopleRecyclerViewAdapter(R.layout.item_sign, signPeopleData,mHandler);
        mSignPeopleRecyclerViewAdapter.setOnLoadMoreListener(HomeDataRefreshHandler.this, signpeopleRecyclerView);
        mSignPeopleRecyclerViewAdapter.addHeaderView(mView);
        //重复执行动画
        mSignPeopleRecyclerViewAdapter.isFirstOnly(false);
        signpeopleRecyclerView.setAdapter(mSignPeopleRecyclerViewAdapter);
        signpeopleRecyclerView.scrollToPosition(0);
        mLoading.setVisibility(View.GONE);
    }




    private void refreshSignPeopleData(List<SignProple.DataListBean> SignPeopleData) {
        refreshSignPeopleData.clear();
        refreshSignPeopleData.addAll(SignPeopleData);
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
            Log.d("signPelpleUrl1",url);
        }
        else {
            url=BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSigninCustomers&token_id="+token_id+"&page="+page+"&rows=10"+"&activity_id="+activity_id+"&merchant_id="+merchantId+"&condition=";
            Log.d("signPelpleUrl2",url);
        }
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        SignProple signProple = JSONUtil.fromJson(response, SignProple.class);
                        if(signProple.getRet().equals("200")){
                            if(page>signProple.getIntmaxPage()) {
                                page=1;
                                mSignPeopleRecyclerViewAdapter.loadMoreComplete();
                                mSignPeopleRecyclerViewAdapter.loadMoreEnd();
                            }
                            else if(signProple.getDataList().size()!=0){
                                List<SignProple.DataListBean> data = signProple.getDataList();
                                LoadMoreData(data);
                                mSignPeopleRecyclerViewAdapter.loadMoreComplete();
                            }
                        }
                        else {
                            mSignPeopleRecyclerViewAdapter.loadMoreComplete();
                        }

                    }
                })
                .build()
                .get();

    }

    private void LoadMoreData(List<SignProple.DataListBean> data) {
        mSignPeopleRecyclerViewAdapter.addData(data);
    }

}
