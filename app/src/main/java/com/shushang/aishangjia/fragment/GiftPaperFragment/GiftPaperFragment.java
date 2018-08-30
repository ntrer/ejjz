package com.shushang.aishangjia.fragment.GiftPaperFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.TabList;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.GiftPaperFragment.refreshHandler.GiftPaperRefreshHandler;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter2;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.TranslationAnimate;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.List;

/**
 * Created by YD on 2018/8/6.
 */

public class GiftPaperFragment extends BaseFragment {

    private RecyclerView mRecyclerView,mGiftPaperRecyclerView,mRecyclerView2;
    private Toolbar mToolbar;
//    private FrameLayout mFrameLayout;
    private TextView mTextView;
//    private ImageView mImageView;
    private SwipeRefreshLayout mSwipeRefreshLayout=null;
    private GiftPaperRefreshHandler mGiftPaperRefreshHandler;
    private TabRecyclerViewAdapter tabRecyclerViewAdapter;
//    private TabRecyclerViewAdapter2 mTabRecyclerViewAdapter;
    private List<TabList.DataListBean> data;
    private RelativeLayout mLoading;
    private boolean isFirst=true;
    private String allData="100";
    private LinearLayout llnodata;
    private MainActivity mMainActivity;
    private int [] SourseLocation=new int[2];
    private int [] targetLocation=new int[2];
    private TranslationAnimate mTranslationAnimate;
    public Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    getData();
                    break;
            }
            return false;
        }
    });
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rl_tab);
//        mRecyclerView2=rootView.findViewById(R.id.rv_sign2);
        mGiftPaperRecyclerView=rootView.findViewById(R.id.rv_sign);
        mToolbar=rootView.findViewById(R.id.toolbar);
        mTextView=rootView.findViewById(R.id.mounth);
        llnodata=rootView.findViewById(R.id.ll_no_data);
        mSwipeRefreshLayout=rootView.findViewById(R.id.srl_home);
//        mImageView=rootView.findViewById(R.id.more);
//        mFrameLayout=rootView.findViewById(R.id.boom);
        mLoading=rootView.findViewById(R.id.loading);
        mToolbar.setTitle("");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTabData(allData);
                mTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                tabRecyclerViewAdapter.setThisPosition(100);
                tabRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        //设置支持toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        initDataRecyclerView();
        mGiftPaperRefreshHandler=GiftPaperRefreshHandler.create(mMainActivity,mSwipeRefreshLayout,mGiftPaperRecyclerView,mLoading,llnodata);
//        mTranslationAnimate=new TranslationAnimate(SourseLocation,targetLocation,mFrameLayout,mImageView);
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mTranslationAnimate.move();
//            }
//        });
    }

    private void initDataRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(getContext());
        mGiftPaperRecyclerView.setLayoutManager(linermanager);
    }


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_gift, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getTabData("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity= (MainActivity) context;
        mMainActivity.setHandler(mHandler);
    }


    //获取tab栏数据
    private void getTabData(final String allData) {
        String token_id= PreferencesUtils.getString(mContext,"token_id");
        String activity_id= PreferencesUtils.getString(mContext,"activityId");
        String url= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getMerchants&token_id="+token_id+"&activity_id="+activity_id;
        Log.d("TabList",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("TabList",response);
                        TabList tabList = JSONUtil.fromJson(response, TabList.class);
                        if(tabList.getRet().equals("101")||tabList.getRet().equals("201")){
                            Toast.makeText(mContext, "token失效了", Toast.LENGTH_SHORT).show();
                            PreferencesUtils.putString(mContext,"token_id",null);
                            startActivity(new Intent(getActivity(), LoginActivity2.class));
                            getActivity().finish();
                        }
                        else {
                            if(tabList.getRet().equals("200")){
                                data = tabList.getDataList();
                                if(data.size()!=0){
                                    showTabData(data);
                                    isFirst=false;
                                    if(!allData.equals("")){
                                        mGiftPaperRefreshHandler.getTab(allData);
                                    }else {
                                        mGiftPaperRefreshHandler.onRefresh();
                                    }
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
                })
                .build()
                .get();
    }



    private void showTabData(List<TabList.DataListBean>  data) {
        if(isFirst){
            tabRecyclerViewAdapter=new TabRecyclerViewAdapter(R.layout.tab_items,data);
            tabRecyclerViewAdapter.setThisPosition(100);
//            mTabRecyclerViewAdapter=new TabRecyclerViewAdapter2(R.layout.tab_items2,data);
//            mTabRecyclerViewAdapter.setThisPosition(100);
        }
        initRecyclerView(tabRecyclerViewAdapter);
//        initRecyclerView2(mTabRecyclerViewAdapter);
    }

    private void initRecyclerView2(final TabRecyclerViewAdapter2 tabRecyclerViewAdapter2) {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        mRecyclerView2.setLayoutManager(gridLayoutManager);
        mRecyclerView2.setAdapter(tabRecyclerViewAdapter2);
        tabRecyclerViewAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTextView.setTextColor(getResources().getColor(R.color.darker_gray));
                tabRecyclerViewAdapter.setThisPosition(position);
                tabRecyclerViewAdapter.notifyDataSetChanged();
                mGiftPaperRefreshHandler.switchData(data.get(position).getMerchant_id());
                mRecyclerView.scrollToPosition(position);
                mTranslationAnimate.close();
            }
        });
    }


    private void initRecyclerView(final TabRecyclerViewAdapter tabRecyclerViewAdapter) {
        final LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(manager);
        //解决嵌套滑动冲突
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(tabRecyclerViewAdapter);
        tabRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTextView.setTextColor(getResources().getColor(R.color.darker_gray));
                tabRecyclerViewAdapter.setThisPosition(position);
                tabRecyclerViewAdapter.notifyDataSetChanged();
                mGiftPaperRefreshHandler.switchData(data.get(position).getMerchant_id());
            }
        });
    }


    public void getData(){
        mGiftPaperRefreshHandler.onRefresh();
    }

}
