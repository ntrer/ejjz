package com.shushang.aishangjia.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Progress;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ProgressAdapter;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.List;

public class ProgressActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String token_id=null;
    private ProgressAdapter mShengAdapter;
    private Toolbar mToolbar;
    private static final int REQUEST_CODE_CHILDCITY = 2011;
    private  List<Progress.DataListBean> mDataListBeen;
    private String progress=null;
    @Override
    public int setLayout() {
        return R.layout.activity_progress;
    }

    @Override
    public void init() {
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_progress);
        token_id= PreferencesUtils.getString(this,"token_id");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData(token_id);
        initRecyclerView();
        PreferencesUtils.putString(ProgressActivity.this,"DecorationProgressName",null);
    }

    private void initData(String token_id) {
        String url= BaseUrl.BASE_URL+"decorationProgressController.do?method=getDecorationProgress&token_id="+token_id;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            Progress progress = JSONUtil.fromJson(response, Progress.class);
                            if(progress.getRet().equals("200")){
                                mDataListBeen = progress.getDataList();
                                showData(mDataListBeen);
                            }
                            else {
                                Toast.makeText(ProgressActivity.this, ""+progress.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(ProgressActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(ProgressActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    private void showData(final List<Progress.DataListBean> dataListBeen) {
        mShengAdapter=new ProgressAdapter(R.layout.item_sheng,dataListBeen);
        mRecyclerView.setAdapter(mShengAdapter);
        mRecyclerView.scrollToPosition(0);
        mShengAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                progress=dataListBeen.get(position).getDecorationProgressName();
                PreferencesUtils.putString(ProgressActivity.this,"DecorationProgressName",progress);
                finish();
            }
        });
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
