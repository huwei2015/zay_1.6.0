package com.example.administrator.zahbzayxy.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.AppVersionBean;
import com.example.administrator.zahbzayxy.beans.LoginBean;
import com.example.administrator.zahbzayxy.beans.UserInfoBean;
import com.example.administrator.zahbzayxy.fragments.UserFragment;
import com.example.administrator.zahbzayxy.interfacecommit.LoginService;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.myinterface.MyLessonInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.BinaryCastUtils;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.example.administrator.zahbzayxy.utils.UUID;
import com.example.administrator.zahbzayxy.widget.CommonRadioDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements CommonRadioDialog.OnItemClickListener {
    @BindView(R.id.name_rg)
    EditText mName_et;
    @BindView(R.id.pw_rg)
    EditText mPw_et;
    @BindView(R.id.login_bt)
    Button login_bt;
    String mPassWord;
    private byte[] mDigest;
    private Unbinder mUnbinder;
    private String mPhone;
    private String downloadAdd;
    private String versionName;
    private int isForce;
    private PopupWindow popupWindow;
    private String uuid;
    private int type;
    private AlertDialog.Builder dialog;
    private int platformId = 0;
    private String platformName;
    List<LoginBean.DataList> list;

    //    private MaterialDialog materialDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        initUUID();
        initAppVersion();
    }

    @OnClick({R.id.free_register_bt, R.id.login_bt, R.id.back_login, R.id.forgetPw_login, R.id.youke_tv, R.id.tv_yinsi, R.id.tv_xieyi})
    public void onClick(View view) {
//        KeyboardUtil.hideKeyBoardForAct(LoginActivity.this);
        switch (view.getId()) {
            // 免费注册按钮
            case R.id.free_register_bt:
                RegisterActivity.startRegisActivity(this, RegisterActivity.REGISTER_FRAGMENT);
                break;
            // 登陆按钮
            case R.id.login_bt:
                if (checkInfo()) {
                    requestLogin();
                }
                break;
            // 返回按钮
            case R.id.back_login:
                finish();
                break;
            // 忘记密码
            case R.id.forgetPw_login:
                RegisterActivity.startRegisActivity(this, RegisterActivity.FORGET_PW_FRAGMENT);
                break;
            case R.id.youke_tv:
                //no登陆把isMechanism wei 0
                SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("isMechanism", 0);
                edit.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("isLoginActivity", "true");
                startActivity(intent);
                finish();
                break;
            case R.id.tv_xieyi:
                startActivity(new Intent(LoginActivity.this, ServiceActivity.class));
                break;
            case R.id.tv_yinsi:
                startActivity(new Intent(LoginActivity.this, PrivacyActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 检查信息
     *
     * @return 检查信息是否正确，正确返回true
     */
    private boolean checkInfo() {
        mPhone = mName_et.getText().toString();
        mPassWord = mPw_et.getText().toString();
        Log.e("LoginmPassWord ", mPassWord);
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(LoginActivity.this, "账户名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mPassWord)) {
            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    private void requestLogin() {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            mDigest = messageDigest.digest(mPassWord.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sPW = BinaryCastUtils.parseByte2HexStr(mDigest);
        Log.e("LoginMd5Password", sPW);

        final LoginService loginService = RetrofitUtils.getInstance().createClass(LoginService.class);
        Call<LoginBean> call = loginService.login(uuid, sPW, mPhone, platformId);
        call.enqueue(new Callback<LoginBean>() {
            private String token;

            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean body1 = response.body();
                int code1 = response.code();
                Log.e("responseCode", String.valueOf(code1));
                if (response != null && body1 != null) {
                    LoginBean body = response.body();
                    String s = new Gson().toJson(body);
                    Log.e("loginbody", s);
                    String code = body.getCode();
                    if (code.equals("00005")) {
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("00009")) {
                        Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("00003")) {
                        Object errMsg = response.body().getErrMsg();
                        ToastUtils.showLongInfo(String.valueOf(errMsg));
                    } else if (code.equals("99999")) {
                        Object errMsg = response.body().getErrMsg();
                        Toast.makeText(LoginActivity.this, String.valueOf(errMsg), Toast.LENGTH_SHORT).show();
                    } else if (code.equals("00000") && body != null) {
                        LoginBean loginBean = response.body();
                        if (loginBean.getData().getToken() == null) {
                            list = loginBean.getData().getPlatform();
                            for (int i = 0; i < list.size(); i++) {
                                platformId = list.get(i).getPlatformId();
                                platformName = list.get(i).getPlatformName();
                            }
                            ShowDialog();
                        }
                        // TODO 拿到token 判断登陆是否成功
                        token = loginBean.getData().getToken();
                        Object errMsg = loginBean.getErrMsg();
                        if (errMsg == null && !TextUtils.isEmpty(token)) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            //登陆成功之后把token存在本地
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("token", token);
                            edit.putString("passWord", mPassWord);
                            edit.putBoolean("isLogin", true);
                            edit.putString("phone", mPhone);
                            edit.putBoolean("wechatLogin", false);
                            edit.commit();
                            EventBus.getDefault().post("login");
                            String loginMethod = getIntent().getStringExtra("loginMethod");
                            EventBus.getDefault().post(UserFragment.FLUSH_USER_INFO_MINE_PAGE);
                            if (!TextUtils.isEmpty(loginMethod)) {
                                if (loginMethod.equals("home")) {
                                    initToHome(token);
                                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("isMechanism", type);
                                    intent1.putExtras(bundle);
                                    startActivity(intent1);
                                }
                            } else {
                                initToHome(token);
                                //把用户信息返回到用户中心显示到已登录界面
                                Intent intent = getIntent();
                                intent.putExtra("phoneNum", mPhone);
                                LoginActivity.this.setResult(RESULT_OK, intent);

                            }
                        }
                    }
                }
            }

            public void onFailure(Call<LoginBean> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToHome(final String token) {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserInfoBean> userInfoData = userInfoInterface.getUserInfoData(token);
        userInfoData.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                if (response != null) {
                    UserInfoBean body = response.body();
                    if (body != null) {
                        Object errMsg = body.getErrMsg();
                        if (errMsg == null) {
                            UserInfoBean.DataBean data = body.getData();
                            type = data.getType();
                            if (Integer.valueOf(type) != null) {
                                //登陆成功之后把token存在本地
                                SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putInt("isMechanism", type);
                                edit.commit();
                                Log.e("isMechanism", type + "");

                                EventBus.getDefault().post(66);
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {

            }
        });

    }

    private void initUUID() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        uuid = tokenDb.getString("uuid", "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.getUUID();
            Log.e("uid", uuid);
            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("uuid", uuid);
            edit.commit();
        } else {
            SharedPreferences tokenDb1 = getSharedPreferences("tokenDb", MODE_PRIVATE);
            uuid = tokenDb1.getString("uuid", "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.e("wechat", data + "resyult");
    }

    private void initAppVersion() {
        MyLessonInterface aClass = RetrofitUtils.getInstance().createClass(MyLessonInterface.class);
        aClass.getAppVersionData(1).enqueue(new Callback<AppVersionBean>() {
            @Override
            public void onResponse(Call<AppVersionBean> call, Response<AppVersionBean> response) {
                int code = response.code();
                if (code == 200) {
                    AppVersionBean body = response.body();
                    if (body != null) {
                        String code1 = body.getCode();
                        if (code1.equals("00000")) {
                            List<AppVersionBean.DataEntity> data = body.getData();
                            if (data != null) {
                                try {
                                    //此app版本
                                    versionName = getVersionName(LoginActivity.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                int size1 = data.size();
                                String verNum1 = data.get(size1 - 1).getVerNum();
                                String verNumString = verNum1.replace(".", "");
                                String versionNameString = versionName.replace(".", "");
                                Integer versionNameInteger = Integer.valueOf(versionNameString);
                                Integer verNumInteger = Integer.valueOf(verNumString);
                                downloadAdd = data.get(size1 - 1).getDownloadAdd();
                                if (versionNameInteger - verNumInteger < 0) {
                                    for (int i = 0; i < size1; i++) {
                                        isForce = data.get(i).getIsForce();
                                        if (isForce == 1) {
                                            String verNum = data.get(i).getVerNum();
                                            String verInfo = data.get(i).getVerInfo();
                                            String verNumString1 = verNum.replace(".", "");
                                            Integer verNumInteager1 = Integer.valueOf(verNumString1);
                                            if (versionNameInteger - verNumInteager1 < 0) {
                                                isForce = 1;
                                            } else {
                                                isForce = 0;
                                            }
                                        }
                                    }
                                    initAppVersionDialog1();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AppVersionBean> call, Throwable t) {

            }
        });

    }

    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    private void initAppVersionDialog1() {
        View popView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.app_update_pop, null, false);
        TextView cancel = popView.findViewById(R.id.myquestion_cancel);
        TextView downLoad_tv = popView.findViewById(R.id.downLoadNow_tv);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(popView, Gravity.CENTER, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        downLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去下载
                Uri uri = Uri.parse(downloadAdd);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // isForce=0;
                if (isForce != 1) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "升级之后才可以使用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    CommonRadioDialog commonRadioDialog;

    private void ShowDialog() {
        commonRadioDialog = new CommonRadioDialog(this, list);
        commonRadioDialog.setOnItemClickListener(this);
        commonRadioDialog.setCancelable(true);
        commonRadioDialog.setCanceledOnTouchOutside(false);
        commonRadioDialog.show();
        commonRadioDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                platformId = 0;
            }
        });

    }
    //点击事件
    @Override
    public void onClick(int postion) {
        platformId = postion;
        requestLogin();
    }
    //对话框关闭事件
    @Override
    public void onClose(View view) {
        commonRadioDialog.dismiss();
        platformId = 0;
    }

}
