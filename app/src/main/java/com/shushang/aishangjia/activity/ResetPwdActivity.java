package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.githang.statusbar.StatusBarCompat;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.MainActivity2;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import okhttp3.Call;

public class ResetPwdActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private AppCompatEditText mAppCompatEditText1,mAppCompatEditText2;
    private Button mButton;
    private String token_id = null;
    private Dialog mRequestDialog;
    private  Dialog modifyPasswordOkDialog;
    @Override
    public int setLayout() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    public void init() {
        modifyPasswordOkDialog = ExtAlertDialog.createModifyPasswordOkDialog(ResetPwdActivity.this);
        //沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), false);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mButton= (Button) findViewById(R.id.submit);
        mButton.setOnClickListener(this);
        mAppCompatEditText1= (AppCompatEditText) findViewById(R.id.old_pwd);
        mAppCompatEditText2= (AppCompatEditText) findViewById(R.id.new_pwd);
        token_id = PreferencesUtils.getString(this, "token_id");
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                String old_pwd=mAppCompatEditText1.getText().toString();
                String new_pwd=mAppCompatEditText2.getText().toString();
                if(old_pwd.equals("")||new_pwd.equals("")){
                    ToastUtils.showLong("没有填写完整");
                    return;
                }
                resetpwd(old_pwd,new_pwd);
        }
    }

    private void resetpwd(String old_pwd, String new_pwd) {
        mRequestDialog.show();
        String url= BaseUrl.BASE_URL+"phoneApi/activityLogin.do?method=updatePassword&token_id="+token_id;
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("oldpassword", md5(old_pwd));
        paramsMap.put("newpassword", md5(new_pwd));
        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
              Log.d("失败",e.toString());
                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                    mRequestDialog.dismiss();
                }
            }

            @Override
            public void onResponse(String response) {
                Log.d("修改活动",response);
                if(response!=null){
                    Response response1 = JSONUtil.fromJson(response, Response.class);
                    if(response1.getRet().equals("200")){
                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                            mRequestDialog.dismiss();
                        }
                        modifyPasswordOkDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(modifyPasswordOkDialog!=null&&modifyPasswordOkDialog.isShowing()){
                                    modifyPasswordOkDialog.dismiss();
                                    startActivity(new Intent(ResetPwdActivity.this, MainActivity2.class));
                                    finish();
                                }
                            }
                        },1000);
                    }
                }
            }
        });
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    public static String toHexString(byte[] b) {
        //String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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
