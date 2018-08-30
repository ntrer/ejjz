package com.shushang.aishangjia.activity.refreshHander;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.MoneyPeople;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.SearchDataAdapter;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD on 2017/12/31.
 * 处理请求数据，刷新数据
 */

public class SearchDataRefreshHandler implements  BaseQuickAdapter.RequestLoadMoreListener {

    private String merchantId="";
    private final RecyclerView signpeopleRecyclerView;
    private SearchDataAdapter mSearchDataAdapter;
    private ProgressBar mLoading;
    private LinearLayout ll_noData;
    private String token_id,activity_id;
    private int page=1;
    private String queryWord;
    List<MoneyPeople.DataListBean> SignPeopleData = new ArrayList<>();
    List<MoneyPeople.DataListBean> refreshSignPeopleData = new ArrayList<>();

    public SearchDataRefreshHandler(RecyclerView signpeopleRecyclerView, ProgressBar loading, LinearLayout ll_noData) {
        this.signpeopleRecyclerView = signpeopleRecyclerView;
        this.mLoading = loading;
        this.ll_noData=ll_noData;
    }

    public static SearchDataRefreshHandler create(RecyclerView signpeopleRecyclerView, ProgressBar loading, LinearLayout ll_noData) {
        return new SearchDataRefreshHandler(signpeopleRecyclerView, loading,ll_noData);
    }


    /**
     * 开始刷新，显示刷新进度
     */
    private void refresh(String query) {
        queryWord=query;
        token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
        activity_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "activityId");
        String signPelpleUrl = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSilvers&token_id="+token_id+"&page=1"+"&rows=10"+"&activity_id="+activity_id+"&condition="+queryWord;
        getSignPeopleData(signPelpleUrl);

    }


    private void getSignPeopleData(final String url) {
        mLoading.setVisibility(View.VISIBLE);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (response != null) {
                            Log.d("search",url);
                            mLoading.setVisibility(View.GONE);
                            MoneyPeople moneyPeople = JSONUtil.fromJson(response, MoneyPeople.class);
                            if(moneyPeople.getRet().equals("200")){
                                SignPeopleData = moneyPeople.getDataList();
                                if(SignPeopleData.size()>0){
                                    refreshSignPeopleData(SignPeopleData);
                                    mSearchDataAdapter = new SearchDataAdapter(R.layout.item_money2, SignPeopleData);
                                    mSearchDataAdapter.setOnLoadMoreListener(SearchDataRefreshHandler.this, signpeopleRecyclerView);
                                    //重复执行动画
                                    mSearchDataAdapter.isFirstOnly(false);
                                    signpeopleRecyclerView.setAdapter(mSearchDataAdapter);
                                    signpeopleRecyclerView.scrollToPosition(0);
                                    mLoading.setVisibility(View.GONE);
                                    ll_noData.setVisibility(View.GONE);
                                }
                                else {
                                    ll_noData.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！SignPeopleData", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！SignPeopleData", Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();
    }



    private void refreshSignPeopleData(List<MoneyPeople.DataListBean> SignPeopleData) {
        refreshSignPeopleData.clear();
        refreshSignPeopleData.addAll(SignPeopleData);
    }


    public void onRefresh(String query) {
            refresh(query);
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }


    private void loadMore() {
        page=page+1;
        String url = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSilvers&token_id="+token_id+"&page="+page+"&rows=10"+"&activity_id="+activity_id+"&condition="+queryWord;
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
                                    mSearchDataAdapter.loadMoreComplete();
                                    mSearchDataAdapter.loadMoreEnd();
                                }
                                else {
                                    List<MoneyPeople.DataListBean> data = moneyPeople.getDataList();
                                    LoadMoreData(data);
                                    mSearchDataAdapter.loadMoreComplete();
                                }
                            }
                        }
                    }
                })
                .build()
                .get();

    }

    private void LoadMoreData(List<MoneyPeople.DataListBean> data) {
        mSearchDataAdapter.addData(data);
    }

}
