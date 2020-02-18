package com.example.administrator.zahbzayxy.utils;

import android.graphics.Color;

/**
 * Project: CircleProgressView
 * Author: Guoger
 * Mail: 505917699@qq.com
 * Created time: 2016/4/18/16 11:11.
 */
public class Constant {
    //Color
    public static final int DEFAULT_ROUND_COLOR = Color.parseColor("#2E666666");
    public static final int DEFAULT_ROUND_PROGRESS_COLOR = Color.parseColor("#FFFF0000");
    public static final int DEFAULT_DESP_TEXT_COLOR = Color.parseColor("#FF000000");
    public static final int DEFAULT_VALUE_TEXT_COLOR = Color.parseColor("#FFFF0000");

    //Number
    public static final int DEFAULT_CIRCLE_STROKE_WIDTH = 5;
    public static final int DEFAULT_VALUE_TEXT_SIZE = 25;
    public static final int DEFAULT_DESP_TEXT_SIZE = 15;
    public static final int DEFAULT_ANIM_DURATION = 1000 * 1;

    //Style
    public static final int STYLE_STROKE = 0;
    public static final int STYLE_FILL = 1;

    //boolean
    public static final boolean DEFAULT_VALUE_TEXT_IS_DISPLAYABLE = true;
    public static final boolean DEFAULT_DESP_TEXT_IS_DISPLAYABLE = true;

    //String
    public static final String DEFAULT_VALUE_TEXT = "0";
    public static final String DEFAULT_DESP_TEXT = "";

    /*************************FHS Start********************************/
//    public static final String  GET_USER_INFO_URL           = "http://app.test.zayxy.com/userCenter/getUserInfo";
    public static final String  GET_USER_INFO_URL           = RetrofitUtils.getBaseUrl()+"/userCenter/getUserInfo";
    public static final String  TOKEN_PARAM                 = "token";
    public static final String  USER_INFO                   = "userInfo";
    public static final String  INTERVAL_TIME_KEY           = "intervalTimeKey";
    public static final String  IS_NEED_VERIFY_KEY          = "isNeedVerifyKey";

    public static final String  UPLOAD_PORTRAIT_URL         = "verify/uploadface";
    public static final String  PORTRAIT_PARAM              = "faceImg";
    public static final String  PORTRAIT                    = "portrait";
    public static final String  PORTRAIT_URL_KEY            = "portraitUrlKey";

    public static final int     NEED_VERIFY                 = 1;        //需要进行人脸识别
    public static final String  SUCCESS_CODE                = "00000";
//    public static final String  FACE_RECOGNITION_IMG_UIL    ="http://app.test.zayxy.com/verify/face/recognition/img";
    public static final String  FACE_RECOGNITION_IMG_UIL    =RetrofitUtils.getBaseUrl()+"/verify/face/recognition/img";
    public static final String  SECTION_ID                  = "sectionId";
    public static final String  USER_COURSE_ID              = "userCourseId";
    public static final String  PLAY_TIME                   = "playTime";
    public static final String  RECOGNITION_IMG             = "recognitionImg";
    public static final String  TOKEN_DB                    = "tokenDb";
    public static final String  WECHAT_LOGIN_KEY            = "wechatLogin";
    public static final int     GET_USR_INFO_ERR            = 1;
    public static final int     NEED_COLLECT_PORTRAIT       = 2;
    //课程保存的参数
    public static final String  STUDY_DB_KEY                = "studyDbKey";
    public static final String  STUDY_TOKEN                 = "token";
    public static final String  STUDY_SELECTION_ID_GET      = "selectionIdGet";
    public static final String  STUDY_SECONDS               = "seconds";
    public static final String  STUDY_USER_COURSE_ID        = "userCourseId";
    public static final String  STUDY_RECOGNIZE_SUCCESS     = "isRecognizeSuccess";
    //人脸对比照显示比例
    public static final float  PORTRAIT_WIDTH_RATIO_LOW     = 0.4f;
    public static final float  PORTRAIT_WIDTH_RATIO_HIGH    = 0.6f;
    public static final int    SHOW_PORTRAIT                = 1;
    public static final int    DEVICE_HEIGHT_EIGHT_HUNDRED  = 800;
    public static final int    PORTRAIT_COLLECT_SUCCEED     = 1;
    public static final int    PORTRAIT_UPLOAD_FAILURED     = 2;
    public static final int    PORTRAIT_UPLOAD_SUCCEED      = 3;
	public static final String  PORTRAIT_NOT_EXIST          = "00086";
    public static final String  BEST_IMG_KEY                = "bestImage0";
    /*************************FHS End********************************/
}
