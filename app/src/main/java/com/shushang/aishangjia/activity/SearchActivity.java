package com.shushang.aishangjia.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.githang.statusbar.StatusBarCompat;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.refreshHander.SearchDataRefreshHandler;

public class SearchActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SearchDataRefreshHandler mSearchDataRefreshHandler;
    private ProgressBar mProgressBar;
    private LinearLayout ll_noData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), false);
        init();
    }


    public void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_search);
        mProgressBar= (ProgressBar) findViewById(R.id.loading);
        ll_noData= (LinearLayout) findViewById(R.id.ll_no_data);
        initDataRecyclerView();
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSearchDataRefreshHandler=SearchDataRefreshHandler.create(mRecyclerView,mProgressBar,ll_noData);
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

    private void initDataRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        //searchview在menn中
        MenuItem item = menu.findItem(R.id.action_search);
        //通过MenuItem得到SearchView
        final SearchView search= (SearchView) MenuItemCompat.getActionView(item);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(query);
                search.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getData(String query) {
        mSearchDataRefreshHandler.onRefresh(query);
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
