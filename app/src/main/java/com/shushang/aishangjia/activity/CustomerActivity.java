package com.shushang.aishangjia.activity;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.ui.GenderDialog;

public class CustomerActivity extends BaseActivity {
    private EditText mEtCustomerName;//客户姓名
    private EditText mEtCustomerMobile;//客户电话
    private TextView mTvCustomerGender;//客户性别
    private TextView mTvDecorateProgress;//装修进度
    private EditText mEtXiaoQu;//小区名称
    private EditText mEtDecorateStyle;//装修风格
    private Button mButton;
    private Toolbar mToolbar;
    private RelativeLayout rl_activity;
    private TextView mEtDecorateAddress;//装修地址
    private EditText mTvIntentionToPurchaseProduct;//意向购买产品
    private TextView mTvActivity;
    private GenderDialog mGenderDialog;
    private String token_id = null;
    private String activityId, activityName, activityCode;
    private String username, phone, sex, address,xiaoqu, sheng_code, shi_code, qu_code, sheng_name, shi_name, qu_name, decorationProgress, decorationStyle, thinkBuyGood;

    @Override
    public int setLayout() {
        return R.layout.activity_app_people;
    }

    @Override
    public void init() {
        mEtCustomerName = (EditText) findViewById(R.id.et_customer_name);
        mEtCustomerMobile = (EditText) findViewById(R.id.et_customer_mobile);
        mTvCustomerGender = (TextView) findViewById(R.id.tv_customer_gender);
        mTvDecorateProgress = (TextView) findViewById(R.id.et_customer_progress);
        mEtDecorateStyle = (EditText) findViewById(R.id.et_decorate_style);
        mEtDecorateAddress = (TextView) findViewById(R.id.tv_customer_address);
        mEtXiaoQu= (EditText) findViewById(R.id.et_customer_xiaoqu);
        mTvActivity = (TextView) findViewById(R.id.tv_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        rl_activity = (RelativeLayout) findViewById(R.id.activity);
        initData();
    }

    private void initData() {

    }


}
