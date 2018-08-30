package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shushang.aishangjia.Bean.Login;
import com.shushang.aishangjia.MainActivity2;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.utils.ActivityManager.ActivityStackManager;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.shushang.aishangjia.R.id.fab;

public class LoginActivity2 extends BaseActivity implements View.OnClickListener {

    private EditText mTxtUserName, mTxtPassword, mTxtCompany;
    private TextInputLayout mViewMobiWrapper;
    private TextInputLayout mViewPasswordWrapper;
    private Button mBtnLogin;
    private CardView mCv;
    private Context mContext;
    private FloatingActionButton mFab;
    private boolean isFirst;
    private Dialog mRequestDialog;
    //退出时的时间
    private long mExitTime;
    @Override
    public int setLayout() {
        return R.layout.activity_login2;
    }

    @Override
    public void init() {
        mContext = MyApplication.getInstance().getApplicationContext();
        mTxtCompany = (EditText) findViewById(R.id.txt_company);
        mTxtUserName = (EditText) findViewById(R.id.txt_username);
        mViewMobiWrapper = (TextInputLayout) findViewById(R.id.view_mobi_wrapper);
        mTxtPassword = (EditText) findViewById(R.id.txt_password);
        mViewPasswordWrapper = (TextInputLayout) findViewById(R.id.view_password_wrapper);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.ssdk_instapaper_logining));
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mCv = (CardView) findViewById(R.id.cv);
        mBtnLogin.setOnClickListener(this);
        initData();
    }

    private void initData() {
        if (PreferencesUtils.getString(mContext, "token_id")!=null) {
//            checkLogin();
            if(PreferencesUtils.getString(mContext,"type").equals("3")){
                startActivity(new Intent(LoginActivity2.this, MainActivity2.class));
            }
            else {
                startActivity(new Intent(LoginActivity2.this, ActivityListActivity.class));
            }
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case fab:
//                getWindow().setExitTransition(null);
//                getWindow().setEnterTransition(null);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options =
//                            ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
//                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
//                } else {
//                    startActivity(new Intent(this, RegisterActivity.class));
//                }
                break;
            case R.id.btn_login: {
                checkLogin();
                break;
            }

        }
    }

    private void checkLogin() {

        final String company = mTxtCompany.getText().toString();
        final String user_name = mTxtUserName.getText().toString();
//            final String company = AESCrypt.encrypt(mTxtCompany.getText().toString(),"");
//            final String user_name = AESCrypt.encrypt(mTxtUserName.getText().toString(),"");
        final String password = mTxtPassword.getText().toString();
        if (company.equals("")) {
            Toast.makeText(mContext, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (user_name.equals("")) {
            Toast.makeText(mContext, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.equals("")) {
            Toast.makeText(mContext, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!company.equals("")&&!user_name.equals("")&&!password.equals("")){
            mRequestDialog.show();
            String url = BaseUrl.BASE_URL+"phoneApi/activityLogin.do?method=login&username=" + user_name + "&code=" + company + "&password=" + md5(password) + "&loginOS=" + "1";
            Log.d("response", url);
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("response",response);
                            Login login = JSONUtil.fromJson(response, Login.class);
                            if(Integer.parseInt(login.getRet())==200){
                                Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                                if (mRequestDialog != null && mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                PreferencesUtils.putString(mContext, "token_id",login.getData().getToken_id());
                                PreferencesUtils.putString(mContext, "xingming",login.getData().getXingming());
                                PreferencesUtils.putString(mContext, "shangjia_name",login.getData().getShangjia_name().toString());
                                PreferencesUtils.putString(mContext, "type",login.getData().getType());
                                isFirst = false;
                                if(login.getData().getType().equals("3")){
                                    startActivity(new Intent(LoginActivity2.this,MainActivity2.class));
                                }
                                else {
                                    startActivity(new Intent(LoginActivity2.this,ActivityListActivity.class));
                                }

                               finish();
                            }
                            else if(Integer.parseInt(login.getRet())==201) {
                                if (mRequestDialog != null && mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(mContext, ""+login.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).failure(new IFailure() {
                @Override
                public void onFailure() {
                    if (mRequestDialog != null && mRequestDialog.isShowing()){
                        mRequestDialog.dismiss();
                    }
                    Toast.makeText(mContext, "请求超时", Toast.LENGTH_SHORT).show();
                }
            }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    if (mRequestDialog != null && mRequestDialog.isShowing()){
                        mRequestDialog.dismiss();
                    }
                    Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
                }
            })
                    .build()
                    .get();
        }

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
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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
