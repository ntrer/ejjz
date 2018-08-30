package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.GenderDialog;

public class NewPeopleDetailActivity extends AppCompatActivity {
    private LinearLayout mLlCustomerGender;
    private TextView mEtCustomerName;//客户姓名
    private TextView mEtCustomerMobile;//客户电话
    private TextView mTvCustomerGender;//客户性别
    private TextView mTvDecorateProgress;//装修进度
    private TextView mEtXiaoQu;//小区名称
    private TextView mEtDecorateStyle;//装修风格
//    private Button mButton,mButton2;
    private Toolbar mToolbar;
    private RelativeLayout rl_activity;
    private TextView mEtDecorateAddress;//装修地址
    private TextView mTvIntentionToPurchaseProduct;//意向购买产品
    private TextView mTvActivity;
    private GenderDialog mGenderDialog;
    private String token_id = null;
    private String activityId, activityName, activityCode;
    private String username, phone, sex, address,xiaoqu, sheng_code, shi_code, qu_code, sheng_name, shi_name, qu_name, decorationProgress, decorationStyle, thinkBuyGood;
    private RadioGroup mRadioButton;
//    private TimeCount time;
    private Dialog mRequestDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_people_detail);
        mEtCustomerName = (TextView) findViewById(R.id.et_customer_name);
        mEtCustomerMobile = (TextView) findViewById(R.id.et_customer_mobile);
        mLlCustomerGender = (LinearLayout) findViewById(R.id.ll_customer_gender);
        mTvCustomerGender = (TextView) findViewById(R.id.tv_customer_gender);
        mTvDecorateProgress = (TextView) findViewById(R.id.et_customer_progress);
        mEtDecorateStyle = (TextView) findViewById(R.id.et_decorate_style);
        mEtDecorateAddress = (TextView) findViewById(R.id.tv_customer_address);
        mEtXiaoQu= (TextView) findViewById(R.id.et_customer_xiaoqu);
        mTvIntentionToPurchaseProduct= (TextView) findViewById(R.id.et_intention_to_purchase_product);
        mTvActivity = (TextView) findViewById(R.id.tv_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        rl_activity = (RelativeLayout) findViewById(R.id.activity);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
//        mButton2= (Button) findViewById(R.id.get_mobile_code);
//        mButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRequestDialog.show();
//                String Phone=mEtCustomerMobile.getText().toString();
//                String url= BaseUrl.BASE_URL+"customerLoginController.do?method=sendYzm&type=5&phone="+Phone+"&token_id="+token_id;
//                Log.d("yzm",url);
//                OkhttpUtil.okHttpPost(url, new CallBackUtil.CallBackString() {
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
//                            mRequestDialog.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("yzm",response);
//                        if(response!=null){
//                            Response response1 = JSONUtil.fromJson(response, Response.class);
//                            if(response1.getRet().equals("200")){
//                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
//                                    mRequestDialog.dismiss();
//                                }
//
//                            }
//                            else {
//                                ToastUtils.showLong(""+response1.getMsg());
//                            }
//                        }
//                    }
//                });
//                time.start();
//            }
//        });
        Intent data=getIntent();
        NewPeople.DataListBean dataListBean= (NewPeople.DataListBean) data.getSerializableExtra("data");

        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData(dataListBean);
    }

    private void initData(NewPeople.DataListBean dataListBean) {
        if(dataListBean.getUsername()!=null){
            mEtCustomerName.setText(dataListBean.getUsername());
        }
        if(dataListBean.getPhone()!=null){
            mEtCustomerMobile.setText(dataListBean.getPhone());
        }
        if(dataListBean.getSex()!=null){
            mTvCustomerGender.setText(dataListBean.getSex());
        }
        if(dataListBean.getDecorationProgress()!=null){
            mTvDecorateProgress.setText(dataListBean.getDecorationProgress());
        }
        if(dataListBean.getAddress()!=null){
            mEtXiaoQu.setText(dataListBean.getAddress());
        }
        if(dataListBean.getDecorationStyle()!=null){
            mEtDecorateStyle.setText(dataListBean.getDecorationStyle().toString());
        }
        if(dataListBean.getSheng_name()!=null){
            mEtDecorateAddress.setText(dataListBean.getSheng_name()+dataListBean.getShi_name()+dataListBean.getQu_name());
        }
        if(dataListBean.getThinkBuyGood()!=null){
            mTvIntentionToPurchaseProduct.setText(dataListBean.getThinkBuyGood().toString());
        }
        if(dataListBean.getActivityName()!=null){
            rl_activity.setVisibility(View.VISIBLE);
            mTvActivity.setText(dataListBean.getActivityName().toString());
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


//    class TimeCount extends CountDownTimer {
//
//        public TimeCount(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
////            mButton2.setBackgroundColor(Color.parseColor("#AAA"));
//            mButton2.setBackgroundColor(getResources().getColor(R.color.darker_gray));
//            mButton2.setClickable(false);
//            mButton2.setText("("+millisUntilFinished / 1000 +")秒后获取");
//        }
//
//        @Override
//        public void onFinish() {
//            mButton2.setText("获取短信验证码");
//            mButton2.setClickable(true);
//            mButton2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//
//        }
//    }

}
