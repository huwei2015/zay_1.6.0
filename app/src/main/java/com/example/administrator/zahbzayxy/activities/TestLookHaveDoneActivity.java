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
import com.example.administrator.zahbzayxy.adapters.TestHaveDoneAdapter;
import com.example.administrator.zahbzayxy.adapters.TestLookHaveDongAdapter;
import com.example.administrator.zahbzayxy.beans.AllHaveDoTestBean;
import com.example.administrator.zahbzayxy.beans.GridItem;
import com.example.administrator.zahbzayxy.beans.SaveUserErrorPrcticeBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.myinterface.UpPx;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;
import com.example.administrator.zahbzayxy.stickheadgv.StickyGridHeadersGridView;
import com.example.administrator.zahbzayxy.utils.AutoScrollTextView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 查看结果错题
 */
public class TestLookHaveDoneActivity extends BaseActivity {
    PopupWindow popupWindow;
    MyRecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    TestLookHaveDongAdapter madapter;
    private ImageView back_tsetPractice_iv;
    List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> totalList = new ArrayList<>();

    List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> allTestList = new ArrayList<>();

    //查看用户作对做错的弹出框
    List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList = new ArrayList<>();
    TextView dijige;
    private int position;
    private String token;
    String encodeToken;
    private int quesSize;
    private AutoScrollTextView testName;
    private String paperName;
    List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> quesDetailsErrorList = new ArrayList<>();
    List<GridItem> gridItemList = new ArrayList<>();
    private int testType;
    private int size;
    private int correct_account;//对题
    private int error_account;//错题
    private int no_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_practice_acivity);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        try {
            encodeToken = URLEncoder.encode(token, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        initView();
        initFinish();
        initRecyClerView();
        initDownLoadAllData();
    }

    private void initDownLoadAllData() {
        int examScoreId = getIntent().getIntExtra("examScoreId", 0);
        testType = getIntent().getIntExtra("testType", 0);
        Log.e("getExamScoreId", examScoreId + "");
        madapter = new TestLookHaveDongAdapter(TestLookHaveDoneActivity.this, totalList, recyclerview, dijige);
        recyclerview.setAdapter(madapter);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestResultPath(examScoreId, token).enqueue(new Callback<AllHaveDoTestBean>() {
            @Override
            public void onResponse(Call<AllHaveDoTestBean> call, Response<AllHaveDoTestBean> response) {
                int code = response.code();
                if (code == 200) {
                    AllHaveDoTestBean body = response.body();
                    String s = new Gson().toJson(body);
                    Log.e("havedoTest", testType + "," + s);
                    if (body != null) {
                        String code1 = body.getCode();
                        if (code1.equals("00000")) {
                            AllHaveDoTestBean.DataEntity data = body.getData();
                            List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> quesDetails = data.getQuesDetails();
                            quesSize = quesDetails.size();
                            Integer integer = Integer.valueOf(testType);
                            if (integer != null) {
                                if (testType == 1) {//查看全部错题
                                    quesDetailsErrorList.clear();
                                    for (int i = 0; i < quesSize; i++) {
                                        //下面的弹出框
                                        SaveUserErrorPrcticeBean.ErrorQuesBean saveUserErrorPrcticeBean = new SaveUserErrorPrcticeBean.ErrorQuesBean();
                                        saveErrorList.add(saveUserErrorPrcticeBean);
                                        saveErrorList.get(i).setIsRight(-1);
                                        AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity answerResult = quesDetails.get(i).getAnswerResult();
                                        int isRight = answerResult.getIsRight();//错题
                                        if (isRight == 0) {
                                            int j = i;
                                            error_account++;
                                            AllHaveDoTestBean.DataEntity.QuesDetailsEntity quesDetailsEntity = quesDetails.get(j);
                                            //做错的list
                                            quesDetailsErrorList.add(quesDetailsEntity);
                                        }
                                        correct_account = quesSize - error_account;
                                        no_answer = quesSize - correct_account- error_account;//未答
                                    }
                                    totalList.clear();
                                    totalList.addAll(quesDetailsErrorList);
                                    madapter.notifyDataSetChanged();


                                } else if (testType == 2) {//查看全部试题
//                                    adapter = new TestResultAdapter(mGirdList, ResultActivity.this);
//                                    resultGridView.setAdapter(adapter);
                                    for (int i = 0; i < quesSize; i++) {
                                        //下面的弹出框
                                        SaveUserErrorPrcticeBean.ErrorQuesBean saveUserErrorPrcticeBean = new SaveUserErrorPrcticeBean.ErrorQuesBean();
                                        saveErrorList.add(saveUserErrorPrcticeBean);
                                        saveErrorList.get(i).setIsRight(-1);
                                        AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity answerResult = quesDetails.get(i).getAnswerResult();
                                        int isRight = answerResult.getIsRight();//错题
                                        if (isRight == 0) {
                                            int j = i;
                                            error_account++;
                                            AllHaveDoTestBean.DataEntity.QuesDetailsEntity quesDetailsEntity = quesDetails.get(j);
                                            //做错的list
                                            quesDetailsErrorList.add(quesDetailsEntity);
                                        }
                                        correct_account = quesSize - error_account;
                                        no_answer = quesSize - error_account - correct_account; //未答
                                    }
                                    totalList.clear();
                                    totalList.addAll(quesDetails);
                                    madapter.notifyDataSetChanged();
                                }
                            }
                            size = totalList.size();
                            for (int i = 0; i < size; i++) {
                                //下面的弹出框
                                SaveUserErrorPrcticeBean.ErrorQuesBean saveUserErrorPrcticeBean = new SaveUserErrorPrcticeBean.ErrorQuesBean();
                                saveErrorList.add(saveUserErrorPrcticeBean);
                                saveErrorList.get(i).setIsRight(-1);
                            }

                            dijige.setText((position + 1) + "/" + size);
                            allTestList.addAll(quesDetails);
                            madapter.notifyDataSetChanged();

                        } else if (code1.equals("99999")) {
                            Toast.makeText(TestLookHaveDoneActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<AllHaveDoTestBean> call, Throwable t) {

            }
        });

    }

    private void initView() {
        testName = (AutoScrollTextView) findViewById(R.id.paperName_tv);
        back_tsetPractice_iv = (ImageView) findViewById(R.id.back_testPractice_iv);
        recyclerview = (MyRecyclerView) findViewById(R.id.recyclerview);
        dijige = (TextView) findViewById(R.id.dijige);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        recyclerview.setLayoutManager(linearLayoutManager);
        paperName = getIntent().getStringExtra("paperName");
        if (!TextUtils.isEmpty(paperName)) {
            testName.setText(paperName);
        }
    }

    private void initRecyClerView() {
        recyclerview.setUp(new UpPx() {
            @Override
            public void upPx(int tag) {
                position = madapter.getWeiZhi();
                Log.e("postion", position + "222222");
                if (tag == 1) {
                    if (position > 0) {
                        //左
                        position = position - 1;
                        int childPosition = madapter.getChildPosition();
                        if (childPosition >= 0) {
                            if (childPosition == 1){
                                madapter.setChildPosition(-1);
                            } else {
                                madapter.setChildPosition(childPosition - 2);
                                position += 1;
                            }
                        }
                        int kePosition = madapter.getKeChildPosition();
                        if (kePosition >= 0) {
                            if (kePosition == 1){
                                madapter.setKeChildPosition(-1);
                            } else {
                                madapter.setKeChildPosition(kePosition - 2);
                                position += 1;
                            }
                        }
                        recyclerview.scrollToPosition(position);
                        madapter.notifyDataSetChanged();
                        dijige.setText((position + 1) + "/" + size);
                    }
                } else if (tag == 2) {
                    //右
                    ////////////////////////////////////////
                    //此处的100是题的个数  根据实际题的个数做更改
                    if (position < size - 1) {
                        position = position + 1;
                        int childPosition = madapter.getChildPosition();
                        if (childPosition >= 0) {
                            if (childPosition >= totalList.get(position - 1).getChildren().size()){
                                madapter.setChildPosition(-1);
                            } else {
                                position -= 1;
                            }
                        }
                        int kePosition = madapter.getKeChildPosition();
                        if (kePosition >= 0) {
                            if (kePosition >= totalList.get(position - 1).getChildren().size()){
                                madapter.setKeChildPosition(-1);
                            } else {
                                position -= 1;
                            }
                        }
                        recyclerview.scrollToPosition(position);
                        dijige.setText((position + 1) + "/" + size);
                        madapter.notifyDataSetChanged();
                    }
                } else {

                }
            }
        });
    }

    //左按钮
    public void leftButton(View view) {
        position = madapter.getWeiZhi();
        Log.e("postion", position + "33333");
        if (position > 0) {
            //左
            position = position - 1;
            int childPosition = madapter.getChildPosition();
            if (childPosition >= 0) {
                if (childPosition == 1){
                    madapter.setChildPosition(-1);
                } else {
                    madapter.setChildPosition(childPosition - 2);
                    position += 1;
                }
            }
            int kePosition = madapter.getKeChildPosition();
            if (kePosition >= 0) {
                if (kePosition == 1){
                    madapter.setKeChildPosition(-1);
                } else {
                    madapter.setKeChildPosition(kePosition - 2);
                    position += 1;
                }
            }
            recyclerview.scrollToPosition(position);
            madapter.notifyDataSetChanged();
            dijige.setText((position + 1) + "/" + size);
        }
    }

    //右按钮
    public void rightButton(View view) {
        position = madapter.getWeiZhi();
        Log.e("postion", position + "44444");
        //右
        ////////////////////////////////////////
        //此处的100是题的个数  根据实际题的个数做更改
        if (position < size - 1) {
            position = position + 1;
            int childPosition = madapter.getChildPosition();
            if (childPosition >= 0) {
                if (childPosition >= totalList.get(position - 1).getChildren().size()){
                    madapter.setChildPosition(-1);
                } else {
                    position -= 1;
                }
            }
            int kePosition = madapter.getKeChildPosition();
            if (kePosition >= 0) {
                if (kePosition >= totalList.get(position - 1).getChildren().size()){
                    madapter.setKeChildPosition(-1);
                } else {
                    position -= 1;
                }
            }
            recyclerview.scrollToPosition(position);
            madapter.notifyDataSetChanged();
            dijige.setText((position + 1) + "/" + size);
        }

    }
    int isRight;
    //点击当前页的图标看当前做题进度
    public void currentProgress(View view) {
        final View popView = LayoutInflater.from(this).inflate(R.layout.poplayout, null, false);
        StickyGridHeadersGridView gridView = popView.findViewById(R.id.pop_gv);
        ImageView dialogDissmiss_iv = popView.findViewById(R.id.dialog_dismiass_iv);
        TextView wrongNumPop_tv = popView.findViewById(R.id.wrongNum_pop_tv); //错题
        TextView rightNumPop_tv = popView.findViewById(R.id.rightNum_pop_tv); //对题
        TextView noDone_tv = popView.findViewById(R.id.noDone_pop_tv); //未答
        wrongNumPop_tv.setText("答错:"+error_account);
        rightNumPop_tv.setText("答对:"+correct_account);
        noDone_tv.setText("未答:"+no_answer);
//        noDone_tv.setVisibility(View.INVISIBLE);
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
        ColorDrawable colorDrawable = new ColorDrawable(0x3cffffff);
        popUpWindow.setBackgroundDrawable(colorDrawable);
        dialogDissmiss_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popUpWindow.dismiss();
            }
        });
        List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> list = madapter.getList();
        List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> oneList = new ArrayList<>();
        List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> twoList = new ArrayList<>();
        List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> threeList = new ArrayList<>();
        List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> fourList = new ArrayList<>();
        List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> fiveList = new ArrayList<>();
        List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> sixList = new ArrayList<>();
        int size = list.size();//所有题库
        for (int i = 0; i < size; i++) {
            int quesType = list.get(i).getQuesType();
            isRight = list.get(i).getAnswerResult().getIsRight();
            if (quesType == 1) {//单选
                int j = i + 1;
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity examDetailsBean1 = new AllHaveDoTestBean.DataEntity.QuesDetailsEntity();
                examDetailsBean1.setLocation(j);
                examDetailsBean1.setAnswerResult(new AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity());
                if (isRight == 1) {
                    examDetailsBean1.getAnswerResult().setIsRight(1);
                }
                oneList.add(examDetailsBean1);
            } else if (quesType == 2) {//多选
                int j = i + 1;
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity examDetailsBean1 = new AllHaveDoTestBean.DataEntity.QuesDetailsEntity();
                examDetailsBean1.setLocation(j);
                examDetailsBean1.setAnswerResult(new AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity());
                if (isRight == 1) {
                    examDetailsBean1.getAnswerResult().setIsRight(1);
                }
                twoList.add(examDetailsBean1);

            } else if (quesType == 3) {//判断
                int j = i + 1;
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity examDetailsBean1 = new AllHaveDoTestBean.DataEntity.QuesDetailsEntity();
                examDetailsBean1.setLocation(j);
                examDetailsBean1.setAnswerResult(new AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity());
                if (isRight == 1) {
                    examDetailsBean1.getAnswerResult().setIsRight(1);
                }
                threeList.add(examDetailsBean1);
            } else if (quesType == 4) {//主管案例
                int j = i + 1;
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity examDetailsBean1 = new AllHaveDoTestBean.DataEntity.QuesDetailsEntity();
                examDetailsBean1.setLocation(j);
                examDetailsBean1.setAnswerResult(new AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity());
                if (isRight == 1) {
                    examDetailsBean1.getAnswerResult().setIsRight(1);
                }
                fourList.add(examDetailsBean1);
            } else if (quesType == 5) {//客观案例
                int j = i + 1;
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity examDetailsBean1 = new AllHaveDoTestBean.DataEntity.QuesDetailsEntity();
                examDetailsBean1.setLocation(j);
                examDetailsBean1.setAnswerResult(new AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity());
                if (isRight == 1) {
                    examDetailsBean1.getAnswerResult().setIsRight(1);
                }
                fiveList.add(examDetailsBean1);
            } else if (quesType == 6) {//简答题
                int j = i + 1;
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity examDetailsBean1 = new AllHaveDoTestBean.DataEntity.QuesDetailsEntity();
                examDetailsBean1.setLocation(j);
                examDetailsBean1.setAnswerResult(new AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity());
                if (isRight == 1) {
                    examDetailsBean1.getAnswerResult().setIsRight(1);
                }
                sixList.add(examDetailsBean1);
            }
        }


        List<GridItem> mGirdList = new ArrayList<GridItem>();
        GridItem item1 = null;
        GridItem item2 = null;
        GridItem item3 = null;
        GridItem item4 = null;
        GridItem item5 = null;
        GridItem item6 = null;
        int size1 = oneList.size();
        int size2 = twoList.size();
        int size3 = threeList.size();
        int size4 = fourList.size();
        int size5 = fiveList.size();
        int size6 = sixList.size();
        Log.e("errorListSize", size1 + "," + size2 + "," + size3);
        if (size1 > 0) {
            for (int i = 0; i < size1; i++) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity examDetailsBean = oneList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getAnswerResult().getIsRight();
                item1 = new GridItem(location + "," + isRight, i + "", 0, "单选题", isRight);
                mGirdList.add(item1);
            }
        }
        if (size2 > 0) {
            for (int i = 0; i < size2; i++) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity quesDetailsEntity = twoList.get(i);
                int location = quesDetailsEntity.getLocation();
                int isRight = quesDetailsEntity.getAnswerResult().getIsRight();
                item2 = new GridItem(location + "," + isRight, i + "", 1, "多选", isRight);
                mGirdList.add(item2);
            }
        }
        if (size3 > 0) {
            for (int i = 0; i < size3; i++) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity quesDetailsEntity = threeList.get(i);
                int location = quesDetailsEntity.getLocation();
                int isRight = quesDetailsEntity.getAnswerResult().getIsRight();
                item3 = new GridItem(location + "," + isRight, i + "", 2, "判断", isRight);
                mGirdList.add(item3);
            }
        }
        if (size4 > 0) {
            for (int i = 0; i < size4; i++) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity quesDetailsEntity = fourList.get(i);
                int location = quesDetailsEntity.getLocation();
                int isRight = quesDetailsEntity.getAnswerResult().getIsRight();
                item4 = new GridItem(location + "," + isRight, i + "", 3, "主观案例", isRight);
                mGirdList.add(item4);
            }
        }
        if (size5 > 0) {
            for (int i = 0; i < size5; i++) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity quesDetailsEntity = fiveList.get(i);
                int location = quesDetailsEntity.getLocation();
                int isRight = quesDetailsEntity.getAnswerResult().getIsRight();
                item5 = new GridItem(location + "," + isRight, i + "", 4, "客观案例", isRight);
                mGirdList.add(item5);
            }
        }
        if (size6 > 0) {
            for (int i = 0; i < size6; i++) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity quesDetailsEntity = sixList.get(i);
                int location = quesDetailsEntity.getLocation();
                int isRight = quesDetailsEntity.getAnswerResult().getIsRight();
                item6 = new GridItem(location + "," + isRight, i + "", 5, "简答题", isRight);
                mGirdList.add(item6);
            }
        }
        //展示
        popUpWindow.showAtLocation(popView, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        gridView.setAdapter(new TestHaveDoneAdapter(mGirdList, TestLookHaveDoneActivity.this, recyclerview, popUpWindow, madapter, size, dijige));

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
    }

}
