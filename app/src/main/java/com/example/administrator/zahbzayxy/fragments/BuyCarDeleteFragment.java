package com.example.administrator.zahbzayxy.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.CarAdapter;
import com.example.administrator.zahbzayxy.beans.BuyCarListBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.BuyCarGroupInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class BuyCarDeleteFragment extends Fragment {
   private Context context;
   private PullToRefreshListView deleteBuyCar_plv;
    CarAdapter adapter;
    private View view;
    private List<BuyCarListBean.DataBean.CoursesBean>list=new ArrayList<>();
    private String token;
   private int pager=1;
    private Button delete_bt;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    public BuyCarDeleteFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_delete, container, false);
        initView();
        getSP();
        initPulltoRefershLv();
        //删除购物车条目
        return view;
        }
    private void initDelete() {
        delete_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDeleteData();

            }
        });
    }

    private void postDeleteData() {
        int  pos = 0;
         int tag1;
         int subSize;
         List<BuyCarListBean.DataBean.CoursesBean.SubCoursesBean> subCoursesList;
        int mainCourseId;
        JSONArray array=new JSONArray();
        JSONArray subArray=new JSONArray();
        JSONObject object=new JSONObject();
        for (int i=0;i<list.size();i++){
            pos=i;
            boolean isChildChecked=false;
            mainCourseId= list.get(i).getId();
            subCoursesList = list.get(i).getSubCourses();
            subSize = subCoursesList.size();
            for (int j=0;j<subSize;j++){
                tag1 = subCoursesList.get(j).getTag1();
                if (tag1==1){
                    isChildChecked=true;
                    int subId = subCoursesList.get(j).getId();
                    subArray.put(subId);

                }
            }
            if (isChildChecked){
                object=new JSONObject();
                try {
                    object.put("mainCourseId",mainCourseId);
                    object.put("subCourseIds",subArray);
                    array.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        String s = array.toString();
      // Log.e("aaaaaaaaaaaaa",s);
        Map<String,String>deleteBuyCar=new HashMap<>();
        deleteBuyCar.put("courseIds",s);
        deleteBuyCar.put("token",token);
        BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
        final int finalPos = pos;
        if (s!=null) {
            aClass.buyCarDeleteData(deleteBuyCar).enqueue(new Callback<SuccessBean>() {
                @Override
                public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                    SuccessBean body = response.body();
                    if (body != null && body.getErrMsg() == null) {
                        boolean data = body.getData();
                        if (data == true) {
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                            //initPulltoRefershLv();
                            downLoadData1(pager);
                        }
                    } else {
                        Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SuccessBean> call, Throwable t) {
                    String message = t.getMessage();
                   // Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                   // Log.e("deletefailaaaa", message);
                }
            });
        }
    }

    private void initPulltoRefershLv() {
        deleteBuyCar_plv.setMode(PullToRefreshBase.Mode.BOTH);
        deleteBuyCar_plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                pager=1;
                downLoadData(pager);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager++;
                downLoadData(pager);
            }
        });
        downLoadData(pager);

    }
    private void downLoadData(int pager) {
        adapter=new CarAdapter(list,context);
        deleteBuyCar_plv.setAdapter(adapter);
       //首先拿到购物车中的数据
        BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
        aClass.buyCarListData(pager,10,token).enqueue(new Callback<BuyCarListBean>() {
            @Override
            public void onResponse(Call<BuyCarListBean> call, Response<BuyCarListBean> response) {
                BuyCarListBean body = response.body();
                if (body!=null) {
                    String code = body.getCode();
                    if (code.equals("00003")) {
                        Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("isLogin", false);
                        edit.commit();
                    } else if (code.equals("99999")) {
                        Toast.makeText(context, "系统异常", Toast.LENGTH_SHORT).show();
                    } else if (dbIsLogin() == false) {
                        Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                    } else if (response != null && body != null && body.getErrMsg() == null) {
                        List<BuyCarListBean.DataBean.CoursesBean> courses = body.getData().getCourses();
                        if (courses != null) {
                            list.addAll(courses);
                            adapter.notifyDataSetChanged();
                            initDelete();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<BuyCarListBean> call, Throwable t) {
                Toast.makeText(context,"请求服务器失败",Toast.LENGTH_SHORT).show();
              //  Log.e("ssssssssss",t.getMessage());
            }
        });
          if (deleteBuyCar_plv.isRefreshing()){
              deleteBuyCar_plv.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      deleteBuyCar_plv.onRefreshComplete();
                  }
              },1000);
          }
    }
    private void downLoadData1(int pager) {
        //首先拿到购物车中的数据
        BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
        aClass.buyCarListData(pager,10,token).enqueue(new Callback<BuyCarListBean>() {
            @Override
            public void onResponse(Call<BuyCarListBean> call, Response<BuyCarListBean> response) {
                BuyCarListBean body = response.body();
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
                else if (response != null && body != null&&body.getErrMsg()==null) {
                    List<BuyCarListBean.DataBean.CoursesBean> courses = body.getData().getCourses();
                    if (courses != null) {
                       // list.clear();
                        list=courses;
                        adapter=new CarAdapter(list,context);
                        deleteBuyCar_plv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        initDelete();
                    }
                }
            }
            @Override
            public void onFailure(Call<BuyCarListBean> call, Throwable t) {
              //  Toast.makeText(context,"请求服务器失败",Toast.LENGTH_SHORT).show();
                //  Log.e("ssssssssss",t.getMessage());
            }
        });
        if (deleteBuyCar_plv.isRefreshing()){
            deleteBuyCar_plv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    deleteBuyCar_plv.onRefreshComplete();
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
        deleteBuyCar_plv= (PullToRefreshListView) view.findViewById(R.id.buyCarDelete_plv);
        delete_bt= (Button) view.findViewById(R.id.delete_buyCar_bt);

    }
    }



