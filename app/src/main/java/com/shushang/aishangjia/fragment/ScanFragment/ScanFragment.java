package com.shushang.aishangjia.fragment.ScanFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.necer.ncalendar.calendar.WeekCalendar;
import com.necer.ncalendar.listener.OnWeekCalendarChangedListener;
import com.necer.ncalendar.utils.MyLog;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.Bean.ScanTabData;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.MainActivity2;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.AppPeopleActivity;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.NewPeopleDetailActivity;
import com.shushang.aishangjia.activity.SignActivity;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.SignFragment.SignFragment;
import com.shushang.aishangjia.fragment.SignFragment.adapter.SignAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.MyFab.FabAlphaAnimate;
import com.shushang.aishangjia.ui.MyFab.FabAttributes;
import com.shushang.aishangjia.ui.MyFab.OnFabClickListener;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  : demo about FragmentUtils
 * </pre>
 */
public class ScanFragment extends BaseFragment implements View.OnClickListener, OnWeekCalendarChangedListener, OnFabClickListener,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WeekCalendar ncalendar;
    private RecyclerView mRecyclerView;
    private SignAdapter mScanAdapter;
    private TextView mTextView;
    private TextView mTextView1,mTextView2,mTextView3,mTextView4;
    private  List<NewPeople.DataListBean> dataList=new ArrayList<>();
    private  List<NewPeople.DataListBean> data=new ArrayList<>();
    private static final int REQUEST_CODE_SIGN= 2003;
    private static final int REQUEST_CODE_ADD= 2004;
    private LocalDate time=null;
    private String token_id=null;
    private boolean isMounth=false;
    private View head=null;
    private MainActivity mMainActivity;
    private SignFragment mSignFragment;
    private int page=1;
    private  SuspensionFab fabTop;
    private boolean isClick=false;
    private Handler mFabHandler=new Handler();
    private LinearLayout ll_nodata;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rv_data);
        mTextView=rootView.findViewById(R.id.this_month);
        fabTop= (SuspensionFab) rootView.findViewById(R.id.fab_top);
        ncalendar = (WeekCalendar) rootView.findViewById(R.id.weekCalendar);
        ll_nodata=rootView.findViewById(R.id.ll_no_data);
        ncalendar.setOnWeekCalendarChangedListener(this);
        mTextView.setOnClickListener(this);
        FabAttributes collection = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.note))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();

        FabAttributes email = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#FF9800"))
                .setSrc(getResources().getDrawable(R.mipmap.addp))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();
        fabTop.addFab(collection, email);
        fabTop.setAnimationManager(new FabAlphaAnimate(fabTop));
        fabTop.setFabClickListener(this);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_sign, null);
        mSwipeRefreshLayout=view.findViewById(R.id.srl_home);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity2 mainActivity = (MainActivity2) getActivity();
        try {
            mSignFragment = (SignFragment)mainActivity.getSupportFragmentManager().findFragmentByTag("tag1");
        }
        catch (Exception e){
            Log.d("es",e.toString());
        }
    }

    @Override
    public void initData() {
        super.initData();
        token_id= PreferencesUtils.getString(getActivity(),"token_id");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getData(time,token_id);
    }

    public void getData(final LocalDate time, final String token_id) {
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomers&token_id="+token_id+"&page=1&rows=10"+"&date="+time+"&type=0";
        Log.d("BaseUrl",url);
        mSwipeRefreshLayout.setRefreshing(true);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            mSwipeRefreshLayout.setRefreshing(false);
                            Log.d("AppPeopleActivity",response);
                            NewPeople test = JSONUtil.fromJson(response, NewPeople.class);
                            if(test.getRet().equals("101")||test.getRet().equals("201")){
                                Toast.makeText(mContext, "token失效了", Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(mContext,"token_id",null);
                                startActivity(new Intent(getActivity(), LoginActivity2.class));
                                getActivity().finish();
                            }
                            else if(test.getRet().equals("200")){
                                dataList = test.getDataList();
                                getTabData(time,token_id);
                                if(dataList.size()!=0){
                                    showTabData(dataList);
                                    ll_nodata.setVisibility(View.GONE);
                                }
                                else {
                                    showTabData(dataList);
                                    ll_nodata.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showTabData(final List<NewPeople.DataListBean> dataList) {
        head = View.inflate(getActivity(), R.layout.headerview, null);
        mTextView1=head.findViewById(R.id.num1);
        mTextView2=head.findViewById(R.id.num2);
        mTextView3=head.findViewById(R.id.num3);
        mTextView4=head.findViewById(R.id.num4);
        mScanAdapter = new SignAdapter(R.layout.item_sign, dataList);
        final LinearLayoutManager linermanager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linermanager);
        mScanAdapter.addHeaderView(head);
        mScanAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mScanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(mContext,NewPeopleDetailActivity.class);
                NewPeople.DataListBean dataListBean = dataList.get(position);
                intent.putExtra("data",dataListBean);
                startActivity(intent);
            }
        });
        //重复执行动画
        mScanAdapter.isFirstOnly(false);
        mRecyclerView.setAdapter(mScanAdapter);
    }

    public void setDate(View view) {
        ncalendar.setDate("2017-12-31");
    }


    public void toToday(View view) {
        ncalendar.toToday();
    }

    public void toNextPager(View view) {
        ncalendar.toNextPager();
    }

    public void toLastPager(View view) {
        ncalendar.toLastPager();
    }

    @Override
    public void onWeekCalendarChanged(LocalDate date) {
        MyLog.d("dateTime::" + date);
        time=date;
        if(!isClick){
            isMounth=false;
            getData(time,token_id);
        }
    }


    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
        if (tag.equals(1)) {
            mFabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), SignActivity.class),REQUEST_CODE_SIGN);
        }else if (tag.equals(2)){
            mFabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), AppPeopleActivity.class),REQUEST_CODE_ADD);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        String type=null;
        if(isMounth){
            type="1";
        }else {
            type="0";
        }
        page=page+1;
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomers&token_id="+token_id+"&page="+page+"&rows=10"+"&date="+time+"&type="+type;
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null) {
                            Log.d("nnnnnnn",response);
                            NewPeople test = JSONUtil.fromJson(response, NewPeople.class);
                            if(test.getRet().equals("200")){
                                if(page>test.getIntmaxPage()){
                                    page=1;
                                    mScanAdapter.loadMoreComplete();
                                    mScanAdapter.loadMoreEnd();
                                }
                                else {
                                    List<NewPeople.DataListBean> dataList = test.getDataList();
                                    LoadMoreData(dataList);
                                    Log.d("33333333333",response);
                                    mScanAdapter.loadMoreComplete();
                                }
                            }
                            else {
                                mScanAdapter.loadMoreComplete();
                            }
                        }
                    }
                })
                .build()
                .get();

    }

    private void LoadMoreData(List<NewPeople.DataListBean> dataList) {
        if(isMounth){
            if(dataList.size()!=0){
                mScanAdapter.addData(dataList);
                mScanAdapter.loadMoreComplete();
            }
        }
        else {
            if(dataList.size()!=0){
                mScanAdapter.addData(dataList);
                mScanAdapter.loadMoreComplete();
            }
            mScanAdapter.addData(dataList);
        }
    }

    @Override
    public void onRefresh() {
        if(isMounth){
            getMounthData(time,token_id);
        }
        else {
            getData(time,token_id);
        }
    }


    private void getMounthData(final LocalDate time, final String token_id) {
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomers&token_id="+token_id+"&page=1&rows=10"+"&date="+time+"&type=1";
        mSwipeRefreshLayout.setRefreshing(true);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("wocaocacoa",response);
                        if(response!=null){
                            isClick=false;
                            mSwipeRefreshLayout.setRefreshing(false);
                            NewPeople signList = JSONUtil.fromJson(response, NewPeople.class);
                            if(signList.getRet().equals("101")||signList.getRet().equals("201")){
                                Toast.makeText(mContext, "token失效了", Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(mContext,"token_id",null);
                                startActivity(new Intent(getActivity(), LoginActivity2.class));
                                getActivity().finish();
                            }
                            else if(signList.getRet().equals("200")){
                                if(signList.getDataList()!=null){
                                    dataList = signList.getDataList();
                                    getTabMounthData(time,token_id);
                                    if(dataList.size()!=0){
                                        showTabData(dataList);
                                        ll_nodata.setVisibility(View.GONE);
                                    }
                                    else {
                                        showTabData(dataList);
                                        ll_nodata.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                        }

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showLong(msg);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.this_month:
                isMounth=true;
                isClick=true;
                ncalendar.toToday();
                getMounthData(time,token_id);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN) {
            mSignFragment.getData(time,token_id);
        }else if(requestCode==REQUEST_CODE_ADD){
            getData(time,token_id);
        }
    }


    private void getTabData(final LocalDate time, String token_id){
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomerCount&token_id="+token_id+"&date="+time+"&type=0";
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("wocaocaocacoacoa",response);
                        if(response!=null){
                            ScanTabData scanTabData = JSONUtil.fromJson(response, ScanTabData.class);
                            if(scanTabData.getData()!=null){
                                if(mTextView1.getText()==String.valueOf(scanTabData.getData().getCustomerNum())){

                                }else {
                                    mTextView1.setText(String.valueOf(scanTabData.getData().getCustomerNum()));
                                    mTextView2.setText(String.valueOf(scanTabData.getData().getAcCustomerNum()));
                                    mTextView3.setText(String.valueOf(Math.round(Float.parseFloat(scanTabData.getData().getAcRate())*100))+"%");
                                    mTextView4.setText(String.valueOf(scanTabData.getData().getRefuseCustomer()));
                                }
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void getTabMounthData(LocalDate time,String token_id){
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomerCount&token_id="+token_id+"&date="+time+"&type=1";
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("wocaocaocacoacoa",response);
                        if(response!=null){
                            ScanTabData scanTabData = JSONUtil.fromJson(response, ScanTabData.class);
                            if(scanTabData.getData()!=null){
                                if(mTextView1.getText()==String.valueOf(scanTabData.getData().getCustomerNum())){

                                }else {
                                    mTextView1.setText(String.valueOf(scanTabData.getData().getCustomerNum()));
                                    mTextView2.setText(String.valueOf(scanTabData.getData().getAcCustomerNum()));
                                    mTextView3.setText(String.valueOf(Math.round(Float.parseFloat(scanTabData.getData().getAcRate()))));
                                    mTextView4.setText(String.valueOf(scanTabData.getData().getRefuseCustomer()));
                                }
                            }
                        }

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFabHandler.removeCallbacksAndMessages(getActivity());
    }
}
