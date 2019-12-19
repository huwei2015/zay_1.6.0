package com.example.administrator.zahbzayxy.Runnables;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.example.administrator.zahbzayxy.vo.UserInfo;
import com.google.gson.Gson;
import java.util.concurrent.ExecutorService;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import static android.content.Context.MODE_PRIVATE;

/**
 * @author tu-mengting
 * 访问用户信息的线程
 */

public class UserInfoRunnable implements Runnable {
//    private Fragment currentFragment;
    private Context context;
//    private Dialog dialog;
    private Handler handler;

//    public UserInfoRunnable(Fragment fragment, Context context, Dialog dialog) {
//        this.currentFragment = fragment;
//        this.context = context;
//        this.dialog = dialog;
//    }

    public UserInfoRunnable(Context context,Handler handler) {
        this.handler = handler;
        this.context = context;
    }

//    /**
//     * @author tu-mengting
//     * 开启获取用户信息的线程
//     */
//    public static void startUsrInfoRunnable(Fragment fragment, Context context, Dialog dialog){
//        try{
//            //获取是否已登录信息
//            SharedPreferences tokenDb = context.getSharedPreferences(Constant.TOKEN_DB, MODE_PRIVATE);
//            Boolean isLogin = tokenDb.getBoolean("isLogin",false);
//            if (isLogin){
//                ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
//                UserInfoRunnable task = new UserInfoRunnable(fragment, context, dialog);
//                threadPool.submit(task);
//            }
//            else {}
//
//        }
//        catch (Exception e){
//            Log.e("startUsrInfoRunnable", StringUtil.getExceptionMessage(e));
//        }
//    }
    /**
     * @author tu-mengting
     * 开启获取用户信息的线程
     */
    public static void startUsrInfoRunnable( Context context,Handler handler){
        try{
            //获取是否已登录信息
            SharedPreferences tokenDb = context.getSharedPreferences(Constant.TOKEN_DB, MODE_PRIVATE);
            Boolean isLogin = tokenDb.getBoolean("isLogin",false);
            if (isLogin){
                ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
                UserInfoRunnable task = new UserInfoRunnable(context,handler);
                threadPool.submit(task);
            }
            else {}

        }
        catch (Exception e){
            Log.e("startUsrInfoRunnable", StringUtil.getExceptionMessage(e));
        }
    }

    @Override
    public void run() {
        try{
            //获取token
            SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", MODE_PRIVATE);
            String token = tokenDb.getString("token","");
            //通过OKHttp访问后台接口
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add(Constant.TOKEN_PARAM, token)
                    .build();
            Request request = new Request.Builder()
                    .url(Constant.GET_USER_INFO_URL)
                    .post(requestBody)
                    .build();
            okhttp3.Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            // 将json字符串转化成对应数据类
            Gson gson = new Gson( );
            UserInfo userInfo = gson.fromJson( responseData, UserInfo.class );
            if(checkUsrInfo(userInfo)){
                //用户信息获取成功,保存用户信息
                saveUsrInfo(userInfo);
                if (isNeedCollectFace(userInfo)){
                    //显示提示要人脸识别的对话框
                    sendMsg(Constant.NEED_COLLECT_PORTRAIT);
                }
                else {
                    //不需要进行人脸采集操作
                }
            }
            else{
                //用户信息获取失败
                sendMsg(Constant.GET_USR_INFO_ERR);
            }
        }
        catch (Exception e){
            Log.d("FaceRecogRunnable", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * @author tu-mengting
     * 保存用户信息
     */
    private void saveUsrInfo(UserInfo userInfo){
        try{
            SharedPreferences sp = context.getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt(Constant.IS_NEED_VERIFY_KEY, userInfo.getData().getNeedVerify());
            edit.putString(Constant.INTERVAL_TIME_KEY, userInfo.getData().getIntervalTime());
            edit.putString(Constant.PORTRAIT_URL_KEY, userInfo.getData().getFacePath());
            edit.commit();
        }
        catch (Exception e){
            Log.e("saveUsrInfo", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * @author tu-mengting
     * 判断是否需要进行人脸采集操作
     */
    private boolean isNeedCollectFace(UserInfo userInfo){
        boolean isNeed = false;

        try{
            //判断是否启用人脸识别
            if( Constant.NEED_VERIFY == userInfo.getData().getNeedVerify() ){
                //判断是否人像是否已经上传
                if ( "".equals(userInfo.getData().getFacePath()) ){
                    //未上传
                    isNeed = true;
                }
                else {
                    //已上传
                    isNeed = false;
                }
            }
            else {
                isNeed = false;
            }
        }
        catch (Exception e){
            isNeed = false;
            Log.e("isNeedCollectFace", StringUtil.getExceptionMessage(e));
        }

        return isNeed;
    }

    /**
     * @author tu-mengting
     * 判断用户信息是否返回成功
     */
    private boolean checkUsrInfo(UserInfo userInfo){
        boolean ret = false;

        try{
            if (Constant.SUCCESS_CODE.equals(userInfo.getCode())){
                ret = true;
            }
            else {
                ret = false;
            }
        }
        catch (Exception e){
            ret = false;
            Log.e("judgeToNextActivity", StringUtil.getExceptionMessage(e));
        }

        return ret;
    }

//    /**
//     * @author tu-mengting
//     * 显示错误信息对话框
//     */
//    private void showErrDialog(int contentRes){
//        try{
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            dialog = builder.setTitle("提示")
//                    .setMessage(contentRes)
//                    .setNegativeButton(R.string.btn_confirm, new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    }).create();
//
//            dialog.setCanceledOnTouchOutside(false);
//            if (currentFragment.isVisible()){
//                dialog.show();
//                //设置对话框size
//                Window dialogWindow =  dialog.getWindow();
//                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//                lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
//                lp.dimAmount = 0f;//设置背景不变暗
//                dialogWindow.setAttributes(lp);
//            } else {
//                dialog = null;
//            }
//        }
//        catch (Exception e){
//            Log.e("showDialog", StringUtil.getExceptionMessage(e));
//        }
//    }

//    /**
//     * @author tu-mengting
//     * 显示要进行人脸识别的对话框
//     */
//    private void showPromptDialog(int contentRes){
//        try{
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            dialog = builder.setTitle("提示")
//                        .setMessage(contentRes)
//                        .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener(){
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                                context.startActivity(new Intent(context, EditMessageActivity.class));
//                            }
//                        }).create();
//            dialog.setCanceledOnTouchOutside(false);
//            if (currentFragment.isVisible()){
//                dialog.show();
//            } else {
//                dialog = null;
//            }
//        }
//        catch (Exception e){
//            Log.e("showDialog", StringUtil.getExceptionMessage(e));
//        }
//    }

    /**
     * @author tu-mengting
     * 通过向handler发送消息来更新画面
     */
    private void sendMsg(int msgWhat){
        try{
            Message msg = handler.obtainMessage();
            msg.what = msgWhat;
            msg.sendToTarget();
        }
        catch (Exception e){
            Log.e("", StringUtil.getExceptionMessage(e));
        }
    }

//    /**
//     * @author tu-mengting
//     * 用于更新画面
//     */
//    protected Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            try{
//                switch (msg.what){
//                    case Constant.GET_USR_INFO_ERR:
//                        showErrDialog(R.string.get_user_info_err);
//                        break;
//                    case Constant.NEED_COLLECT_PORTRAIT:
//                        showPromptDialog(R.string.upload_portrait_prompt);
//                        break;
//                    default:
//                        break;
//                }
//            }
//            catch (Exception e){
//                Log.e("handleMessage", StringUtil.getExceptionMessage(e));
//            }
//        }
//    };
}
