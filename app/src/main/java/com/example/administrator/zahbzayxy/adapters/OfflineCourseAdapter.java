package com.example.administrator.zahbzayxy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
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
import com.example.administrator.zahbzayxy.activities.OfflineDetailActivity;
import com.example.administrator.zahbzayxy.beans.OfflineCourseBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
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
public class OfflineCourseAdapter extends  BaseAdapter {
    private Context context;
    private List<OfflineCourseBean.DataBean.CourseListBean> list;
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


    public OfflineCourseAdapter(Context context) {
        this.context = context;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public OfflineCourseAdapter(List<OfflineCourseBean.DataBean.CourseListBean> list, Context context, String token) {
        this.list = list;
        this.context = context;
        this.token = token;
        inflater = LayoutInflater.from(context);
    }

    public OfflineCourseAdapter(List<OfflineCourseBean.DataBean.CourseListBean> list, Context context, String token, Handler handler) {
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
            convertView = inflater.inflate(R.layout.item_offline_layout, parent, false);
            myViewHold.recPic1 = convertView.findViewById(R.id.recPic1);
            myViewHold.recPic2 = convertView.findViewById(R.id.recPic2);
            myViewHold.rec_courseName1 = convertView.findViewById(R.id.rec_courseName1);
            myViewHold.rec_courseName2 = convertView.findViewById(R.id.rec_courseName2);
            myViewHold.rec_price1 = convertView.findViewById(R.id.rec_price1);
            myViewHold.rec_price2 = convertView.findViewById(R.id.rec_price2);
            myViewHold.left_layout=convertView.findViewById(R.id.left_layout);
            myViewHold.right_layout=convertView.findViewById(R.id.right_layout);
            myViewHold.sign_zxIV1= convertView.findViewById(R.id.sign_zxIV1);
            myViewHold.sign_zxIV2=convertView.findViewById(R.id.sign_zxIV2);
            convertView.setTag(myViewHold);
        } else {
            myViewHold = (OfflineCourseAdapter.myViewHold) convertView.getTag();
        }

        OfflineCourseBean.DataBean.CourseListBean courseListBean = list.get(position);
        if(courseListBean.getIsRecommend()==1){
            myViewHold.rec_courseName1.setText(TextAndPictureUtil.getText(context,courseListBean.getCourseName(),R.mipmap.recommend_course));
        }else{
            myViewHold.rec_courseName1.setText(courseListBean.getCourseName());
        }

        myViewHold.rec_price1.setText(String.valueOf(courseListBean.getTotalHours())+"学时");
        if (!TextUtils.isEmpty(courseListBean.getImagePath())) {
            Picasso.with(context).load(courseListBean.getImagePath()).placeholder(R.mipmap.loading_png).into(myViewHold.recPic1);
        }

        if(courseListBean.getIsNew()==1){
            myViewHold.sign_zxIV1.setVisibility(View.VISIBLE);
        }else{
            myViewHold.sign_zxIV1.setVisibility(View.INVISIBLE);
        }
        final int courseId=courseListBean.getCourseId();
        myViewHold.left_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,  OfflineDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("courseId",courseId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        //=============================================================================第二个值
        if (!TextUtils.isEmpty(courseListBean.getCourseName1())) {
            if(courseListBean.getIsRecommend1()==1) {
                myViewHold.rec_courseName2.setText(TextAndPictureUtil.getText(context, courseListBean.getCourseName1(), R.mipmap.recommend_course));
            }else{
                myViewHold.rec_courseName2.setText(courseListBean.getCourseName1());
            }
            myViewHold.rec_price2.setText(String.valueOf(courseListBean.getTotalHours1())+"学时");
            if (!TextUtils.isEmpty(courseListBean.getImagePath1())) {
                Picasso.with(context).load(courseListBean.getImagePath1()).placeholder(R.mipmap.loading_png).into(myViewHold.recPic2);
            }

            if(courseListBean.getIsNew1()==1){
                myViewHold.sign_zxIV2.setVisibility(View.VISIBLE);
            }else{
                myViewHold.sign_zxIV2.setVisibility(View.INVISIBLE);
            }
            myViewHold.right_layout.setVisibility(View.VISIBLE);
            final int courseId1=courseListBean.getCourseId1();
            myViewHold.right_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,  OfflineDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("courseId",courseId1);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }else{
            myViewHold.right_layout.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    static class myViewHold {
        ImageRadiusView recPic1,recPic2;
        TextView rec_courseName1, rec_courseName2, rec_price1, rec_price2;
        ImageView sign_zxIV1,sign_zxIV2;
        LinearLayout left_layout,right_layout;
    }

}
