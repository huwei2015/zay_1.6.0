package com.example.administrator.zahbzayxy.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.example.administrator.zahbzayxy.vo.UploadInfo;
import com.example.administrator.zahbzayxy.widget.DefaultDialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author tu-mengting
 * 人脸采集画面
 */
public class FaceCollectActivity extends FaceLivenessActivity {

    private Dialog mDefaultDialog;
    private HashMap<ImageView, String> portraitMap = new HashMap<>();
    private String selectedBase64Data = "";
    private List<ImageView> imageViewList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            //判断相机权限是否已被允许
            if (ContextCompat.checkSelfPermission(FaceCollectActivity.this,android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(FaceCollectActivity.this, new String[]{Manifest.permission.CAMERA}, 1000);
            }
        }catch (Exception e){
            Log.e("onCreate", StringUtil.getExceptionMessage(e));
        }
    }


    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        try{
            super.onLivenessCompletion(status, message, base64ImageMap);
            if (status == FaceStatusEnum.OK && mIsCompletion) {

            } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                    status == FaceStatusEnum.Error_LivenessTimeout ||
                    status == FaceStatusEnum.Error_Timeout) {
                showMessageDialog("人脸图像采集", "采集超时", true);
            }
        }
        catch (Exception e){
            Log.e("onLivenessCompletion", StringUtil.getExceptionMessage(e));
        }
    }

    private void showMessageDialog(String title, String message, final Boolean isNeedFinish) {
        try{
            DefaultDialog.Builder builder = new DefaultDialog.Builder(this);
            builder.setTitle(title).
                    setMessage(message).
                    setNegativeButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDefaultDialog.dismiss();
                                    if (isNeedFinish){
                                        finish();
                                    } else {}
                                }
                            });
            mDefaultDialog = builder.create();
            mDefaultDialog.setCanceledOnTouchOutside(false);
            if (null != mDefaultDialog && mDefaultDialog.isShowing()){
                mDefaultDialog.dismiss();
            }
            mDefaultDialog.show();
        }
        catch (Exception e){
            Log.e("showMessageDialog", StringUtil.getExceptionMessage(e));
        }
    }

    private void showDialog(int title, int message) {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            mDefaultDialog = builder.setTitle(title)
                    .setMessage(message)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (imageViewList!=null&&imageViewList.size()>0){
                                imageViewList.clear();
                            }
                            if (portraitMap.size()>0){
                                portraitMap.clear();
                            }
                            resetRecogize();
                        }
                    })
                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //点击了确定按钮
                            dialogInterface.dismiss();
                            //将图片保存到本地
                            saveImageToLocal(selectedBase64Data);
                            //上传图片到服务器
                            ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
                            UploadPortraitRunnable task = new UploadPortraitRunnable();
                            threadPool.submit(task);
                        }
                    }).create();
            mDefaultDialog.setCanceledOnTouchOutside(false);
            if ( null != mDefaultDialog && mDefaultDialog.isShowing()){
                mDefaultDialog.dismiss();
            }
            mDefaultDialog.show();
        }
        catch (Exception e){
            Log.e("showMessageDialog", StringUtil.getExceptionMessage(e));
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 将图片保存在本地（SharedPreferences）
     * @param imageData
     */
    private void saveImageToLocal(String imageData) {
        try{
            //将图片保存到本地
            SharedPreferences sp = getSharedPreferences(Constant.PORTRAIT, MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(Constant.PORTRAIT_PARAM, imageData);
            edit.commit();
        }
        catch (Exception e){
            Log.e("saveImage", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * 通过后台接口上传图片
     */
    class UploadPortraitRunnable implements Runnable {
        @Override
        public void run() {
            try{
                //获取token
                SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
                String token = tokenDb.getString("token","");
                //将图片转化为二进制
                SharedPreferences portrait = getSharedPreferences(Constant.PORTRAIT, MODE_PRIVATE);
                Bitmap bmp = base64ToBitmap(portrait.getString(Constant.PORTRAIT_PARAM, ""));
                //获取去除黑色背景后的图片
//                Bitmap bmp = cutBitmap(base64ToBitmap(portrait.getString(Constant.PORTRAIT_PARAM, "")));
                byte[] binaryBytes = getBitmapByte(bmp);

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binaryBytes);
                MultipartBody.Part body = MultipartBody.Part.createFormData("faceImg", "photo.jpg", requestBody);
                UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
                userInfoInterface.uploadPortrait(body,token).enqueue(new Callback<UploadInfo>() {
                    @Override
                    public void onResponse(Call<UploadInfo> call, Response<UploadInfo> response) {
                        UploadInfo uploadInfo = response.body();
                        //检查返回结果
                        checkUploadResult(uploadInfo);
                    }
                    @Override
                    public void onFailure(Call<UploadInfo> call, Throwable t) {
                        Message msg = handler.obtainMessage();
                        msg.what = Constant.PORTRAIT_UPLOAD_FAILURED;
                        msg.sendToTarget();
                        Log.e("photoFailure",t.getMessage());
                    }
                });
            }
            catch (Exception e){
                Log.d("FaceRecogRunnable", StringUtil.getExceptionMessage(e));
            }
        }
    }

    /**
     * 图片转换为二进制(byte[])
     * @param bitmap
     *            图片
     * @return
     *       图片二进制流
     * */
    public byte[] getBitmapByte(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bitmapBytes;
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            bitmapBytes = out.toByteArray();
        } catch (Exception e) {
            bitmapBytes = null;
            Log.e("getBitmapByte", StringUtil.getExceptionMessage(e));
        }
        return bitmapBytes;
    }

    private static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64Utils.decode(base64Data, Base64Utils.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 获取图片上传结果
     */
    private void checkUploadResult(UploadInfo uploadInfo){
        try{
            Message msg = handler.obtainMessage();
            SharedPreferences sp = getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            if (Constant.SUCCESS_CODE.equals(uploadInfo.getCode()) && !"".equals(uploadInfo.getData().getFaceUrl())){
                msg.what = Constant.PORTRAIT_UPLOAD_SUCCEED;
                //将返回的人脸图片地址保存在本地
                edit.putString(Constant.PORTRAIT_URL_KEY, uploadInfo.getData().getFaceUrl());
            }
            else {
                edit.putString(Constant.PORTRAIT_URL_KEY, "");
                msg.what = Constant.PORTRAIT_UPLOAD_FAILURED;
            }
            edit.commit();
            msg.sendToTarget();
        }
        catch (Exception e){
            Log.e("checkUploadResult", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * 用于更新画面
     */
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try{
                switch (msg.what){
                    case Constant.PORTRAIT_COLLECT_SUCCEED:
                        //弹出图像采集成功的提示框
                        showMessageDialog("人脸图像采集", "采集成功，请在下方挑选一张五官清晰的图片进行上传，模糊的图片会导致后续人脸识别验证失败,请谨慎选择", false);
                        break;
                    case Constant.PORTRAIT_UPLOAD_FAILURED:
                        //弹出图像上传失败的提示框
                        showMessageDialog("人脸图像上传", "上传失败", true);
                        break;
                    case Constant.PORTRAIT_UPLOAD_SUCCEED:
                        //弹出图像采集成功的提示框
                        showMessageDialog("人脸图像采集", "上传成功", true);
                    default:
                        break;
                }
            }catch (Exception e){
                Log.e("", StringUtil.getExceptionMessage(e));
            }
        }
    };

    @Override
    protected void saveImage(HashMap<String, String> imageMap) {
        try{
            Iterator iterator = imageMap.entrySet().iterator();
            Bitmap bmp = null;
            mImageLayout.removeAllViews();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                bmp = base64ToBitmap((String)entry.getValue());
                if (!Constant.BEST_IMG_KEY.equals((String)entry.getKey())){
                    ImageView iv = new ImageView(this);
                    iv.setImageBitmap(bmp);
                    iv.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Iterator iterator = portraitMap.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Map.Entry entry = (Map.Entry) iterator.next();
                                ImageView imageView = (ImageView) entry.getKey();
                                if (imageView.equals(view)){
                                    //用户选择了当前照片
                                    selectedBase64Data = (String)entry.getValue();
                                    //显示确认上传对话框
                                    showDialog( R.string.title_confirm_upload, R.string.content_confirm_upload);
                                    break;
                                }
                                else {}
                            }
                        }
                    });
                    imageViewList.add(iv);
                    portraitMap.put(iv, (String)entry.getValue());
                }else {}
            }

            for (int i=0; i<imageViewList.size(); i++){
                mImageLayout.addView(imageViewList.get(i), new LinearLayout.LayoutParams(300, 300));
            }
            Message msg = handler.obtainMessage();
            msg.what = Constant.PORTRAIT_COLLECT_SUCCEED;
            msg.sendToTarget();
        }catch (Exception e){
            Log.e("saveImage", StringUtil.getExceptionMessage(e));
        }
    }
}
