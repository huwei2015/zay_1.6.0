package com.example.administrator.zahbzayxy.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.OneCunBean;
import com.example.administrator.zahbzayxy.beans.UpBean;
import com.example.administrator.zahbzayxy.beans.UserInfoBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2020-01-17.
 * Time 10:07.
 * 上传身份证正反面
 */
public class UpIdCardActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_photo,btn_reverse;
    private ImageView img_photo,img_reverse,back_editMessage;
    private PopupWindow mPopupWindow;
    private RelativeLayout cancle;
    private String token;
    private String path;
    private Bitmap bitmap;
    private Bitmap bitmap_rese;
    private byte[] bitmapByte;
    private byte[] bitmapByte_revers;
    private final int WRITE_PERMISSION_REQ_CODE = 100;
    boolean bPermission = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_idcard);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        btn_reverse= (Button) findViewById(R.id.btn_reverse);//反面
        btn_reverse.setOnClickListener(this);
        btn_photo= (Button) findViewById(R.id.btn_photo);//正面
        btn_photo.setOnClickListener(this);
        img_photo= (ImageView) findViewById(R.id.img_photo);//正面图片
        img_reverse= (ImageView) findViewById(R.id.img_reverse);//反面图片
        back_editMessage= (ImageView) findViewById(R.id.back_editMessage);
        back_editMessage.setOnClickListener(this);
        checkPublishPermission();
        initView();
        initUserInfo();
    }
    private void initUserInfo() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserInfoBean> userInfoData = userInfoInterface.getUserInfoData(token);
        userInfoData.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                if(response !=null && response.body() !=null){
                    UserInfoBean body = response.body();
                    UserInfoBean.DataBean data = body.getData();
                    String oneInchPhoto = data.getIdCardFrontPath();
                    if(!TextUtils.isEmpty(oneInchPhoto)){
                        Picasso.with(UpIdCardActivity.this).load(oneInchPhoto).into(img_reverse);
                        btn_reverse.setText("重新上传一寸照");
                    }else{
                        btn_reverse.setText("上传一寸照片");
                    }
                }

            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                ToastUtils.showInfo(t.getMessage(),500);
            }
        });
    }
    private void initView() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserInfoBean> userInfoData = userInfoInterface.getUserInfoData(token);
        userInfoData.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                if(response !=null && response.body() !=null){
                    UserInfoBean body = response.body();
                    UserInfoBean.DataBean data = body.getData();
                    String oneInchPhoto = data.getIdCardBackPath();
                    if(!TextUtils.isEmpty(oneInchPhoto)){
                        Picasso.with(UpIdCardActivity.this).load(oneInchPhoto).into(img_photo);
                        btn_photo.setText("重新上传一寸照");
                    }else{
                        btn_photo.setText("上传一寸照片");
                    }
                }

            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                ToastUtils.showInfo(t.getMessage(),500);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_editMessage:
                finish();
                break;
            case R.id.btn_reverse:
                initHeadReverse();
                break;
            case R.id.btn_photo://上传图片
                initHeadPhoto();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void initHeadReverse() {
        //加载popupwindow的布局文件
        View view1 = View.inflate(UpIdCardActivity.this, R.layout.activity_pop_one, null);
        cancle = (RelativeLayout) view1.findViewById(R.id.mine_pop_cancle);
        RelativeLayout camera = (RelativeLayout) view1.findViewById(R.id.mine_pop_camera);
        RelativeLayout selector = (RelativeLayout) view1.findViewById(R.id.mine_pop_selector);
        //创建popupwindow为全屏

        mPopupWindow = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //设置动画,就是style里创建的那个j
        mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
        //设置popupwindow的位置,这里直接放到屏幕上就行
        mPopupWindow.showAsDropDown(view1, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //为popwindow的每个item添加监听
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        //调用相机
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPublishPermission() == true) {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    String filea = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "icons";
                    File file = new File(filea);
                    file.mkdirs();
                    path = new File(file, System.currentTimeMillis() + ".jpg").getAbsolutePath();
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(UpIdCardActivity.this, UpIdCardActivity.this.getApplicationContext().getPackageName() + ".fileprovider", new File(path));
                    } else {
                        ContentValues contentValues = new ContentValues(1);
                        contentValues.put(MediaStore.Images.Media.DATA, path);
                        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, 120);
                    mPopupWindow.dismiss();
                } else if (checkPublishPermission() == false) {
                    Toast.makeText(UpIdCardActivity.this, "请先打开允许相机拍照权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //调用相册
        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 122);
                mPopupWindow.dismiss();
            }
        });
    }




    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void initHeadPhoto() {
        //加载popupwindow的布局文件
        View view1 = View.inflate(UpIdCardActivity.this, R.layout.activity_pop_one, null);
        cancle = (RelativeLayout) view1.findViewById(R.id.mine_pop_cancle);
        RelativeLayout camera = (RelativeLayout) view1.findViewById(R.id.mine_pop_camera);
        RelativeLayout selector = (RelativeLayout) view1.findViewById(R.id.mine_pop_selector);
        //创建popupwindow为全屏

        mPopupWindow = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //设置动画,就是style里创建的那个j
        mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
        //设置popupwindow的位置,这里直接放到屏幕上就行
        mPopupWindow.showAsDropDown(view1, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //为popwindow的每个item添加监听
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        //调用相机
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPublishPermission() == true) {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    String filea = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "icons";
                    File file = new File(filea);
                    file.mkdirs();
                    path = new File(file, System.currentTimeMillis() + ".jpg").getAbsolutePath();
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(UpIdCardActivity.this, UpIdCardActivity.this.getApplicationContext().getPackageName() + ".fileprovider", new File(path));
                    } else {
                        ContentValues contentValues = new ContentValues(1);
                        contentValues.put(MediaStore.Images.Media.DATA, path);
                        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, 110);
                    mPopupWindow.dismiss();
                } else if (checkPublishPermission() == false) {
                    Toast.makeText(UpIdCardActivity.this, "请先打开允许相机拍照权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //调用相册
        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 112);
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 检查权限
     * @return
     */
    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)) {
                permissions.add(android.Manifest.permission.CAMERA);
            }

            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                break;
            default:
                break;
        }
    }
    //上传正面
    private void updatePhoto(byte[] url) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), url);
        MultipartBody.Part body = MultipartBody.Part.createFormData("idCardFront", "idCardFront.jpg", requestBody);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        aClass.getIdCardFrontData(body,token).enqueue(new Callback<UpBean>() {
            @Override
            public void onResponse(Call<UpBean> call, Response<UpBean> response) {
                  UpBean body = response.body();
                if (body != null && body.getErrMsg() == null) {
                    String phonePath = body.getData().getPhotoUrl();
                    Log.i("huwei","huwei=========="+phonePath);
//                    Log.e("photoUrlphotoUrl", photoUrl);
                    if (!TextUtils.isEmpty(phonePath)) {
                        EventBus.getDefault().post(phonePath);
                        Picasso.with(UpIdCardActivity.this).load(phonePath).into(img_photo);
                        btn_photo.setText("重新上传正面照片");
                    }else{
                        btn_photo.setText("上传身份正面照");
                    }
                }
            }

            @Override
            public void onFailure(Call<UpBean> call, Throwable t) {
                Toast.makeText(UpIdCardActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("photoFailure", t.getMessage());
            }
        });
    }

    //反面
    private void updateReveres(byte[] url) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), url);
        MultipartBody.Part body = MultipartBody.Part.createFormData("idCardBack", "idCardBack.jpg", requestBody);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        aClass.getIdCardBackData(body,token).enqueue(new Callback<UpBean>() {
            @Override
            public void onResponse(Call<UpBean> call, Response<UpBean> response) {
                UpBean body = response.body();
                if (body != null && body.getErrMsg() == null) {
                    String phonePath = body.getData().getPhotoUrl();
//                    Log.e("photoUrlphotoUrl", photoUrl);
                    if (!TextUtils.isEmpty(phonePath)) {
                        EventBus.getDefault().post(phonePath);
                        Picasso.with(UpIdCardActivity.this).load(phonePath).into(img_reverse);
                        btn_reverse.setText("重新上传反面照片");
                    }else{
                        img_reverse.setVisibility(View.GONE);
                        btn_reverse.setText("上传反面照");
                    }
                }
            }

            @Override
            public void onFailure(Call<UpBean> call, Throwable t) {
                Toast.makeText(UpIdCardActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("photoFailure", t.getMessage());
            }
        });
    }


    public Bitmap bmpTopath(String path) {
        //先得到图片的参数类的对象Options
        BitmapFactory.Options options = new BitmapFactory.Options();
        //第一次，目的得到图片边缘区域的外宽和外高
        options.inJustDecodeBounds = true;
        //第一次解码得到图片的边界不加载图片的内容到内存
        BitmapFactory.decodeFile(path, options);
        //原图的宽和高
        int w = options.outWidth;
        int h = options.outHeight;
        //把原图的宽和高跟自己指定的宽和高进行对比得到缩放比例
        //假设缩小1/2
        options.inSampleSize = 1;
        //设置图片的每个颜色基数在内存所占的字节数（ARGB_8888：32）
        //ARGB_4444:16位
        //RGB_565:16位，推荐
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //第二次,得到缩小之后的图片进行加载
        options.inJustDecodeBounds = false;
        //第二次解码加载整个缩小图片的内容到内存
        return BitmapFactory.decodeFile(path, options);
    }

    public byte[] getBitmapByte(Bitmap bitmap) {   //将bitmap转化为byte[]类型也就是转化为二进制
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 110:
                    bitmap = bmpTopath(path);
                    byte[] bitmapByte = getBitmapByte(bitmap);
                    //上传拍照照片
                    updatePhoto(bitmapByte);
                    break;
                case 112:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    final String picturePath = cursor.getString(columnIndex);
                    Log.e("用户选择相册上传", "url: " + picturePath);
                    cursor.close();
                    Log.i("hw","hw=====picturePath======="+picturePath);
                    bitmap = bmpTopath(picturePath);
                    this.bitmapByte = getBitmapByte(bitmap);
                    //上传从相册取出来的图片
                    updatePhoto(this.bitmapByte);
                    img_photo.setVisibility(View.VISIBLE);
                    Toast.makeText(UpIdCardActivity.this,"图片上传成功",Toast.LENGTH_SHORT).show();
                    break;
                case 120:
                    bitmap_rese = bmpTopath(path);
                    byte[] bitmapByte_revers = getBitmapByte(bitmap_rese);
                    //上传拍照照片
                    updateReveres(bitmapByte_revers);
                    break;
                case 122:
                    Uri selectedImage_revese = data.getData();
                    String[] filePathColumn_rese = {MediaStore.Images.Media.DATA};
                    Cursor cursor_reves = getContentResolver().query(selectedImage_revese,
                            filePathColumn_rese, null, null, null);
                    cursor_reves.moveToFirst();
                    int columnIndex_res = cursor_reves.getColumnIndex(filePathColumn_rese[0]);
                    final String picturePath_res = cursor_reves.getString(columnIndex_res);
                    Log.e("用户选择相册上传", "url: " + picturePath_res);
                    cursor_reves.close();
                    bitmap = bmpTopath(picturePath_res);
                    this.bitmapByte_revers = getBitmapByte(bitmap);
                    //上传从相册取出来的图片
                    updateReveres(this.bitmapByte_revers);
                    img_photo.setVisibility(View.VISIBLE);
                    Toast.makeText(UpIdCardActivity.this,"图片上传成功",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
