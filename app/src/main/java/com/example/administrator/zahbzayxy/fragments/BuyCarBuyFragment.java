package com.example.administrator.zahbzayxy.fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.PayUiActivity;
import com.example.administrator.zahbzayxy.adapters.BuyCarListAdapter;
import com.example.administrator.zahbzayxy.beans.BuyCarListBean;
import com.example.administrator.zahbzayxy.beans.LessonPriceBean;
import com.example.administrator.zahbzayxy.beans.TestSubmitOrderBean;
import com.example.administrator.zahbzayxy.interfacecommit.BuyCarGroupInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * A simple {@link Fragment} subclass.
 */
public class BuyCarBuyFragment extends Fragment {
    private Context context;
    private View view;
    private PullToRefreshListView buyCar_lv;
    private List<BuyCarListBean.DataBean.CoursesBean>coursesBeanList=new ArrayList<>();
    private String token;
    private BuyCarListAdapter adapter;
    private String encodeToken;
    int pager=1;
    private Button jieSuan_bt;
    String price;
    int courseType;
    public BuyCarBuyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy, container, false);
        initView();
        getSP();
        initPullToRefreshListView();
        initJiSuan();
        return view;
    }
//点击结算按钮时
    private void initJiSuan() {
        jieSuan_bt.setOnClickListener(new View.OnClickListener() {
            JSONArray  jsonArray=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            Integer id;
            private List<BuyCarListBean.DataBean.CoursesBean.SubCoursesBean> subCoursesList;
            @Override
            public void onClick(View v) {
                try {
                    int size = coursesBeanList.size();
                    for (int i = 0; i < size; i++) {
                        boolean ishasChild = false;
                        int tag = coursesBeanList.get(i).getTag();
                        //if (tag==1){
                           // jsonObject = new JSONObject();
                            id= coursesBeanList.get(i).getId();
                        //主课程id的集合
                        subCoursesList = coursesBeanList.get(i).getSubCourses();
                        String zIdString1 = "";
                        for (int k = 0; k < subCoursesList.size(); k++) {
                            if (subCoursesList.get(k).getTag1() == 1) {
                                ishasChild = true;
                                int subId = subCoursesList.get(k).getId();
                                zIdString1 += subId + ",";
                            }
                        }
                        if (ishasChild) {
                            jsonObject = new JSONObject();
                            String substring = zIdString1.substring(0, zIdString1.length() - 1);
                            jsonObject.put("mainCourseId", id);
                            jsonObject.put("subCourseId", substring);
                            jsonObject.put("courseType",courseType);//新添加的字段 //TODO
                            jsonArray.put(jsonObject);
                        }
                   // }
                    }
                   // Log.e("lessonJsonArray", jsonArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Map<String, String> buyCarPrice = new HashMap<>();
                buyCarPrice.put("List",jsonArray.toString());
                buyCarPrice.put("token", token);
                BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
                if (jsonArray != null) {
                    Log.i("ynf","ynf======"+buyCarPrice.toString());
                    aClass.buyCarGetPriceData(buyCarPrice).enqueue(new Callback<LessonPriceBean>() {
                        @Override
                        public void onResponse(Call<LessonPriceBean> call, Response<LessonPriceBean> response) {
                            int code = response.code();
                            Log.e("priceCodecode", String.valueOf(code));
                            LessonPriceBean body = response.body();
                            String s = new Gson().toJson(body);
                            Log.e("aaaaaaaaaaaaa", s);
                            if (response != null && body != null) {
                                price = body.getData().getPrice();
                                if (!TextUtils.isEmpty(String.valueOf(price))) {
                                    //提交课程订单
                                    CommitLessonOrder();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<LessonPriceBean> call, Throwable t) {
                            Log.e("pricefailfail", t.getMessage());
                        }
                        private void CommitLessonOrder() {
                            Map<String, String> lessonOrderNum = new HashMap<>();
                            lessonOrderNum.put("List",jsonArray.toString());
                            lessonOrderNum.put("price", price);
                            lessonOrderNum.put("token", token);

                            BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
                            aClass.commitLessonOrderData(lessonOrderNum).enqueue(new Callback<TestSubmitOrderBean>() {
                                private String orderNumber;
                                @Override
                                public void onResponse(Call<TestSubmitOrderBean> call, Response<TestSubmitOrderBean> response) {
                                    if (response != null && response.body() != null) {
                                        TestSubmitOrderBean body = response.body();
                                        String code = body.getCode();
                                        if (code.equals("00023")) {
                                            Toast.makeText(context, "该订单已存在", Toast.LENGTH_SHORT).show();
                                        } else if (code.equals("00014")) {
                                            Toast.makeText(context, "该订单已存在", Toast.LENGTH_SHORT).show();
                                        } else if(code.equals("00025")){
                                            Toast.makeText(context, "用户已购买该课程", Toast.LENGTH_SHORT).show();
                                        }else if (code.equals("99999")){
                                            Toast.makeText(context, "系统繁忙", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (code.equals("00000")){
                                            orderNumber = response.body().getData().getOrderNumber();
                                            {
                                                if (!TextUtils.isEmpty(orderNumber)) {
                                                    Intent intent = new Intent(context, PayUiActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("testPrice", price);
                                                    bundle.putString("orderNumber", orderNumber);
                                                    bundle.putBoolean("isLessonOrder",true);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                }
                                            }
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Call<TestSubmitOrderBean> call, Throwable t) {
                                  //  Log.e("comitOrderTiku", t.getMessage());
                                }
                            });

                        }
                    });
                }
            }
        });
    }
      @Override
    public void onResume() {//此方法执行的是它附属的activity的生命周期方法
        super.onResume();
          initJiSuan();
          Log.e("hidehidehide3","onResume");
        // adapter.notifyDataSetChanged();
    }

    private void initPullToRefreshListView() {
        buyCar_lv.setMode(PullToRefreshBase.Mode.BOTH);
        buyCar_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                coursesBeanList.clear();
                pager=1;
                downLoadData(pager);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                ++pager;
                downLoadData(pager);
            }
        });
        downLoadData(pager);
    }
    //获取购物车列表
    private void downLoadData(int pager) {
        adapter = new BuyCarListAdapter(context, coursesBeanList);
        buyCar_lv.setAdapter(adapter);
        BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
        try {
            encodeToken = URLEncoder.encode(token,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("tokenTestencode",encodeToken);
        Log.e("tokenTestencode",token);
        aClass.buyCarListData(pager,10,token).enqueue(new Callback<BuyCarListBean>() {
          @Override
          public void onResponse(Call<BuyCarListBean> call, Response<BuyCarListBean> response) {
              BuyCarListBean body = response.body();
              String s = new Gson().toJson(body);
              Log.e("bodyaaaaaaaaaaa",s);
                  if (response != null && body != null) {
                      String code = body.getCode();
                      if (code.equals("00003")){
                          Toast.makeText(context,"用户未登录",Toast.LENGTH_SHORT).show();
                          SharedPreferences sp = context.getSharedPreferences("tokenDb",context.MODE_PRIVATE);
                          SharedPreferences.Editor edit = sp.edit();
                          edit.putBoolean("isLogin",false);
                          edit.commit();
                      }else if (code.equals("99999")){
                          Toast.makeText(context,"系统繁忙",Toast.LENGTH_SHORT).show();
                      }else if (dbIsLogin()==false){
                          Toast.makeText(context,"用户未登录", Toast.LENGTH_SHORT).show();
                      }
                      else if (body.getCode().equals("00000")) {
                          List<BuyCarListBean.DataBean.CoursesBean> courses = body.getData().getCourses();
                          if (courses != null) {
                             // coursesBeanList.clear();
                              for (int i =0; i < courses.size(); i++){
                                  courseType = courses.get(i).getCourseType();
                                  Log.i("ynf","ynf==========="+courseType);
                              }
                              coursesBeanList.addAll(courses);
                              adapter.notifyDataSetChanged();
                          }
                      }
                  }
          }
          @Override
          public void onFailure(Call<BuyCarListBean> call, Throwable t) {
             // Toast.makeText(context,"请求服务器失败",Toast.LENGTH_SHORT).show();
          }
      });
        if (buyCar_lv.isRefreshing()){

            buyCar_lv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    buyCar_lv.onRefreshComplete();
                }
            },1000);
        }
    }

    private boolean dbIsLogin() {
        SharedPreferences sharedPreferences =context.getSharedPreferences("tokenDb",context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }
    }

    private void getSP() {
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb",context.MODE_PRIVATE);
        token = tokenDb.getString("token","");
    }

    private void initView() {
        buyCar_lv= (PullToRefreshListView) view.findViewById(R.id.buyCarBuy_lv);
        jieSuan_bt= (Button) view.findViewById(R.id.jiesuan_bt);

    }
}