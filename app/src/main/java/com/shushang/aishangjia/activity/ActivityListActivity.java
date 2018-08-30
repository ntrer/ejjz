package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.ActivityList;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ActivityListAdapter;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.ActivityManager.ActivityStackManager;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityListActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private String token_id = null;
    private Button mButton;
    private LinearLayout ll_nodata;
    private ActivityListAdapter mActivityListAdapter;
    private List<ActivityList.DataListBean> dataList=new ArrayList<>();
    //退出时的时间
    private long mExitTime;
    @Override
    public int setLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_activity);
        ll_nodata= (LinearLayout) findViewById(R.id.ll_no_data);
        token_id = PreferencesUtils.getString(this, "token_id");
        mButton= (Button) findViewById(R.id.btn_quit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesUtils.putString(getApplicationContext(), "company", null);
                PreferencesUtils.putString(getApplicationContext(), "user_name", null);
                PreferencesUtils.putString(getApplicationContext(), "password", null);
                PreferencesUtils.putString(getApplicationContext(), "token_id",null);
                PreferencesUtils.putString(getApplicationContext(),"type",null);
                startActivity(new Intent(ActivityListActivity.this, LoginActivity2.class));
                finish();
            }
        });
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    private void initData() {
        String url = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getActivitys&token_id=" + token_id;
        Log.d("BaseUrl", url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            ActivityList activityList = JSONUtil.fromJson(response, ActivityList.class);
                            if(activityList.getRet().equals("200")){
                                dataList = activityList.getDataList();
                                if(dataList.size()!=0){
                                    ll_nodata.setVisibility(View.GONE);
                                    mButton.setVisibility(View.GONE);
                                    showData(dataList);
                                }
                                else {
                                      ll_nodata.setVisibility(View.VISIBLE);
                                      mButton.setVisibility(View.VISIBLE);
//                                    PreferencesUtils.putString(ActivityListActivity.this, "token_id",null);
//                                    startActivity(new Intent(ActivityListActivity.this,LoginActivity2.class));
//                                    finish();
                                }
                            }
                            else {
                                PreferencesUtils.putString(ActivityListActivity.this, "token_id",null);
                                startActivity(new Intent(ActivityListActivity.this,LoginActivity2.class));
                                finish();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        ll_nodata.setVisibility(View.VISIBLE);
                        mButton.setVisibility(View.VISIBLE);
                        Toast.makeText(ActivityListActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(ActivityListActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showData(final List<ActivityList.DataListBean> dataList) {
        mActivityListAdapter=new ActivityListAdapter(R.layout.item_activitylist,dataList);
        mRecyclerView.setAdapter(mActivityListAdapter);
        mRecyclerView.scrollToPosition(0);
        mActivityListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String activityId =dataList.get(position).getActivityId();
                String roleType=dataList.get(position).getRoleType();
                PreferencesUtils.putString(ActivityListActivity.this,"activityId",activityId);
                PreferencesUtils.putString(ActivityListActivity.this,"roleType",roleType);
                startActivity(new Intent(ActivityListActivity.this, MainActivity.class));
               finish();
            }
        });
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //退出应用
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityStackManager.getActivityStackManager().popAllActivity();//remove all activity.
            finish();
            System.exit(0);
        }
    }
}
