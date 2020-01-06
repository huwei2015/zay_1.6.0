package com.example.administrator.zahbzayxy.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;
import com.example.administrator.zahbzayxy.beans.OneCunBean;
import com.example.administrator.zahbzayxy.fragments.ExcelFragment;
import com.example.administrator.zahbzayxy.fragments.FileAllFragment;
import com.example.administrator.zahbzayxy.fragments.PdfFragment;
import com.example.administrator.zahbzayxy.fragments.PicFragment;
import com.example.administrator.zahbzayxy.fragments.WordFragment;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import org.greenrobot.eventbus.EventBus;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.administrator.zahbzayxy.R.id.myYouhuiJuan_tab;
import static com.example.administrator.zahbzayxy.R.id.switch_msg;

/**
 * Created by huwei.
 * Data 2019-12-18.
 * Time 16:10.
 * 我的附件
 */
public class MyFileActivitiy extends BaseActivity implements View.OnClickListener {
    private ViewPager myYouHuiJuan_vp;
    private TabLayout myYouHuiJuan_tab;
    private List<String> myYouHuiJuanTabList;
    private List<Fragment> myYouHuiJuanVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    FragmentManager fragmentManager;
    TextView tv_updateFile, tv_updateImg;
    private final int WRITE_PERMISSION_REQ_CODE = 100;
    private String token;
    boolean bPermission = false;
    private Bitmap bitmap;
    private byte[] bitmapByte;
    String photo_name;//拍照名称
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_file);
        initView();
        initViewPagerAndTable();
    }

    private void initViewPagerAndTable() {
        myYouHuiJuanTabList = new ArrayList<>();
        myYouHuiJuanVPList = new ArrayList<>();
        myYouHuiJuanTabList.add("全部");
        myYouHuiJuanTabList.add("图片");
        myYouHuiJuanTabList.add("DOC");
        myYouHuiJuanTabList.add("PDF");
        myYouHuiJuanTabList.add("EXCEL");
        FileAllFragment fileAllFragment = new FileAllFragment();
        PicFragment picFragment = new PicFragment();
        WordFragment wordFragment = new WordFragment();
        PdfFragment pdfFragment = new PdfFragment();
        ExcelFragment excelFragment = new ExcelFragment();
        myYouHuiJuanVPList.add(fileAllFragment);
        myYouHuiJuanVPList.add(picFragment);
        myYouHuiJuanVPList.add(wordFragment);
        myYouHuiJuanVPList.add(pdfFragment);
        myYouHuiJuanVPList.add(excelFragment);
        fragmentManager = getSupportFragmentManager();
        pagerAdapter = new LessonFragmentPageAdapter(fragmentManager, myYouHuiJuanVPList, myYouHuiJuanTabList);
        myYouHuiJuan_vp.setAdapter(pagerAdapter);
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(0)));
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(1)));
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(2)));
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(3)));
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(4)));
        myYouHuiJuan_tab.setupWithViewPager(myYouHuiJuan_vp);
    }

    private void initView() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        myYouHuiJuan_vp = (ViewPager) findViewById(R.id.myYouHuiJuan_vp);
        myYouHuiJuan_tab = (TabLayout) findViewById(myYouhuiJuan_tab);
        tv_updateFile = (TextView) findViewById(R.id.tv_updateFile);
        tv_updateFile.setOnClickListener(this);
        tv_updateImg = (TextView) findViewById(R.id.tv_pic);
        tv_updateImg.setOnClickListener(this);
    }

    public void myYouHuiJuanBackOnClick(View view) {
        finish();
    }

    /**
     * 通过手机选择文件
     */
    public void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件上传"), 345);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MyFileActivitiy.this, "请安装一个文件管理器", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 345:
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
                    bitmap = bmpTopath(picturePath);
                    this.bitmapByte = getBitmapByte(bitmap);
                    //上传从相册取出来的图片
                    updatePhoto(this.bitmapByte);
                    Toast.makeText(MyFileActivitiy.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void updatePhoto(byte[] url) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), url);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", System.currentTimeMillis()+".jpg", requestBody);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        aClass.updateFile(token, body).enqueue(new Callback<OneCunBean>() {
            @Override
            public void onResponse(Call<OneCunBean> call, Response<OneCunBean> response) {
                OneCunBean body = response.body();
                if (body != null && body.getErrMsg() != null) {
                    String photoUrl = body.getData().getPhotoUrl();
                    Log.e("photoUrlphotoUrl", photoUrl);
                    if (!TextUtils.isEmpty(photoUrl)) {
                        EventBus.getDefault().post(photoUrl);
                    }
                }
            }

            @Override
            public void onFailure(Call<OneCunBean> call, Throwable t) {
                Toast.makeText(MyFileActivitiy.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("photoFailure", t.getMessage());
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_updateFile:
                selectFile();
                break;
            case R.id.tv_pic://上传图片
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 112);
                break;
        }
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
}
