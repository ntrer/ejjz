package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Qu;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.QuAdapter;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.List;

public class QuActivity extends BaseActivity {
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private String token_id=null;
    private String sheng_code=null;
    private String qu_code=null;
    private String shi_code=null;
    private String sheng_name=null;
    private String shi_name=null;
    private String qu_name=null;
    private List<Qu.DataListBean> dataList;
    private QuAdapter mQuAdapter;
    private Toolbar mToolbar;

    @Override
    public int setLayout() {
        return R.layout.activity_qu;
    }

    @Override
    public void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        sheng_code=intent.getStringExtra("sheng_code");
        sheng_name=intent.getStringExtra("sheng_name");
        shi_code=intent.getStringExtra("shi_code");
        shi_name=intent.getStringExtra("shi_name");
        mTextView= (TextView) findViewById(R.id.select_qu);
        mTextView.setText(sheng_name+shi_name);
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_qu);
        token_id= PreferencesUtils.getString(this,"token_id");
        initData(token_id);
        initRecyclerView();
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    private void initData(String token_id) {
        String url= BaseUrl.BASE_URL+"addressController.do?method=getQuByShiCode&token_id="+token_id+"&shiCode="+shi_code;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Qu qu = JSONUtil.fromJson(response, Qu.class);
                        dataList = qu.getDataList();
                        showData(dataList);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(QuActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(QuActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showData(final List<Qu.DataListBean> dataList) {
        mQuAdapter=new QuAdapter(R.layout.item_sheng,dataList);
        mRecyclerView.setAdapter(mQuAdapter);
        mRecyclerView.scrollToPosition(0);
        mQuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                qu_code=dataList.get(position).getCode();
                qu_name=dataList.get(position).getMingcheng();
                PreferencesUtils.putString(QuActivity.this,"sheng_code",sheng_code);
                PreferencesUtils.putString(QuActivity.this,"sheng_name",sheng_name);
                PreferencesUtils.putString(QuActivity.this,"shi_code",shi_code);
                PreferencesUtils.putString(QuActivity.this,"shi_name",shi_name);
                PreferencesUtils.putString(QuActivity.this,"qu_code",qu_code);
                PreferencesUtils.putString(QuActivity.this,"qu_name",qu_name);
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
