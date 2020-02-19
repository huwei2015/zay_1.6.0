package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.activities.OfflineCourseActivity;
import com.example.administrator.zahbzayxy.beans.OfflineCourseBean;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/audipreinstalledservice_demo/log/";
    private static final String FILE_NAME = "crash";

    // log文件的后缀名
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler mInstance;
    //系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;

    /**
     * 构造函数
     */
    private CrashHandler() {
    }

    /**
     * 获取对象
     *
     * @return 对象
     */
    public static CrashHandler getInstance() {
        if (null == mInstance) {
            synchronized (CrashHandler.class) {
                if (null == mInstance) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        //获取系统默认的异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 未捕获异常
     *
     * @param thread 出现未捕获异常的线程
     * @param ex     未捕获的异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            Log.w(TAG, "没有找到SD卡，跳过导出异常信息");
            //导出异常信息到SD卡中
            dumpExceptionToSDCard(ex);
            //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
            uploadExceptionToServer(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //打印出当前调用栈信息
        ex.printStackTrace();

        //如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess( android.os.Process.myPid());
        }
    }

    /**
     * 导出异常信息到SD卡中
     * @param ex 异常信息
     * @throws IOException IO异常
     */
    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        //如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "没有找到SD卡，跳过导出异常信息");
                return;
            }
        }

        // 创建Log目录
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 获取当前时间
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        //以当前时间创建log文件
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //导出发生异常的时间
            pw.println(time);

            //导出手机信息
            dumpPhoneInfo(pw);

            pw.println();
            //导出异常的调用栈信息
            ex.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            Log.i(TAG, "dump crash info failed");
        }
    }

    /**
     * 导出手机信息
     * @param pw PrintWriter
     * @throws PackageManager.NameNotFoundException 没有包名异常
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        //应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);

        //android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);

        //cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    /**
     * 上传异常信息到服务器
     */
    private void uploadExceptionToServer(Throwable ex) {
        String errorInfo=ex.getMessage();
        IndexInterface aClass = RetrofitUtils.getInstance().createClass(IndexInterface.class);
        aClass.saveErrorInfo(errorInfo).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int code1 = response.code();

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String message = t.getMessage();
            }
        });
    }
}
