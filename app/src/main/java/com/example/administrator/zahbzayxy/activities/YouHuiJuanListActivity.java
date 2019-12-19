package com.example.administrator.zahbzayxy.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TestSubmitOrderBean;
import com.example.administrator.zahbzayxy.beans.YouHuiJuanListBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouHuiJuanListActivity extends AppCompatActivity {
    @BindView(R.id.orderDetailType_tv)
    TextView orderType_tv;
    @BindView(R.id.yhj_testName_tv)
    TextView yhj_testName_tv;
    @BindView(R.id.yhj_testPrice_tv)
    TextView yhj_testPrice_tv;
    @BindView(R.id.yhjName_click_tv)
    TextView yhjName_click_tv;
    @BindView(R.id.yhj_goPay__tv)
    TextView yhjPay_tv;
    Unbinder mUnbinder;
    private int quesLibPackageId;
    private int quesLibId;
    private String token;
    private PopupWindow pop;
    ListView listView = null;
    private YHJSelectAdapter yhjSelectAdapter;
    private List<YouHuiJuanListBean.DataEntity.CouponListEntity> couponList;
    private Integer couponId= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_juan_list);
        mUnbinder= ButterKnife.bind(this);
        initView();
        downLoadData();
    }

    private void initView() {
        quesLibPackageId = getIntent().getIntExtra("quesLibPackageId", 0);
        quesLibId = getIntent().getIntExtra("quesLibId", 0);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
    }

    private void downLoadData() {
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getYouhuiJuanData(quesLibId,quesLibPackageId,token).enqueue(new Callback<YouHuiJuanListBean>() {
            @Override
            public void onResponse(Call<YouHuiJuanListBean> call, Response<YouHuiJuanListBean> response) {
                if (response!=null){
                    YouHuiJuanListBean body = response.body();
                    if (body!=null){
                        YouHuiJuanListBean.DataEntity data = body.getData();
                        String quesLibName = data.getQuesLibName();
                        double quesLibPackagePrice = data.getQuesLibPackagePrice();
                        String quesLibPackageName = data.getQuesLibPackageName();
                        if (!TextUtils.isEmpty(quesLibPackageName)){
                            orderType_tv.setText(quesLibPackageName);
                        }if (!TextUtils.isEmpty(quesLibName)){
                            yhj_testName_tv.setText(quesLibName);
                        }
                        yhj_testPrice_tv.setText("￥"+quesLibPackagePrice+"元");

                        couponList = data.getCouponList();
                        if (couponList!=null){
                            int size = couponList.size();
                            if (size>0){
                                yhjName_click_tv.setEnabled(true);
                                String couponName = couponList.get(0).getCouponName();
                                couponId=couponList.get(0).getId();
                                if (!TextUtils.isEmpty(couponName)){
                                    yhjName_click_tv.setText(couponName);
                                }
                            }else {
                                yhjName_click_tv.setText("暂无优惠卷");
                                yhjName_click_tv.setEnabled(false);
                            }

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<YouHuiJuanListBean> call, Throwable t) {

            }
        });
    }




    public void youHuiJuanOnClick(View view) {
        if(pop == null){
            listView = new ListView(this);
            listView.setDividerHeight(1);
            listView.setBackgroundResource(R.drawable.bg_gray_rectangle);
            listView.setCacheColorHint(0x00000000);
            yhjSelectAdapter = new YHJSelectAdapter(couponList,YouHuiJuanListActivity.this);
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
            WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
            p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的1.0
            p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
            p.alpha = 1.0f; // 设置本身透明度
            p.dimAmount = 0.0f; // 设置黑暗度
            pop = new PopupWindow(listView,p.width , p.height, true);
            pop.setTouchable(true);
            // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
            pop.setOutsideTouchable(true);
            pop.setAnimationStyle(R.style.take_photo_anim);
        }
        listView.setAdapter(yhjSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.RED));
        pop.showAsDropDown(yhjName_click_tv, 0, 0);


    }



    public class YHJSelectAdapter extends BaseAdapter {
        List<YouHuiJuanListBean.DataEntity.CouponListEntity>list;
        Context context;
        LayoutInflater inflater;

        public YHJSelectAdapter(List<YouHuiJuanListBean.DataEntity.CouponListEntity> list, Context context) {
            this.list = list;
            this.context = context;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size()>0?list.size():0;
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
           ViewHolder holder;
            if(convertView==null){
                holder = new YHJSelectAdapter.ViewHolder();
                convertView = inflater.inflate(R.layout.item, null);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);

            }else{
                holder = (YHJSelectAdapter.ViewHolder) convertView.getTag();
            }
            final YouHuiJuanListBean.DataEntity.CouponListEntity couponListEntity = list.get(position);
            final String couponName = couponListEntity.getCouponName();
            if (!TextUtils.isEmpty(couponName)) {
                holder.tv_name.setText(couponName);
            }
            holder.tv_name.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    yhjName_click_tv.setText(couponName);
                    if (pop.isShowing()){
                        pop.dismiss();
                    }
                    couponId = couponListEntity.getId();


                }
            });
            return convertView;
        }
        class ViewHolder{
            TextView tv_name;
        }
    }



    @OnClick({R.id.yhj_cancleOrder_tv,R.id.yhj_goPay__tv})
    public void onClick(View view){
         switch (view.getId()){
             case R.id.yhj_cancleOrder_tv:
                 finish();
                 break;
             case R.id.yhj_goPay__tv:
                 initYhjCommitOrder();

                 break;
         }
        }

    @Override
    protected void onResume() {
        super.onResume();
       // initYhjCommitOrder();
    }

    private void initYhjCommitOrder() {
        yhjPay_tv.setEnabled(false);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestSubmitOrderData(couponId,quesLibId,quesLibPackageId,token).enqueue(new Callback<TestSubmitOrderBean>() {
            @Override
            public void onResponse(Call<TestSubmitOrderBean> call, Response<TestSubmitOrderBean> response) {
                if (response!=null){
                    if (response.code()==200){
                        TestSubmitOrderBean body = response.body();
                        if (body!=null){
                            String code = body.getCode();
                            if (code.equals("00000")){
                                TestSubmitOrderBean.DataBean data = body.getData();
                                if (data!=null){
                                    String orderNumber = data.getOrderNumber();
                                    float price = data.getPrice();
                                    Intent intent = new Intent(YouHuiJuanListActivity.this, PayUiActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("testPrice", String.valueOf(price));
                                    bundle.putInt("quesLibId",quesLibId);
                                    bundle.putString("orderNumber", orderNumber);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                    yhjPay_tv.setEnabled(true);
                                }
                            }else {
                                Object errMsg = body.getErrMsg();
                                if (errMsg!=null){
                                    Toast.makeText(YouHuiJuanListActivity.this, errMsg.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TestSubmitOrderBean> call, Throwable t) {

            }
        });

    }

    public void myOrderDetailOnClick(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
