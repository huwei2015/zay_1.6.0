package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PersonTiKuListBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMyTikuActivity extends BaseActivity {
    Unbinder mUnBinder;
    private ListView listView;
    private NewTestAdapter myAdapter;
    private PopupWindow popupWindow;
    @BindView(R.id.myNewTest_sp_tv)
    TextView myNewTest_sp_tv;
    @BindView(R.id.myNewTest_sp_ll)
    RelativeLayout myNewTest_sp_ll;
    @BindView(R.id.newMyTestType_tv)
    TextView newMyTestType_tv;
    @BindView(R.id.myNewTestRemainDays_tv)
    TextView myNewTestRemainDays_tv;
    @BindView(R.id.myNewTestRemainNums_tv)
    TextView myNewTestRemainNums_tv;
    @BindView(R.id.pMyRenZhengBack_iv)
    ImageView imageView_back;
    private String token;
    List<PersonTiKuListBean.DataEntity.QuesLibsEntity> totalList = new ArrayList<>();
    private int quesLibId;
    private int userLibId;
    private int packageId;
    private String quesLibName;
    int examNum;//考试次数
    int remainingTime;//剩余时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_my_tiku);
        mUnBinder = ButterKnife.bind(this);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.newMyTiku_sp_iv, R.id.newMyTest_prictice_ll, R.id.newMyTest_test_ll, R.id.newMyError_ll, R.id.newMyTestSearch_ll})
    public void OnClick(View view) {
        if (totalList.size() <= 0) {
            Toast.makeText(NewMyTikuActivity.this, "暂无题库", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.newMyTiku_sp_iv:
                showPopupWindow();
                break;
            //顺序练习
            case R.id.newMyTest_prictice_ll:
                if (examNum > 0 && remainingTime > 0) {
                    Intent intentPractice = new Intent(this, TestPracticeAcivity.class);
                    Bundle bundlePractice = new Bundle();
                    bundlePractice.putInt("quesLibId", quesLibId);
                    bundlePractice.putString("paperName", quesLibName);
                    bundlePractice.putInt("packageId", packageId);
                    bundlePractice.putInt("userLibId",userLibId);
                    Log.e("aaaaaaaaaquslibslid", quesLibId + "");
                    intentPractice.putExtras(bundlePractice);
                    startActivity(intentPractice);
                } else {//弹框提示购买题库
                    initPopUpWindow1();
                }
                break;
            //模拟考试
            case R.id.newMyTest_test_ll:
                if (examNum > 0 && remainingTime > 0) {
                    Intent intent = new Intent(NewMyTikuActivity.this, TestContentActivity1.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("quesLibId", quesLibId);
                    bundle.putInt("userLibId", userLibId);
                    bundle.putInt("examType", 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {//弹框提示购买题库
                    initPopUpWindow1();
                }
                break;
            //我的错题
            case R.id.newMyError_ll:
                if (examNum > 0 && remainingTime > 0) {
                    Intent intent1 = new Intent(NewMyTikuActivity.this, PLookCuoTiActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("quesLibId", quesLibId);
                    bundle1.putInt("userLibId", userLibId);
                    bundle1.putInt("packageId", packageId);
                    Log.e("qusLibsId", String.valueOf(quesLibId) + ",1111," + userLibId + "," + packageId);
                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                } else {
                    //弹框提示购买题库
                    initPopUpWindow1();
                }
                break;

            //搜题
            case R.id.newMyTestSearch_ll:
                if (examNum > 0 && remainingTime > 0) {
                    Intent searchIntent = new Intent(NewMyTikuActivity.this, SearchTestActivity.class);
                    Bundle searchBundle = new Bundle();
                    searchBundle.putInt("quesLibId", quesLibId);
                    searchIntent.putExtras(searchBundle);
                    startActivity(searchIntent);
                } else {
                    //弹框提示购买题库
                    initPopUpWindow1();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        initData();
    }

    /**
     * 初始化显示数据
     */
    private void initData() {
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyTiKuData(1, 20, token).enqueue(new Callback<PersonTiKuListBean>() {
            @Override
            public void onResponse(Call<PersonTiKuListBean> call, Response<PersonTiKuListBean> response) {
                if (response != null) {
                    PersonTiKuListBean body = response.body();
                    if (body != null) {
                        String errMsg = body.getErrMsg();
                        if (!TextUtils.isEmpty(errMsg)) {
                            Toast.makeText(NewMyTikuActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                        } else {
                            PersonTiKuListBean.DataEntity data = body.getData();
                            List<PersonTiKuListBean.DataEntity.QuesLibsEntity> quesLibs = data.getQuesLibs();
                            totalList.clear();
                            totalList.addAll(quesLibs);
                            if (totalList.size() > 0) {
                                PersonTiKuListBean.DataEntity.QuesLibsEntity quesLibsEntity = totalList.get(0);
                                upDateTest(quesLibsEntity);
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonTiKuListBean> call, Throwable t) {

            }
        });
    }

    private void upDateTest(PersonTiKuListBean.DataEntity.QuesLibsEntity quesLibsEntity) {
        myNewTest_sp_tv.setText(quesLibsEntity.getQuesLibName() + "");
        String packageName = quesLibsEntity.getPackageName();
        newMyTestType_tv.setText(packageName + "");
        remainingTime = quesLibsEntity.getRemainingTime();
        myNewTestRemainDays_tv.setText(remainingTime + "天");
//        int parsing = quesLibsEntity.isParsing();
//        if (parsing == 1) {
        examNum = quesLibsEntity.getExamNum();
        myNewTestRemainNums_tv.setText("剩余" + examNum + "次");
//        }
//        else {
//            myNewTestRemainNums_tv.setText("不限次数");
//        }
        quesLibId = quesLibsEntity.getQuesLibId();
        userLibId = quesLibsEntity.getUserLibId();
        packageId = quesLibsEntity.getPackageId();
        quesLibName = quesLibsEntity.getQuesLibName();


    }


    /**
     * 展示popwindow
     */
    protected void showPopupWindow() {
        // 创建好listview，并设置适配器
        createListView();
        popupWindow = new PopupWindow(listView, WindowManager.LayoutParams.MATCH_PARENT, 400);
        // 单击别处可使popwindow关闭
        popupWindow.setOutsideTouchable(true);
        // 获取焦点
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(myNewTest_sp_tv, 0, -5);
    }

    /**
     * 创建listview
     */
    private void createListView() {
        // 创建ListView
        listView = new ListView(this);
        listView.setBackgroundColor(getResources().getColor(R.color.white));
        listView.setDivider(null);

        // 填充适配器
        myAdapter = new NewTestAdapter();
        listView.setAdapter(myAdapter);

        // 设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PersonTiKuListBean.DataEntity.QuesLibsEntity quesLibsEntity = totalList.get(position);
                upDateTest(quesLibsEntity);
                myNewTest_sp_tv.setText(quesLibsEntity.getQuesLibName() + "");
                popupWindow.dismiss();
            }
        });
    }

    //购买题库点击事件
    public void addTikuOnClick(View view) {
        Intent intent = new Intent(NewMyTikuActivity.this, MainActivity.class);
        intent.putExtra("moreTiKu", "addTikuOnClick");
        startActivity(intent);
        finish();
    }

    private class ViewHolder {
        public TextView des;
    }


    private class NewTestAdapter extends BaseAdapter {

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // 复用convertView
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_user_message, null);
                vh = new ViewHolder();
                vh.des = (TextView) convertView.findViewById(R.id.tv_des);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            String quesLibName = totalList.get(position).getQuesLibName();
            // 设置条目信息
            //String str = getItem(position);
            vh.des.setText(quesLibName + "");
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return totalList.get(position);
        }

        @Override
        public int getCount() {
            return totalList.size();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    private void initPopUpWindow1() {
        View view = View.inflate(NewMyTikuActivity.this, R.layout.test_pop_layout, null);
        TextView cancel = (TextView) view.findViewById(R.id.myquestion_cancel);
        TextView downLoad_tv = (TextView) view.findViewById(R.id.downLoadNow_tv);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        //设置动画,就是style里创建的那个j
        window.setAnimationStyle(R.style.take_photo_anim);
        window.showAsDropDown(view, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        window.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        window.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        cancel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        cancel.getPaint().setAntiAlias(true);//抗锯齿
        downLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去购买,跳到题库详情
                Intent intent = new Intent(NewMyTikuActivity.this, TestDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("quesLibId", quesLibId);
                intent.putExtras(bundle);
                startActivity(intent);
                window.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
    }
}
