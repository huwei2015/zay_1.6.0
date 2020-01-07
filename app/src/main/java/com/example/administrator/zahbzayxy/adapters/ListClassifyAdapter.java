package com.example.administrator.zahbzayxy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.beans.PMyLessonBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.myviews.CustomLayout;
import com.example.administrator.zahbzayxy.myviews.ImageRadiusView;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.DateUtil;
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
public class ListClassifyAdapter extends BaseAdapter {
    private Context context;
    int userCourse_Id;
    int coruse_Id;
    private List<PMyLessonBean.DataBean.CourseListBean> list;
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


    public ListClassifyAdapter(Context context) {
        this.context = context;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ListClassifyAdapter(List<PMyLessonBean.DataBean.CourseListBean> list, Context context, String token) {
        this.list = list;
        this.context = context;
        this.token = token;
        inflater = LayoutInflater.from(context);
    }

    public ListClassifyAdapter(List<PMyLessonBean.DataBean.CourseListBean> list, Context context, String token, Handler handler) {
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
            convertView = inflater.inflate(R.layout.item_classify_layout, parent, false);
            myViewHold.txtRL = convertView.findViewById(R.id.txtRL);
            myViewHold.mainClassify = convertView.findViewById(R.id.mainClassify);
            convertView.setTag(myViewHold);
        } else {
            myViewHold = (ListClassifyAdapter.myViewHold) convertView.getTag();
        }

        PMyLessonBean.DataBean.CourseListBean courseListBean = list.get(position);
        userCourse_Id = courseListBean.getUserCourseId();
        myViewHold.mainClassify.setText("全部");
        for(int i=0;i<10;i++){
            TextView tv=new TextView(context);
            tv.setText("分类"+i);
            tv.setTextSize(12);
            tv.setHint("");
            tv.setBackgroundResource(R.drawable.corner_text_view_normal);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v=(TextView)v;
                    v.setBackgroundResource(R.drawable.corner_text_view);
                }
            });
            myViewHold.txtRL.addView(tv);
        }
//        myViewHold.rec_courseName1.setText(courseListBean.getCourseName());
//        myViewHold.rec_courseName2.setText(courseListBean.getCourseName());
//        myViewHold.rec_price1.setText("￥" + String.valueOf(courseListBean.getTotalHours()));
//        myViewHold.rec_price2.setText("￥" + String.valueOf(courseListBean.getTotalHours()));
//        String logo = courseListBean.getLogo();
//        if (!TextUtils.isEmpty(logo)) {
//            Picasso.with(context).load(logo).placeholder(R.mipmap.loading_png).into(myViewHold.recPic1);
//            Picasso.with(context).load(logo).placeholder(R.mipmap.loading_png).into(myViewHold.recPic2);
//        }
//        //用户课程id传到下个界面
//        final int userCourseId = courseListBean.getUserCourseId();
//        final int coruseId = courseListBean.getCoruseId();
//        String endDate = courseListBean.getEndDate();
//        Log.e("endDate", endDate + "");
//        String currentTime = DateUtil.getCurrentTimeAll();
//        int i = endDate.compareTo(currentTime);
        return convertView;
    }

    static class myViewHold {
        CustomLayout txtRL;
        TextView mainClassify;
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
