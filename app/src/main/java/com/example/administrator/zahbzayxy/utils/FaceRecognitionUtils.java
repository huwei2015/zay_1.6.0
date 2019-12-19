package com.example.administrator.zahbzayxy.utils;

import android.content.Context;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.example.administrator.zahbzayxy.ExitApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by deng-xiaoxi on 2019/04/12.
 */

public class FaceRecognitionUtils {
    /**
     * @Author dxx
     * 初始化默认的人脸识别，不需要设置识别的参数
     * @param context
     */
    public static void initDefaultFaceRecognition(Context context){
        //初始化人脸识别SDK
        FaceSDKManager.getInstance().initialize(context, Config.licenseID, Config.licenseFileName);
        //添加活体动作
        addLivingAction();
        //设置语音提示
        setVoicePrompt();
        FaceSDKManager.getInstance().setFaceConfig(getFaceConfig());

    }

    /**
     * @Author dxx
     * 初始化默认的人脸识别，不需要设置识别的参数
     * @param context
     */
    public static void initContrastFaceRecognition(Context context){
        //初始化人脸识别SDK
        FaceSDKManager.getInstance().initialize(context, Config.licenseID, Config.licenseFileName);
        //添加活体动作
        List<LivenessTypeEnum> actionList = Arrays.asList(LivenessTypeEnum.Eye
                ,LivenessTypeEnum.Mouth,LivenessTypeEnum.HeadUp
                ,LivenessTypeEnum.HeadDown,LivenessTypeEnum.HeadLeft
                ,LivenessTypeEnum.HeadRight,LivenessTypeEnum.HeadLeftOrRight);
        int action = new Random().nextInt(actionList.size());
        ExitApplication.livenessList.clear();
        ExitApplication.livenessList.add(actionList.get(action));
        //设置语音提示
        setVoicePrompt();
        FaceSDKManager.getInstance().setFaceConfig(getFaceConfig());

    }

    /**
     * @author tu-mengting
     * 打开语音提示
     */
    private static void setVoicePrompt(){
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        config.setSound(true);
    }

    /**
     * @author dxx
     * 获取识别的参数设置类，此类可设置识别过程中的质量检测、遮挡阈值、光照等，
     * 具体参考百度SDK技术文档
     * 注：调用此方法之前不需要初始化人脸识别SDK
     * @param context
     * @return
     */
    public static FaceConfig getConfig(Context context){
        FaceSDKManager.getInstance().initialize(context, Config.licenseID, Config.licenseFileName);
        addLivingAction();//添加活体动作
        return FaceSDKManager.getInstance().getFaceConfig();
    }

    /**
     * 设置人脸识别参数，设置完成后即可开启人脸识别
     * @param config
     */
    public static void setFaceConfig(FaceConfig config){
        FaceSDKManager.getInstance().setFaceConfig(config);
    }

    /**
     * @author
     * 初始化SDK参数
     */
    public static FaceConfig getFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整

        config.setLivenessTypeList(ExitApplication.livenessList);
        config.setLivenessRandom(ExitApplication.isLivenessRandom);
        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        config.setCheckFaceQuality(true);
        config.setFaceDecodeNumberOfThreads(2);
        return config;
    }

    /**
     * @author dxx
     * 根据需求添加活体动作
     */
    public static void addLivingAction(){

//        List<LivenessTypeEnum> actionList = new ArrayList<>();
//        List<LivenessTypeEnum> actionList = Arrays.asList(LivenessTypeEnum.Eye
//                ,LivenessTypeEnum.Mouth,LivenessTypeEnum.HeadUp
//                ,LivenessTypeEnum.HeadDown,LivenessTypeEnum.HeadLeft
//                ,LivenessTypeEnum.HeadRight,LivenessTypeEnum.HeadLeftOrRight);
        List<LivenessTypeEnum> actionList = Arrays.asList(LivenessTypeEnum.Eye
                ,LivenessTypeEnum.Mouth,LivenessTypeEnum.HeadUp
                ,LivenessTypeEnum.HeadDown);
        Random random = new Random();
        int times = 0;
        int size = actionList.size();
        List<Integer> steps = new ArrayList<>();
        int step = 0;
        while (times<3){
            step = random.nextInt(size);
            if (!steps.contains(step)){
                steps.add(step);
                times++;
            }
        }
        ExitApplication.livenessList.clear();
        for(Integer s: steps){
            ExitApplication.livenessList.add(actionList.get(s));
        }
    }

}
