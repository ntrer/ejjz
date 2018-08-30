package com.shushang.aishangjia.fragment.MyFragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.ActivityListActivity;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.ResetPwdActivity;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;
import com.tencent.bugly.beta.Beta;
import com.umeng.analytics.MobclickAgent;
import com.xys.libzxing.zxing.utils.JSONUtil;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  : demo about FragmentUtils
 * </pre>
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    private String token_id=null;
    private String username=null;
    private String shangjia=null;
    private LinearLayout ll_reset;
    private LinearLayout ll_quit;
    private LinearLayout ll_activity;
    private LinearLayout ll_check;
    private TextView mTextView1,mTextView2;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ll_reset=rootView.findViewById(R.id.re_password);
        ll_quit=rootView.findViewById(R.id.quit);
        ll_check=rootView.findViewById(R.id.check_update);
        mTextView1=rootView.findViewById(R.id.login_info_view);
        mTextView2=rootView.findViewById(R.id.login_view);
        mTextView1.setText(username);
        mTextView2.setText(shangjia);
        ll_activity=rootView.findViewById(R.id.switch_activity);
        ll_reset.setOnClickListener(this);
        ll_quit.setOnClickListener(this);
        ll_activity.setOnClickListener(this);
        ll_check.setOnClickListener(this);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_my, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        token_id= PreferencesUtils.getString(getActivity(),"token_id");
        username= PreferencesUtils.getString(mContext, "xingming");
        shangjia= PreferencesUtils.getString(mContext, "shangjia_name");
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyFragment");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_password:
                startActivity(new Intent(getActivity(), ResetPwdActivity.class));
                break;
            case R.id.switch_activity:
                startActivity(new Intent(getActivity(), ActivityListActivity.class));
                getActivity().finish();
                break;
            case R.id.check_update:
                Beta.checkUpgrade();
                break;
            case R.id.quit:
                ExtAlertDialog.showSureDlg(getActivity(), null, getString(R.string.logout_tip), getString(R.string.exit_login), new ExtAlertDialog.IExtDlgClick() {
                    @Override
                    public void Oclick(int result) {
                        if (result == 1) {
                            String url= BaseUrl.BASE_URL+"phoneApi/activityLogin.do?method=logout&token_id="+token_id;
                            Log.d("BaseUrl",url);
                            RestClient.builder()
                                    .url(url)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            if(response!=null){
                                                Response response1 = JSONUtil.fromJson(response, Response.class);
                                                try {
                                                    if(response1.getRet().equals("200")){
                                                        Log.d("退出",response);
                                                        Toast.makeText(mContext, "退出成功", Toast.LENGTH_SHORT).show();
                                                        PreferencesUtils.putString(mContext, "company", null);
                                                        PreferencesUtils.putString(mContext, "user_name", null);
                                                        PreferencesUtils.putString(mContext, "password", null);
                                                        PreferencesUtils.putString(mContext, "token_id",null);
                                                        PreferencesUtils.putString(mContext,"roleType",null);
                                                        startActivity(new Intent(getActivity(), LoginActivity2.class));
                                                        getActivity().finish();
                                                    }
                                                    else {
                                                        Toast.makeText(mContext, "退出失败"+response1.getMsg(), Toast.LENGTH_SHORT).show();
                                                        PreferencesUtils.putString(mContext, "company", null);
                                                        PreferencesUtils.putString(mContext, "user_name", null);
                                                        PreferencesUtils.putString(mContext, "password", null);
                                                        PreferencesUtils.putString(mContext, "token_id",null);
                                                        PreferencesUtils.putString(mContext,"roleType",null);
                                                        startActivity(new Intent(getActivity(), LoginActivity2.class));
                                                        getActivity().finish();
                                                    }
                                                }
                                                catch (Exception e){
                                                    Log.d("e",e.toString());
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
                    }
                });
                break;
        }
    }
}
