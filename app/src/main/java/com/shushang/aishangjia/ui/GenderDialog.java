package com.shushang.aishangjia.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import com.shushang.aishangjia.R;

/**
 * Created by Jason on 2017/9/5.
 */

public class GenderDialog extends Dialog implements View.OnClickListener{

    private OnGenderDialogListener mOnGenderDialogListener;
    private LinearLayout mLlMale;
    private LinearLayout mLlFemale;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;

    public GenderDialog(@NonNull Context context) {
        super(context);
        initDialog();
    }

    /**
     * 初始化对话框
     */
    private void initDialog() {
        setContentView(R.layout.dialog_gender);

        mLlMale = (LinearLayout) findViewById(R.id.ll_male);
        mLlFemale = (LinearLayout) findViewById(R.id.ll_female);

        mRbMale = (RadioButton) findViewById(R.id.rb_male);
        mRbFemale = (RadioButton) findViewById(R.id.rb_female);

        mLlMale.setOnClickListener(this);
        mLlFemale.setOnClickListener(this);

        Window window = this.getWindow();
        //位于屏幕中间
        window.setGravity(Gravity.CENTER);
        //添加动画
        window.setWindowAnimations(R.style.dialog_style);

    }

    public void setListener(OnGenderDialogListener listener){
        mOnGenderDialogListener = listener;
    }

    @Override
    public void onClick(View view) {
        responseClickEvent(view.getId());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },300);
    }

    /**
     * 响应点击事件
     */
    public void responseClickEvent(int id){
        switch (id){
            case R.id.ll_male://男
                mRbMale.setChecked(true);
                mRbFemale.setChecked(false);
                if(mOnGenderDialogListener != null)
                    mOnGenderDialogListener.onGenderDialogClick(getContext().getResources().getString(R.string.male));
                break;
            case R.id.ll_female://女
                mRbMale.setChecked(false);
                mRbFemale.setChecked(true);
                if(mOnGenderDialogListener != null)
                    mOnGenderDialogListener.onGenderDialogClick(getContext().getResources().getString(R.string.female));
                break;
        }
    }

    /**
     * 显示原有记录
     * @param oldRecords
     */
    public void displayOldRecords(String oldRecords) {
        if (TextUtils.isEmpty(oldRecords))
            return;
        String genderText = oldRecords.trim();
        if(getContext().getResources().getString(R.string.female).equals(genderText)){
            mRbFemale.setChecked(true);
            mRbMale.setChecked(false);
        }else {
            mRbMale.setChecked(true);
            mRbFemale.setChecked(false);
        }
    }

    public interface OnGenderDialogListener{
        void onGenderDialogClick(String itemName);
    }

    /**
     * 清除选中记录
     */
    public void clearSelectRecord(){
        mRbMale.setChecked(false);
        mRbFemale.setChecked(false);
    }
}
