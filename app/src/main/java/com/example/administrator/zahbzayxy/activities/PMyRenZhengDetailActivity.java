package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PMyRenZhengDetailBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PMyRenZhengDetailActivity extends BaseActivity {
   private ImageView back_iv;
   private TextView name_tv,userId_tv,lessonName_tv,studyNum_tv,bianHao_tv,lessonInclude_tv;
    private String token;
    int userCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmy_ren_zheng_detail);
        initView();
        downLoadData();
    }

    private void downLoadData() {
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyRenZhengDetailData(token,userCourseId).enqueue(new Callback<PMyRenZhengDetailBean>() {
            @Override
            public void onResponse(Call<PMyRenZhengDetailBean> call, Response<PMyRenZhengDetailBean> response) {
                if (response!=null) {
                    PMyRenZhengDetailBean body = response.body();
                    if (body != null) {
                        String code = response.body().getCode();
                        if(code.equals("99999")){
                            Toast.makeText(PMyRenZhengDetailActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        }
                        if (body.getErrMsg() == null) {
                            PMyRenZhengDetailBean.DataBean data = body.getData();
                            String userName = data.getUserName();
                            if (!TextUtils.isEmpty(userName)) {
                                name_tv.setText(userName);
                            }
                            String idCard = data.getIdCard();
                            String courseName = data.getCourseName();
                            if (!TextUtils.isEmpty(courseName)) {
                                lessonName_tv.setText(courseName);
                            }

                            String cerCode = data.getCerCode();
                            if (!TextUtils.isEmpty(cerCode)) {
                                bianHao_tv.setText(cerCode);
                            }
                            String cerSelectionNames = data.getCerSelectionNames();
                            Integer courseHours = data.getCourseHours();
                            if (!TextUtils.isEmpty(String.valueOf(courseHours))) {
                                studyNum_tv.setText(courseHours + "学时");
                            }
                            String s = Html.fromHtml(cerSelectionNames).toString();
                            lessonInclude_tv.setText(s);
                            userId_tv.setText(idCard);

                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<PMyRenZhengDetailBean> call, Throwable t) {
                Toast.makeText(PMyRenZhengDetailActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        back_iv= (ImageView) findViewById(R.id.pMyRenZhengDetailBack_iv);
        name_tv= (TextView) findViewById(R.id.renZhengName_tv);
        userId_tv= (TextView) findViewById(R.id.renZhengId_tv);
        lessonName_tv= (TextView) findViewById(R.id.rZLessonName_tv);
        studyNum_tv= (TextView) findViewById(R.id.rZStudyTime_tv);
        bianHao_tv= (TextView) findViewById(R.id.rZBianHao_tv);
        lessonInclude_tv= (TextView) findViewById(R.id.rZLessonInclude_tv);
       userCourseId= getIntent().getIntExtra("userCourseId",0);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
