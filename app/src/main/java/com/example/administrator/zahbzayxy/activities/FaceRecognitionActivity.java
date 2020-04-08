package com.example.administrator.zahbzayxy.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PLessonPlayTimeBean;
import com.example.administrator.zahbzayxy.ccvideo.MediaPlayActivity;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.ImageUtils;
import com.example.administrator.zahbzayxy.utils.NetworkUtils;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.example.administrator.zahbzayxy.vo.RecogInfo;
import com.example.administrator.zahbzayxy.widget.DefaultDialog;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaceRecognitionActivity extends FaceLivenessActivity {

    private DefaultDialog mDefaultDialog;
    private byte[] mImage;
    private byte[] fact_img;
    int courseId;
    int userCourseId;
    int playTime;
    int selectionId;
    String token;
    ImageView mCloseView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCloseView = mRootView.findViewById(com.baidu.idl.face.platform.ui.R.id.liveness_close);
        mCloseView.setVisibility(View.GONE);
        //无网络不需要识别
        if (NetworkUtils.isConnected(this)){
            if (isRootInVideo()){
//                initDownLoadData();
            }
        }else{
            showMessageToast("无网络连接");
        }


        if (ContextCompat.checkSelfPermission(FaceRecognitionActivity.this,android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FaceRecognitionActivity.this, new String[]{Manifest.permission.CAMERA}, 1000);
        }

    }

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        try{
            super.onLivenessCompletion(status, message, base64ImageMap);
            if (status == FaceStatusEnum.OK && mIsCompletion) {
                mImage = convertBitMapToFile(base64ImageMap);
                Bitmap bitmap = ImageUtils.getBitmapFromByte(mImage);
                //质量压缩
                Bitmap scaledBitmap = ImageUtils.compressImage(bitmap);
                fact_img= ImageUtils.getBitmapByte(scaledBitmap);
                setMessageWhat(0);
                //上传图片到服务器
                ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
                UploadFaceRecognitionRunnable task = new UploadFaceRecognitionRunnable();
                threadPool.submit(task);
            } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                    status == FaceStatusEnum.Error_LivenessTimeout ||
                    status == FaceStatusEnum.Error_Timeout) {
                showMessageToast("本次人脸对比失败，请点击“开始学习”重新进行人脸识别。");
                finishByVideo(false);
            }
        }
        catch (Exception e){
            Log.e("onLivenessCompletion", StringUtil.getExceptionMessage(e));
        }
    }
    private void showMessageDialog(String title, String message) {
        if (mDefaultDialog == null) {
            DefaultDialog.Builder builder = new DefaultDialog.Builder(this);
            builder.setTitle(title)
                    .setMessage(message);
            mDefaultDialog = builder.create();
            mDefaultDialog.setCancelable(true);
            mDefaultDialog.setCanceledOnTouchOutside(false);
        }
        mDefaultDialog.dismiss();
        mDefaultDialog.show();

    }
    private void closeMessageDialog(){
        if (mDefaultDialog!=null){
            mDefaultDialog.dismiss();
        }
    };


    private byte[] convertBitMapToFile(HashMap<String, String> imageMap){
        Set<Map.Entry<String, String>> sets = imageMap.entrySet();
        Bitmap bmp = null;
        byte[] imageBytes = null;
        bmp = base64ToBitmap(imageMap.get("specialImage"));
        if (bmp!=null){
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG,100,baos);
                baos.flush();
                baos.close();
                imageBytes = baos.toByteArray();
            } catch (Exception e) {
                Log.e("convertBitMapToFile", StringUtil.getExceptionMessage(e));
            }finally {
                if (baos!=null){
                    try {
                        baos.flush();
                        baos.close();
                    } catch (IOException e) {
                        Log.e("convertBitMapToFile", StringUtil.getExceptionMessage(e));
                    }

                }
            }
        }
        return imageBytes;
    }
    private static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64Utils.decode(base64Data, Base64Utils.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    /**
     * 上传图片识别
     */
    class UploadFaceRecognitionRunnable implements Runnable {
        @Override
        public void run() {
            try{
                //获取token
                SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
                token = tokenDb.getString("token","");
                courseId = getIntent().getIntExtra("coruseId",0);
                selectionId = getIntent().getIntExtra("selectionId",selectionId);
                userCourseId = getIntent().getIntExtra("userCourseId",0);
                playTime = getIntent().getIntExtra("playTime",playTime);
                if(fact_img==null){
                    setMessageWhat(1);
                    showMessageToast("本次人脸对比失败，请点击“开始学习”重新进行人脸识别");
                    finishByVideo(false);
                }else{
                    //通过OKHttp访问后台接口
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(8,TimeUnit.SECONDS)
                            .build();
                    RequestBody img = RequestBody.create(MediaType.parse("image/png"),fact_img);
                    Log.d("UploadFaceRecognition","token="+token+"   courseId="+courseId+"    userCourseId="+userCourseId+"   playTime="+playTime+"   selectioId="+selectionId+"   ");
                    RequestBody requestBody = new MultipartBody.Builder()
                            .addFormDataPart(Constant.TOKEN_PARAM, token)
                            .addFormDataPart(Constant.SECTION_ID, String.valueOf(selectionId))
                            .addFormDataPart(Constant.USER_COURSE_ID,String.valueOf(userCourseId))
                            .addFormDataPart(Constant.PLAY_TIME,String.valueOf(playTime))
                            .addFormDataPart(Constant.RECOGNITION_IMG,"face.png",img)
                            .build();
                    Request request = new Request.Builder()
                            .url(Constant.FACE_RECOGNITION_IMG_UIL)
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    response.close();
                    // 将json字符串转化成对应数据类
                    Gson gson = new Gson();
                    RecogInfo recogInfo = gson.fromJson( responseData, RecogInfo.class );
                    Log.d("UploadFaceRecognition","code="+recogInfo.getCode()+"   msg="+recogInfo.getErrMsg()+"   data="+recogInfo.isData());
                    //检查返回结果
                    checkUploadResult(recogInfo);
                }
            }
            catch (SocketTimeoutException e){
                Log.d("UploadFaceRecognition", StringUtil.getExceptionMessage(e));
                showMessageToast("服务器连接超时");
                finishByVideo(false);
            }catch (IOException e){
                Log.d("UploadFaceRecognition", StringUtil.getExceptionMessage(e));
                showMessageToast("本次人脸对比失败，请点击“开始学习”重新进行人脸识别");
                finishByVideo(false);
            }
        }
    }

    /**
     * 获取图片上传结果
     */
    private void checkUploadResult(RecogInfo recogInfo){
        try{
            setMessageWhat(1);
            if (Constant.SUCCESS_CODE.equals(recogInfo.getCode()) && recogInfo.isData()){
//                showMessageToast("识别成功");
                finishByVideo(true);
            }
            else if (Constant.PORTRAIT_NOT_EXIST.equals(recogInfo.getCode())){
                //人像被后台管理员删除
                showMessageToast(getString(R.string.prompt_Portrait_not_exist));
                finishByVideo(false);
            }
            else {
                showMessageToast("本次人脸对比失败，请点击“开始学习”重新进行人脸识别");
                finishByVideo(false);
            }
        }
        catch (Exception e){
            Log.e("checkUploadResult", StringUtil.getExceptionMessage(e));
        }
    }

    private void showMessageToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setMessageWhat(int what){
        Message msg = msgHandler.obtainMessage();
        msg.what = what;
        msg.sendToTarget();
    }

    @SuppressLint("HandlerLeak")
    protected Handler msgHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    showMessageDialog("人脸识别","识别中，请稍后...");
                    break;
                case 1:
                    closeMessageDialog();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        msgHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "相机权限已被禁止无法进行人脸识别,请在设置中打开", Toast.LENGTH_LONG).show();
                finishByVideo(false);
            }
        }
    }

    private boolean isRootInVideo(){
        int rootIn = getIntent().getIntExtra("rootIn",0);
        if (rootIn==2){
            return false;
        }
        return true;
    }

    /**
     * 退出画面
     * @param isSuccess
     */
    private void finishByVideo(boolean isSuccess){
        int rootIn = getIntent().getIntExtra("rootIn",0);
        try {
            switch (rootIn){
                case 0:
                    break;
                case 1:
                    if (isSuccess){
                        goToMediaPlayActivity(1);
                    }else{
                        FaceRecognitionActivity.this.finish();
                    }
                    break;
                case 2:
                    if (isSuccess){
//                        setResult(200);
                        goToMediaPlayActivity(2);
                        FaceRecognitionActivity.this.finish();
                    }else{
                        saveStudyTime();//保存视频播放进度
                        if(null != MediaPlayActivity.mediaPlayWeakReference) {
                            MediaPlayActivity ac = MediaPlayActivity.mediaPlayWeakReference.get();
                            if (null != ac) {
                                ac.finish();
                            }
                        }
                        FaceRecognitionActivity.this.finish();
                    }
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            showMessageToast("本次人脸对比失败，请点击“开始学习”重新进行人脸识别");
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goToMediaPlayActivity(int rootIn){
        Intent intent=new Intent(FaceRecognitionActivity.this, MediaPlayActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("userCourseId",userCourseId);
        bundle.putInt("coruseId",courseId);
        bundle.putString("token",token);
        bundle.putBoolean("isLocalPlay",getIntent().getBooleanExtra("isLocalPlay",false));
        bundle.putBoolean("isComeFromRecognition",true);
        if (rootIn==2){
            bundle.putString("videoId",getIntent().getStringExtra("videoId"));
            bundle.putDouble("getPlayPercent",getIntent().getDoubleExtra("getPlayPercent",0));
            bundle.putString("getSelectionName",getIntent().getStringExtra("getSelectionName"));
            bundle.putInt("selectionId",getIntent().getIntExtra("selectionId",0));
            bundle.putInt("currentPosition",getIntent().getIntExtra("currentPosition",0));
            bundle.putInt("posIndex",getIntent().getIntExtra("posIndex",0));
            bundle.putSerializable("listsize",getIntent().getSerializableExtra("listsize"));
        }

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    /**
     * 保存当前视频学习进度
     */
    private void saveStudyTime(){
        Integer  seconds = getIntent().getIntExtra(Constant.STUDY_SECONDS,-1);
        Integer  selectionIdGet = getIntent().getIntExtra("selectionId",-1);
        token = getIntent().getStringExtra("token");
        userCourseId = getIntent().getIntExtra("userCourseId",0);
        Log.e("UploadFaceRecognition", "saveStudyTime"+"seconds="+seconds+"selectionIdGet="+selectionIdGet);
        if (seconds<0||selectionIdGet<0||"".equals(token)||userCourseId<0){
            return;
        }
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getPMyLessonPlayTimeData(seconds, selectionIdGet, token, userCourseId).enqueue(new Callback<PLessonPlayTimeBean>() {
            @Override
            public void onResponse(Call<PLessonPlayTimeBean> call, Response<PLessonPlayTimeBean> response) {
                if (response != null) {
                    PLessonPlayTimeBean body = response.body();
                    if (body != null) {
                        if (body.getErrMsg() == null && body.getCode().equals("00000")) {
                            Log.e("saveStudyTime"," save the studyTime successful!");
                        }
                    } else {
                        Log.e("saveStudyTime","fail to save the studyTime!");
                    }
                }
            }

            @Override
            public void onFailure(Call<PLessonPlayTimeBean> call, Throwable t) {
                Log.e("saveStudyTime","fail to save the studyTime!");
            }
        });
    }

    @Override
    protected void saveImage(HashMap<String, String> imageMap) {
        Log.d("FaceRecognitionActivity", "saveImage");
    }
}
