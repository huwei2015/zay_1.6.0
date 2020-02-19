package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.administrator.zahbzayxy.adapters.LookErrorPopAdater;
import com.example.administrator.zahbzayxy.adapters.PMyCuotiAdapter;
import com.example.administrator.zahbzayxy.beans.GridItem;
import com.example.administrator.zahbzayxy.beans.PrictaceErrorBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.event.ErrorDataEvent;
import com.example.administrator.zahbzayxy.interfacecommit.PracticeInterface;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.myinterface.MyInterface;
import com.example.administrator.zahbzayxy.myinterface.UpPx;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;
import com.example.administrator.zahbzayxy.stickheadgv.StickyGridHeadersGridView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PLookCuoTiActivity extends BaseActivity {
    private List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> list = new ArrayList<>();
    private int quesLibId;
    private String token;
    private PMyCuotiAdapter adapter;
    private MyRecyclerView recyclerview;
    private int position = 0;
    private int size;
    TextView dijige;
    LinearLayoutManager linearLayoutManager;
    ImageView delete_iv, back_iv;
    int getQuesLibId, getPosition;
    private PopupWindow popUpWindow1;
    private int getSize;
    private int userLibId;
    private int packageId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plook_cuo_ti);
        initView();
        initRecyClerView();
        downLoadData();
    }

    private void downLoadData() {
        adapter = new PMyCuotiAdapter(new MyInterface.ErrorOnClickedListenner() {
            @Override
            public void onMyItemClickedListenner(int questionId, final int postion) {
                getQuesLibId = questionId;
                getPosition = postion;
                Log.e("getPostion", getPosition + "");
                Log.e("questionIdAndPostion", getQuesLibId + ",,," + getPosition);
                delete_iv.setEnabled(true);
                delete_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_iv.setClickable(false);
                        PracticeInterface aClass = RetrofitUtils.getInstance().createClass(PracticeInterface.class);
                        aClass.deleteErrorDeatilData(quesLibId, getQuesLibId, token).enqueue(new Callback<SuccessBean>() {
                            @Override
                            public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                                SuccessBean body = response.body();
                                if (body != null) {
                                    if (body.getErrMsg() == null) {
                                        position = adapter.getWeiZhi();
                                        delete_iv.setEnabled(false);
                                        if (list.size() > 0) {
                                            if (position + 1 < list.size()) {
                                                list.remove(position);
                                                recyclerview.scrollToPosition(position);
                                                dijige.setText((position + 1) + "/" + list.size());
                                                adapter.notifyDataSetChanged();
                                                Toast.makeText(PLookCuoTiActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                                            } else {
                                                list.remove(position);
                                                recyclerview.scrollToPosition(position - 1);
                                                dijige.setText((position) + "/" + list.size());
                                                adapter.notifyDataSetChanged();
                                            }

                                        } else {
                                            dijige.setText((position) + "/" + list.size());
                                        }
                                        delete_iv.setClickable(true);

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<SuccessBean> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        }, list, PLookCuoTiActivity.this);
        recyclerview.setAdapter(adapter);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestPracticeData1(quesLibId, userLibId, packageId, token).enqueue(new Callback<PrictaceErrorBean>() {
            private int size;

            @Override
            public void onResponse(Call<PrictaceErrorBean> call, Response<PrictaceErrorBean> response) {
                int code = response.code();
                if (code == 200) {
                    PrictaceErrorBean body = response.body();
                    if (body != null) {
                        String code1 = body.getCode();
                        if (code1.equals("00000")) {
                            PrictaceErrorBean.DataEntity data = body.getData();
                            if (data != null) {
                                List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> errorRecords = data.getErrorRecords();
                                list.clear();
                                list.addAll(errorRecords);
                                adapter.notifyDataSetChanged();
                                size = list.size();
                                position = adapter.getWeiZhi();
                                dijige.setText((position + 1) + "/" + size);
                                Log.e("errorListSize", size + ",1111");

                            }
                        } else {
                            Toast.makeText(PLookCuoTiActivity.this, body.getErrMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PLookCuoTiActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PLookCuoTiActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PrictaceErrorBean> call, Throwable t) {
                Toast.makeText(PLookCuoTiActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initView() {
        back_iv = (ImageView) findViewById(R.id.back_lookError_iv);
        delete_iv = (ImageView) findViewById(R.id.myErrorDelete_iv);
        recyclerview = (MyRecyclerView) findViewById(R.id.pMyCuoti_rcy);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        recyclerview.setLayoutManager(linearLayoutManager);
        quesLibId = getIntent().getIntExtra("quesLibId", 0);
        userLibId = getIntent().getIntExtra("userLibId", 0);
        packageId = getIntent().getIntExtra("packageId", 0);
        Log.e("qusLibsId", String.valueOf(quesLibId) + ",22222," + userLibId + "," + packageId);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        dijige = (TextView) findViewById(R.id.dijige);
        token = tokenDb.getString("token", "");

        // downLoadData();

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initRecyClerView() {
        recyclerview.setUp(new UpPx() {
            @Override
            public void upPx(int tag) {
                position = adapter.getWeiZhi();
                if (tag == 1) {
                    if (position > 0) {
                        //左
                        position = position - 1;
                        recyclerview.scrollToPosition(position);
                        adapter.notifyDataSetChanged();
                        size = list.size();
                        dijige.setText((position + 1) + "/" + size);
                    }
                } else if (tag == 2) {
                    if (position < list.size() - 1) {
                        position = position + 1;
                        recyclerview.scrollToPosition(position);
                        adapter.notifyDataSetChanged();
                        size = list.size();
                        dijige.setText((position + 1) + "/" + size);
                    }
                }
            }
        });
    }

    //左按钮
    public void leftButton(View view) {
        position = adapter.getWeiZhi();
        if (size >= 0) {
            if (position > 0) {
                //左
                position = position - 1;
                recyclerview.scrollToPosition(position);
                adapter.notifyDataSetChanged();
                dijige.setText((position + 1) + "/" + list.size());
            }
        }
    }

    //右按钮
    public void rightButton(View view) {
        position = adapter.getWeiZhi();
        if (size >= 0) {
            if (position < list.size() - 1) {
                position = position + 1;
                recyclerview.scrollToPosition(position);
                adapter.notifyDataSetChanged();
                dijige.setText((position + 1) + "/" + list.size());
            }
        }
    }


    //查看当前进度
    public void currentProgressClick(View view) {
        LookErrorPopAdater stickyGridAdapter = null;
        final View popView = LayoutInflater.from(PLookCuoTiActivity.this).inflate(R.layout.poplayout_test, null, false);
        ImageView dialogDissmiss_iv = (ImageView) popView.findViewById(R.id.dialog_dismiass_iv);
        StickyGridHeadersGridView gridView = (StickyGridHeadersGridView) popView.findViewById(R.id.asset_grid);
        TextView haveDone_tv = (TextView) popView.findViewById(R.id.haveDone_pop_tv);
        TextView noDone_tv = (TextView) popView.findViewById(R.id.noDone_pop_tv);
        haveDone_tv.setVisibility(View.GONE);
        noDone_tv.setVisibility(View.GONE);


        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
        p.height = (int) (d.getHeight() * 0.85); // 高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
        p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.0f; // 设置黑暗度
        popUpWindow1 = new PopupWindow(popView, p.width, p.height, true);
        popUpWindow1.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        popUpWindow1.setOutsideTouchable(true);
        popUpWindow1.setAnimationStyle(R.style.take_photo_anim);
        // 配合 点击外部区域消失使用 否则 没有效果
        ColorDrawable colorDrawable = new ColorDrawable(0x3cffffff);
        dialogDissmiss_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow1.dismiss();
            }
        });
        popUpWindow1.setBackgroundDrawable(colorDrawable);
        //展示
        popUpWindow1.showAtLocation(popView, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> oneList = new ArrayList<>();
        List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> twoList = new ArrayList<>();
        List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> threeList = new ArrayList<>();
        List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> list = adapter.getList();
        getSize = list.size();
        for (int i = 0; i < getSize; i++) {
            PrictaceErrorBean.DataEntity.ErrorRecordsEntity errorRecordsEntity = this.list.get(i);
            int questionType = errorRecordsEntity.getQuesType();
            if (questionType == 1) {//单选
                int j = i + 1;
                PrictaceErrorBean.DataEntity.ErrorRecordsEntity examDetailsBean1 = new PrictaceErrorBean.DataEntity.ErrorRecordsEntity();
                examDetailsBean1.setLocation(j);
                oneList.add(examDetailsBean1);

            } else if (questionType == 2) {//多选
                int j = i + 1;
                PrictaceErrorBean.DataEntity.ErrorRecordsEntity examDetailsBean1 = new PrictaceErrorBean.DataEntity.ErrorRecordsEntity();
                examDetailsBean1.setLocation(j);
                twoList.add(examDetailsBean1);

            } else {//判断
                int j = i + 1;
                PrictaceErrorBean.DataEntity.ErrorRecordsEntity examDetailsBean1 = new PrictaceErrorBean.DataEntity.ErrorRecordsEntity();
                examDetailsBean1.setLocation(j);
                threeList.add(examDetailsBean1);

            }
        }
        List<GridItem> mGirdList = new ArrayList<GridItem>();
        GridItem item1 = null;
        GridItem item2 = null;
        GridItem item3 = null;
        int size1 = oneList.size();
        int size2 = twoList.size();
        int size3 = threeList.size();
        Log.e("errorListSize", size1 + "," + size2 + "," + size3);
        if (size1 > 0) {
            for (int i = 0; i < size1; i++) {
                PrictaceErrorBean.DataEntity.ErrorRecordsEntity examDetailsBean = oneList.get(i);
                int location = examDetailsBean.getLocation();
                item1 = new GridItem(location + "", i + "", 0, "单选", 1);
                mGirdList.add(item1);
            }
        }
        if (size2 > 0) {
            for (int i = 0; i < size2; i++) {
                PrictaceErrorBean.DataEntity.ErrorRecordsEntity examDetailsBean = twoList.get(i);
                int location = examDetailsBean.getLocation();
                item2 = new GridItem(location + "", i + "", 1, "多选", 1);
                mGirdList.add(item2);
            }
        }
        if (size3 > 0) {
            for (int i = 0; i < size3; i++) {
                PrictaceErrorBean.DataEntity.ErrorRecordsEntity examDetailsBean = threeList.get(i);
                int location = examDetailsBean.getLocation();
                item3 = new GridItem(location + "", i + "", 2, "判断", 1);
                mGirdList.add(item3);
            }
        }
        stickyGridAdapter = new LookErrorPopAdater(mGirdList, PLookCuoTiActivity.this, recyclerview, popUpWindow1, adapter, this.getSize, dijige);
        gridView.setAdapter(stickyGridAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在此处把当前错题个数传到上一级的adapter中
        EventBus.getDefault().post(new ErrorDataEvent(list.size()));
        if (popUpWindow1 != null) {
            if (popUpWindow1.isShowing()) {
                popUpWindow1.dismiss();
            }
        }
    }

}
