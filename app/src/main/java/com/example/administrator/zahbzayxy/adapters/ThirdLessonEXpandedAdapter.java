package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LessonThiredBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.BuyCarGroupInterface;
import com.example.administrator.zahbzayxy.myviews.MyExpandableLV;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${ZWJ} on 2017/2/6 0006.
 */
public class ThirdLessonEXpandedAdapter extends BaseExpandableListAdapter {
    private int mainCourseId;
    private List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean>list;
    LayoutInflater mInflater;
    Context context;
    private String token;
    public void setToken(String token) {
        this.token = token;
    }
    public void setMainCourseId(int mainCourseId) {
        this.mainCourseId = mainCourseId;
    }

    public ThirdLessonEXpandedAdapter(Context context,List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean>list) {
        this.list=list;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getChapterlist().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_third_lesson_groupview_layout,parent,false);
        //子课程学时
        TextView zLessonNum_tv= (TextView) view.findViewById(R.id.zLessonNum_tv);
        int courseHours = list.get(groupPosition).getCourseHours();
        zLessonNum_tv.setText("本课程"+courseHours+"学时");
        //groupName
        TextView tv1 = (TextView) view.findViewById(R.id.lesson_group_name_tv);
       ImageView group_navegation_iv = (ImageView) view.findViewById(R.id.group_lesson_navagation_iv);

        if (isExpanded){//展开
            group_navegation_iv.setImageResource(R.mipmap.open_up);

        }else {
            group_navegation_iv.setImageResource(R.mipmap.close_down);
        }


        //加入购物车
        Button b  = (Button) view.findViewById(R.id.bt);
        tv1.setText(list.get(groupPosition).getCourseName());
        //子课程id
        final int subCourseIds= list.get(groupPosition).getId();
        final String[] cIds=new String[]{String.valueOf(subCourseIds)};
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
                Log.e("getFromFragment",token+","+mainCourseId);
                Call<SuccessBean> successBeanCall = aClass.buyCarAddCourseData(mainCourseId,cIds, token);
                successBeanCall.enqueue(new Callback<SuccessBean>() {
                    @Override
                    public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                        SuccessBean body = response.body();
                        if (body!=null) {
                            String code = body.getCode();
                            boolean data = body.getData();
                            if (body != null) {
                                if (code.equals("00003")) {
                                    Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sp = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putBoolean("isLogin", false);
                                    edit.commit();
                                } else if (dbIsLogin() == false) {
                                    Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                                } else if (data == true) {
                                    Toast.makeText(context, "加入购物车成功", Toast.LENGTH_SHORT).show();
                                } else if (code.equals("99999")) {
                                    Toast.makeText(context, "系统异常", Toast.LENGTH_SHORT).show();
                                } else if (code.equals("00012")) {
                                    Toast.makeText(context, "该课程已加入购物车", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<SuccessBean> call, Throwable t) {
                        Toast.makeText(context,"请求服务器失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    private boolean dbIsLogin() {
        SharedPreferences sharedPreferences =context.getSharedPreferences("tokenDb",context.MODE_APPEND);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MyExpandableLV listView = new MyExpandableLV(context);
        List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean.ChapterlistBean> chapterlist = list.get(groupPosition).getChapterlist();
        ThirdLessonSecondEXAdapter adapter = new ThirdLessonSecondEXAdapter(chapterlist,context);
        //去掉ExpandableListView 默认的箭头
        listView.setGroupIndicator(null);
        listView.setAdapter(adapter);

        return listView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
