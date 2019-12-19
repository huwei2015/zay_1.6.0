package com.example.administrator.zahbzayxy.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.Category;
import com.example.administrator.zahbzayxy.beans.PUserHeadPhotoBean;
import com.example.administrator.zahbzayxy.beans.UserInfoBean;
import com.example.administrator.zahbzayxy.beans.UserInfoData;
import com.example.administrator.zahbzayxy.beans.UserInfoResetBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.FaceRecognitionUtils;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMessageActivity extends BaseActivity implements View.OnClickListener {
    TextView nickName_tv, userName_tv, phoneNum_tv, userSex_tv, personNum_tv, gangWei_tv, danWei_tv,tv_work,
            tv_professional,tv_type,tv_skills;
    private String phone;
    private RelativeLayout headPhoto_layout, nickName_layout, nameSet_layout, sexSet_layout, personId_layout,
            phone_layout, gangWei_layout, danWei_layout, changPw_layout, culture_layout, one_inch_layout,work_layout;
    private RelativeLayout faceRecognitionLayout,professional_layout,type_layout,skills_layout;
    Dialog dialog;
    //获取到的token
    private String token;
    private RadioGroup sexChoice_rg, rg_culture;
    private TextView dismiss_sex, dismiss_culture, tv_culture;
    private int gender;
    private String sexChoice;
    private int educationalLevel;
    private String culture;
    private RelativeLayout cancle;
    private ImageView headPhoto_iv, finish_iv, faceIdentifyIV, one_inch_view;
    private boolean isLogin;
    private String path;

    private final int WRITE_PERMISSION_REQ_CODE = 100;
    boolean bPermission = false;

    private PopupWindow mPopupWindow;
    private boolean wechatLogin;
    private String weChatHeadImg;
    private Bitmap bmp;
    private byte[] bitmapByte;
    private Bitmap bmp1;
    private String oneInchPhoto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);
        checkPublishPermission();
        initView();
        initGetToken();
        initUserInfo();
    }

    private void initGetToken() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        isLogin = tokenDb.getBoolean("isLogin", false);
    }

    private void initView() {
        headPhoto_iv = (ImageView) findViewById(R.id.headPhoto_iv);
        nickName_tv = (TextView) findViewById(R.id.nickName_tv);
        userName_tv = (TextView) findViewById(R.id.userName_tv);
        phoneNum_tv = (TextView) findViewById(R.id.phoneNum_tv);
        userSex_tv = (TextView) findViewById(R.id.userSex_tv);//性别
        personNum_tv = (TextView) findViewById(R.id.personNum_tv);
        gangWei_tv = (TextView) findViewById(R.id.gangWei_tv);
        danWei_tv = (TextView) findViewById(R.id.danWei_tv);
        tv_culture = (TextView) findViewById(R.id.tv_culture);//文化程度
        work_layout = (RelativeLayout) findViewById(R.id.work_layout);//工作单位
        tv_work= (TextView) findViewById(R.id.tv_work);//工作单位
        headPhoto_layout = (RelativeLayout) findViewById(R.id.headMessage_edit_layout);
        nickName_layout = (RelativeLayout) findViewById(R.id.edit_nickName_layout);
        nameSet_layout = (RelativeLayout) findViewById(R.id.nameSet_layout);
        sexSet_layout = (RelativeLayout) findViewById(R.id.sexSet_layout);
        personId_layout = (RelativeLayout) findViewById(R.id.personId_layout);
        phone_layout = (RelativeLayout) findViewById(R.id.phoneSet_layout);
        gangWei_layout = (RelativeLayout) findViewById(R.id.gangWeiSet_layout);
        danWei_layout = (RelativeLayout) findViewById(R.id.danWeiSet_layout);
        changPw_layout = (RelativeLayout) findViewById(R.id.pWChange_layout);
        finish_iv = (ImageView) findViewById(R.id.back_editMessage);
        culture_layout = (RelativeLayout) findViewById(R.id.culture_layout);//文化程度
        one_inch_layout = (RelativeLayout) findViewById(R.id.one_inch_layout);//一寸照片
        one_inch_view = (ImageView) findViewById(R.id.one_inch_view);//一寸照片显示图片
        professional_layout= (RelativeLayout) findViewById(R.id.professional_layout);//职业名称
        tv_professional= (TextView) findViewById(R.id.tv_professional);//输入职业名称
        type_layout= (RelativeLayout) findViewById(R.id.type_layout);//工种名称
        tv_type= (TextView) findViewById(R.id.tv_type);//输入工种名称
        skills_layout= (RelativeLayout) findViewById(R.id.skills_layout);//职业技能等级
        tv_skills= (TextView) findViewById(R.id.tv_skills);//输入职业技能等级
        /**********************FHS Start******************/
        //人脸对比照片
        faceRecognitionLayout = (RelativeLayout) findViewById(R.id.face_recognition_layout);
        faceIdentifyIV = (ImageView) findViewById(R.id.civFaceIdentify);
        //获取需不需要进行人脸识别
        SharedPreferences sp = getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
        int isNeedVerify = sp.getInt(Constant.IS_NEED_VERIFY_KEY, 0);
        if (Constant.NEED_VERIFY == isNeedVerify) {
            //需要人脸识别,显示“人脸识别”
            faceRecognitionLayout.setVisibility(View.VISIBLE);
            //加载人脸识别图片
            loadFaceIdentifyPic();
        } else {
            //不需要人脸识别,不显示“人脸识别”
            faceRecognitionLayout.setVisibility(View.GONE);
        }
        /**********************FHS End******************/
        headPhoto_layout.setOnClickListener(this);
        nickName_layout.setOnClickListener(this);
        nameSet_layout.setOnClickListener(this);
        sexSet_layout.setOnClickListener(this);
        personId_layout.setOnClickListener(this);
        phone_layout.setOnClickListener(this);
        gangWei_layout.setOnClickListener(this);
        danWei_layout.setOnClickListener(this);
        changPw_layout.setOnClickListener(this);
        culture_layout.setOnClickListener(this);
        one_inch_layout.setOnClickListener(this);
        work_layout.setOnClickListener(this);
        professional_layout.setOnClickListener(this);
        type_layout.setOnClickListener(this);
        skills_layout.setOnClickListener(this);
        finish_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**********************FHS Start******************/
        faceRecognitionLayout.setOnClickListener(this);
        /**********************FHS End******************/
    }

    private void initUserInfo() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserInfoBean> userInfoData = userInfoInterface.getUserInfoData(token);
        userInfoData.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                UserInfoBean body = response.body();
                String s = new Gson().toJson(body);
                Log.e("aaaassss", s);
                if (response != null && body != null) {
                    if ((body.getData() != null) && isLogin == true) {
                        UserInfoBean.DataBean data = body.getData();
                        phone = data.getPhone();
                        gender = data.getGender();
                        String nickName = data.getNickName();
                        String name = (String) data.getUserName();
                        //设置姓名
                        if (!TextUtils.isEmpty(name)) {
                            userName_tv.setText(name);
                        }
                        // Log.e("sss", "name: "+name);
                        String image = (String) data.getPhotoUrl();
                        //      Log.e("onResponse: ",station+"" );
                        Object idCard = data.getIdCard();
                        if (gender == 1) {
                            userSex_tv.setText("男");
                        }
                        if (gender == 2) {
                            userSex_tv.setText("女");
                        }
                        if (gender != 1 && gender != 2) {
                            userSex_tv.setText("保密");
                        }
                        //文化程度
                        educationalLevel = data.getEducationalLevel();
                        if (educationalLevel == 1) {// 初中
                            tv_culture.setText("初中");
                        } else if (educationalLevel == 2) {//中专或者高中
                            tv_culture.setText("中专或者高中");
                        } else if (educationalLevel == 3) {//大专
                            tv_culture.setText("大专");
                        } else if (educationalLevel == 4) {//本科
                            tv_culture.setText("本科");
                        } else if (educationalLevel == 5) {//硕士
                            tv_culture.setText("硕士");
                        } else if (educationalLevel == 6) {//博士
                            tv_culture.setText("博士");
                        } else if (educationalLevel == 0) {
                            tv_culture.setText("");
                        }

                        if (!TextUtils.isEmpty(nickName)) {
                            nickName_tv.setText(nickName);
                        }
                        String unit =(String)data.getUnit();
                        //单位
                        if(!TextUtils.isEmpty(unit)){
                            tv_work.setText(unit);
                        }
                        //职业名称
                        String occupaName = data.getOccupaName();
                        if(!TextUtils.isEmpty(occupaName)){
                            tv_professional.setText(occupaName);
                        }
                        //工种名称
                        String wokrTypeName=data.getWorkTypeName();
                        if(!TextUtils.isEmpty(wokrTypeName)){
                            tv_type.setText(wokrTypeName);
                        }
                        //职业技能等级
                       String occLevel= data.getOccupaSkillLevel();
                        if(!TextUtils.isEmpty(occLevel)){
                            tv_skills.setText(occLevel);
                        }
                        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
                        wechatLogin = sharedPreferences.getBoolean("wechatLogin", false);
                        weChatHeadImg = sharedPreferences.getString("weChatHeadImg", "");
                        if (wechatLogin == true) {
                            if (!TextUtils.isEmpty(weChatHeadImg)) {
                                Picasso.with(EditMessageActivity.this).load(weChatHeadImg).into(headPhoto_iv);
                            }
                        } else if (wechatLogin == false) {
                            if (!TextUtils.isEmpty(image)) {
                                Picasso.with(EditMessageActivity.this).load(image).into(headPhoto_iv);
                            }
                        }
                        //一寸照片
                        oneInchPhoto = data.getOneInchPhoto();
                        if (!TextUtils.isEmpty(oneInchPhoto)) {
                            Picasso.with(EditMessageActivity.this).load(oneInchPhoto).into(one_inch_view);
                        }

                        String quarters = data.getQuarters();//岗位
                        if (!TextUtils.isEmpty(quarters)) {
                            gangWei_tv.setText(quarters);
                            //之前岗位是选择
//                            ArrayList<Category> gangWeiData = UserInfoData.getGangWeiData();
//                            int size = gangWeiData.size();
//                            for (int i = 0; i < size; i++) {
//                                // Log.e("nameSSs",s1+","+item);
//                                Category category = gangWeiData.get(i);
//                                int itemCount = category.getItemCount();
//                                for (int j = 0; j < itemCount; j++) {
//                                    String item1 = category.getItem(j);
//                                    Log.e("nameSSs", "," + item1);
//                                    if (item1.contains(quarters)) {
//                                        String[] split = item1.split(" ");
//                                        String gangWei = split[1];
//                                        gangWei_tv.setText(gangWei);
//                                    }
//                                }
//                            }

                        }

                        String industry = data.getIndustry();//行业
                        if (!TextUtils.isEmpty(industry)) {
                            ArrayList<Category> hangYeData = UserInfoData.getHangYeData();
                            int size = hangYeData.size();
                            for (int i = 0; i < size; i++) {
                                Category category = hangYeData.get(i);
                                int itemCount = category.getItemCount();
                                for (int j = 0; j < itemCount; j++) {
                                    String item1 = category.getItem(j);
                                    Log.e("nameSSs", "," + item1 + "," + industry);
                                    if (item1.contains(industry)) {
                                        String[] split = item1.split(" ");
                                        String gangWei = split[1];
                                        danWei_tv.setText(gangWei);
                                    }
                                }
                            }


                        }


                        if (!TextUtils.isEmpty(phone)) {
                            phoneNum_tv.setText(phone);
                        }
                        if (!TextUtils.isEmpty((String) idCard)) {
                            personNum_tv.setText((String) idCard);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            //设置头像
            case R.id.headMessage_edit_layout:
                initHeadPhoto();
                break;
            //设置昵称
            case R.id.edit_nickName_layout:
                intent = new Intent(EditMessageActivity.this, NickNameActivity.class);
                startActivityForResult(intent, 2);
                break;
            //设置姓名
            case R.id.nameSet_layout:
                intent = new Intent(EditMessageActivity.this, SetUserNameActivity.class);
                startActivityForResult(intent, 3);
                break;
            //设置性别
            case R.id.sexSet_layout:
                initSex();
                break;
            case R.id.dissmess_sex:
                dialog.dismiss();
                break;
            //设置身份证号
            case R.id.personId_layout:
                intent = new Intent(EditMessageActivity.this, SetUserIdActivity.class);
                //参数二;请求码
                startActivityForResult(intent, 5);
                break;
            /**********************FHS Start******************/
            //设置人脸对比照片
            case R.id.face_recognition_layout:
                if (isPortraitExist()) {
                    //人像已存在，跳转到人像对比照画面
                    startActivity(new Intent(this, PortraitDisplayActivity.class));
                } else {
                    //人像不存在，跳转到人像采集画面,初始化SDK
                    FaceRecognitionUtils.initDefaultFaceRecognition(this);
                    //开启预览
                    startActivity(new Intent(this, FaceCollectActivity.class));
                }
                break;
            /**********************FHS End******************/
            //设置手机号
            case R.id.phoneSet_layout:
//                intent = new Intent(EditMessageActivity.this, SetPhoneActivity.class);
//                startActivityForResult(intent, 6);
                break;
            //设置岗位
            case R.id.gangWeiSet_layout:
//                intent = new Intent(EditMessageActivity.this, SetGangWeiActivity.class);
                intent = new Intent(EditMessageActivity.this,jobsActivity.class);
                startActivityForResult(intent, 7);
                break;
            //设置所在hangye
            case R.id.danWeiSet_layout:
                intent = new Intent(EditMessageActivity.this, SetHangYeActivity.class);
                startActivityForResult(intent, 8);
                break;
            //修改密码
            case R.id.pWChange_layout:
                intent = new Intent(EditMessageActivity.this, ChangePWActivity.class);
                startActivity(intent);
                break;
            case R.id.culture_layout://文化程度
                ultureDegree();
                break;
            case R.id.dissmess_chengdu://文化程度取消框
                dialog.dismiss();
                break;
            case R.id.work_layout://工作单位
                intent = new Intent(EditMessageActivity.this, WorkActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.one_inch_layout://一寸照片
                intent = new Intent(EditMessageActivity.this, CertificateOneCunActivity.class);
                startActivityForResult(intent, 9);
                break;
            case R.id.professional_layout://职业名称
                intent = new Intent(EditMessageActivity.this,ProfessionalActivity.class);
                startActivityForResult(intent, 11);
                break;
            case R.id.type_layout://工种名称
                intent = new Intent(EditMessageActivity.this,TypeActivity.class);
                startActivityForResult(intent, 12);
                break;
            case R.id.skills_layout:
                intent = new Intent(EditMessageActivity.this,SkillsActivity.class);
                startActivityForResult(intent, 13);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void initHeadPhoto() {
        if (wechatLogin == true) {
            Toast.makeText(EditMessageActivity.this, "微信头像不能在此修改", Toast.LENGTH_SHORT).show();
        } else {

            //加载popupwindow的布局文件
            View view1 = View.inflate(EditMessageActivity.this, R.layout.pop_userphoto_layout, null);
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
                            uri = FileProvider.getUriForFile(EditMessageActivity.this, EditMessageActivity.this.getApplicationContext().getPackageName() + ".fileprovider", new File(path));
                        } else {
                            ContentValues contentValues = new ContentValues(1);
                            contentValues.put(MediaStore.Images.Media.DATA, path);
                            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        }
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 100);
                        mPopupWindow.dismiss();
                    } else if (checkPublishPermission() == false) {
                        Toast.makeText(EditMessageActivity.this, "请先打开允许相机拍照权限", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //调用相册
            selector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 101);
                    mPopupWindow.dismiss();
                }
            });
        }
    }

    //文化程度
    private void ultureDegree() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.culture_pop_layout, null);
        //初始化控件
        rg_culture = (RadioGroup) inflate.findViewById(R.id.rg_chengdu);
        dismiss_culture = (TextView) inflate.findViewById(R.id.dissmess_chengdu);//取消
        dismiss_culture.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
        // p.height= (int) (d.getHeight()*0.9);
        lp.width = p.width;
        //  lp.y = 20;//设置Dialog距离底部的距离
        //    将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
        //性别选择的radioGroup的点击事件
        rg_culture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.middle_school:
                        educationalLevel = 1;
                        break;
                    case R.id.high_school:
                        educationalLevel = 2;
                        break;
                    case R.id.college:
                        educationalLevel = 3;
                        break;
                    case R.id.course:
                        educationalLevel = 4;
                        break;
                    case R.id.degree:
                        educationalLevel = 5;
                        break;
                    case R.id.dr:
                        educationalLevel = 6;
                        break;

                }
                comimitDegree();
                if (educationalLevel == 1) {
                    culture = "初中";
                } else if (educationalLevel == 2) {
                    culture = "中专或高中";
                } else if (educationalLevel == 3) {
                    culture = "大专";
                } else if (educationalLevel == 4) {
                    culture = "本科";
                } else if (educationalLevel == 5) {
                    culture = "硕士";
                } else if (educationalLevel == 6) {
                    culture = "博士";
                }
            }

        });
    }

    private void comimitDegree() {
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("educationalLevel", educationalLevel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String cultureJson = jsonObject.toString();
        Map<String, Object> editMessage = new HashMap<>();
        editMessage.put("token", token);
        editMessage.put("updateInfo", cultureJson);
        editMessage.put("updateType", 9);
        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
            @Override
            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                UserInfoResetBean body = response.body();
                if (response != null & body != null) {
                    Object errMsg = body.getErrMsg();
                    if (errMsg == null) {
                        boolean data = body.isData();
                        if (data == true) {
                            Toast.makeText(EditMessageActivity.this, "文化程度修改成功", Toast.LENGTH_LONG).show();
                            tv_culture.setText(culture);
                        }
                    } else {
                        Toast.makeText(EditMessageActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
//                Toast.makeText(EditMessageActivity.this, "网络问题", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initSex() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.sex_pop_layout, null);
        //初始化控件
        sexChoice_rg = (RadioGroup) inflate.findViewById(R.id.sexChoice_rg);
        dismiss_sex = (TextView) inflate.findViewById(R.id.dissmess_sex);

        dismiss_sex.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
        // p.height= (int) (d.getHeight()*0.9);
        lp.width = p.width;
        //  lp.y = 20;//设置Dialog距离底部的距离
        //    将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
        //性别选择的radioGroup的点击事件
        sexChoice_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.women_sex:
                        gender = 2;
                        break;
                    case R.id.man_sex:
                        gender = 1;
                        break;

                }
                comimitSex();
                if (gender == 1) {
                    sexChoice = "男";

                } else if (gender == 2) {
                    sexChoice = "女";
                }

            }

        });
    }

    private void comimitSex() {
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gender", gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String genderJson = jsonObject.toString();
        Map<String, Object> editMessage = new HashMap<>();
        editMessage.put("token", token);
        editMessage.put("updateInfo", genderJson);
        editMessage.put("updateType", 2);
        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
            @Override
            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                UserInfoResetBean body = response.body();
                if (response != null & body != null) {
                    Object errMsg = body.getErrMsg();
                    if (errMsg == null) {
                        boolean data = body.isData();
                        if (data == true) {
                            Toast.makeText(EditMessageActivity.this, "性别修改成功", Toast.LENGTH_LONG).show();
                            userSex_tv.setText(sexChoice);
                        }
                    } else {
                        Toast.makeText(EditMessageActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
//                Toast.makeText(EditMessageActivity.this, "网络问题", Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照获取
                case 100:
                    bmp1 = bmp(path);
                    byte[] bitmapByte = getBitmapByte(bmp1);
                    //上传拍照照片
                    downLoadPhotoUrl(bitmapByte);
                    break;
                //从相册选择
                case 101:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    final String picturePath = cursor.getString(columnIndex);
                    Log.e("用户选择相册上传", "url: " + picturePath);
                    cursor.close();
                    bmp = bmp(picturePath);
                    this.bitmapByte = getBitmapByte(bmp);
                    //上传从相册取出来的图片
                    downLoadPhotoUrl(this.bitmapByte);
                    break;
                //设置昵称
                case 2:
                    if (null != data) {
                        String nickNameGet = data.getStringExtra("nickName");
                        nickName_tv.setText(nickNameGet);
                    }
                    break;
                //设置姓名
                case 3:
                    if (data != null) {
                        String userName = data.getStringExtra("userName");
                        userName_tv.setText(userName);
                    }
                    break;
                //设置用户省份证号
                case 5:
                    if (null != data) {
                        String personId = data.getStringExtra("personId");
                        Log.e("personIdaaaaaa", personId);
                        personNum_tv.setText(personId);
                    }
                    break;
                //修改手机号
                case 6:
                    String phoneNum = data.getStringExtra("newPhone");
                    personNum_tv.setText(phoneNum);
                    break;

                //设置岗位
                case 7:
                    String gangWei = data.getStringExtra("gangWei");
                    gangWei_tv.setText(gangWei);
                    break;
                //设置单位
                case 8:
                    String danWei = data.getStringExtra("danWei");
                    danWei_tv.setText(danWei);
                    break;
                case 10://工作单位
                        String work_danwei = data.getStringExtra("unit");
                        tv_work.setText(work_danwei);
                    break;
                case 11://职业名称
                    String occupaName=data.getStringExtra("occupaName");
                    tv_professional.setText(occupaName);
                    break;
                case 12://工种名称
                    String workTypeName=data.getStringExtra("workTypeName");
                    tv_type.setText(workTypeName);
                    break;
                case 13://职业技能等级
                    String occupaSkillLevel=data.getStringExtra("occupaSkillLevel");
                    tv_skills.setText(occupaSkillLevel);
                    break;
                default:
                    break;
            }
        }

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

    private void downLoadPhotoUrl(byte[] url) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), url);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", "photo.jpg", requestBody);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        aClass.getUserPhotoData(body, token).enqueue(new Callback<PUserHeadPhotoBean>() {
            @Override
            public void onResponse(Call<PUserHeadPhotoBean> call, Response<PUserHeadPhotoBean> response) {
                PUserHeadPhotoBean body = response.body();
                if (body != null && body.getErrMsg() == null) {
                    String photoUrl = body.getData().getPhotoUrl();
                    Log.e("photoUrlphotoUrl", photoUrl);
                    if (!TextUtils.isEmpty(photoUrl)) {
                        EventBus.getDefault().post(photoUrl);
                        Picasso.with(EditMessageActivity.this).load(photoUrl).into(headPhoto_iv);
                    }
                }
            }

            @Override
            public void onFailure(Call<PUserHeadPhotoBean> call, Throwable t) {
                Log.e("photoFailure", t.getMessage());
            }
        });
    }


    public Bitmap bmp(String path) {
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
        options.inSampleSize = 8;
        //设置图片的每个颜色基数在内存所占的字节数（ARGB_8888：32）
        //ARGB_4444:16位
        //RGB_565:16位，推荐
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //第二次,得到缩小之后的图片进行加载
        options.inJustDecodeBounds = false;
        //第二次解码加载整个缩小图片的内容到内存
        return BitmapFactory.decodeFile(path, options);
    }

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

    /**
     * 图片转成string
     *
     * @param bitmap 　@return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                finish();
                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * @author tu-mengting
     */
    @Override
    protected void onResume() {
        super.onResume();
        //加载人脸识别图片
        loadFaceIdentifyPic();
        initUserInfo();
    }

    /**
     * @author tu-mengting
     * 加载人脸对比照片
     */
    private void loadFaceIdentifyPic() {
        try {
            SharedPreferences faceUrl = getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
            String faceUrlStr = faceUrl.getString(Constant.PORTRAIT_URL_KEY, "");
            if (!"".equals(faceUrlStr)) {
                faceIdentifyIV.setVisibility(View.VISIBLE);
                Picasso.with(EditMessageActivity.this).load(faceUrlStr).into(faceIdentifyIV);
            } else {
                faceIdentifyIV.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Log.e("loadFaceIdentifyPic", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * @author tu-mengting
     * 判断人像识别对比照是否已存在
     */
    private boolean isPortraitExist() {
        boolean isExist;

        try {
            SharedPreferences faceUrl = getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
            String faceUrlStr = faceUrl.getString(Constant.PORTRAIT_URL_KEY, "");
            if (!"".equals(faceUrlStr)) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            isExist = false;
            Log.e("isGoToFaceCollect", StringUtil.getExceptionMessage(e));
        }

        return isExist;
    }
}
