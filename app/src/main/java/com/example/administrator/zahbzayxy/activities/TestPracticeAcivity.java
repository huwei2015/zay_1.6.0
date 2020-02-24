package com.example.administrator.zahbzayxy.activities;

import android.app.Activity;
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

import com.alibaba.fastjson.JSON;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.PracticeStickyGridAdapter;
import com.example.administrator.zahbzayxy.adapters.TestPracticeAdapter;
import com.example.administrator.zahbzayxy.beans.DaoMaster;
import com.example.administrator.zahbzayxy.beans.GridItem;
import com.example.administrator.zahbzayxy.beans.OptsBean;
import com.example.administrator.zahbzayxy.beans.QuesListBean;
import com.example.administrator.zahbzayxy.beans.QuesListBean2;
import com.example.administrator.zahbzayxy.beans.QuesListBeanDao;
import com.example.administrator.zahbzayxy.beans.SaveUserErrorPrcticeBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.beans.TestPracticeBean;
import com.example.administrator.zahbzayxy.beans.TestResultBean;
import com.example.administrator.zahbzayxy.databases.SaveListDBManager;
import com.example.administrator.zahbzayxy.interfacecommit.PracticeInterface;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.myinterface.UpPx;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;
import com.example.administrator.zahbzayxy.stickheadgv.StickyGridHeadersGridView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.database.Database;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//顺序练习
public class TestPracticeAcivity extends BaseActivity {
    PopupWindow popupWindow;
    MyRecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    TestPracticeAdapter madapter;
    private ImageView back_tsetPractice_iv;
    List<QuesListBean2> totalList = new ArrayList<>();
    //查看用户作对做错的弹出框
    List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList = new ArrayList<>();
    TextView dijige;
    private int position;
    private String token;
    int quesLibId;
    String encodeToken;
    private long id;
    int quesLibPackageId;
    int userQuesLibId;
    private int size;
    private String string;
    //创建数据库查询的单例对象
    SaveListDBManager saveDb;
    //从数据库中取出的集合对象
    List<QuesListBean> saveUserErrorDbBeen = new ArrayList<>();
    private TextView paperName_tv;
    int postion_weizhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_practice_acivity);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        //保存做题记录
        SharedPreferences sp = getSharedPreferences("tiku", MODE_PRIVATE);
        postion_weizhi = sp.getInt("position", 0);
        try {
            encodeToken = URLEncoder.encode(token, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        initView();
        initFinish();
        initRecyClerView();
        try {
            initDb();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Database db = new DaoMaster.DevOpenHelper(TestPracticeAcivity.this, "saveList").getWritableDb();
                if (db != null) {
                    QuesListBeanDao.dropTable(db, true);
                    QuesListBeanDao.createTable(db, false);
                    saveDb = new SaveListDBManager(getApplicationContext());
                    if (saveDb != null) {
                        initDownLoadData();
                        return;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ToastUtils.showLongInfo("数据异常，请稍后重试");
                finish();
                return;
            }
            ToastUtils.showLongInfo("数据初始化失败，请稍后重试");
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        position = madapter.getWeiZhi();
        Log.e("postion", position + "1111");
    }

    private void initDb() {
        saveDb = SaveListDBManager.getInstance(getApplicationContext());
        if (saveDb != null) {
            Log.i("jkljl", "test===========" + quesLibId);
            saveUserErrorDbBeen = saveDb.queryAll(quesLibId);
            int size1 = saveUserErrorDbBeen.size();
            if (size1 > 0) {//从本地取数据
                if (saveUserErrorDbBeen != null) {
                    List<QuesListBean> quesList = saveUserErrorDbBeen;
                    this.size = quesList.size();
                    Gson gson = new Gson();
                    for (int j = 0; j < this.size; j++) {
                        QuesListBean item = quesList.get(j);
                        List<QuesListBean2> children = null;
                        String childrenStr = item.getChildren();
                        if (!TextUtils.isEmpty(childrenStr)) {
                            children = gson.fromJson(childrenStr, new TypeToken<List<QuesListBean2>>(){}.getType());
                        }
                        QuesListBean2 bean = new QuesListBean2(item.getBiaoJi(), item.getDiffType(), item.getId(),
                                item.getParsing(), item.getContent(), item.getQuesType(),
                                JSON.parseArray(item.getOpts(), OptsBean.class), children);
                        //保存用户错题记录的
                        int quesType = quesList.get(j).getQuesType();
                        SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean = new SaveUserErrorPrcticeBean.ErrorQuesBean();
                        errorQuesBean.setQuestionType(quesType);
                        saveErrorList.add(errorQuesBean);
                        saveErrorList.get(j).setIsRight(-1);
                        totalList.add(bean);
                    }
                    madapter = new TestPracticeAdapter(TestPracticeAcivity.this, totalList, saveErrorList, recyclerview, dijige);
                    recyclerview.setAdapter(madapter);

                    SharedPreferences sp = getSharedPreferences("tiku", MODE_PRIVATE);
                    int weizhi = sp.getInt("position", 0);
                    position = weizhi;
                    madapter.notifyDataSetChanged();
                    dijige.setText((position + 1) + "/" + size);
                    recyclerview.scrollToPosition(weizhi);
                }

            } else {//从服务器请求数据
                initDownLoadData();
            }
        }
    }


    private void initDownLoadData() {
        madapter = new TestPracticeAdapter(TestPracticeAcivity.this, totalList, saveErrorList, recyclerview, dijige);
        recyclerview.setAdapter(madapter);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        Call<TestPracticeBean> testContentData = aClass.getTestPracticeDataNew(quesLibId, token);
        testContentData.enqueue(new Callback<TestPracticeBean>() {
            @Override
            public void onResponse(Call<TestPracticeBean> call, Response<TestPracticeBean> response) {
                final TestPracticeBean body = response.body();
                if (response != null & body != null) {
                    if (body.getErrMsg() == null) {
                        String json = new Gson().toJson(body);
                        Log.e("responseresponse", json);
                        //把题库id和请求到的json字符串存进去
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                List<QuesListBean2> dataList = body.getData().getQuesList();
                                try {
                                    id = saveDb.queryAll().size();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    id = 0;
                                }
                                if (saveUserErrorDbBeen.size() < 1) {
                                    for (int i = 0; i < dataList.size(); i++) {
                                        QuesListBean2 item = dataList.get(i);
                                        QuesListBean saveUserErrorDbBean = new QuesListBean(id + 1, item.getBiaoJi(), quesLibId, item.getDiffType(), item.getId(),
                                                item.getParsing(), item.getContent(), item.getQuesType(), JSON.toJSONString(item.getOpts()), JSON.toJSONString(item.getChildren()));
                                        saveDb.insert(saveUserErrorDbBean);
                                        id++;
                                    }
                                }
                            }
                        }).start();

                        //请求数据
                        List<QuesListBean2> quesData = body.getData().getQuesList();
                        size = quesData.size();
                        position = madapter.getWeiZhi();
                        dijige.setText((position + 1) + "/" + size);
                        //查看当前错题集合的弹出框
                        for (int i = 0; i < size; i++) {
                            int quesType = quesData.get(i).getQuesType();
                            SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean = new SaveUserErrorPrcticeBean.ErrorQuesBean();
                            //保存用户错题记录的
                            saveErrorList.add(errorQuesBean);
                            saveErrorList.get(i).setIsRight(-1);
                            saveErrorList.get(i).setQuestionType(quesType);
                        }
                        totalList.addAll(quesData);
                        madapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<TestPracticeBean> call, Throwable t) {
                Toast.makeText(TestPracticeAcivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        paperName_tv = (TextView) findViewById(R.id.paperName_tv);

        quesLibId = getIntent().getIntExtra("quesLibId", 0);
        quesLibPackageId = getIntent().getIntExtra("packageId", 0);
        userQuesLibId = getIntent().getIntExtra("userLibId", 0);
        String paperName = getIntent().getStringExtra("paperName");
        if (!TextUtils.isEmpty(paperName)) {
            paperName_tv.setText(paperName);
        }
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
            SharedPreferences sharedPreferences = getSharedPreferences("tiku", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("position", position);
            Log.i("dfdf", "test====" + position);
            editor.commit();
        }

    }

    //点击当前页的图标看当前做题进度
    public void currentProgress(View view) {

        PracticeStickyGridAdapter stickyGridAdapter = null;
        int size = saveErrorList.size();

        int newDoNum = 0;
        for (int i = 0; i < size; i++) {
            String userAnswerIds = saveErrorList.get(i).getUserAnswerIds();
            if (!TextUtils.isEmpty(userAnswerIds)) {
                newDoNum++;
            }
        }
        final View popView = LayoutInflater.from(TestPracticeAcivity.this).inflate(R.layout.poplayout_test, null, false);
        ImageView dialogDissmiss_iv = popView.findViewById(R.id.dialog_dismiass_iv);
        StickyGridHeadersGridView gridView = (StickyGridHeadersGridView) popView.findViewById(R.id.asset_grid);
        TextView haveDone_tv = (TextView) popView.findViewById(R.id.haveDone_pop_tv);
        TextView noDone_tv = (TextView) popView.findViewById(R.id.noDone_pop_tv);
        haveDone_tv.setText("已作答" + newDoNum);
        noDone_tv.setText("未答" + (size - newDoNum));
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
        p.height = (int) (d.getHeight() * 0.85); // 高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
        p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.0f; // 设置黑暗度
        popupWindow = new PopupWindow(popView, p.width, p.height, true);
        popupWindow.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        // 配合 点击外部区域消失使用 否则 没有效果
        ColorDrawable colorDrawable = new ColorDrawable(0x3cffffff);
        dialogDissmiss_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //setBackgroundAlpha(TestPracticeAcivity.this, (float) 1.0);
            }
        });
        popupWindow.setBackgroundDrawable(colorDrawable);
        //展示
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        List<TestResultBean.ExamDetailsBean> oneList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> twoList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> threeList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> fourList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> fiveList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> sixList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean = saveErrorList.get(i);
            int questionType = errorQuesBean.getQuestionType();
            int isRight = errorQuesBean.getIsRight();
            if (questionType == 1) {//单选
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                if (isRight == 2) {
                    examDetailsBean1.setIsRight(2);
                } else if (isRight == 1) {
                    examDetailsBean1.setIsRight(1);
                }
                examDetailsBean1.setLocation(j);
                oneList.add(examDetailsBean1);

            } else if (questionType == 2) {//多选
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (isRight == 2) {
                    examDetailsBean1.setIsRight(2);
                } else if (isRight == 1) {
                    examDetailsBean1.setIsRight(1);
                }
                twoList.add(examDetailsBean1);

            } else if (questionType == 3) {//判断
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (isRight == 2) {
                    examDetailsBean1.setIsRight(2);
                } else if (isRight == 1) {
                    examDetailsBean1.setIsRight(1);
                }
                threeList.add(examDetailsBean1);

            } else if (questionType == 4) { //主观案例
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (isRight == 2) {
                    examDetailsBean1.setIsRight(2);
                } else if (isRight == 1) {
                    examDetailsBean1.setIsRight(1);
                }
                fourList.add(examDetailsBean1);
            } else if (questionType == 5) { //客观案例
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (isRight == 2) {
                    examDetailsBean1.setIsRight(2);
                } else if (isRight == 1) {
                    examDetailsBean1.setIsRight(1);
                }
                fiveList.add(examDetailsBean1);
            } else if (questionType == 6) { //简答题
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (isRight == 2) {
                    examDetailsBean1.setIsRight(2);
                } else if (isRight == 1) {
                    examDetailsBean1.setIsRight(1);
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
        Log.e("sizeSelect", size1 + ",," + size2 + ",," + size3 + ",," + size4 + ",," + size5 + ",," + size6);
        if (size1 > 0) {
            for (int i = 0; i < size1; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = oneList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                Log.e("isRight", isRight + "单选,11111");
                item1 = new GridItem(location + "," + isRight, i + "", 0, "单选", isRight);
                mGirdList.add(item1);
            }
        }
        if (size2 > 0) {
            for (int i = 0; i < size2; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = twoList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item2 = new GridItem(location + "," + isRight, i + "", 1, "多选", isRight);
                mGirdList.add(item2);
                Log.e("isRight", isRight + "多选,11111");
            }
        }
        if (size3 > 0) {
            for (int i = 0; i < size3; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = threeList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item3 = new GridItem(location + "," + isRight, i + "", 2, "判断", isRight);
                Log.e("isRight", isRight + "判断,11111");
                mGirdList.add(item3);
            }
        }
        if (size4 > 0) {
            for (int i = 0; i < size4; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = fourList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item4 = new GridItem(location + "," + isRight, i + "", 3, "主观案例", isRight);
                Log.e("isRight", isRight + "判断,11111");
                mGirdList.add(item4);
            }
        }
        if (size5 > 0) {
            for (int i = 0; i < size5; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = fiveList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item5 = new GridItem(location + "," + isRight, i + "", 4, "客观案例", isRight);
                Log.e("isRight", isRight + "判断,11111");
                mGirdList.add(item5);
            }
        }
        if (size6 > 0) {
            for (int i = 0; i < size6; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = sixList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item6 = new GridItem(location + "," + isRight, i + "", 5, "简答题", isRight);
                Log.e("isRight", isRight + "判断,11111");
                mGirdList.add(item6);
            }
        }
        stickyGridAdapter = new PracticeStickyGridAdapter(TestPracticeAcivity.this, mGirdList, recyclerview, popupWindow, madapter, size, dijige);
        gridView.setAdapter(stickyGridAdapter);

//setBackgroundAlpha(this, (float) 0.5);


    }


    /**
     * 设置页面的透明度
     *
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
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
        //拿到adapter中的保存错误练习题的集合
        List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList1 = madapter.getSaveErrorList1();
        if (saveErrorList1 != null) {
            int size = saveErrorList1.size();
            Log.e("doneSize", size + "");
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
                jsonObject.put("quesLibPackageId", quesLibPackageId);
                jsonObject.put("userQuesLibId", userQuesLibId);
                jsonObject.put("errorQues", array);
                string = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PracticeInterface aClass = RetrofitUtils.getInstance().createClass(PracticeInterface.class);
            Log.e("saveErrorPrctice", string);

            aClass.saveUserData0(string, token).enqueue(new Callback<SuccessBean>() {
                @Override
                public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                    if (response != null && response.body() != null) {
                        Object errMsg = response.body().getErrMsg();
                        if (errMsg == null) {
                            boolean data = response.body().getData();
                            if (data == true) {
                                // Toast.makeText(TestPracticeAcivity.this, "保存错题成功", Toast.LENGTH_SHORT).show();
                            } else {
                                // Toast.makeText(TestPracticeAcivity.this, "保存错题失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SuccessBean> call, Throwable t) {
                    // Toast.makeText(TestPracticeAcivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
