/**
 * Copyright (C) 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.idl.face.platform.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.aip.face.stat.Ast;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ILivenessStrategy;
import com.baidu.idl.face.platform.ILivenessStrategyCallback;
import com.baidu.idl.face.platform.ui.utils.CameraUtils;
import com.baidu.idl.face.platform.ui.utils.VolumeUtils;
import com.baidu.idl.face.platform.ui.widget.FaceDetectRoundView;
import com.baidu.idl.face.platform.utils.APIUtils;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.baidu.idl.face.platform.utils.CameraPreviewUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 活体检测接口
 */
public class FaceLivenessActivity extends Activity implements
        SurfaceHolder.Callback,
        Camera.PreviewCallback,
        Camera.ErrorCallback,
        VolumeUtils.VolumeCallback,
        ILivenessStrategyCallback {

    public static final String TAG = FaceLivenessActivity.class.getSimpleName();

    // View
    protected View mRootView;
    protected FrameLayout mFrameLayout;
    protected SurfaceView mSurfaceView;
    protected SurfaceHolder mSurfaceHolder;
    protected ImageView mCloseView;
    protected ImageView mSoundView;
    protected ImageView mSuccessView;
    protected TextView mTipsTopView;
    protected TextView mTipsBottomView;
    protected FaceDetectRoundView mFaceDetectRoundView;
    protected LinearLayout mImageLayout;
    // 人脸信息
    protected FaceConfig mFaceConfig;
    protected ILivenessStrategy mILivenessStrategy;
    // 显示Size
    private Rect mPreviewRect = new Rect();
    protected int mDisplayWidth = 0;
    protected int mDisplayHeight = 0;
    protected int mSurfaceWidth = 0;
    protected int mSurfaceHeight = 0;
    protected Drawable mTipsIcon;
    // 状态标识
    protected volatile boolean mIsEnableSound = true;
    protected HashMap<String, String> mBase64ImageMap = new HashMap<String, String>();
    protected boolean mIsCreateSurface = false;
    protected boolean mIsCompletion = false;
    // 相机
    protected Camera mCamera;
    protected Camera.Parameters mCameraParam;
    protected int mCameraId;
    protected int mPreviewWidth;
    protected int mPreviewHight;
    protected int mPreviewDegree;
    // 监听系统音量广播
    protected BroadcastReceiver mVolumeReceiver;

    private int takePictureStatus = 0; //正脸照的保存状态，0：未保存 1：需保存 2：已保存
    private String specilImageEndcode; //正脸图片的编码

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_face_liveness_v3100);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = this.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        mDisplayWidth = dm.widthPixels;
        mDisplayHeight = dm.heightPixels;

        FaceSDKResSettings.initializeResId();
        mFaceConfig = FaceSDKManager.getInstance().getFaceConfig();

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        mIsEnableSound = vol > 0 ? mFaceConfig.isSound : false;

        mRootView = this.findViewById(R.id.liveness_root_layout);
        mFrameLayout = (FrameLayout) mRootView.findViewById(R.id.liveness_surface_layout);

        mSurfaceView = new SurfaceView(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setSizeFromLayout();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        int w = mDisplayWidth;
        int h = mDisplayHeight;

        FrameLayout.LayoutParams cameraFL = new FrameLayout.LayoutParams(
                (int) (w * FaceDetectRoundView.SURFACE_RATIO), (int) (h * FaceDetectRoundView.SURFACE_RATIO),
                Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        mSurfaceView.setLayoutParams(cameraFL);
        mFrameLayout.addView(mSurfaceView);

        mRootView.findViewById(R.id.liveness_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mFaceDetectRoundView = (FaceDetectRoundView) mRootView.findViewById(R.id.liveness_face_round);
        mCloseView = (ImageView) mRootView.findViewById(R.id.liveness_close);
        mSoundView = (ImageView) mRootView.findViewById(R.id.liveness_sound);
        mSoundView.setImageResource(mIsEnableSound ?
                R.mipmap.ic_enable_sound_ext : R.mipmap.ic_disable_sound_ext);
        mSoundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsEnableSound = !mIsEnableSound;
                mSoundView.setImageResource(mIsEnableSound ?
                        R.mipmap.ic_enable_sound_ext : R.mipmap.ic_disable_sound_ext);
                if (mILivenessStrategy != null) {
                    mILivenessStrategy.setLivenessStrategySoundEnable(mIsEnableSound);
                }
            }
        });
        mTipsTopView = (TextView) mRootView.findViewById(R.id.liveness_top_tips);
        mTipsBottomView = (TextView) mRootView.findViewById(R.id.liveness_bottom_tips);
        mSuccessView = (ImageView) mRootView.findViewById(R.id.liveness_success_image);

        mImageLayout = (LinearLayout) mRootView.findViewById(R.id.liveness_result_image_layout);
        if (mBase64ImageMap != null) {
            mBase64ImageMap.clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mVolumeReceiver = VolumeUtils.registerVolumeReceiver(this, this);
        if (mTipsTopView != null) {
            mTipsTopView.setText(R.string.detect_face_in);
        }
        startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPreview();
    }

    @Override
    public void onStop() {
        if (mILivenessStrategy != null) {
            mILivenessStrategy.reset();
        }
        VolumeUtils.unRegisterVolumeReceiver(this, mVolumeReceiver);
        mVolumeReceiver = null;
        super.onStop();
        stopPreview();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void volumeChanged() {
        try {
            AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                int cv = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                mIsEnableSound = cv > 0;
                mSoundView.setImageResource(mIsEnableSound
                        ? R.mipmap.ic_enable_sound_ext : R.mipmap.ic_disable_sound_ext);
                if (mILivenessStrategy != null) {
                    mILivenessStrategy.setLivenessStrategySoundEnable(mIsEnableSound);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Camera open() {
        Camera camera;
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            return null;
        }

        int index = 0;
        while (index < numCameras) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(index, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                break;
            }
            index++;
        }

        if (index < numCameras) {
            camera = Camera.open(index);
            mCameraId = index;
        } else {
            camera = Camera.open(0);
            mCameraId = 0;
        }
        return camera;
    }

    protected void startPreview() {
        if (mSurfaceView != null && mSurfaceView.getHolder() != null) {
            mSurfaceHolder = mSurfaceView.getHolder();
            mSurfaceHolder.addCallback(this);
        }

        if (mCamera == null) {
            try {
                mCamera = open();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mCamera == null) {
            return;
        }

        if (mCameraParam == null) {
            mCameraParam = mCamera.getParameters();
        }

        mCameraParam.setPictureFormat(PixelFormat.JPEG);
        int degree = displayOrientation(this);
        mCamera.setDisplayOrientation(degree);
        // 设置后无效，camera.setDisplayOrientation方法有效
        mCameraParam.set("rotation", degree);
        mPreviewDegree = degree;

        Point point = CameraPreviewUtils.getBestPreview(mCameraParam,
                new Point(mDisplayWidth, mDisplayHeight));

        mPreviewWidth = point.x;
        mPreviewHight = point.y;
        // Preview 768,432

        if (mILivenessStrategy != null) {
            mILivenessStrategy.setPreviewDegree(degree);
        }

        mPreviewRect.set(0, 0, mPreviewHight, mPreviewWidth);

        mCameraParam.setPreviewSize(mPreviewWidth, mPreviewHight);
        mCamera.setParameters(mCameraParam);

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.stopPreview();
            mCamera.setErrorCallback(this);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (RuntimeException e) {
            e.printStackTrace();
            CameraUtils.releaseCamera(mCamera);
            mCamera = null;
        } catch (Exception e) {
            e.printStackTrace();
            CameraUtils.releaseCamera(mCamera);
            mCamera = null;
        }
    }

    protected void stopPreview() {
        if (mCamera != null) {
            try {
                mCamera.setErrorCallback(null);
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CameraUtils.releaseCamera(mCamera);
                mCamera = null;
            }
        }
        if (mSurfaceHolder != null) {
            mSurfaceHolder.removeCallback(this);
        }
        if (mILivenessStrategy != null) {
            mILivenessStrategy = null;
        }
    }

    private int displayOrientation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                degrees = 0;
                break;
        }
        int result = (0 - degrees + 360) % 360;
        if (APIUtils.hasGingerbread()) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(mCameraId, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }
        }
        return result;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsCreateSurface = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format,
                               int width,
                               int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;
        if (holder.getSurface() == null) {
            return;
        }
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsCreateSurface = false;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //获取正脸照片
        if (takePictureStatus==1){
            obtainSpecialImage(data,camera);
        }

        if (mIsCompletion) {
            return;
        }

        if (mILivenessStrategy == null) {
            mILivenessStrategy = FaceSDKManager.getInstance().getLivenessStrategyModule();
            mILivenessStrategy.setPreviewDegree(mPreviewDegree);
            mILivenessStrategy.setLivenessStrategySoundEnable(mIsEnableSound);

            Rect detectRect = FaceDetectRoundView.getPreviewDetectRect(
                    mDisplayWidth, mPreviewHight, mPreviewWidth);
            mILivenessStrategy.setLivenessStrategyConfig(
                    mFaceConfig.getLivenessTypeList(), mPreviewRect, detectRect, this);
        }
        mILivenessStrategy.livenessStrategy(data);
    }

    @Override
    public void onError(int error, Camera camera) {
    }

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message,
                                     HashMap<String, String> base64ImageMap) {
        if (mIsCompletion) {
            return;
        }

        onRefreshView(status, message);

        if (status == FaceStatusEnum.OK) {
            mIsCompletion = true;
            base64ImageMap.put("specialImage",specilImageEndcode);
            saveImage(base64ImageMap);
            takePictureStatus=2;
        }
        Ast.getInstance().faceHit("liveness");
    }

    private void onRefreshView(FaceStatusEnum status, String message) {
        switch (status) {
            case Detect_NoFace:     //未检测到人脸
                mTipsBottomView.setText(R.string.adjust_light_distance);
                break;
            case OK:
            case Liveness_OK:
            case Liveness_Completion:
                onRefreshTipsView(false, message);
                mTipsBottomView.setText("");
                mFaceDetectRoundView.processDrawState(false);
                onRefreshSuccessView(true);
                break;
            case Detect_DataNotReady:
            case Liveness_Eye:
            case Liveness_Mouth:
            case Liveness_HeadUp:
            case Liveness_HeadDown:
            case Liveness_HeadLeft:
            case Liveness_HeadRight:
            case Liveness_HeadLeftRight:
                takePictureStatus = takePictureStatus==0?1:2;
                onRefreshTipsView(false, message);
                mTipsBottomView.setText("");
                mFaceDetectRoundView.processDrawState(false);
                onRefreshSuccessView(false);
                break;
            case Detect_PitchOutOfUpMaxRange:
            case Detect_PitchOutOfDownMaxRange:
            case Detect_PitchOutOfLeftMaxRange:
            case Detect_PitchOutOfRightMaxRange:
                onRefreshTipsView(true, message);
                mTipsBottomView.setText(message);
                mFaceDetectRoundView.processDrawState(true);
                onRefreshSuccessView(false);
                break;
            default:
                onRefreshTipsView(false, message);
                mTipsBottomView.setText("");
                mFaceDetectRoundView.processDrawState(true);
                onRefreshSuccessView(false);
        }
    }

    private void onRefreshTipsView(boolean isAlert, String message) {
        if (isAlert) {
            if (mTipsIcon == null) {
                mTipsIcon = getResources().getDrawable(R.mipmap.ic_warning);
                mTipsIcon.setBounds(0, 0, (int) (mTipsIcon.getMinimumWidth() * 0.7f),
                        (int) (mTipsIcon.getMinimumHeight() * 0.7f));
                mTipsTopView.setCompoundDrawablePadding(15);
            }
            mTipsTopView.setBackgroundResource(R.drawable.bg_tips);
            mTipsTopView.setText(R.string.detect_standard);
            mTipsTopView.setCompoundDrawables(mTipsIcon, null, null, null);
        } else {
            mTipsTopView.setBackgroundResource(R.drawable.bg_tips_no);
            mTipsTopView.setCompoundDrawables(null, null, null, null);
            if (!TextUtils.isEmpty(message)) {
                mTipsTopView.setText(message);
            }
        }
    }

    private void onRefreshSuccessView(boolean isShow) {
        if (mSuccessView.getTag() == null) {
            Rect rect = mFaceDetectRoundView.getFaceRoundRect();
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mSuccessView.getLayoutParams();
            rlp.setMargins(
                    rect.centerX() - (mSuccessView.getWidth() / 2),
                    rect.top - (mSuccessView.getHeight() / 2),
                    0,
                    0);
            mSuccessView.setLayoutParams(rlp);
            mSuccessView.setTag("setlayout");
        }
        mSuccessView.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    protected void saveImage(HashMap<String, String> imageMap) {
        Set<Map.Entry<String, String>> sets = imageMap.entrySet();
        Bitmap bmp = null;
        mImageLayout.removeAllViews();
        for (Map.Entry<String, String> entry : sets) {
            bmp = base64ToBitmap(entry.getValue());
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(bmp);
            mImageLayout.addView(iv, new LinearLayout.LayoutParams(300, 300));
        }
    }

    private static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64Utils.decode(base64Data, Base64Utils.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 获取正脸照片
     * @param data
     * @param camera
     */
    private void obtainSpecialImage(byte[] data, Camera camera){
        try {
            Camera.Size previewSize = camera.getParameters().getPreviewSize();
            YuvImage yuvimage=new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 80, baos);
            byte[] specDate = baos.toByteArray();
            baos.flush();
            baos.close();
            if (previewSize.width>previewSize.height){
                //逆时针旋转九十度
                Matrix matrix = new Matrix();
                matrix.setRotate(270);
                Bitmap bitmap = BitmapFactory.decodeByteArray(specDate, 0, specDate.length);
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                baos = new ByteArrayOutputStream();
                newBitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                specDate = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            specilImageEndcode = Base64Utils.encodeToString(specDate, Base64Utils.NO_WRAP);
//                ImageView image = new ImageView(FaceLivenessActivity.this);
//                image.setImageBitmap(bitmap);
//                mImageLayout.addView(image, new LinearLayout.LayoutParams(300, 300));
            takePictureStatus = 2;
        }catch (RuntimeException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新进行人脸识别
     */
    public void resetRecogize(){

        specilImageEndcode = null;
        takePictureStatus = 0;

        if (mBase64ImageMap != null) {
            mBase64ImageMap.clear();
        }
        mImageLayout.removeAllViews();
        if (mILivenessStrategy != null) {
            mILivenessStrategy.reset();
            mIsCompletion = false;
        }
        stopPreview();
        if (mTipsTopView != null) {
            mTipsTopView.setText(R.string.detect_face_in);
        }
        startPreview();

    }

}
