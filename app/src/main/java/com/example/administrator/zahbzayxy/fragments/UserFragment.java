package com.example.administrator.zahbzayxy.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.Runnables.UserInfoRunnable;
import com.example.administrator.zahbzayxy.activities.AuthorizationActivity;
import com.example.administrator.zahbzayxy.activities.BuyCarActivity;
import com.example.administrator.zahbzayxy.activities.ContacActivity;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.activities.H5PageActivity;
import com.example.administrator.zahbzayxy.activities.LoginActivity;
import com.example.administrator.zahbzayxy.activities.MsgListActivity;
import com.example.administrator.zahbzayxy.activities.MyAccountActivity;
import com.example.administrator.zahbzayxy.activities.MyCuoTiActivity;
import com.example.administrator.zahbzayxy.activities.MyExamActivity;
import com.example.administrator.zahbzayxy.activities.MyFileActivitiy;
import com.example.administrator.zahbzayxy.activities.MyLessonActivity;
import com.example.administrator.zahbzayxy.activities.MyRenZhengActivity;
import com.example.administrator.zahbzayxy.activities.MySignUpActivity;
import com.example.administrator.zahbzayxy.activities.MyYouHuiJuanActivity;
import com.example.administrator.zahbzayxy.activities.NewMyChengJiActivity;
import com.example.administrator.zahbzayxy.activities.NewMyOrderActivity;
import com.example.administrator.zahbzayxy.activities.NewMyTikuActivity;
import com.example.administrator.zahbzayxy.activities.ResetActivity;
import com.example.administrator.zahbzayxy.beans.UserCenter;
import com.example.administrator.zahbzayxy.beans.UserInfoBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadListActivity;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.myviews.CircleImageView;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    Context context;
    View view;
    //用户头像
    CircleImageView userHead_iv;
    LinearLayout userCenter_layout;
    //未登录时的界面
    @BindView(R.id.noLogin_layout)
    RelativeLayout noLoginLayout;
    //已登录时的界面
    @BindView(R.id.haveLogin_layout)
    RelativeLayout haveLoginLayout;
    @BindView(R.id.name_tv)
    TextView nickName_tv;
    private LinearLayout myMonney_ll;
    private LinearLayout myLesson_ll;
    private LinearLayout ll_order;
    private LinearLayout myTiKu_ll;
    private LinearLayout myRenZheng_ll;
    private LinearLayout myGrad_ll;
    private LinearLayout myError_ll;
    private LinearLayout myShopping;
    private LinearLayout myYouhuiJuan_ll;
    private LinearLayout tv_offline;
    private LinearLayout myExam_ll;
    private LinearLayout ll_authorization;
    private RelativeLayout rl_contac;
    private ImageView getResetMessage_ic;
    private String weChatHeadImg;
    private String weChatName;
    private String token;
    private ImageView img_msg;
    private SharedPreferences sharedPreferences;
    private TextView tv_username,tv_account,tv_couponNum,tv_role,tv_order,tv_shop_num,tab_unread_message;
    private Button editMessage_logined;
    private Button no_editMessage_logined;//没有登录
    private LinearLayout ll_no_role;//未登陆
    private LinearLayout ll_role;//登录
    private LinearLayout ll_sign;
    private LinearLayout ll_file;
    /**********FHS Start**********/
    private AlertDialog dialog;
    boolean isLogin;
    int messageNum;//消息数量
    /**********FHS Start**********/

    //HYY添加
    private RelativeLayout common_problemRL;
    private RelativeLayout user_manualRL;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        EventBus.getDefault().register(this);
        sharedPreferences = context.getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        initView();
        initIsLogin();
        initUserInfo();
        initUserCenter();
        return view;
    }

    private void initUserCenter() {
        token = sharedPreferences.getString("token", "");
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserCenter> userCenter = userInfoInterface.getUserCenter(token);
        userCenter.enqueue(new Callback<UserCenter>() {
            @Override
            public void onResponse(Call<UserCenter> call, Response<UserCenter> response) {
                if(response !=null && response.body() != null){
                    String code=response.body().getCode();
                    if(code.equals("00000")){
                        UserCenter.userCenterData data = response.body().getData();
                         Double amount=data.getAmount();//金额
                        tv_account.setText(String.valueOf(amount));
                        String userLevelStr = data.getUserLevelStr();//角色
                        tv_role.setText(userLevelStr);
                        boolean agreementAdmin = data.isAgreementAdmin();
                        if(agreementAdmin){
                            ll_authorization.setVisibility(View.VISIBLE);
                        }else{
                            ll_authorization.setVisibility(View.INVISIBLE);

                        }

                        messageNum = data.getMessageNum();//消息
                        if(messageNum > 0){
                            tab_unread_message.setVisibility(View.VISIBLE);
                            tab_unread_message.setText(String.valueOf(messageNum));
                        }else{
                            tab_unread_message.setVisibility(View.INVISIBLE);
                        }
                        int shopCarNum = data.getShopCarNum();//购物车数量
                        tv_shop_num.setText(String.valueOf(shopCarNum));
                        int orderNum = data.getOrderNum();//订单
                        tv_order.setText(String.valueOf(orderNum));
                        int couponNum = data.getCouponNum();//优惠券
                        tv_couponNum.setText(String.valueOf(couponNum));
                    }
                }
            }

            @Override
            public void onFailure(Call<UserCenter> call, Throwable t) {

            }
        });
    }
    //初始化view
    private void initView() {
        userHead_iv = view.findViewById(R.id.userHead_img);//用户图像
        ll_no_role = view.findViewById(R.id.ll_no_role);
        ll_role = view.findViewById(R.id.ll_role);
        editMessage_logined = view.findViewById(R.id.editMessage_logined); //登录时点击头像右侧时的箭头
        editMessage_logined.setOnClickListener(this);
        no_editMessage_logined = view.findViewById(R.id.no_editMessage_logined);//没有登录
        no_editMessage_logined.setOnClickListener(this);
        userCenter_layout = view.findViewById(R.id.userCenter_layout);
        ll_authorization = view.findViewById(R.id.ll_authorization);
        ll_authorization.setOnClickListener(this);
        myYouhuiJuan_ll = view.findViewById(R.id.ll_yhj);
        myYouhuiJuan_ll.setOnClickListener(this);
        tv_offline = view.findViewById(R.id.tv_offline);
        tv_offline.setOnClickListener(this);
        myExam_ll = view.findViewById(R.id.myExam_ll);
        myExam_ll.setOnClickListener(this);
        myMonney_ll = view.findViewById(R.id.ll_account);
        myMonney_ll.setOnClickListener(this);
        myError_ll = view.findViewById(R.id.myError_ll);
        myError_ll.setOnClickListener(this);
        myGrad_ll = view.findViewById(R.id.myGrad_ll);
        myGrad_ll.setOnClickListener(this);
        myRenZheng_ll = view.findViewById(R.id.myRenZheng_ll);
        myRenZheng_ll.setOnClickListener(this);
        myTiKu_ll = view.findViewById(R.id.myTiKu_ll);
        myTiKu_ll.setOnClickListener(this);
        ll_order = view.findViewById(R.id.ll_order);
        ll_order.setOnClickListener(this);
        myLesson_ll = view.findViewById(R.id.myLesson_ll);
        myLesson_ll.setOnClickListener(this);
        rl_contac = view.findViewById(R.id.rl_contac);
        rl_contac.setOnClickListener(this);
        getResetMessage_ic = view.findViewById(R.id.resetMessage_ic);
        getResetMessage_ic.setOnClickListener(this);
        img_msg = view.findViewById(R.id.img_msg);
        img_msg.setOnClickListener(this);
        myShopping = view.findViewById(R.id.ll_shopping);
        myShopping.setOnClickListener(this);
        tv_username = view.findViewById(R.id.tv_username);//登录成功显示项目
        tv_account=view.findViewById(R.id.tv_account);//显示金额
        tv_couponNum=view.findViewById(R.id.tv_couponNum);//优惠券
        tv_role=view.findViewById(R.id.tv_role);//角色
        tv_order=view.findViewById(R.id.tv_order);//订单
        tab_unread_message=view.findViewById(R.id.tab_unread_message);//消息数
        tv_shop_num=view.findViewById(R.id.tv_shop_num);//购物车数量
        noLoginLayout = view.findViewById(R.id.noLogin_layout);//没有登录
        haveLoginLayout = view.findViewById(R.id.haveLogin_layout);//已经登录
        ll_sign=view.findViewById(R.id.ll_sign);//我的报名
        ll_sign.setOnClickListener(this);
        ll_file=view.findViewById(R.id.ll_file);
        ll_file.setOnClickListener(this);

        //HYY添加 常见问题  使用手册
        common_problemRL=view.findViewById(R.id.common_problemRL);
        common_problemRL.setOnClickListener(this);
        user_manualRL=view.findViewById(R.id.user_manualRL);
        user_manualRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        switch (v.getId()) {
            case R.id.ll_authorization://授权管理
                if (isLogin) {
                    startActivity(new Intent(context, AuthorizationActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.ll_yhj://优惠券
                if (isLogin) {
                    startActivity(new Intent(context, MyYouHuiJuanActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.tv_offline://离线视频
                if (isLogin) {
                    startActivity(new Intent(context, DownloadListActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.myExam_ll://我的考试
                if (isLogin) {
                    startActivity(new Intent(context, MyExamActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.ll_account://我的余额
                if (isLogin) {
                    startActivity(new Intent(context, MyAccountActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.myError_ll://我的错题
                if (isLogin) {
                    startActivity(new Intent(context, MyCuoTiActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.myGrad_ll://模考记录
                if (isLogin) {
                    startActivity(new Intent(context, NewMyChengJiActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.myRenZheng_ll://我的认证
                if (isLogin) {
                    startActivity(new Intent(context, MyRenZhengActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.myTiKu_ll://我的题库
                if (isLogin) {
                    startActivity(new Intent(context, NewMyTikuActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.ll_order://我的订单
                if (isLogin) {
                    startActivity(new Intent(context, NewMyOrderActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.myLesson_ll://我的课程
                if (isLogin) {
                    startActivity(new Intent(context, MyLessonActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.rl_contac://联系我们
                if (isLogin) {
                    startActivity(new Intent(context, ContacActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.resetMessage_ic://设置
                if (isLogin) {
                    startActivity(new Intent(context, ResetActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.editMessage_logined://信息编辑
                if (isLogin) {
                    startActivity(new Intent(context, EditMessageActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.img_msg://消息
                if (isLogin) {
                    Intent intent = new Intent(context,MsgListActivity.class);
                    intent.putExtra("messageNum",String.valueOf(messageNum));
                    startActivity(intent);
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.ll_shopping://购物车
                if (isLogin) {
                    startActivity(new Intent(context, BuyCarActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.no_editMessage_logined://没有登录
                startActivity(new Intent(context, LoginActivity.class));
                break;
            case R.id.ll_sign://我的报名
                if(isLogin){
                    startActivity(new Intent(context, MySignUpActivity.class));
                }else{
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.ll_file://我的附件
                if(isLogin){
                    startActivity(new Intent(context, MyFileActivitiy.class));
                }else{
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.common_problemRL://常见问题
                if(isLogin){
                    Intent intent=new Intent(context, H5PageActivity.class);
                    intent.putExtra("h5Type","common_problem");
                    startActivity(intent);
                }else{
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.user_manualRL://使用手册
                if(isLogin){
                    Intent intent=new Intent(context, H5PageActivity.class);
                    intent.putExtra("h5Type","user_manual");
                    startActivity(intent);
                }else{
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initIsLogin();
        /*****************FHS Start****************/
        if (isHidden()) {
            //当前fragment对用户不可见
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            //当前fragment对用户可见,在新线程中访问获取用户信息的接口
//            UserInfoRunnable.startUsrInfoRunnable(this, context, dialog);
            UserInfoRunnable.startUsrInfoRunnable(context, handler);
        }
        Log.d("UserFragment", "onResume");
        /*****************FHS End****************/
    }

    private void initIsLogin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin == true) {
            noLoginLayout.setVisibility(View.GONE);
            haveLoginLayout.setVisibility(View.VISIBLE);
        } else if (isLogin == false) {
            noLoginLayout.setVisibility(View.VISIBLE);
            haveLoginLayout.setVisibility(View.GONE);
            ll_role.setVisibility(View.VISIBLE);
            ll_no_role.setVisibility(View.INVISIBLE);
            ll_authorization.setVisibility(View.INVISIBLE);
            tab_unread_message.setVisibility(View.INVISIBLE);
            tv_account.setText("0.0");
            tv_couponNum.setText("0");
            tv_order.setText("0");
            tv_shop_num.setText("0");
        }
    }

    private void initUserInfo() {
        boolean wechatLogin = sharedPreferences.getBoolean("wechatLogin", false);
        if (wechatLogin == true) {
            weChatHeadImg = sharedPreferences.getString("weChatHeadImg", "");
            weChatName = sharedPreferences.getString("weChatName", "");
            Log.e("wechatBack", "44444" + weChatHeadImg + ",,," + weChatName);
            if (!TextUtils.isEmpty(weChatHeadImg)) {
                Picasso.with(context).load(weChatHeadImg).placeholder(R.mipmap.icon_touxiang).into(userHead_iv);
                nickName_tv.setText(weChatName);
                Log.e("wechatBack", "22222111");

            }
        } else if (wechatLogin == false) {
            Log.e("wechatBack", "222223333");
            token = sharedPreferences.getString("token", "");
            UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
            Call<UserInfoBean> userInfoData = userInfoInterface.getUserInfoData(token);
            userInfoData.enqueue(new Callback<UserInfoBean>() {
                @Override
                public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                    int code = response.code();
                    Log.e("wechatBack", "6666666");
                    if (code == 200) {
                        UserInfoBean body = response.body();
                        if (body != null) {
                            String code1 = body.getCode();
                            if (code1.equals("00000")) {
                                Log.e("wechatBack", "777777");
                                UserInfoBean.DataBean data = body.getData();
                                Object userName = data.getUserName();//姓名
                                Object phone = data.getPhone();//手机号
                                String image = (String) data.getPhotoUrl();
                                if (!TextUtils.isEmpty(String.valueOf(phone))) {
                                    tv_username.setText(phone+ "");
                                }
                                if (!TextUtils.isEmpty(image)) {
                                    Picasso.with(context).load(image).placeholder(R.mipmap.icon_touxiang).into(userHead_iv);
                                }

                            } else if (code1.equals("99999")) {
                                Toast.makeText(context, "系统异常", Toast.LENGTH_SHORT).show();
                            } else if (code1.equals("00003")) {
                                // Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                                SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", MODE_PRIVATE);
                                SharedPreferences.Editor edit = tokenDb.edit();
                                edit.putBoolean("isLogin", false);
                                edit.putBoolean("wechatLogin", false);
                                edit.commit();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserInfoBean> call, Throwable t) {

                }
            });
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (data != null) {
                //把已登录界面显示出来,未登录界面隐藏掉;d
                noLoginLayout.setVisibility(View.GONE);
                haveLoginLayout.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences = context.getSharedPreferences("tokenDb", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                weChatHeadImg = sharedPreferences.getString("weChatHeadImg", "");
                weChatName = sharedPreferences.getString("weChatName", "");
                boolean wechatLogin = sharedPreferences.getBoolean("wechatLogin", false);
                Log.e("wechatBack", "44444" + weChatHeadImg + ",,," + weChatName);
                if (!TextUtils.isEmpty(weChatHeadImg) && wechatLogin == true) {
                    Picasso.with(context).load(weChatHeadImg).placeholder(R.mipmap.icon_touxiang).into(userHead_iv);
                    nickName_tv.setText(weChatName);
                    Log.e("wechatBack", "22222");
                } else if (wechatLogin == false) {
                    initUserInfo();
                    Log.e("wechatBack", "33333" + token);
                }

            }
        }
        if (requestCode == 100) {
            if (data != null) {
                boolean isLogin1 = data.getBooleanExtra("isLogin", false);
                if (isLogin1 == false) {
                    noLoginLayout.setVisibility(View.VISIBLE);
                    haveLoginLayout.setVisibility(View.GONE);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventForImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            Log.e("eventbusWechatImg", image);
            //  Picasso.with(getContext()).load(image).placeholder(R.mipmap.icon_touxiang).into(userHead_iv);
            initUserInfo();
            initUserCenter();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * @param hidden
     * @author tu-mengting
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        try {
            if (hidden) {
                //当前fragment对用户不可见
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            } else {
                //当前fragment对用户可见,在新线程中访问获取用户信息的接口
//                UserInfoRunnable.startUsrInfoRunnable(this, context, dialog);
                UserInfoRunnable.startUsrInfoRunnable(context, handler);
            }
            Log.d("UserFragment", "onHiddenChanged");
        } catch (Exception e) {
            Log.e("onHiddenChanged", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * @author tu-mengting
     * 用于更新画面
     */
    @SuppressLint("HandlerLeak")
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case Constant.GET_USR_INFO_ERR:
                        showErrDialog(R.string.get_user_info_err);
                        break;
                    case Constant.NEED_COLLECT_PORTRAIT:
                        showPromptDialog(R.string.upload_portrait_prompt);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                Log.e("handleMessage", StringUtil.getExceptionMessage(e));
            }
        }
    };

    /**
     * @author tu-mengting
     * 显示要进行人脸识别的对话框
     */
    private void showPromptDialog(int contentRes) {
        try {
            closeAlertDialog();//清除之前的提示框
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            dialog = builder.setTitle("提示")
                    .setMessage(contentRes)
                    .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            context.startActivity(new Intent(context, EditMessageActivity.class));
                        }
                    }).create();
            dialog.setCanceledOnTouchOutside(false);
            if (this.isVisible()) {
                dialog.show();
            } else {
                dialog = null;
            }
        } catch (Exception e) {
            Log.e("showDialog", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * @author tu-mengting
     * 显示错误信息对话框
     */
    private void showErrDialog(int contentRes) {
        try {
            closeAlertDialog();//清除之前的提示框
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            dialog = builder.setTitle("提示")
                    .setMessage(contentRes)
                    .setNegativeButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            dialogInterface.dismiss();
                        }
                    }).create();

            dialog.setCanceledOnTouchOutside(false);
            if (this.isVisible()) {
                dialog.show();
                //设置对话框size
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
                lp.dimAmount = 0f;//设置背景不变暗
                dialogWindow.setAttributes(lp);
            } else {
                dialog = null;
            }
        } catch (Exception e) {
            Log.e("showDialog", StringUtil.getExceptionMessage(e));
        }
    }

    private void closeAlertDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
