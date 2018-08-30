package com.shushang.aishangjia.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.shushang.aishangjia.MainActivity2;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.PermissionListener;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.mylibrary.FileProvider7;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.valueOf;

public class SignActivity extends BaseActivity {

    private PermissionListener mListener;
    private static final int PERMISSION_REQUESTCODE = 100;
    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    private Button mButton;
    private Toolbar mToolbar;
    private TextView mTextView1, mTextView2, mTextView3;
    private Bitmap mBitmap;
    private String sign_time = null;
    private String sign_address = null;
    private String sign_map = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String token_id = null;
    private Uri fileUri=null;
    private File imgFile;
    private Dialog mRequestDialog;
    private Bitmap newBitmap;
    @Override
    public void init() {
        mImageView = (ImageView) findViewById(R.id.img);
        mTextView1 = (TextView) findViewById(R.id.sign_time);
        mTextView2 = (TextView) findViewById(R.id.sign_address);
        mTextView3 = (TextView) findViewById(R.id.sign_map);
        mButton = (Button) findViewById(R.id.submit);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        token_id = PreferencesUtils.getString(this, "token_id");
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_time = mTextView1.getText().toString();
                sign_address = mTextView2.getText().toString();
                sign_map = String.valueOf(mTextView3.getText());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (mBitmap != null) {
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                    submit(sign_time, sign_address, sign_map, isBm);
                }
                Log.d("isBm", sign_time + sign_address + sign_map);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionCamera();
            }
        });
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        permissionLocation();
    }

    private void initMap() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        mTextView1.setText(simpleDateFormat.format(date));
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public int setLayout() {
        return R.layout.activity_sign;
    }


    public void submit(String sign_time, String sign_address, String sign_map, InputStream isBm) {
        if (isBm == null) {
            Toast.makeText(SignActivity.this,"请拍照！", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            mRequestDialog.show();
            //        String url = BaseUrl.BASE_URL + "phoneApi/customerManager.do?method=managerSignin&token_id="+token_id+"&qdsj="+sign_time+"&qddz="+sign_map+"&qdlh="+sign_address;
            String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=managerSignin";
//          String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=managerSignin&token_id="+token_id;
            postGoodsPicToServer(url);
        }
    }


    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    mTextView3.setText(aMapLocation.getAddress());
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }


    private void permissionLocation() {
        requestRunPermisssion(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                initMap();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(SignActivity.this, "被拒绝的权限：" + permission, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //请求相机权限
    private void permissionCamera() {
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                takePhotoNoCompress();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(SignActivity.this, "被拒绝的权限：" + permission, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void takePhotoNoCompress() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addCategory("android.intent.category.DEFAULT");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename ="photo.png";
//            imgFile=getFile(newBitmap);
            imgFile= new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = imgFile.getAbsolutePath();
            fileUri = FileProvider7.getUriForFile(this, imgFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            mBitmap = getSmallBitmap(mCurrentPhotoPath);
            int bitmapDegree = getBitmapDegree(mCurrentPhotoPath);
            newBitmap= rotateBitmapByDegree(mBitmap, bitmapDegree);
            mImageView.setImageBitmap(newBitmap);
        }

    }

    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
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

//    private void upLoad(String url,String filePath) {
//        File file = new File(filePath);
//        RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), token_id);
//        RequestBody qddzBody = RequestBody.create(MediaType.parse("text/plain"),sign_map );
//        RequestBody qdsjBody = RequestBody.create(MediaType.parse("text/plain"), sign_time);
//        RequestBody qdlhBody = RequestBody.create(MediaType.parse("text/plain"), sign_address);
//        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("zhaopian", file.getName(), imageBody);
//        ApiUtil.uploadMemberIcon(tokenBody,qddzBody,qdsjBody,qdlhBody,imageBodyPart).enqueue(new Callback<String>() {//返回结果
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//               Log.d("成功",response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("照片",t.toString());
//            }
//        });
//    }

//    private void uploadMonofile(String filename){
//        //先创建 service
//        ApiService service = Api.getDefault();
//        //构建要上传的文件
//        File file = new File(filename);
//        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("zhaopian", file.getName(), imageBody);
//        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token_id);
//        RequestBody qddzBody = RequestBody.create(MediaType.parse("multipart/form-data"), sign_map);
//        RequestBody qdsjBody = RequestBody.create(MediaType.parse("multipart/form-data"), sign_time);
//        RequestBody qdlhBody = RequestBody.create(MediaType.parse("multipart/form-data"), sign_address);
//        Call<String> call = service.uploadMemberIcon(tokenBody,qddzBody,qdsjBody, qdlhBody,imageBodyPart);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call,
//                                   Response<String> response) {
//                System.out.println("success");
//                Log.d("成功",response.toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                t.printStackTrace();
//                Log.d("失败",t.toString());
//            }
//        });
//    }

    private void postGoodsPicToServer(String url){
        Map<String, Object> params = new HashMap<>();
        params.put("token_id", token_id);
        params.put("qdsj",sign_time);
        params.put("qddz",sign_map);
        params.put("qdlh",sign_address);
        post_file(url,params,getFile(newBitmap));
    }

//
//    private RequestBody convertToRequestBody(String param){
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
//        return requestBody;
//    }
//
//
//    private List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
//        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
//        for (File file : files) {
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//            MultipartBody.Part part = MultipartBody.Part.createFormData("zhaopian", file.getName(), requestBody);
//            parts.add(part);
//        }
//        return parts;
//    }


    protected void post_file(final String url, Map<String, Object> map,File file) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("zhaopian", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                builder.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
                Log.d("map",map.toString());
            }
        }
        RequestBody body = builder.build();
        final Request request = new Request.Builder().url(url).post(body).build();
        Log.i("file.getName" ,file.getName());
        // readTimeout("请求超时时间" , 时间单位);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("上传",response+"");
//                if(response1.getRet().equals("200")){
                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                        mRequestDialog.dismiss();
                    }
                    startActivity(new Intent(SignActivity.this, MainActivity2.class));
                    ActivityCompat.finishAfterTransition(SignActivity.this);
//                }else {
//                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
//                        mRequestDialog.dismiss();
//                    }
//                    ToastUtils.showLong(""+response1.getMsg());
//                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                    mRequestDialog.dismiss();
                }
                Log.d("失败",e.toString());
            }
        });

    }

    public static String gb2312ToUtf8(String str) {

        String urlEncode = "" ;

        try {

            urlEncode = URLEncoder.encode (str, "UTF-8" );

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

        return urlEncode;

    }

    @Override
    protected void onDestroy() {
        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
            mRequestDialog.dismiss();
        }
        if(mBitmap!=null&&!mBitmap.isRecycled()){
            mBitmap.recycle();
        }
        super.onDestroy();
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

    /**
     * 获取图片的旋转角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照指定的角度进行旋转
     *
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree) {
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        if (bitmap != null & !bitmap.isRecycled()) {
//            bitmap.recycle();
//        }
        return newBitmap;
    }

    private File getFile(Bitmap bitmap){
        String pictureDir = "";
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        File file = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            String saveDir = Environment.getExternalStorageDirectory()
                    + "/dreamtownImage";
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            file = new File(saveDir,"photo.jpg");
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);
            pictureDir = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return file;
    }

}
