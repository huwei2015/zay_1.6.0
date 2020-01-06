package com.example.administrator.zahbzayxy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.activities.LivePlayActivity;
import com.example.administrator.zahbzayxy.beans.LiveCourseBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.myviews.ImageRadiusView;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.example.administrator.zahbzayxy.vo.UserInfo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class LiveCourseAdapter extends BaseAdapter {
    private Context context;
    int userCourse_Id;
    int coruse_Id;
    private List<LiveCourseBean.DataBean> list;
    private LayoutInflater inflater;
    String price, token;
    Handler mHandler;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        //item点击事件
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public LiveCourseAdapter(Context context) {
        this.context = context;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public LiveCourseAdapter(List<LiveCourseBean.DataBean> list, Context context, String token) {
        this.list = list;
        this.context = context;
        this.token = token;
        inflater = LayoutInflater.from(context);
    }

    public LiveCourseAdapter(List<LiveCourseBean.DataBean> list, Context context, String token, Handler handler) {
        this.list = list;
        this.context = context;
        this.token = token;
        inflater = LayoutInflater.from(context);
        mHandler = handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        myViewHold myViewHold;
        if (convertView == null) {
            myViewHold = new myViewHold();
            convertView = inflater.inflate(R.layout.item_live_layout, parent, false);
            myViewHold.recPic1 = convertView.findViewById(R.id.recPic1);
            myViewHold.recPic2 = convertView.findViewById(R.id.recPic2);
            myViewHold.subject1 = convertView.findViewById(R.id.subject1);
            myViewHold.subject2 = convertView.findViewById(R.id.subject2);
            myViewHold.start_time1 = convertView.findViewById(R.id.start_time1);
            myViewHold.start_time2 = convertView.findViewById(R.id.start_time2);
            myViewHold.livesStatus1 = convertView.findViewById(R.id.livesStatus1);
            myViewHold.livesStatus2 = convertView.findViewById(R.id.livesStatus2);
            myViewHold.left_layout=convertView.findViewById(R.id.left_layout);
            myViewHold.right_layout=convertView.findViewById(R.id.right_layout);
            myViewHold.sign_zxIV1= convertView.findViewById(R.id.sign_zxIV1);
            myViewHold.sign_zxIV2=convertView.findViewById(R.id.sign_zxIV2);
            convertView.setTag(myViewHold);
        } else {
            myViewHold = (LiveCourseAdapter.myViewHold) convertView.getTag();
        }

        LiveCourseBean.DataBean courseListBean = list.get(position);
        myViewHold.subject1.setText(courseListBean.getSubject());

        myViewHold.start_time1.setText(String.valueOf(courseListBean.getStart_time_ymd()));
        if (!TextUtils.isEmpty(courseListBean.getThumb())) {
            Picasso.with(context).load(courseListBean.getThumb()).placeholder(R.mipmap.loading_png).into(myViewHold.recPic1);
        }else{
            Picasso.with(context).load(R.mipmap.loading_png).placeholder(R.mipmap.loading_png).into(myViewHold.recPic1);
        }
        if(courseListBean.getStatus()==1){//直播中
            Drawable drawableLeft = context.getResources().getDrawable(R.mipmap.live_sign_playing);
            myViewHold.livesStatus1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
            myViewHold.livesStatus1.setTextColor(context.getResources().getColor(R.color.live_playing));
            myViewHold.livesStatus1.setText("直播中 ");
        }else if(courseListBean.getStatus()==2){//预约中
            Drawable drawableLeft = context.getResources().getDrawable(R.mipmap.live_sign_yy);
            myViewHold.livesStatus1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
            myViewHold.livesStatus1.setTextColor(context.getResources().getColor(R.color.live_yy));
            myViewHold.livesStatus1.setText("预约 ");
        }else if(courseListBean.getStatus()==5){//回放
            Drawable drawableLeft = context.getResources().getDrawable(R.mipmap.back_paly);
            myViewHold.livesStatus1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
            myViewHold.livesStatus1.setTextColor(context.getResources().getColor(R.color.live_back_play));
            myViewHold.livesStatus1.setText("回放 ");
        }else{//结束
            myViewHold.livesStatus1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            myViewHold.livesStatus1.setTextColor(context.getResources().getColor(R.color.signin_tit_color));
            myViewHold.livesStatus1.setText("已结束");
        }

        if(courseListBean.getIsNew()==1){
            myViewHold.sign_zxIV1.setVisibility(View.VISIBLE);
        }else{
            myViewHold.sign_zxIV1.setVisibility(View.INVISIBLE);
        }
        final int webinar_id=courseListBean.getWebinar_id();
        myViewHold.left_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LivePlayActivity.class);
                intent.putExtra("webinar_id", webinar_id);
                context.startActivity(intent);
            }
        });

        //=============================================================================第二个值
        if (!TextUtils.isEmpty(courseListBean.getSubject1())) {
            myViewHold.subject2.setText(courseListBean.getSubject1());
            myViewHold.start_time2.setText(String.valueOf(courseListBean.getStart_time_ymd1()));
            if (!TextUtils.isEmpty(courseListBean.getThumb1())) {
                Picasso.with(context).load(courseListBean.getThumb1()).placeholder(R.mipmap.loading_png).into(myViewHold.recPic2);
            }else{
                Picasso.with(context).load(R.mipmap.loading_png).placeholder(R.mipmap.loading_png).into(myViewHold.recPic2);
            }
            if(courseListBean.getStatus1()==1){//直播中
                Drawable drawableLeft = context.getResources().getDrawable(R.mipmap.live_sign_playing);
                myViewHold.livesStatus2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                myViewHold.livesStatus2.setTextColor(context.getResources().getColor(R.color.live_playing));
                myViewHold.livesStatus2.setText("直播中 ");
            }else if(courseListBean.getStatus1()==2){//预约中
                Drawable drawableLeft = context.getResources().getDrawable(R.mipmap.live_sign_yy);
                myViewHold.livesStatus2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                myViewHold.livesStatus2.setTextColor(context.getResources().getColor(R.color.live_yy));
                myViewHold.livesStatus2.setText("预约 ");
            }else if(courseListBean.getStatus1()==5){//回放
                Drawable drawableLeft = context.getResources().getDrawable(R.mipmap.back_paly);
                myViewHold.livesStatus2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                myViewHold.livesStatus2.setTextColor(context.getResources().getColor(R.color.live_back_play));
                myViewHold.livesStatus2.setText("回放 ");
            }else{//结束
                myViewHold.livesStatus2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                myViewHold.livesStatus2.setTextColor(context.getResources().getColor(R.color.signin_tit_color));
                myViewHold.livesStatus2.setText("已结束");
            }

            if(courseListBean.getIsNew()==1){
                myViewHold.sign_zxIV2.setVisibility(View.VISIBLE);
            }else{
                myViewHold.sign_zxIV2.setVisibility(View.INVISIBLE);
            }
            final int webinar_id1=courseListBean.getWebinar_id1();
            myViewHold.right_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LivePlayActivity.class);
                    intent.putExtra("webinar_id", webinar_id1);
                    context.startActivity(intent);
                }
            });

            myViewHold.right_layout.setVisibility(View.VISIBLE);
        }else{
            myViewHold.right_layout.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public static int getYears(String startDay){
        boolean flag=false;

        return 0;
    }

    static class myViewHold {
        ImageRadiusView recPic1,recPic2;
        TextView subject1, subject2, start_time1, start_time2,livesStatus1,livesStatus2;
        ImageView sign_zxIV1,sign_zxIV2;
        LinearLayout left_layout,right_layout;
    }

    static class GetUserInfoRunnable implements Runnable {
        Context mContext;
        int needVerify;
        String faceUrl;
        int mUserCourseId;
        int mCourseId;
        String mToken;
        Handler runHandler;

        GetUserInfoRunnable(Context context, int userCourseId, int coruseId, String token, Handler handler) {
            mContext = context;
            mUserCourseId = userCourseId;
            mCourseId = coruseId;
            mToken = token;
            runHandler = handler;
        }

        @Override
        public void run() {
            Message message = runHandler.obtainMessage();
            try {
                mToken = mToken == null ? "" : mToken;
                //通过OKHttp访问后台接口
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add(Constant.TOKEN_PARAM, mToken)
                        .build();
                Request request = new Request.Builder()
                        .url(Constant.GET_USER_INFO_URL)
                        .post(requestBody)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                // 将json字符串转化成对应数据类
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(responseData, UserInfo.class);
                if (Constant.SUCCESS_CODE.equals(userInfo.getCode())) {
                    saveUsrInfo(userInfo);
                    needVerify = userInfo.getData().getNeedVerify();
                    faceUrl = userInfo.getData().getFacePath();
                    faceUrl = faceUrl == null ? "" : faceUrl;
                    checkResult(message, needVerify, faceUrl, mUserCourseId, mCourseId, mToken);
                } else {
                    message.what = 3;
                }
                message.sendToTarget();
            } catch (Exception e) {
                message.what = 4;
                message.sendToTarget();
                StringUtil.getExceptionMessage(e);
            }
        }

        private void checkResult(Message msg, int needVerify, String faceUrl, int userCourseId, int coruseId, String token) {

            if (needVerify == 0) {
                msg.what = 0;
            } else if (needVerify == 1 && "".equals(faceUrl)) {
                msg.what = 1;
            } else {
                msg.what = 2;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("userCourseId", userCourseId);
            bundle.putInt("coruseId", coruseId);
            bundle.putString("token", token);
//            bundle.putBoolean("isLocalPlay",false);
            msg.setData(bundle);

        }

        private void saveUsrInfo(UserInfo userInfo) {
            try {
                SharedPreferences sp = mContext.getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt(Constant.IS_NEED_VERIFY_KEY, userInfo.getData().getNeedVerify());
                edit.putString(Constant.INTERVAL_TIME_KEY, userInfo.getData().getIntervalTime());
                edit.putString(Constant.PORTRAIT_URL_KEY, userInfo.getData().getFacePath());
                edit.commit();
            } catch (Exception e) {
                Log.e("saveUsrInfo", StringUtil.getExceptionMessage(e));
            }
        }
    }

    private void isPerfectPersonInfo() {
        PersonGroupInterfac personGroupInterfac = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        personGroupInterfac.getPersonInfo(token, userCourse_Id).enqueue(new Callback<PersonInfo>() {
            @Override
            public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                       boolean data = (boolean) response.body().getData();
                        if (!data) {
                            //获取用户信息
                            ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
                            GetUserInfoRunnable task = new GetUserInfoRunnable(context, userCourse_Id, coruse_Id, token, mHandler);
                            threadPool.submit(task);
                        } else {
                            showUploadDialog();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<PersonInfo> call, Throwable t) {
                String msg= t.getMessage();
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private AlertDialog upLoadAlertDialog;

    private void showUploadDialog() {

        if (upLoadAlertDialog != null) {
            upLoadAlertDialog.dismiss();
        }
        upLoadAlertDialog = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("请先完善个人信息，再进行学习")
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, EditMessageActivity.class));
                        dialog.dismiss();
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        upLoadAlertDialog.show();
    }
}
