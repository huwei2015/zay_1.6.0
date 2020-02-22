package com.example.administrator.zahbzayxy.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MsgAdapter;
import com.example.administrator.zahbzayxy.adapters.MySignAdapter;
import com.example.administrator.zahbzayxy.beans.SignBean;
import com.example.administrator.zahbzayxy.beans.TimeData;
import com.example.administrator.zahbzayxy.beans.UserCenter;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.TimeComparator;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.example.administrator.zahbzayxy.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-13.
 * Time 11:13.
 * 消息列表
 */
public class MsgListActivity extends BaseActivity implements View.OnClickListener, MsgAdapter.onClickItemListener{

    public static final String FLUSH_MSG_INFO_EVENT_FLAG = "flushMsgInfoEventFlag";

    private ImageView exam_archives_back;
    private TextView tab_unread_message;
    private List<TimeData.MsgList> msgLists = new ArrayList<>();
    private int  currenPage =1;
    private int PageSize =10;
    private String token;
    MsgAdapter adapter;
    TextView tv_msg;
    RelativeLayout rl_empty;
    LinearLayout ll_list;
    private boolean mHasData = true;
    private final int MSG_LIST=1999;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsHasData = true;
    private boolean mIsLoading;
    private LoadingDialog mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        mLoading = new LoadingDialog(MsgListActivity.this);
        mLoading.setShowText("加载中...");
        initView();
        initEvent();
        initData();
    }
    private void initView() {
        String messageNum = getIntent().getStringExtra("messageNum");
        exam_archives_back= (ImageView) findViewById(R.id.exam_archives_back);
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        Log.i("hw","hw============="+token);
        exam_archives_back.setOnClickListener(this);
        tab_unread_message= (TextView) findViewById(R.id.tab_unread_message);
        if(!TextUtils.isEmpty(messageNum)){
            int msg_count = Integer.parseInt(messageNum);
            if(msg_count > 0){
                tab_unread_message.setVisibility(View.VISIBLE);
                tab_unread_message.setText(messageNum);
            }
        }
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty_layout);
        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        //初始化adapter
        adapter = new MsgAdapter(MsgListActivity.this, msgLists);
        adapter.setOnClickItemListener(this);
        //设置EmptyView
        View emptyView = View.inflate(this, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.msg_list_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.msg_list_recycler_view);
        Utils.setRefreshViewColor(mRefreshLayout);
        mLayoutManager = new LinearLayoutManager(MsgListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void initEvent(){
        mRefreshLayout.setOnRefreshListener(() -> {
            currenPage = 1;
            mIsLoading = true;
            mIsHasData = true;
            initUserCenter();
            initData();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 没有更多的数据了
                if (!mIsHasData) return;
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !mIsLoading) {
                    mLoading.show();
                    currenPage++;
                    initData();
                    mIsLoading = true;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initData() {
        mLoading.show();
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getSystemMsg(currenPage,PageSize,3,token,"yes").enqueue(new Callback<TimeData>() {
            @Override
            public void onResponse(Call<TimeData> call, Response<TimeData> response) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                if (response != null && response.body() != null && response.body().getData() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        List<TimeData.MsgList> allTopAnnounceList = response.body().getData().getAllTopAnnounceList();
                        if (currenPage == 1 && (allTopAnnounceList == null || allTopAnnounceList.size() == 0)) {
                            emptyLayout(false);
                            mIsHasData = false;
                        } else {
                            emptyLayout(true);
                        }
                        if (currenPage == 1) {
                            mHasData = true;
                            msgLists.clear();
                        }
                        if (allTopAnnounceList != null && allTopAnnounceList.size() > 0) {
                            msgLists.addAll(allTopAnnounceList);
                            adapter.setList(msgLists);
                        } else {
                            if (currenPage > 1 && mHasData) {
                                mHasData = false;
                                mIsHasData = false;
                                Toast.makeText(MsgListActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return;
                    }
                }
                emptyLayout(false);
            }

            @Override
            public void onFailure(Call<TimeData> call, Throwable t) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                ToastUtils.showInfo(t.getMessage(),5000);
            }
        });
    }

    private void closeSwipeRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    private void initUserCenter() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserCenter> userCenter = userInfoInterface.getUserCenter(token);
        userCenter.enqueue(new Callback<UserCenter>() {
            @Override
            public void onResponse(Call<UserCenter> call, Response<UserCenter> response) {
                if(response !=null && response.body() != null){
                    String code=response.body().getCode();
                    if(code.equals("00000")){
                        UserCenter.userCenterData data = response.body().getData();
                        int messageNum = data.getMessageNum();//消息
                        tab_unread_message.setText(String.valueOf(messageNum));
                    }
                }
            }

            @Override
            public void onFailure(Call<UserCenter> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String pageFlag = intent.getStringExtra("page");
            if ("MsgListActivity".equals(pageFlag)) {
                initUserCenter();
                int position = intent.getIntExtra("position", -1);
                if (position > 0 && msgLists != null && msgLists.size() > position) {
                    TimeData.MsgList bean = msgLists.get(position);
                    if (bean != null) {
                        bean.setyRead(true);
                        msgLists.set(position, bean);
                        adapter.setList(msgLists);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exam_archives_back:
                EventBus.getDefault().post(FLUSH_MSG_INFO_EVENT_FLAG);
                finish();
                break;
        }
    }

    private void emptyLayout(boolean isVisible){
        if(isVisible){
            mRefreshLayout.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
            tv_msg.setText("暂无消息");
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(MsgListActivity.this,H5MsgDetailActivity.class);
        intent.putExtra("id",String.valueOf(msgLists.get(position).getId()));
        intent.putExtra("type","msg");
        intent.putExtra("page","MsgListActivity");
        intent.putExtra("position", position);
        startActivityForResult(intent,MSG_LIST);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(FLUSH_MSG_INFO_EVENT_FLAG);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case MSG_LIST :

                if (resultCode == Activity.RESULT_OK) {
                    String id = data.getStringExtra("id");
                    if(id==null){
                        id="0";
                    }
                    List<TimeData.MsgList> mlists = new ArrayList<>();
                    if(msgLists!=null){
                        for(TimeData.MsgList msg:msgLists){
                            if(msg.getId()==Integer.valueOf(id)){
                                msg.setyRead(true);
                            }
                            mlists.add(msg);
                        }
                    }
                    msgLists=mlists;
                    adapter.addList(msgLists);
                    adapter.notifyDataSetChanged();
                    initUserCenter();
                }
                break;
            default:break;
        }
    }
}
