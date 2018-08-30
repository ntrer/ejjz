package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Sheng;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ShengAdapter;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.List;

public class CityActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String token_id=null;
    private List<Sheng.DataListBean> mDataListBeen;
    private ShengAdapter mShengAdapter;
    private String sheng_code=null;
    private String sheng_name=null;
    private static final int REQUEST_CODE_CHILDCITY = 2011;
    private Toolbar mToolbar;
    @Override
    public int setLayout() {
        return R.layout.activity_city;
    }

    @Override
    public void init() {
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_sheng);
        token_id= PreferencesUtils.getString(this,"token_id");
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData(token_id);
        initRecyclerView();
    }

    private void initData(String token_id) {
        String url= BaseUrl.BASE_URL+"addressController.do?method=getSheng&token_id="+token_id;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            Sheng sheng = JSONUtil.fromJson(response, Sheng.class);
                            if(sheng.getRet().equals("200")){
                                mDataListBeen = sheng.getDataList();
                                showData(mDataListBeen);
                            }
                            else {
                                ToastUtils.showLong(""+sheng.getMsg());
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(CityActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(CityActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    private void showData(List<Sheng.DataListBean> dataListBeen) {
        mShengAdapter=new ShengAdapter(R.layout.item_sheng,dataListBeen);
        mRecyclerView.setAdapter(mShengAdapter);
        mRecyclerView.scrollToPosition(0);
        mShengAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                sheng_code=mDataListBeen.get(position).getCode();
                sheng_name=mDataListBeen.get(position).getMingcheng();
                Intent intent=new Intent();
                intent.putExtra("sheng_code",sheng_code);
                intent.putExtra("sheng_name",sheng_name);
                intent.setClass(CityActivity.this,ChildCityActivity.class);
                startActivity(intent);
                PreferencesUtils.putString(CityActivity.this, "sheng_code", null);
                PreferencesUtils.putString(CityActivity.this, "sheng_name", null);
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
