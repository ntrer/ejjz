package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.Bean.Progress;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.GenderDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class AppPeopleActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlCustomerGender,mllDecorateProgress;
    private EditText mEtCustomerName;//客户姓名
    private EditText mEtCustomerMobile;//客户电话
    private TextView mTvCustomerGender;//客户性别
    private TextView mTvDecorateProgress;//装修进度
    private EditText mEtXiaoQu;//小区名称
    private EditText mEditTextCode;
    private EditText mEtDecorateStyle;//装修风格
    private Button mButton,mButton2;
    private Toolbar mToolbar;
    private RelativeLayout rl_activity;
    private TextView mEtDecorateAddress;//装修地址
    private EditText mTvIntentionToPurchaseProduct;//意向购买产品
    private TextView mTvActivity;
    private GenderDialog mGenderDialog;
    private String token_id = null;
    private String activityId, activityName, activityCode;
    private String username, yzmCode,phone, sex, address,xiaoqu, sheng_code, shi_code, qu_code, sheng_name, shi_name, qu_name, decorationProgress, decorationStyle, thinkBuyGood,activity;
    private RadioGroup mRadioButton;
    private long lastClick;
    private static final int REQUEST_CODE_CITY = 2010;
    private static final int REQUEST_CODE_ACTIVITY = 2011;
    private static final int REQUEST_PROGRESS_ACTIVITY = 2012;
    private Dialog mRequestDialog;
    private TimeCount time;
    private boolean isVisiable=false;
    private  List<Progress.DataListBean> mDataListBeen;
    private String progress=null;
    @Override
    public int setLayout() {
        return R.layout.activity_app_people;
    }


    @Override
    public void init() {
        mEtCustomerName = (EditText) findViewById(R.id.et_customer_name);
        mEtCustomerMobile = (EditText) findViewById(R.id.et_customer_mobile);
        mLlCustomerGender = (LinearLayout) findViewById(R.id.ll_customer_gender);
        mTvCustomerGender = (TextView) findViewById(R.id.tv_customer_gender);
        mTvDecorateProgress = (TextView) findViewById(R.id.et_customer_progress);
        mEtDecorateStyle = (EditText) findViewById(R.id.et_decorate_style);
        mEtDecorateAddress = (TextView) findViewById(R.id.tv_customer_address);
        mEtXiaoQu= (EditText) findViewById(R.id.et_customer_xiaoqu);
        mllDecorateProgress= (LinearLayout) findViewById(R.id.ll_DecorateProgress);
        mEditTextCode= (EditText) findViewById(R.id.et_customer_mobile_code);
        mTvActivity = (TextView) findViewById(R.id.tv_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        time = new TimeCount(60000, 1000);
        mButton2= (Button) findViewById(R.id.get_mobile_code);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone=mEtCustomerMobile.getText().toString();
                if(!Phone.equals("")){
                    mRequestDialog.show();
                    String url= BaseUrl.BASE_URL+"customerLoginController.do?method=sendYzm&type=4&phone="+Phone+"&token_id="+token_id;
                    Log.d("yzm",url);
                    OkhttpUtil.okHttpPost(url, new CallBackUtil.CallBackString() {
                        @Override
                        public void onFailure(Call call, Exception e) {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                        }

                        @Override
                        public void onResponse(String response) {
                            Log.d("yzm",response);
                            if(response!=null){
                                Response response1 = JSONUtil.fromJson(response, Response.class);
                                if(response1.getRet().equals("200")){
                                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                        mRequestDialog.dismiss();
                                    }

                                }
                                else {
                                    ToastUtils.showLong(""+response1.getMsg());
                                }
                            }
                        }
                    });
                    time.start();
                }
                else {
                    ToastUtils.showShort("手机号不能为空");
                }
            }
        });
        mRadioButton = (RadioGroup) findViewById(R.id.rd_btn);
        rl_activity = (RelativeLayout) findViewById(R.id.activity);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        Intent data=getIntent();
        NewPeople.DataListBean dataListBean= (NewPeople.DataListBean) data.getSerializableExtra("data");

        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRadioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.btn3) {
                    rl_activity.setVisibility(View.VISIBLE);
                    isVisiable=true;
                } else {
                    rl_activity.setVisibility(View.GONE);
                    isVisiable=false;
                }

            }
        });
        rl_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AppPeopleActivity.this, ProActivityActivity.class), REQUEST_CODE_ACTIVITY);
            }
        });
        mButton = (Button) findViewById(R.id.btn_submit);
        token_id = PreferencesUtils.getString(this, "token_id");
        mTvIntentionToPurchaseProduct = (EditText) findViewById(R.id.et_intention_to_purchase_product);
        mEtDecorateAddress.setOnClickListener(this);
        mGenderDialog = new GenderDialog(this);
        mLlCustomerGender.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mGenderDialog.setListener(new GenderDialog.OnGenderDialogListener() {
            @Override
            public void onGenderDialogClick(String itemName) {
                mTvCustomerGender.setText(itemName);
            }
        });
        mTvDecorateProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivityForResult(new Intent(AppPeopleActivity.this,ProgressActivity.class),REQUEST_PROGRESS_ACTIVITY);
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_customer_gender://客户性别
                if (!mGenderDialog.isShowing())
                    mGenderDialog.show();
                break;
            case R.id.tv_customer_address:
                startActivityForResult(new Intent(this, CityActivity.class), REQUEST_CODE_CITY);
                break;
            case R.id.btn_submit:
                username = mEtCustomerName.getText().toString().replace(" ", "");
                phone = mEtCustomerMobile.getText().toString().replace(" ", "");
               if(mTvCustomerGender.getText().toString().replace(" ", "").equals("男")){
                   sex="1";
               }else {
                   sex="2";
               }
                address = mEtDecorateAddress.getText().toString().replace(" ", "");
                xiaoqu=mEtXiaoQu.getText().toString().replace(" ", "");
                yzmCode=mEditTextCode.getText().toString().replace(" ", "");
                decorationStyle=mEtDecorateStyle.getText().toString().replace(" ", "");
                thinkBuyGood=mTvIntentionToPurchaseProduct.getText().toString().replace(" ", "");
                decorationProgress = mTvDecorateProgress.getText().toString().replace(" ", "");
                activity=mTvActivity.getText().toString();
               if(isVisiable){
                   if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||yzmCode.equals("")||activity.equals("")){
                       Toast.makeText(this, "必填项不能为空", Toast.LENGTH_SHORT).show();
                   }
                   else {
                       mRequestDialog.show();
                       submit(username, phone, sex, address,xiaoqu,decorationStyle, decorationProgress, sheng_name, shi_name, qu_name, sheng_code, shi_code, qu_code, activityName, activityCode, activityId,thinkBuyGood,yzmCode);
                   }
               }
               else {
                   if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||yzmCode.equals("")){
                       Toast.makeText(this, "必填项不能为空", Toast.LENGTH_SHORT).show();
                   }
                   else {
                       mRequestDialog.show();
                       submit(username, phone, sex, address,xiaoqu,decorationStyle, decorationProgress, sheng_name, shi_name, qu_name, sheng_code, shi_code, qu_code, activityName, activityCode, activityId,thinkBuyGood,yzmCode);
                   }
               }

                break;
        }
    }


    public void submit(String username, String phone, String sex, String address, String xiaoqu, String decorationStyle, String decorationProgress, String sheng_name, String shi_name, String qu_name, String sheng_code, String shi_code, String qu_code, String activityName, String activityCode, String activityId, String thinkBuyGood, String yzmCode) {
        String url =  BaseUrl.BASE_URL + "phoneApi/customerManager.do?method=saveCustomer";
        Log.d("创建活动", yzmCode);
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("token_id", token_id);
        paramsMap.put("username", username);
        paramsMap.put("address", xiaoqu);
        paramsMap.put("decorationStyle", decorationStyle);
        paramsMap.put("thinkBuyGood", thinkBuyGood);
        paramsMap.put("yzmCode",yzmCode);
        if(sheng_name!=null){
            paramsMap.put("sheng_name", sheng_name);
            paramsMap.put("sheng_code", sheng_code);
            paramsMap.put("shi_code", shi_code);
            paramsMap.put("qu_code", qu_code);
            paramsMap.put("shi_name", shi_name);
            paramsMap.put("qu_name", qu_name);
        }
        paramsMap.put("phone", phone);
        paramsMap.put("sex", sex);
        paramsMap.put("decorationProgress", decorationProgress);
        if (activityName == null) {
            paramsMap.put("type", "0");
        } else {
            paramsMap.put("type", "1");
            paramsMap.put("activityCode", activityCode);
            paramsMap.put("activityId", activityId);
            paramsMap.put("activityName", activityName);
        }

        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                    mRequestDialog.dismiss();
                }
            }

            @Override
            public void onResponse(String response) {
                Log.d("创建活动",response);
                if(response!=null){
                    Response response1 = JSONUtil.fromJson(response, Response.class);
                    if(response1.getRet().equals("200")){
                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                            mRequestDialog.dismiss();
                        }
                        PreferencesUtils.putString(AppPeopleActivity.this, "sheng_code", null);
                        PreferencesUtils.putString(AppPeopleActivity.this, "shi_code", null);
                        PreferencesUtils.putString(AppPeopleActivity.this, "qu_code", null);
                        PreferencesUtils.putString(AppPeopleActivity.this, "sheng_name", null);
                        PreferencesUtils.putString(AppPeopleActivity.this, "shi_name", null);
                        PreferencesUtils.putString(AppPeopleActivity.this, "qu_name", null);
                        Toast.makeText(AppPeopleActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        mEtCustomerName.setText("");
                        mEtDecorateAddress.setText("");
                        mEtCustomerMobile.setText("");
                        mEtDecorateStyle.setText("");
                        mEtXiaoQu.setText("");
                        mTvActivity.setText("");
                        mTvCustomerGender.setText("");
                        mTvDecorateProgress.setText("");
                        mTvIntentionToPurchaseProduct.setText("");
                        mEditTextCode.setText("");
                        mButton2.setText("获取验证码");
                        mButton2.setClickable(true);
                        mButton2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        if(time!=null){
                            time.cancel();
                        }
                    }
                    else if(response1.getRet().equals("201")){
                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                            mRequestDialog.dismiss();
                        }
                        Toast.makeText(AppPeopleActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
//        RestClient.builder()
//                .url(url)
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        Log.d("创建活动",response);
//                        PreferencesUtils.putString(AppPeopleActivity.this,"sheng_code",null);
//                        PreferencesUtils.putString(AppPeopleActivity.this,"shi_code",null);
//                        PreferencesUtils.putString(AppPeopleActivity.this,"qu_code",null);
//                        PreferencesUtils.putString(AppPeopleActivity.this,"sheng_name",null);
//                        PreferencesUtils.putString(AppPeopleActivity.this,"shi_name",null);
//                        PreferencesUtils.putString(AppPeopleActivity.this,"qu_name",null);
//                        Log.d("添加人员",response);
//                        Toast.makeText(AppPeopleActivity.this, "成功", Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//                .failure(new IFailure() {
//                    @Override
//                    public void onFailure() {
//                        Toast.makeText(AppPeopleActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
//                    }
//                }).error(new IError() {
//            @Override
//            public void onError(int code, String msg) {
//                Toast.makeText(AppPeopleActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
//            }
//        })
//                .build()
//                .post();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (time != null) {
            time.cancel();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CITY) {
            if(PreferencesUtils.getString(AppPeopleActivity.this, "sheng_code")==null){
                mEtDecorateAddress.setText("");
            }else {
                sheng_code = PreferencesUtils.getString(AppPeopleActivity.this, "sheng_code");
                shi_code = PreferencesUtils.getString(AppPeopleActivity.this, "shi_code");
                qu_code = PreferencesUtils.getString(AppPeopleActivity.this, "qu_code");
                sheng_name = PreferencesUtils.getString(AppPeopleActivity.this, "sheng_name");
                shi_name = PreferencesUtils.getString(AppPeopleActivity.this, "shi_name");
                qu_name = PreferencesUtils.getString(AppPeopleActivity.this, "qu_name");
                mEtDecorateAddress.setText(sheng_name + shi_name + qu_name);
            }
        } else if (requestCode == REQUEST_CODE_ACTIVITY) {
            if (data != null && data.getStringExtra("activityName") != null) {
                activityName = data.getStringExtra("activityName");
                activityId = data.getStringExtra("activityId");
                activityCode = data.getStringExtra("activityCode");
                mTvActivity.setText(activityName);
                PreferencesUtils.putString(this, "activity_id", activityId);
                Log.d("活动id", activityId);
            }

        }
        else if(requestCode==REQUEST_PROGRESS_ACTIVITY){
            if(PreferencesUtils.getString(AppPeopleActivity.this, "DecorationProgressName")==null){
                mTvDecorateProgress.setText("");
            }else {
                progress = PreferencesUtils.getString(AppPeopleActivity.this, "DecorationProgressName");
                mTvDecorateProgress.setText(progress);
            }
        }
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



    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            mButton2.setBackgroundColor(Color.parseColor("#AAA"));
            mButton2.setBackgroundColor(getResources().getColor(R.color.darker_gray));
            mButton2.setClickable(false);
            mButton2.setText("("+millisUntilFinished / 1000 +")秒后获取");
        }

        @Override
        public void onFinish() {
            mButton2.setText("获取验证码");
            mButton2.setClickable(true);
            mButton2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
    }


}
