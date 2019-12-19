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

public class SetGangWeiActivity extends BaseActivity{
    Unbinder mUnBinder;
    private String token;
    private CategoryAdapter mCustomBaseAdapter;
    @BindView(R.id.gangWei_lv)
    ListView gangWei_lv;
    @BindView(R.id.gW_back_iv)
    ImageView gWBack_iv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gang_wei);
        mUnBinder=ButterKnife.bind(this);
        initView();
        initToken();
        initData();

    }

    private void initData() {
        // 数据
        ArrayList<Category> listData = UserInfoData.getGangWeiData();

        mCustomBaseAdapter = new CategoryAdapter(SetGangWeiActivity.this, listData);

        // 适配器与ListView绑定
       gangWei_lv.setAdapter(mCustomBaseAdapter);

        gangWei_lv.setOnItemClickListener(new ItemClickListener());
    }

    private void initToken() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
    }
    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
           // Toast.makeText(getBaseContext(),  (String)mCustomBaseAdapter.getItem(position),
           //         Toast.LENGTH_SHORT).show();
            Object item = mCustomBaseAdapter.getItem(position);
            String s = item.toString();
            initPost(s);
        }

    }

    private void initPost(final String s) {
        String[] split = s.split(" ");
        String a = split[0];
        final String name = split[1];
        Log.e("postGangWei",a+","+name);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("quarters", a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String stationJson = jsonObject.toString();
        Log.e("postGangWei",a+","+name+","+stationJson);
        Map<String, Object> editMessage = new HashMap<>();
        editMessage.put("token", token);
        editMessage.put("updateInfo", stationJson);
        editMessage.put("updateType", 5);
        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
            @Override
            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                UserInfoResetBean body = response.body();
                if (response != null & body != null) {
                    boolean data = body.isData();
                    Object errMsg = body.getErrMsg();
                    if (errMsg==null) {
                        if (data == true) {
                            Intent intent = new Intent();
                            intent.putExtra("gangWei", name);
                            setResult(RESULT_OK, intent);
                            Toast.makeText(SetGangWeiActivity.this, "修改岗位成功", Toast.LENGTH_LONG).show();
                            SharedPreferences userInfo = getSharedPreferences("userInfoDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = userInfo.edit();
                            edit.putString("gangWei", name);
                            edit.commit();
                            SetGangWeiActivity.this.finish();


                        }
                    } else {
                        Toast.makeText(SetGangWeiActivity.this,errMsg+"" , Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
                Toast.makeText(SetGangWeiActivity.this, "修改失败，网络问题", Toast.LENGTH_LONG).show();
            }
        });



    }


    private void initView() {
        gWBack_iv= (ImageView) findViewById(R.id.gW_back_iv);

//        gangWei_RG = (RadioGroup) findViewById(R.id.gangWei_RG);
//        fZR_rb= (RadioButton) findViewById(R.id.fZR_rb);
//        aGRY_rb= (RadioButton) findViewById(R.id.aGRY_rb);
//        tZZY_rb= (RadioButton) findViewById(R.id.tZZY_rb);
//        normal_rb=(RadioButton)findViewById(R.id.normal_rb);
        gWBack_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        gangWei_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.fZR_rb:
//                        station=0;
//                        break;
//                    case R.id.aGRY_rb:
//                        station=1;
//                        break;
//                    case R.id.tZZY_rb:
//                        station=2;
//                        break;
//                    case R.id.normal_rb:
//                        station=3;
//                        break;
//                }
//                if (station==0){
//                    gangWei_tv=fZR_rb.getText().toString();
//
//
//                }else if (station==1){
//                    gangWei_tv=aGRY_rb.getText().toString();
//
//                }else if (station==2){
//                    gangWei_tv=tZZY_rb.getText().toString();
//                }
//                else if (station==2){
//                    gangWei_tv=normal_rb.getText().toString();
//                }
//
//            }
//
//        });
//
//
//    }
//
//    public void gWQDOnclick(View view) {
//
//
//        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("station",station);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String stationJson = jsonObject.toString();
//        Map<String, Object> editMessage=new HashMap<>();
//        editMessage.put("token",token);
//        editMessage.put("updateInfo",stationJson);
//        editMessage.put("updateType",5);
//        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
//        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
//            @Override
//            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
//                UserInfoResetBean body = response.body();
//                if (response!=null&body!=null){
//                    boolean data = body.isData();
//                    if (data==true){
//                        Intent intent=new Intent();
//                        intent.putExtra("gangWei",gangWei_tv);
//                        setResult(RESULT_OK,intent);
//                        Toast.makeText(SetGangWeiActivity.this,"修改岗位成功",Toast.LENGTH_LONG).show();
//                        SharedPreferences userInfo = getSharedPreferences("userInfoDb", MODE_PRIVATE);
//                        SharedPreferences.Editor edit = userInfo.edit();
//                        edit.putString("gangWei",gangWei_tv);
//                        edit.commit();
//                        SetGangWeiActivity.this.finish();
//
//
//                    }
//                }
//                else {
//                    Toast.makeText(SetGangWeiActivity.this,"修改失败,请稍后重试",Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
//                Toast.makeText(SetGangWeiActivity.this,"修改失败，网络问题",Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder!=null){
            mUnBinder.unbind();
        }
    }
}