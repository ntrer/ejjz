/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xys.libzxing.zxing.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.xys.libzxing.R;
import com.xys.libzxing.zxing.Bean.UserInfo;
import com.xys.libzxing.zxing.camera.CameraManager;
import com.xys.libzxing.zxing.decode.DecodeThread;
import com.xys.libzxing.zxing.net.RestClient;
import com.xys.libzxing.zxing.net.callback.IError;
import com.xys.libzxing.zxing.net.callback.IFailure;
import com.xys.libzxing.zxing.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.BaseUrl;
import com.xys.libzxing.zxing.utils.BeepManager;
import com.xys.libzxing.zxing.utils.CaptureActivityHandler;
import com.xys.libzxing.zxing.utils.InactivityTimer;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.OkhttpUtils.CallBackUtil;
import com.xys.libzxing.zxing.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import okhttp3.Call;

/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Handler scanHandler=new Handler();
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private ProgressBar mProgressBar;
    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private RelativeLayout mRelativeLayout;
    private LinearLayout ll_shop,ll_user,ll_edit;
    private ImageView scanLine;
    private Button mBtn;
    private EditText money;
    private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mTextView6;
    private Rect mCropRect = null;
    private boolean isHasSurface = false;
    private String type;

    public Handler getHandler() {
        return handler;
    }

    private String tokenId, activityId, merchantId,userId;

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent intent = getIntent();//获取传来的intent对象
        type = intent.getStringExtra("type");//获取键值对的键名
        if(type.equals("0")){
            setContentView(R.layout.activity_capture);
        }else if(type.equals("1")){
            setContentView(R.layout.activity_capture3);
            initChild();
        }else if(type.equals("2")){
            setContentView(R.layout.activity_capture2);
        }
        Toast.makeText(this, "请远距离扫描二维码", Toast.LENGTH_LONG).show();
//        SoftHideKeyBoardUtil.assistActivity(this);
        scanPreview =findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        scanCropView = findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);

        mProgressBar=findViewById(R.id.loading);
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation
                .RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);
    }

    private void initChild() {
        mBtn=findViewById(R.id.btn_submit);
        mRelativeLayout=findViewById(R.id.rl_info);
        ll_shop=findViewById(R.id.shop_info);
        ll_user=findViewById(R.id.user_info);
        ll_edit=findViewById(R.id.edit);
        mTextView1=findViewById(R.id.shop_id);
        mTextView2=findViewById(R.id.shop_name);
        mTextView3=findViewById(R.id.shop_code);
        mTextView4=findViewById(R.id.user_id);
        mTextView5=findViewById(R.id.user_name);
        mTextView6=findViewById(R.id.user_code);
        money=findViewById(R.id.pro_money);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTextView1.getText().equals("")||mTextView4.getText().equals("")){
                    Toast.makeText(CaptureActivity.this, "请先扫码", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        mProgressBar.setVisibility(View.VISIBLE);
                        closeKeybord(money,getApplicationContext());
                        String url =  BaseUrl.BASE_URL + "phoneApi/activityController.do?method=saveOrder";
                        HashMap<String, String> paramsMap = new HashMap<>();
                        paramsMap.put("token_id", tokenId);
                        paramsMap.put("activity_id",activityId);
                        paramsMap.put("merchant_id",merchantId);
                        paramsMap.put("user_id",userId);
                        paramsMap.put("money",money.getText().toString());
//                    String url=BaseUrl.BASE_URL+"phoneApi/activityController.do?method=saveOrder&token_id="+tokenId+"&user_id="+userId+"&merchant_id="+merchantId+"&activity_id="+activityId+"&money="+money.getText();
                        Log.d("ScanUrl",url);

                        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                            @Override
                            public void onFailure(Call call, Exception e) {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(CaptureActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                                reStart();
                            }

                            @Override
                            public void onResponse(String response) {
                                Log.d("ScanUrl",response);
                                UserInfo userInfo = JSONUtil.fromJson(response, UserInfo.class);
                                if(userInfo.getRet().equals("200")){
                                    Toast.makeText(CaptureActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                                    mProgressBar.setVisibility(View.GONE);
                                    mTextView1.setText("");
                                    mTextView2.setText("");
                                    mTextView3.setText("");
                                    mTextView4.setText("");
                                    mTextView5.setText("");
                                    mTextView6.setText("");
//                                ll_shop.setVisibility(View.GONE);
//                                ll_user.setVisibility(View.GONE);
//                                ll_edit.setVisibility(View.GONE);
                                    money.setText("");
                                    reStart();
                                }
                                else {
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(CaptureActivity.this, "下单失败"+userInfo.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


//                    RestClient.builder()
//                            .url(url)
//                            .success(new ISuccess() {
//                                @Override
//                                public void onSuccess(String response) {
//                                    Log.d("ScanUrl",response);
//                                    UserInfo userInfo = JSONUtil.fromJson(response, UserInfo.class);
//                                    if(userInfo.getRet().equals("200")){
//                                        Toast.makeText(CaptureActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
//                                        mProgressBar.setVisibility(View.GONE);
//                                        ll_shop.setVisibility(View.GONE);
//                                        ll_user.setVisibility(View.GONE);
//                                        ll_edit.setVisibility(View.GONE);
//                                        money.setText("");
//                                        reStart();
//                                    }
//                                    else {
//                                        mProgressBar.setVisibility(View.GONE);
//                                        Toast.makeText(CaptureActivity.this, "下单失败"+userInfo.getMsg(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }).failure(new IFailure() {
//                        @Override
//                        public void onFailure() {
//                            mProgressBar.setVisibility(View.GONE);
//                            Toast.makeText(CaptureActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
//                        }
//                    }).error(new IError() {
//                        @Override
//                        public void onError(int code, String msg) {
//                            mProgressBar.setVisibility(View.GONE);
//                            Toast.makeText(CaptureActivity.this, "下单失败"+code+msg, Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                            .build()
//                            .post();
                    }
                    catch (Exception e){
                        Toast.makeText(CaptureActivity.this, "出错"+e.toString(), Toast.LENGTH_SHORT).show();
                        reStart();
                    }
                }
            }
        });


    }


    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
        scanHandler.removeCallbacksAndMessages(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
        mProgressBar.setVisibility(View.VISIBLE);
        String text=rawResult.getText();
        tokenId= PreferencesUtils.getString(this,"token_id");
        activityId= PreferencesUtils.getString(this,"activityId");
        if(type.equals("1")){
            final String url=BaseUrl.BASE_URL+"phoneApi/activityController.do?method=silver&token_id="+tokenId+"&code_num="+rawResult.getText()+"&activity_id="+activityId;
            Log.d("收银",url);
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("收银",response);
                            UserInfo userInfo = JSONUtil.fromJson(response, UserInfo.class);
                            if(userInfo.getRet().equals("200")){
                                if(userInfo.getData()!=null){
                                    if(userInfo.getData().getType().equals("2")){
                                        merchantId=String.valueOf(userInfo.getData().getMerchant_id());
                                        if(userInfo.getData().getMerchant_address()==null){
                                            mTextView2.setText("");
                                        }
                                        else {
                                            mTextView2.setText(String.valueOf(userInfo.getData().getMerchant_address().toString()));
                                        }
                                        mTextView1.setText(userInfo.getData().getMerchant_name());
                                        mTextView3.setText(userInfo.getData().getMerchant_code());
                                        mProgressBar.setVisibility(View.GONE);
//                                        ll_shop.setVisibility(View.VISIBLE);
                                        scanHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                reStart();
                                            }
                                        },1000);
                                    }
                                    else if(userInfo.getData().getType().equals("1")){
                                        userId=String.valueOf(userInfo.getData().getUser_id());
                                        mTextView6.setText(String.valueOf(userInfo.getData().getAddress().toString()));
                                        mTextView4.setText(String.valueOf(userInfo.getData().getUser_name()));
                                        mTextView5.setText(String.valueOf(userInfo.getData().getUser_phone()));
                                        mProgressBar.setVisibility(View.GONE);
//                                        mRelativeLayout.setVisibility(View.VISIBLE);
//                                        ll_user.setVisibility(View.VISIBLE);
//                                        ll_edit.setVisibility(View.VISIBLE);
                                        scanHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                reStart();
                                            }
                                        },1000);

                                    }
                            }
                            }
                            else {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(CaptureActivity.this, ""+userInfo.getMsg(), Toast.LENGTH_SHORT).show();
                                scanHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        reStart();
                                    }
                                },1000);
                            }
                        }
                    })
                    .build()
                    .get();

//            else {
//            RestClient.builder()
//                    .url(url)
//                    .success(new ISuccess() {
//                        @Override
//                        public void onSuccess(String response) {
//                            Log.d("收银",response);
//                            Log.d("收银",url);
//                            UserInfo userInfo = JSONUtil.fromJson(response, UserInfo.class);
//                            if(userInfo.getData()!=null){
//                                userId=String.valueOf(userInfo.getData().getUser_id());
//                                mTextView4.setText(String.valueOf(userInfo.getData().getUser_id()));
//                                mTextView5.setText(String.valueOf(userInfo.getData().getUser_name()));
//                                mTextView6.setText(String.valueOf(userInfo.getData().getUser_phone()));
//                                mProgressBar.setVisibility(View.GONE);
//                                mRelativeLayout.setVisibility(View.VISIBLE);
//                                ll_user.setVisibility(View.VISIBLE);
//                                ll_edit.setVisibility(View.VISIBLE);
//                                reStart();
//                            }
//                            else {
//                                mProgressBar.setVisibility(View.GONE);
//                                Toast.makeText(CaptureActivity.this, "客户不存在", Toast.LENGTH_SHORT).show();
//                                reStart();
//                            }
//
//                        }
//                    })
//                    .build()
//                    .get();
//            }
        }
        else if(type.equals("0")){
            final String url= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=signin&token_id="+tokenId+"&card_num="+rawResult.getText()+"&activity_id="+activityId;
            Log.d("签到链接",""+url);
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("签到",response);
                            UserInfo userInfo = JSONUtil.fromJson(response, UserInfo.class);
                            if(userInfo.getRet().equals("200")){
                                if(userInfo.getData()!=null){
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(CaptureActivity.this, userInfo.getData().getUser_name()+"签到成功", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(CaptureActivity.this, "签到失败", Toast.LENGTH_SHORT).show();
                                }
                                scanHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        reStart();
                                    }
                                },1000);
                            }
                            else {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(CaptureActivity.this, userInfo.getMsg()+"", Toast.LENGTH_SHORT).show();
                                scanHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        reStart();
                                    }
                                },1500);
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(CaptureActivity.this, "签到失败", Toast.LENGTH_SHORT).show();
                            scanHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    reStart();
                                }
                            },1500);
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(CaptureActivity.this, "签到失败"+msg, Toast.LENGTH_SHORT).show();
                            scanHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    reStart();
                                }
                            },1000);

                        }
                    })
                    .build()
                    .get();

        }
        else if(type.equals("2")){
            final String url=BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getGift&coupon_code="+rawResult.getText()+"&token_id="+tokenId+"&activity_id="+activityId;
            Log.d("领取",url);
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("领取",response);
                            UserInfo userInfo = JSONUtil.fromJson(response, UserInfo.class);
                            if(userInfo.getRet().equals("200")){
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(CaptureActivity.this, "领取成功", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(CaptureActivity.this, "领取失败"+userInfo.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            scanHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    reStart();
                                }
                            },1000);

                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(CaptureActivity.this, "领取失败", Toast.LENGTH_SHORT).show();
                            reStart();

                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(CaptureActivity.this, "领取失败"+msg+code, Toast.LENGTH_SHORT).show();
                            scanHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    reStart();
                                }
                            },1000);

                        }
                    })
                    .build()
                    .get();
        }

//        Intent resultIntent = new Intent();
//        bundle.putInt("width", mCropRect.width());
//        bundle.putInt("height", mCropRect.height());
//        bundle.putString("result", rawResult.getText());
//        resultIntent.putExtras(bundle);
//        this.setResult(RESULT_OK, resultIntent);
//        CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Camera error");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public void reStart() {
        cameraManager = new CameraManager(getApplication());
        handler = null;
        if (isHasSurface) {
            initCamera(scanPreview.getHolder());
        } else {
            scanPreview.getHolder().addCallback(this);
        }
        inactivityTimer.onResume();
    }


    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}