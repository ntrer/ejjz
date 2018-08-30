package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Activity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ActivityAdapter;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.List;

public class ProActivityActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private String token_id=null;
    private String activityId=null;
    private String activityCode=null;
    private String activityName=null;
    List<Activity.DataListBean> dataList;
    private ActivityAdapter mActivityAdapter;
    private Toolbar mToolbar;
    @Override
    public int setLayout() {
        return R.layout.activity_pro_activity;
    }

    @Override
    public void init() {
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_activity);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        token_id= PreferencesUtils.getString(this,"token_id");
        initData(token_id);
        initRecyclerView();
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    private void initData(String token_id) {
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getActivitys&token_id="+token_id;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("活动列表",response);
                        if(response!=null){
                            Activity activity = JSONUtil.fromJson(response, Activity.class);
                            if(activity.getDataList()!=null){
                                dataList = activity.getDataList();
                                showData(dataList);
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(ProActivityActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(ProActivityActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showData(final List<Activity.DataListBean> dataList) {
        mActivityAdapter=new ActivityAdapter(R.layout.item_activity,dataList);
        mRecyclerView.setAdapter(mActivityAdapter);
        mRecyclerView.scrollToPosition(0);
        if(dataList.size()>0){
            mActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    activityId=dataList.get(position).getActivityId();
                    activityName=dataList.get(position).getActivityName();
                    activityCode=String.valueOf(dataList.get(position).getActivityCode());
                    Intent data=new Intent();
                    data.putExtra("activityId",activityId);
                    data.putExtra("activityName",activityName);
                    data.putExtra("activityCode",activityCode);
                    setResult(100,data);
                    ActivityCompat.finishAfterTransition(ProActivityActivity.this);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

}
