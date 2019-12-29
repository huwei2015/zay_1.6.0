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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
import com.example.administrator.zahbzayxy.beans.QueslibBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.myviews.ImageRadiusView;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.TextAndPictureUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.example.administrator.zahbzayxy.vo.UserInfo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.ta.utdid2.android.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class QueslibAdapter extends BaseAdapter {
    private Context context;
    int userCourse_Id;
    int coruse_Id;
    private List<QueslibBean.DataBean.QueslibListBean> list;
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


    public QueslibAdapter(Context context) {
        this.context = context;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public QueslibAdapter(List<QueslibBean.DataBean.QueslibListBean> list, Context context, String token) {
        this.list = list;
        this.context = context;
        this.token = token;
        inflater = LayoutInflater.from(context);
    }

    public QueslibAdapter(List<QueslibBean.DataBean.QueslibListBean> list, Context context, String token, Handler handler) {
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
            convertView = inflater.inflate(R.layout.item_queslib_layout, parent, false);
            myViewHold.recPic1 = convertView.findViewById(R.id.recPic1);
            myViewHold.recPic2 = convertView.findViewById(R.id.recPic2);
            myViewHold.rec_courseName1 = convertView.findViewById(R.id.rec_courseName1);
            myViewHold.rec_courseName2 = convertView.findViewById(R.id.rec_courseName2);
            myViewHold.rec_price1 = convertView.findViewById(R.id.rec_price1);
            myViewHold.rec_price2 = convertView.findViewById(R.id.rec_price2);
            myViewHold.rec_sign_shikan1 = convertView.findViewById(R.id.rec_sign_shikan1);
            myViewHold.rec_sign_shikan2 = convertView.findViewById(R.id.rec_sign_shikan2);
            myViewHold.left_layout=convertView.findViewById(R.id.left_layout);
            myViewHold.right_layout=convertView.findViewById(R.id.right_layout);
            myViewHold.sign_zxIV1= convertView.findViewById(R.id.sign_zxIV1);
            myViewHold.sign_zxIV2=convertView.findViewById(R.id.sign_zxIV2);
            convertView.setTag(myViewHold);
        } else {
            myViewHold = (QueslibAdapter.myViewHold) convertView.getTag();
        }

        QueslibBean.DataBean.QueslibListBean courseListBean = list.get(position);
        if(courseListBean.getIsRecommend()==1){
            myViewHold.rec_courseName1.setText(TextAndPictureUtil.getText(context,courseListBean.getQuesLibName(),R.mipmap.recommend_course));
        }else{
            myViewHold.rec_courseName1.setText(courseListBean.getQuesLibName());
        }

        myViewHold.rec_price1.setText("￥" + String.valueOf(courseListBean.getsPrice()));
        if (!TextUtils.isEmpty(courseListBean.getImagePath())) {
            Picasso.with(context).load(courseListBean.getImagePath()).placeholder(R.mipmap.loading_png).into(myViewHold.recPic1);
        }
//        if(courseListBean.getIsTrailers()==1){
//            myViewHold.rec_sign_shikan1.setVisibility(View.VISIBLE);
//        }else{
//            myViewHold.rec_sign_shikan1.setVisibility(View.INVISIBLE);
//        }

        if(!StringUtils.isEmpty(courseListBean.getUpdateTime())){
            String[] arrs=courseListBean.getUpdateTime().split("-");
            String newDate=(Integer.valueOf(arrs[0])+1)+arrs[1]+arrs[2];
            SimpleDateFormat srtFormat = new SimpleDateFormat("yyyyMMdd");

            try {
                Date date = srtFormat.parse(newDate);
                long d1=date.getTime();
                long d2=System.currentTimeMillis();
                if(d1>d2){
                    myViewHold.sign_zxIV1.setVisibility(View.VISIBLE);
                }else{
                    myViewHold.sign_zxIV1.setVisibility(View.INVISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            myViewHold.sign_zxIV1.setVisibility(View.INVISIBLE);
        }

        //=============================================================================第二个值
        if (!TextUtils.isEmpty(courseListBean.getQuesLibName1())) {
            if(courseListBean.getIsRecommend1()==1) {
                myViewHold.rec_courseName2.setText(TextAndPictureUtil.getText(context, courseListBean.getQuesLibName1(), R.mipmap.recommend_course));
            }else{
                myViewHold.rec_courseName2.setText(courseListBean.getQuesLibName1());
            }
            myViewHold.rec_price2.setText("￥" + String.valueOf(courseListBean.getsPrice1()));
            if (!TextUtils.isEmpty(courseListBean.getImagePath1())) {
                Picasso.with(context).load(courseListBean.getImagePath1()).placeholder(R.mipmap.loading_png).into(myViewHold.recPic2);
            }
//            if (courseListBean.getIsTrailers1() == 1) {
//                myViewHold.rec_sign_shikan2.setVisibility(View.VISIBLE);
//            } else {
//                myViewHold.rec_sign_shikan2.setVisibility(View.INVISIBLE);
//            }

            if(!StringUtils.isEmpty(courseListBean.getUpdateTime1())){
                String[] arrs=courseListBean.getUpdateTime1().split("-");
                String newDate=(Integer.valueOf(arrs[0])+1)+arrs[1]+arrs[2];
                SimpleDateFormat srtFormat = new SimpleDateFormat("yyyyMMdd");
                try {
                    Date date = srtFormat.parse(newDate);
                    long d1=date.getTime();
                    long d2=System.currentTimeMillis();
                    if(d1>d2){
                        myViewHold.sign_zxIV2.setVisibility(View.VISIBLE);
                    }else{
                        myViewHold.sign_zxIV2.setVisibility(View.INVISIBLE);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                myViewHold.sign_zxIV2.setVisibility(View.INVISIBLE);
            }
            myViewHold.right_layout.setVisibility(View.VISIBLE);
        }else{
            myViewHold.right_layout.setVisibility(View.INVISIBLE);
        }
        //用户课程id传到下个界面
//        final int userCourseId = courseListBean.getUserCourseId();
//        final int coruseId = courseListBean.getCoruseId();
//        String endDate = courseListBean.getEndDate();
//        Log.e("endDate", endDate + "");
//        String currentTime = DateUtil.getCurrentTimeAll();
//        int i = endDate.compareTo(currentTime);
        return convertView;
    }

    public static int getYears(String startDay){
        boolean flag=false;

        return 0;
    }

    static class myViewHold {
        ImageRadiusView recPic1,recPic2;
        TextView rec_courseName1, rec_courseName2, rec_price1, rec_price2;
        ImageView rec_sign_shikan1,rec_sign_shikan2,sign_zxIV1,sign_zxIV2;
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
