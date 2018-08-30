package com.shushang.aishangjia.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.shushang.aishangjia.R;


/**
 * Create on 2016/8/23.
 * 参考 maning github项目，表示感谢
 */
public class SLEditTextView extends AppCompatEditText implements View.OnFocusChangeListener,TextWatcher {

    private Context mContext;

    /**
     * 画线
     */
    private Paint mPaint;
    private int color;
    /**
     * 右边可以清除的小图标
     */
    private Drawable mClearDrawable;
    /**
     * 右边可以清除的小图标
     */
    private Drawable mLeftDrawable;
    /**
     * 标记
     */
    private static final int DRAWABLE_LEFT = 0; //左边图标
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    public SLEditTextView(Context context) {
        super(context);
        init(context);
    }

    public SLEditTextView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public SLEditTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context){
        this.mContext = context;
        //画线
        drawLine();
        //设置图标
        setDrawable(context);

        this.setOnFocusChangeListener(this);
        this.addTextChangedListener(this);

        //默认状态
        updateDrawable(true);
    }


    private void drawLine() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(3.0f);
        color = Color.parseColor("#a9b7b7");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.setColor(color);
//        canvas.drawLine(dip2px(mContext, 2), this.getHeight() - 1, this.getWidth() - dip2px(mContext, 2),
//                this.getHeight() - 1, mPaint);
    }

    public void setDrawable(Context context) {
        mClearDrawable = getCompoundDrawables()[DRAWABLE_RIGHT];
        if (mClearDrawable == null) {
            //获取默认图标
            mClearDrawable = getResources().getDrawable(R.mipmap.icon_clear_edit);
        }

        mClearDrawable.setBounds(0, 0, dip2px(context, 25), dip2px(context, 25));
        //左边图标
        mLeftDrawable = getCompoundDrawables()[DRAWABLE_LEFT];
        if (mLeftDrawable != null) {
            mLeftDrawable.setBounds(0, 0, dip2px(context, 28), dip2px(context, 28));
        }
    }

    // 更新删除图片状态: 当内容不为空，而且获得焦点，才显示右侧删除按钮
    private void updateDrawable(boolean hasFocus) {

        if(length() > 0 && hasFocus){
            setCompoundDrawables(mLeftDrawable,getCompoundDrawables()[DRAWABLE_TOP],mClearDrawable,getCompoundDrawables()[DRAWABLE_BOTTOM]);
        }else{
            setCompoundDrawables(mLeftDrawable,getCompoundDrawables()[DRAWABLE_TOP],null,getCompoundDrawables()[DRAWABLE_BOTTOM]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //可以获取上下左右四个drawable,右侧排第二个。图标没有设置则为空。
        Drawable rightDeleteIcon =  getCompoundDrawables()[DRAWABLE_RIGHT];

        if(rightDeleteIcon != null && event.getAction() == MotionEvent.ACTION_UP){
            //检查点击的位置是否是右侧的删除图标
            //注意，使用getRwwX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
            int deleteIconLeftBoundary = getRight() - getPaddingRight() - rightDeleteIcon.getBounds().width();
            float rawX = event.getRawX();

            if(rawX >= deleteIconLeftBoundary)
                setText("");
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View view, boolean hasFoucus) {
        this.hasFoucs = hasFoucus;
        updateDrawable(hasFoucus);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //更新状态
        updateDrawable(true);
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }


    @Override
    protected void finalize() throws Throwable {
        mClearDrawable = null;
        super.finalize();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void clearDeleteIcon(){
        setCompoundDrawables(mLeftDrawable,getCompoundDrawables()[DRAWABLE_TOP],null,getCompoundDrawables()[DRAWABLE_BOTTOM]);
    }

}
