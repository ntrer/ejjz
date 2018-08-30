package com.shushang.aishangjia;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.shushang.aishangjia.Bean.UpDate;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.PermissionListener;
import com.shushang.aishangjia.fragment.MyFragment2.MyFragment2;
import com.shushang.aishangjia.fragment.ScanFragment.ScanFragment;
import com.shushang.aishangjia.fragment.SignFragment.SignFragment;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.service.AppUpdateService;
import com.shushang.aishangjia.utils.ActivityManager.ActivityStackManager;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.File;
import java.util.List;

public class MainActivity2 extends BaseActivity{

    private Context mContext;
    private String mSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ejjz.apk";
    private String mDownloadUrl ;
    private static final int REQUEST_CODE_WRITE_STORAGE = 1002;
    private static final int REQUEST_CODE_UNKNOWN_APP = 2001;
    private static final int REQUEST_CODE_SCAN = 2002;
    private Handler mHandler;
    private MyFragment2 mMyFragment;
    private ScanFragment mScanFragment;
    private SignFragment mSignFragment;
    private int lastfragment;//用于记录上个选择的Fragment
    private BottomNavigationView navigation;
    private Fragment[] mFragments ;
    private String type;
    private int versionCode;
    private String token_id;
    //退出时的时间
    private long mExitTime;
    @Override
    public int setLayout() {
        return R.layout.activity_main2;
    }

    @Override
    public void init() {
        mContext=this;
        initFragment();
        token_id= PreferencesUtils.getString(mContext,"token_id");
        inidData(token_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void inidData(String token_id) {
        RestClient.builder()
                .url(BaseUrl.BASE_URL+"phoneApi/activityLogin.do?method=updateApp&type=1&token_id="+ token_id)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            Log.d("Update",response);
                            UpDate upDate = JSONUtil.fromJson(response, UpDate.class);
                            versionCode = getVersionCode(mContext);
                            if(upDate.getData()!=null){
                                mDownloadUrl=upDate.getData().getUrl();
                                if(String.valueOf(versionCode).equals(upDate.getData().getVersion())||upDate.getData()==null){
                                    Log.d("wocao666","无新版本");
                                }
                                else {
                                    initDialog(upDate);
                                }
                            }
                            else {
                                Log.d("wocao666","无新版本");
                            }
                        }
                    }
                })
                .build()
                .get();
    }


    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    //初始化fragment和fragment数组
    private void initFragment() {
        mMyFragment = new MyFragment2();
        mScanFragment=new ScanFragment();
        mSignFragment=new SignFragment();
        mFragments = new Fragment[]{mSignFragment,mScanFragment, mMyFragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mSignFragment,"tag1").show(mSignFragment).commit();
        navigation = (BottomNavigationView) findViewById(R.id.navigation_fragment);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_fragment_zero:
                    {
                            if(lastfragment!=0)
                            {
                                switchFragment(lastfragment,0);
                                lastfragment=0;

                            }
                            return true;

                    }
                    case R.id.navigation_fragment_one:
                    {
                        if(lastfragment!=1)
                        {
                            switchFragment(lastfragment,1);
                            lastfragment=1;

                        }

                        return true;
                    }
                    case R.id.navigation_fragment_two:
                    {
                        if(lastfragment!=2)
                        {
                            switchFragment(lastfragment,2);
                            lastfragment=2;

                        }

                        return true;
                    }

                }
                return false;
            }
        });

    }

    //显示更新对话框
    private void initDialog(UpDate upDate) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity2.this);
        normalDialog.setTitle("新版本更新");
        normalDialog.setMessage(upDate.getData().getComment());
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        permissionStorage();

                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        normalDialog.setCancelable(false);
        // 显示
        normalDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UNKNOWN_APP) {
            downloadAPK();
        }
        else if(requestCode == REQUEST_CODE_SCAN){
            Message message=Message.obtain();
            message.what=1;
            mHandler.sendMessage(message);
        }
    }

    //下载app
    private void downloadAPK() {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (b) {
                AppUpdateService.start(mContext, mSavePath, mDownloadUrl);//安装应用的逻辑(写自己的就可以)
            } else {
                Uri packageUri=Uri.parse("package:"+getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageUri);
                startActivityForResult(intent, REQUEST_CODE_UNKNOWN_APP);
            }
        } else {
            AppUpdateService.start(mContext, mSavePath, mDownloadUrl);
        }
    }


    //切换Fragment
    private void switchFragment(int lastfragment,int index)
    {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragments[lastfragment]);//隐藏上个Fragment
        if(mFragments[index].isAdded()==false)
        {
            transaction.add(R.id.fragment_container,mFragments[index],"tag"+index);


        }
        transaction.show(mFragments[index]).commitAllowingStateLoss();


    }


    //请求相机权限
    private void permissionCamera(){
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                Intent openCameraIntent = new Intent(MainActivity2.this, CaptureActivity.class);
                openCameraIntent.putExtra("type", "2");
                startActivityForResult(openCameraIntent, REQUEST_CODE_SCAN );
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for(String permission : deniedPermission){
                    Toast.makeText(MainActivity2.this, "被拒绝的权限：" + permission, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //请求存储权限
    private void permissionStorage(){
        requestRunPermisssion(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                downloadAPK();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for(String permission : deniedPermission){
                    Toast.makeText(MainActivity2.this, "被拒绝的权限：" + permission, Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public void setHandler(Handler handler){
        this.mHandler = handler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

