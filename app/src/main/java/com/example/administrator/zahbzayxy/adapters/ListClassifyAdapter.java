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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.PMyLessonBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.myviews.CateTextView;
import com.example.administrator.zahbzayxy.myviews.CustomLayout;
import com.example.administrator.zahbzayxy.myviews.ImageRadiusView;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.DateUtil;
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
public class ListClassifyAdapter extends BaseAdapter {
    private Context context;
    private List<CourseCatesBean.DataBean.Cates> list;
    private LayoutInflater inflater;
    String price, token;
    Handler mHandler;
    private OnItemClickListener mOnItemClickListener;
    private Integer cateId;

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

    public ListClassifyAdapter(List<CourseCatesBean.DataBean.Cates> list, Context context, String token) {
        this.list = list;
        this.context = context;
        this.token = token;
        inflater = LayoutInflater.from(context);
    }

    public ListClassifyAdapter(List<CourseCatesBean.DataBean.Cates> list, Context context, String token, Handler handler,Integer cateId) {
        this.list = list;
        this.context = context;
        this.token = token;
        inflater = LayoutInflater.from(context);
        mHandler = handler;
        this.cateId=cateId;
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

        CourseCatesBean.DataBean.Cates cate = list.get(position);
        myViewHold.mainClassify.setText(cate.getCateName());
        if(cate.getChilds().size()>0){
            for(CourseCatesBean.DataBean.Cates c:cate.getChilds()){
                CateTextView tv=new CateTextView(context);
                tv.setText(c.getCateName());
                tv.setDataId(c.getId());
                tv.setStr("0");
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                tv.setBackgroundResource(R.drawable.corner_text_view_normal);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.i("dataId",((CateTextView)v).getDataId()+"");
                        String str=((CateTextView)v).getStr();
                        if("0".equals(str)){
                            v.setBackgroundResource(R.drawable.corner_text_view);
                            ((CateTextView)v).setText(TextAndPictureUtil.getTextRightImg(context,((CateTextView)v).getText().toString(),R.mipmap.circle_right));
                            ((CateTextView)v).setStr("1");
                        }else{
                            v.setBackgroundResource(R.drawable.corner_text_view_normal);
                            ((CateTextView)v).setText(((CateTextView)v).getText().toString().trim());
                            ((CateTextView)v).setStr("0");
                        }
                    }
                });
                myViewHold.txtRL.addView(tv);
            }
        }
        return convertView;
    }

    static class myViewHold {
        CustomLayout txtRL;
        TextView mainClassify;
    }


}
