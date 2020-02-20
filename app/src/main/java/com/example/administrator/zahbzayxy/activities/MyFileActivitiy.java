package com.example.administrator.zahbzayxy.activities;


import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
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
import com.example.administrator.zahbzayxy.beans.UpdateBean;
import com.example.administrator.zahbzayxy.fragments.ExcelFragment;
import com.example.administrator.zahbzayxy.fragments.FileAllFragment;
import com.example.administrator.zahbzayxy.fragments.PdfFragment;
import com.example.administrator.zahbzayxy.fragments.PicFragment;
import com.example.administrator.zahbzayxy.fragments.WordFragment;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.manager.DownloadManager;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ImageUtils;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private static final int REQUEST_EXTERNAL_STORAGE_FILE = 100;
    private static final int REQUEST_EXTERNAL_STORAGE_PAGE = 101;

    private ViewPager myYouHuiJuan_vp;
    private TabLayout myYouHuiJuan_tab;
    private List<String> myYouHuiJuanTabList;
    private List<Fragment> myYouHuiJuanVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    FragmentManager fragmentManager;
    TextView tv_updateFile, tv_updateImg;
    private String token;
    boolean bPermission = false;
    private Bitmap bitmap;
    private byte[] bitmapByte;
    private ProgressBarLayout mLoading;
    private int mPosition = 0;

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
        myYouHuiJuan_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("=====position======", position + "");
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
        mLoading = (ProgressBarLayout) findViewById(R.id.my_file_loading_layout);
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

    private boolean checkPermission(int code) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(MyFileActivitiy.this, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(MyFileActivitiy.this, PERMISSIONS_STORAGE, code);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 345:
                    String filePath = getPath(MyFileActivitiy.this, data.getData());
                    if (TextUtils.isEmpty(filePath)) {
                        ToastUtils.showLongInfo("获取文件失败，请重试");
                        return;
                    }
                    String endFile = filePath.substring(filePath.lastIndexOf("/") + 1);
                    if (TextUtils.isEmpty(endFile)) {
                        ToastUtils.showLongInfo("文件类型错误，请重新选择");
                        return;
                    }
                    int pointIndex = endFile.lastIndexOf(".");
                    String fileSuffix = "";
                    if (pointIndex > 0) {
                        fileSuffix = endFile.substring(pointIndex + 1);
                    }
                    if (TextUtils.isEmpty(fileSuffix)) {
                        ToastUtils.showLongInfo("不支持的文件类型");
                        return;
                    }
                    fileSuffix = fileSuffix.toLowerCase();
                    if (!("jpg".equals(fileSuffix) || "jpeg".equals(fileSuffix) || "png".equals(fileSuffix)
                            || "xls".equals(fileSuffix) || "xlsx".equals(fileSuffix) || "xlsm".equals(fileSuffix)
                            || "doc".equals(fileSuffix) || "docx".equals(fileSuffix)
                            || "pdf".equals(fileSuffix))) {
                        ToastUtils.showLongInfo("不能上传该类型的文件");
                        return;
                    }
                    mLoading.setShowContent("上传中...");
                    mLoading.setVisibility(View.VISIBLE);
                    //TODO 这里只是暂时这么处理，需要考虑OOM的问题
                    byte[] file = file2Bytes(new File(filePath));
                    updatePhoto(file, endFile);
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

                    String endFile1 = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                    if (TextUtils.isEmpty(endFile1)) {
                        ToastUtils.showLongInfo("文件类型错误，请重新选择");
                        return;
                    }
                    int pointIndex1 = endFile1.lastIndexOf(".");
                    String fileSuffix1 = "";
                    if (pointIndex1 > 0) {
                        fileSuffix1 = endFile1.substring(pointIndex1 + 1);
                    }
                    if (TextUtils.isEmpty(fileSuffix1)) {
                        ToastUtils.showLongInfo("不支持的文件类型");
                        return;
                    }
                    fileSuffix1 = fileSuffix1.toLowerCase();
                    if (!("jpg".equals(fileSuffix1) || "jpeg".equals(fileSuffix1) || "png".equals(fileSuffix1))) {
                        ToastUtils.showLongInfo("不支持的类型，只支持JPG、png图片格式");
                        return;
                    }
                    mLoading.setShowContent("上传中...");
                    mLoading.setVisibility(View.VISIBLE);
                    bitmap = bmpTopath(picturePath);
                    this.bitmapByte = getBitmapByte(bitmap);
                    //上传从相册取出来的图片
                    updatePhoto(this.bitmapByte, System.currentTimeMillis() + ".jpg");
                    break;
            }
        }
    }

    private byte[] file2Bytes(File file) {
        int byte_size = 1024;
        byte[] b = new byte[byte_size];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(byte_size);
            for (int length; (length = fileInputStream.read(b)) != -1; ) {
                outputStream.write(b, 0, length);
            }
            fileInputStream.close();
            outputStream.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private void updatePhoto(byte[] url, String fileEndStr) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), url);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileEndStr, requestBody);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        aClass.updateFile(token, body).enqueue(new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                mLoading.setVisibility(View.GONE);
                String code = response.body().getCode();
                if(code.equals("00000")){
                    boolean data = response.body().isData();
                    if(data){
                        EventBus.getDefault().post("fileUploadSuccess");
                        Toast.makeText(MyFileActivitiy.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                mLoading.setVisibility(View.GONE);
                EventBus.getDefault().post("fileUploadSuccess");
                try {
                    Toast.makeText(MyFileActivitiy.this, "上传失败", Toast.LENGTH_SHORT).show();
                    Log.e("photoFailure", t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_FILE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                selectFile();
                break;
            case REQUEST_EXTERNAL_STORAGE_PAGE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 112);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_updateFile:
                if (checkPermission(REQUEST_EXTERNAL_STORAGE_FILE)) {
                    selectFile();
                }
                break;
            case R.id.tv_pic://上传图片
                if (checkPermission(REQUEST_EXTERNAL_STORAGE_PAGE)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 112);
                }
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
        return ImageUtils.compressImage(bitmap, 300);
    }
}
