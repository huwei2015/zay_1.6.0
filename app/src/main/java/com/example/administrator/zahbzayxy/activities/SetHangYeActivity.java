package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.CategoryAdapter;
import com.example.administrator.zahbzayxy.beans.Category;
import com.example.administrator.zahbzayxy.beans.UserInfoData;
import com.example.administrator.zahbzayxy.beans.UserInfoResetBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetHangYeActivity extends BaseActivity{
    Unbinder mUnBinder;
    String token;
    @BindView(R.id.hangye_lv)
    ListView hangye_lv;
    @BindView(R.id.dW_back_iv)
    ImageView dW_back_iv;
    private CategoryAdapter mCustomBaseAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_dan_wei);
        mUnBinder= ButterKnife.bind(this);
        initBack();
        initToken();
        // 数据
        ArrayList<Category> listData = UserInfoData.getHangYeData();

        mCustomBaseAdapter = new CategoryAdapter(getBaseContext(), listData);

        // 适配器与ListView绑定
        hangye_lv.setAdapter(mCustomBaseAdapter);

        hangye_lv.setOnItemClickListener(new ItemClickListener());
    }

    private void initBack() {
        dW_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void initToken() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        Log.e("danWeiToken",token);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder!=null){
            mUnBinder.unbind();
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Object item = mCustomBaseAdapter.getItem(position);
            String s = item.toString();
            initPost(s);
        }

    }

    private void initPost(String s) {
        String[] split = s.split(" ");
        String a = split[0];
        final String name = split[1];
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        JSONObject mObject=new JSONObject();
        try {
            mObject.put("industry",a);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonDanWei=mObject.toString();
        Map<String, Object> editMessage=new HashMap<>();
        editMessage.put("token",token);
        editMessage.put("updateInfo",jsonDanWei);
        editMessage.put("updateType",8);
        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
            @Override
            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                UserInfoResetBean body = response.body();
                if (response!=null&&body!=null){
                    Object errMsg = body.getErrMsg();
                    if (errMsg==null) {
                        boolean data = body.isData();
                        if (data == true) {
                            Toast.makeText(SetHangYeActivity.this, "修改行业成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.putExtra("danWei", name);
                            setResult(RESULT_OK, intent);
                            SharedPreferences userInfo = getSharedPreferences("userInfoDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = userInfo.edit();
                            edit.putString("danWei", name);
                            edit.commit();
                            SetHangYeActivity.this.finish();
                        }
                    }else {
                        Toast.makeText(SetHangYeActivity.this,""+errMsg,Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
                Toast.makeText(SetHangYeActivity.this,"网络原因，请稍后重试",Toast.LENGTH_LONG).show();
            }
        });



    }


    /**
     * 创建测试数据
     */
//    private ArrayList<Category> getData() {
//        ArrayList<Category> listData = new ArrayList<Category>();
//        Category categoryOne = new Category("高危行业");
//        categoryOne.addItem("A.矿山");
//        categoryOne.addItem("B.金属冶炼");
//        categoryOne.addItem("C.建筑施工");
//        categoryOne.addItem("D.道路运输单位和危险物品生产单位");
//        categoryOne.addItem("E.危险物品的经营单位");
//        categoryOne.addItem("F.危险物品储存单位");
//
//        Category categoryTwo = new Category("非高危行业");
//        categoryTwo.addItem("G.农、林、牧、渔业");
//        categoryTwo.addItem("H.采矿业");
//        categoryTwo.addItem("J.电力、热力、燃气及水生产和供应业");
//        categoryTwo.addItem("K.建筑业");
//        categoryTwo.addItem("L.批发和零售业");
//        categoryTwo.addItem("M.交通运输、仓储和邮政业");
//        categoryTwo.addItem("N.住宿和餐饮业");
//        categoryTwo.addItem("O.信息传输、软件和信息技术服务业");
//        categoryTwo.addItem("P.金融业");
//        categoryTwo.addItem("Q.房地产业");
//        categoryTwo.addItem("R.租赁和商务服务业");
//        categoryTwo.addItem("S.科学研究和技术服务业");
//        categoryTwo.addItem("T.水利、环境和公共设施管理业");
//        categoryTwo.addItem("U.居民服务、修理和其他服务业");
//        categoryTwo.addItem("V.教育");
//        categoryTwo.addItem("W.卫生和社会工作");
//        categoryTwo.addItem("X.文化、体育和娱乐业");
//        categoryTwo.addItem("Y.公共管理、社会保障和社会组织");
//        categoryTwo.addItem("Z.国际组织");
//        listData.add(categoryOne);
//        listData.add(categoryTwo);
//        return listData;
//    }

}

//    private void initView() {
//        dWBack_iv= (ImageView) findViewById(R.id.dW_back_iv);
//        dWBack_iv.setOnClickListener(this);
//        dWQueDing_bt= (Button) findViewById(R.id.qDDanWei_bt);
//        dWQueDing_bt.setOnClickListener(this);
//        danWei_et= (EditText) findViewById(R.id.danWei_et);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.dW_back_iv:
//                finish();
//                break;
//            case R.id.qDDanWei_bt:
//                setDanWei();
//                break;
//        }
//    }
//
//    private void setDanWei() {
//        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
//        JSONObject mObject=new JSONObject();
//        danWei_tv=danWei_et.getText().toString();
//        try {
//            mObject.put("unit",danWei_tv);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String jsonDanWei=mObject.toString();
//        Map<String, Object> editMessage=new HashMap<>();
//        editMessage.put("token",token);
//        editMessage.put("updateInfo",jsonDanWei);
//        editMessage.put("updateType",6);
//        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
//        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
//            @Override
//            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
//                UserInfoResetBean body = response.body();
//                if (response!=null&&body!=null){
//                    Object errMsg = body.getErrMsg();
//                    if (errMsg==null) {
//                        boolean data = body.isData();
//                        if (data == true) {
//                            Toast.makeText(SetHangYeActivity.this, "修改单位成功", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent();
//                            intent.putExtra("danWei", danWei_tv);
//                            setResult(RESULT_OK, intent);
//                            SharedPreferences userInfo = getSharedPreferences("userInfoDb", MODE_PRIVATE);
//                            SharedPreferences.Editor edit = userInfo.edit();
//                            edit.putString("danWei", danWei_tv);
//                            edit.commit();
//                            SetHangYeActivity.this.finish();
//                        }
//                    }else {
//                        Toast.makeText(SetHangYeActivity.this,""+errMsg,Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
//                Toast.makeText(SetHangYeActivity.this,"网络原因，请稍后重试",Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//    }



