package com.shushang.aishangjia.fragment.HomeFragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.codingending.popuplayout.PopupLayout;
import com.shushang.aishangjia.Bean.TabList;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter2;
import com.shushang.aishangjia.fragment.HomeFragment.refreshHandler.HomeDataRefreshHandler;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  : demo about FragmentUtils
 * </pre>
 */
public class HomeFragment extends BaseFragment {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView,mSignPeopleRecyclerView,mRecyclerView2;
//    private FrameLayout mFrameLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout=null;
    private ImageView mImageView;
    private TextView mTextView,tabTextView;
    private TabRecyclerViewAdapter tabRecyclerViewAdapter;
    private TabRecyclerViewAdapter2 mTabRecyclerViewAdapter;
    private HomeDataRefreshHandler mHomeDataRefreshHandler;
    private RelativeLayout mLoading;
    private List<TabList.DataListBean> data;
    private boolean isFirst=true;
    private String allData="100";
    private LinearLayout llnodata;
    private MainActivity mMainActivity;
    private View mView;
    private PopupLayout popupLayout;
    private boolean useRadius=true;//是否使用圆角特性
//    private int [] SourseLocation=new int[2];
//    private int [] targetLocation=new int[2];
//    private TranslationAnimate mTranslationAnimate;

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
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity= (MainActivity) context;
        mMainActivity.setHandler(mHandler);
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rl_tab);
//        mRecyclerView2=rootView.findViewById(R.id.rv_sign2);
        mSignPeopleRecyclerView=rootView.findViewById(R.id.rv_sign);
        mSwipeRefreshLayout=rootView.findViewById(R.id.srl_home);
//        mFrameLayout=rootView.findViewById(R.id.boom);
        llnodata=rootView.findViewById(R.id.ll_no_data);
//        mImageView=rootView.findViewById(R.id.more);
        mToolbar=rootView.findViewById(R.id.toolbar);
//        mView=View.inflate(getActivity(),R.layout.tablist,null);
//        mRecyclerView2=mView.findViewById(R.id.rv_sign2);
//        popupLayout=PopupLayout.init(getActivity(),mView);
//        popupLayout.setUseRadius(useRadius);
//        popupLayout.setHeight(850,true);//手动设置弹出布局的高度
        //popupLayout.setWidth(320,true);//手动设置弹出布局的宽度
        mToolbar.setTitle("");
        mTextView=rootView.findViewById(R.id.mounth);
        mLoading=rootView.findViewById(R.id.loading);
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
        mHomeDataRefreshHandler=HomeDataRefreshHandler.create(mMainActivity,mSwipeRefreshLayout,mSignPeopleRecyclerView,mLoading,mHandler,llnodata);
//        mTranslationAnimate=new TranslationAnimate(SourseLocation,targetLocation,mFrameLayout,mImageView);
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupLayout.show();//默认从底部弹出
//            }
//        });

    }

    private void initDataRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(getContext());
        mSignPeopleRecyclerView.setLayoutManager(linermanager);
    }

    @Override
    public void initData() {
        super.initData();
        getTabData("");
    }

    //获取tab栏数据
    private void getTabData(final String allData) {
        String token_id= PreferencesUtils.getString(mContext,"token_id");
        String activity_id= PreferencesUtils.getString(mContext,"activityId");
//        String url="https://raw.githubusercontent.com/ntrer/JSONApi/master/TabApi2.json";
        String url= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getMerchants&token_id="+token_id+"&activity_id="+activity_id;
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        TabList tabList = JSONUtil.fromJson(response, TabList.class);
                        if(response!=null){
                            if(tabList.getRet().equals("101")||tabList.getRet().equals("201")){
                                Toast.makeText(mContext, "token失效了", Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(mContext,"token_id",null);
                                startActivity(new Intent(getActivity(), LoginActivity2.class));
                                getActivity().finish();
                            }
                            else {
                                Log.d("TabList",response);
                                if(tabList.getRet().equals("200")){
                                    if(tabList.getDataList()!=null){
                                        data = tabList.getDataList();
                                        if(data.size()!=0){
                                            showTabData(data);
                                            isFirst=false;
                                            if(!allData.equals("")){
                                                mHomeDataRefreshHandler.getTab(allData);
                                            }else {
                                                mHomeDataRefreshHandler.onRefresh();
                                            }
                                        }
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
                mHomeDataRefreshHandler.switchData(data.get(position).getMerchant_id());
                mRecyclerView.scrollToPosition(position);
                popupLayout.dismiss();
//                mTranslationAnimate.close();
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
                mHomeDataRefreshHandler.switchData(data.get(position).getMerchant_id());
            }
        });
    }


    public void getData(){
        mHomeDataRefreshHandler.onRefresh();
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HomeFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomeFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(popupLayout!=null){
//            popupLayout.dismiss();
//        }
    }
}
