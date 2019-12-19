package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.AginDoErrorAdapter;
import com.example.administrator.zahbzayxy.adapters.ErrorAginDoPopAdapter;
import com.example.administrator.zahbzayxy.beans.GridItem;
import com.example.administrator.zahbzayxy.beans.PrictaceErrorBean;
import com.example.administrator.zahbzayxy.beans.SaveUserErrorPrcticeBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.myinterface.UpPx;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;
import com.example.administrator.zahbzayxy.stickheadgv.StickyGridHeadersGridView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;
import org.json.JSONArray;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AginDoErrorActivity extends BaseActivity {
    PopupWindow popupWindow;
    MyRecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    AginDoErrorAdapter madapter;
    private ImageView back_tsetPractice_iv;
    List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> totalList=new ArrayList<>();
    //查看用户作对做错的弹出框
    List<SaveUserErrorPrcticeBean.ErrorQuesBean>saveErrorList=new ArrayList<>();
    TextView dijige;
    private int position;
    private String token;
    private String beginTime;
    private int correctRate;
    private String useTime;
    int quesLibId;
    private String stopTime;
    JSONArray jsonArray;
    String encodeToken;
    private int size;
    private String string;
    private int userQuesLibId;
    private int quesLibPackageId;
    private TextView paperName_tv;
    private int userLibId;
    private int packageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agin_do_error);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");

        try {
            encodeToken = URLEncoder.encode(token, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        initView();
        initFinish();
        initRecyClerView();
        initDownLoadData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        position=madapter.getWeiZhi();
        Log.e("postion",position+"1111");
    }


    private void initDownLoadData() {
        madapter=new AginDoErrorAdapter(AginDoErrorActivity.this,totalList,saveErrorList,recyclerview,dijige);
        recyclerview.setAdapter(madapter);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestPracticeData1(quesLibId,userLibId,packageId,token).enqueue(new Callback<PrictaceErrorBean>() {
            @Override
            public void onResponse(Call<PrictaceErrorBean> call, Response<PrictaceErrorBean> response) {
                int code = response.code();
                if (code==200) {
                    PrictaceErrorBean body = response.body();
                    String s = new Gson().toJson(body);
                    Log.e(  "saveErrorPrctice",s);
                    if (body != null) {
                        String code1 = body.getCode();
                        if (code1.equals("00000")) {
                            PrictaceErrorBean.DataEntity data = body.getData();

                            quesLibId = data.getQuesLibId();

                            Log.e("packageId",quesLibPackageId+",222,"+userQuesLibId+","+quesLibId);
                            if (data != null) {
                                List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> errorRecords = data.getErrorRecords();
                                if (errorRecords!=null) {
                                    size = errorRecords.size();
                                    position = madapter.getWeiZhi();
                                    dijige.setText((position + 1) + "/" + size);
                                    //查看当前错题集合的弹出框
                                    for (int i = 0; i < size; i++) {
                                        SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean = new SaveUserErrorPrcticeBean.ErrorQuesBean();
                                        int quesType = errorRecords.get(i).getQuesType();
                                        //保存用户错题记录的
                                        saveErrorList.add(errorQuesBean);
                                        saveErrorList.get(i).setIsRight(-1);
                                        saveErrorList.get(i).setQuestionType(quesType);
                                    }
                                    totalList.clear();
                                    totalList.addAll(errorRecords);
                                    madapter.notifyDataSetChanged();
                                }
                            }
                        }else if (code1.equals("99999")){
                            Toast.makeText(AginDoErrorActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<PrictaceErrorBean> call, Throwable t) {

            }
        });

    }

    private void initView() {
        paperName_tv= (TextView) findViewById(R.id.paperName_tv);
        beginTime = getIntent().getStringExtra("beginTime");
        quesLibId = getIntent().getIntExtra("quesLibId",0);
        userLibId = getIntent().getIntExtra("userLibId",0);
        packageId = getIntent().getIntExtra("packageId",0);
        Log.e("packageId",quesLibId+",1111,"+userLibId+","+packageId);
        String paperName = getIntent().getStringExtra("paperName");
        if (!TextUtils.isEmpty(paperName)){
            paperName_tv.setText(paperName);
        }
        back_tsetPractice_iv= (ImageView) findViewById(R.id.back_testPractice_iv);
        recyclerview= (MyRecyclerView) findViewById(R.id.recyclerview);
        dijige= (TextView) findViewById(R.id.dijige);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        recyclerview.setLayoutManager(linearLayoutManager);

    }

    private void initRecyClerView() {
        recyclerview.setUp(new UpPx() {
            @Override
            public void upPx(int tag) {
                position=madapter.getWeiZhi();
                Log.e("postion",position+"222222");
                if (tag==1){
                    if(position>0){
                        //左
                        position= position-1;
                        recyclerview.scrollToPosition(position);
                        madapter.notifyDataSetChanged();
                        dijige.setText((position+1)+"/"+size);
                    }
                }else if (tag==2){
                    //右
                    ////////////////////////////////////////
                    //此处的100是题的个数  根据实际题的个数做更改
                    if(position<size-1){
                        position = position+1;
                        recyclerview.scrollToPosition(position);
                        dijige.setText((position+1)+"/"+size);
                        madapter.notifyDataSetChanged();
                    }
                }else {
                    // recyclerview.scrollToPosition(position);
                    //  dijige.setText((position+1)+"/"+size);
                    // madapter.notifyDataSetChanged();
                }
            }
        });
    }
    //左按钮
    public void leftButton(View view) {
        position=madapter.getWeiZhi();
        if (size>0) {
            Log.e("postion", position + "33333");
            if (position > 0) {
                //左
                position = position - 1;
                recyclerview.scrollToPosition(position);
                madapter.notifyDataSetChanged();
                dijige.setText((position + 1) + "/" + size);
            }
        }
    }
    //右按钮
    public void rightButton(View view) {
        position=madapter.getWeiZhi();
        Log.e("postion",position+"44444");
        if (size>0) {
            //右
            ////////////////////////////////////////
            //此处的100是题的个数  根据实际题的个数做更改
            if (position < size - 1) {
                position = position + 1;
                recyclerview.scrollToPosition(position);
                madapter.notifyDataSetChanged();
                dijige.setText((position + 1) + "/" + size);
            }
        }
    }
    //点击当前页的图标看当前做题进度
    public void currentProgress(View view) {
        final View popView = LayoutInflater.from(this).inflate(R.layout.poplayout, null, false);
        StickyGridHeadersGridView gridView = (StickyGridHeadersGridView) popView.findViewById(R.id.pop_gv);
        ImageView dialogDissmiss_iv= (ImageView) popView.findViewById(R.id.dialog_dismiass_iv);
        TextView wrongNumPop_tv= (TextView) popView.findViewById(R.id.wrongNum_pop_tv);
        TextView rightNumPop_tv= (TextView) popView.findViewById(R.id.rightNum_pop_tv);
        TextView noDone_tv= (TextView) popView.findViewById(R.id.noDone_pop_tv);
        int rightNum = madapter.getRightNum();
        int rongNum = madapter.getRongNum();
        int noDoneNum=size-(rightNum+rongNum);
        wrongNumPop_tv.setText("答错:"+rongNum);
        rightNumPop_tv.setText("答对:"+rightNum);
        noDone_tv.setText("未答:"+noDoneNum);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
        p.height = (int) (d.getHeight() * 0.85); // 高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
        p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.0f; // 设置黑暗度
        final PopupWindow popUpWindow = new PopupWindow(popView, p.width, p.height, true);
        popUpWindow.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        popUpWindow.setOutsideTouchable(true);
        popUpWindow.setAnimationStyle(R.style.take_photo_anim);
        // 配合 点击外部区域消失使用 否则 没有效果
        ColorDrawable colorDrawable=new ColorDrawable(0x3cffffff);
        popUpWindow.setBackgroundDrawable(colorDrawable);
        //展示
        popUpWindow.showAtLocation(popView, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        dialogDissmiss_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow.dismiss();
            }
        });
        List<SaveUserErrorPrcticeBean.ErrorQuesBean>oneList=new ArrayList<>();
        List<SaveUserErrorPrcticeBean.ErrorQuesBean>twoList=new ArrayList<>();
        List<SaveUserErrorPrcticeBean.ErrorQuesBean>threeList=new ArrayList<>();
        saveErrorList=madapter.getSaveErrorList();
        int size = saveErrorList.size();
        Log.e("errorListSize",size+"");
        for (int i=0;i<size;i++){
            SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean1 = saveErrorList.get(i);
            int questionType = errorQuesBean1.getQuestionType();
            int isRight = errorQuesBean1.getIsRight();
            if (questionType==1){//单选
                int j=i+1;
                SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean=new SaveUserErrorPrcticeBean.ErrorQuesBean();
                errorQuesBean.setLocation(j);
                errorQuesBean.setIsRight(isRight);
                oneList.add(errorQuesBean);
            }else if (questionType==2){//多选
                int j=i+1;
                SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean=new SaveUserErrorPrcticeBean.ErrorQuesBean();
                errorQuesBean.setLocation(j);
                errorQuesBean.setIsRight(isRight);
                twoList.add(errorQuesBean);

            }else if (questionType==3){//判断
                int j=i+1;
                SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean=new SaveUserErrorPrcticeBean.ErrorQuesBean();
                errorQuesBean.setLocation(j);
                errorQuesBean.setIsRight(isRight);
                threeList.add(errorQuesBean);
            }
        }
        List<GridItem> mGirdList=new ArrayList<GridItem>();
        GridItem item1=null;
        GridItem item2=null;
        GridItem item3=null;
        int size1 = oneList.size();
        int size2 = twoList.size();
        int size3 = threeList.size();
        Log.e("errorListSize",size1+","+size2+","+size3+",");
        if (size1>0) {
            for (int i = 0; i < size1; i++) {
                SaveUserErrorPrcticeBean.ErrorQuesBean examDetailsBean = oneList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                Log.e("isRight",isRight+"单选,11111");
                item1 = new GridItem(location + ","+isRight, i + "", 0, "单选",isRight);
                mGirdList.add(item1);
            }
        }
        if (size2>0) {
            for (int i = 0; i < size2; i++) {
                SaveUserErrorPrcticeBean.ErrorQuesBean examDetailsBean = twoList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item2 = new GridItem(location + ","+isRight, i + "", 1, "多选",isRight);
                mGirdList.add(item2);
                Log.e("isRight",isRight+"多选,11111");
            }
        }
        if (size3>0) {
            for (int i = 0; i < size3; i++) {
                SaveUserErrorPrcticeBean.ErrorQuesBean examDetailsBean = threeList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item3 = new GridItem(location + ","+isRight, i + "", 2, "判断",isRight);
                Log.e("isRight",isRight+"判断,11111");
                mGirdList.add(item3);
            }
        }

        gridView.setAdapter(new ErrorAginDoPopAdapter(mGirdList,AginDoErrorActivity.this,recyclerview,popUpWindow,madapter,size,dijige));

    }

    private void initFinish() {
        back_tsetPractice_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
      /*  //拿到adapter中的保存错误练习题的集合
        List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList1 = madapter.getSaveErrorList1();

        if (saveErrorList1 != null) {
            int size = saveErrorList1.size();
            Log.e("doneSize",size+"");
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            JSONObject object = null;
            for (int i = 0; i < size; i++) {
                object = new JSONObject();
                SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean = saveErrorList1.get(i);
                if (errorQuesBean != null) {
                    int questionId = errorQuesBean.getQuestionId();
                    String errorAnswerIds = errorQuesBean.getErrorAnswerIds();
                    int questionType = errorQuesBean.getQuestionType();
                    try {
                        object.put("errorAnswerIds", errorAnswerIds);
                        object.put("questionId", questionId);
                        object.put("questionType", questionType);
                        array.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                jsonObject.put("quesLibId", quesLibId);
                jsonObject.put("quesLibPackageId", packageId);
                jsonObject.put("userQuesLibId ", userLibId);
                jsonObject.put("errorQues", array);
                string = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PracticeInterface aClass = RetrofitUtils.getInstance().createClass(PracticeInterface.class);
            Log.e("saveErrorPrctice",string);
            aClass.saveUserData(string, token).enqueue(new Callback<SuccessBean>() {
                @Override
                public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                    int code = response.code();
                    if (code==200) {
                        SuccessBean body = response.body();
                        if (response != null && response.body().getErrMsg() == null) {
                            boolean data = response.body().getData();
                            if (data == true) {
                                 Toast.makeText(AginDoErrorActivity.this, "保存错题成功", Toast.LENGTH_SHORT).show();
                            } else {
                                 Toast.makeText(AginDoErrorActivity.this, "保存错题失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SuccessBean> call, Throwable t) {
                    // Toast.makeText(TestPracticeAcivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                }
            });

        }*/
    }

}
