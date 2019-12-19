package com.example.administrator.zahbzayxy.ccvideo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.activities.FaceRecognitionActivity;
import com.example.administrator.zahbzayxy.activities.FaceRecognitionForOfflineActivity;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.FaceRecognitionUtils;
import com.example.administrator.zahbzayxy.utils.NetworkUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.example.administrator.zahbzayxy.vo.UserInfo;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

/**
 * 已下载标签页
 *
 * @author CC视频
 */
public class DownloadedFragment extends Fragment implements DownloadController.Observer {

    private ListView downloadedListView;

    private List<DownloaderWrapper> downloadedInfos = DownloadController.downloadedList;

    private Context context;

    private DownloadedViewAdapter videoListViewAdapter;

    private FragmentActivity activity;

    private String videoId;
    private int pos = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        context = activity.getApplicationContext();
        RelativeLayout downloadLayout = new RelativeLayout(activity.getApplicationContext());
        downloadLayout.setBackgroundColor(Color.WHITE);

        downloadedListView = new ListView(context);
        downloadedListView.setPadding(10, 10, 10, 10);
        downloadedListView.setDivider(getResources().getDrawable(R.drawable.line));
        LayoutParams downloadedLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        downloadLayout.addView(downloadedListView, downloadedLayoutParams);

        initData();

        downloadedListView.setOnItemClickListener(onItemClickListener);
        downloadedListView.setOnCreateContextMenuListener(onCreateContextMenuListener);

        return downloadLayout;
    }

    /**
     * 前往人脸识别页面
     *
     * @param userCourseId
     * @param coruseId
     * @param token
     * 离线人脸
     */
    private void gotoFaceRecognitionActivity(int userCourseId, int coruseId, String videoId, String token) {
        //初始化人脸识别SDK
        FaceRecognitionUtils.initContrastFaceRecognition(context);
        Intent intent = new Intent(context, FaceRecognitionForOfflineActivity.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
        DownloaderWrapper wrapper = downloadedInfos.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", coruseId);
        bundle.putInt("selectId",wrapper.getDownloadInfo().getSectionId());
        bundle.putString("token", token);
        bundle.putBoolean("isLocalPlay", false);
        bundle.putInt("rootIn", 1);
        bundle.putString("videoId", videoId);
        bundle.putInt("currentPosition",sharedPreferences.getInt("currentPosition"+wrapper.getDownloadInfo().getTitle(),0));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 前往视频播放页面
     *
     * @param userCourseId
     * @param coruseId
     * @param token
     */
    private void gotoMediaPlayActivity(int userCourseId, int coruseId, String token) {
        Intent intent = new Intent(context, MediaPlayActivity.class);
        intent.putExtra("videoId", videoId);
//        intent.putExtra("isLocalPlay", true);
//        intent.putExtra("isTimes", true);
        SharedPreferences sharedPreferences = context.getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
        DownloaderWrapper wrapper = downloadedInfos.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", coruseId);
        bundle.putInt("selectId",wrapper.getDownloadInfo().getSectionId());
        bundle.putString("token", token);
        bundle.putBoolean("isLocalPlay", true);
        intent.putExtra("currentPosition", sharedPreferences.getInt("currentPosition" + wrapper.getDownloadInfo().getTitle(), 0));
        bundle.putInt("selectionId",wrapper.getDownloadInfo().getSectionId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    static class GetUserInfoRunnable implements Runnable {
        Context mContext;
        int needVerify;
        String faceUrl;
        int mUserCourseId;
        int mCourseId;
        String mToken;
        String videoId;
        Handler runHandler;

        GetUserInfoRunnable(Context context, int userCourseId, int coruseId, String token, String videoId, Handler handler) {
            mContext = context;
            mUserCourseId = userCourseId;
            mCourseId = coruseId;
            mToken = token;
            videoId = videoId;
            runHandler = handler;
        }

        @Override
        public void run() {
            Message message = runHandler.obtainMessage();
            try {
                mToken = mToken == null ? "" : mToken;
                //通过OKHttp访问后台接口
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add(Constant.TOKEN_PARAM, mToken)
                        .build();
                Request request = new Request.Builder()
                        .url(Constant.GET_USER_INFO_URL)
                        .post(requestBody)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                // 将json字符串转化成对应数据类
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(responseData, UserInfo.class);
                if (Constant.SUCCESS_CODE.equals(userInfo.getCode())) {
                    saveUsrInfo(userInfo);
                    needVerify = userInfo.getData().getNeedVerify();
                    faceUrl = userInfo.getData().getFacePath();
                    faceUrl = faceUrl == null ? "" : faceUrl;
                    checkResult(message, needVerify, faceUrl, mUserCourseId, mCourseId, mToken);
                } else {
                    message.what = 3;
                }
                message.sendToTarget();
            } catch (Exception e) {
                message.what = 4;
                message.sendToTarget();
                StringUtil.getExceptionMessage(e);
            }
        }

        private void checkResult(Message msg, int needVerify, String faceUrl, int userCourseId, int coruseId, String token) {

            if (needVerify == 0) {
                msg.what = 0;
            } else if (needVerify == 1 && "".equals(faceUrl)) {
                msg.what = 1;
            } else {
                msg.what = 2;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("userCourseId", userCourseId);
            bundle.putInt("coruseId", coruseId);
            bundle.putString("token", token);
//            bundle.putBoolean("isLocalPlay",false);
            msg.setData(bundle);

        }

        private void saveUsrInfo(UserInfo userInfo) {
            try {
                SharedPreferences sp = mContext.getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt(Constant.IS_NEED_VERIFY_KEY, userInfo.getData().getNeedVerify());
                edit.putString(Constant.INTERVAL_TIME_KEY, userInfo.getData().getIntervalTime());
                edit.putString(Constant.PORTRAIT_URL_KEY, userInfo.getData().getFacePath());
                edit.commit();
            } catch (Exception e) {
                Log.e("saveUsrInfo", StringUtil.getExceptionMessage(e));
            }
        }
    }

    private void initData() {
        videoListViewAdapter = new DownloadedViewAdapter(context, downloadedInfos);
        downloadedListView.setAdapter(videoListViewAdapter);
    }


    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            pos = position;
            DownloaderWrapper wrapper = (DownloaderWrapper) parent.getItemAtPosition(position);
            SharedPreferences sharedPreferences = context.getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
            boolean isConnected = NetworkUtils.isConnected(context);
            if (!isConnected) {
                Intent intent = new Intent(context, MediaPlayActivity.class);
                intent.putExtra("videoId", wrapper.getDownloadInfo().getTitle());
                intent.putExtra("isLocalPlay", true);
                intent.putExtra("currentPosition", sharedPreferences.getInt("currentPosition" + wrapper.getDownloadInfo().getTitle(), 0));
                startActivity(intent);
            } else {
                String token = sharedPreferences.getString("token", "");
                int userCourseId = wrapper.getDownloadInfo().getUserCourseId();
                int coruseId = wrapper.getDownloadInfo().getCoruseId();
                videoId = wrapper.getDownloadInfo().getVideoId();
                ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
                GetUserInfoRunnable task = new GetUserInfoRunnable(context, userCourseId, coruseId, token, videoId, handler);
                threadPool.submit(task);
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            gotoVideo(msg);
        }
    };

    /**
     * 画面跳转
     *
     * @param msg
     */
    private void gotoVideo(Message msg) {
        final int mWhat = msg.what;
        if (mWhat == 1) {
            //需要人脸识别且照片未上传
            if (isResumed()) {
                showUploadDialog();
            }
            return;
        } else if (mWhat == 3) {
            Toast.makeText(context, "认证失败", Toast.LENGTH_SHORT).show();
            return;
        } else if (mWhat == 4) {
            Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
            return;
        }

        final int userCourseId = msg.getData().getInt("userCourseId", 0);
        final int courseId = msg.getData().getInt("coruseId", 0);
        final String token = msg.getData().getString("token");
        if (mWhat == 0) {
            //播放不需要参数
            gotoMediaPlayActivity(userCourseId, courseId, token);
        } else if (mWhat == 2) {
            gotoFaceRecognitionActivity(userCourseId, courseId, videoId, token);
        }
    }


    /**
     * 人脸识别对话框
     */
    private AlertDialog upLoadAlertDialog;

    private void showUploadDialog() {

        if (upLoadAlertDialog != null) {
            upLoadAlertDialog.dismiss();
        }
        upLoadAlertDialog = new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(R.string.upload_portrait_prompt)
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(context, EditMessageActivity.class));
                        return;
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
            upLoadAlertDialog.show();
        }
    }


    OnCreateContextMenuListener onCreateContextMenuListener = new OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("操作");
            menu.add(ConfigUtil.DOWNLOADED_MENU_GROUP_ID, 0, 0, "删除");
        }
    };

    @SuppressWarnings("unchecked")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() != ConfigUtil.DOWNLOADED_MENU_GROUP_ID) {
            return false;
        }

        int selectedPosition = ((AdapterContextMenuInfo) item.getMenuInfo()).position;

        DownloaderWrapper wrapper = (DownloaderWrapper) videoListViewAdapter.getItem(selectedPosition);
        DownloadController.deleteDownloadedInfo(selectedPosition);

        File file = new File(Environment.getExternalStorageDirectory() + "/" + ConfigUtil.DOWNLOAD_DIR, wrapper.getDownloadInfo().getTitle() + ".pcm");
        if (file.exists()) {
            file.delete();
        }

        updateView();

        if (getUserVisibleHint()) {
            return true;
        }

        return false;
    }

    @Override
    public void update() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateView();
            }
        });
    }

    private void updateView() {
        videoListViewAdapter.notifyDataSetChanged();
        downloadedListView.invalidate();
    }

    @Override
    public void onPause() {
        DownloadController.detach(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        DownloadController.attach(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

}