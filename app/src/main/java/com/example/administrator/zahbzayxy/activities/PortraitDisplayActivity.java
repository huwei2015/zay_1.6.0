package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.idl.face.platform.utils.Base64Utils;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;

/**
 * @author tu-mengting
 * 人像对比照显示画面
 */
public class PortraitDisplayActivity extends BaseActivity {
    private ImageView backEditMessage;
    private ImageView ivPortrait;
    private TextView tvExplain;
    private int appShowWidth;
    private int appShowHeight;
    private Drawable portraitDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_display);

        //初始化控件
        tvExplain = (TextView) findViewById(R.id.tvExplain);
        backEditMessage = (ImageView) findViewById(R.id.back_editMessage);
        ivPortrait = (ImageView) findViewById(R.id.ivPortrait);
        ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
        LoadPortraitRunnable task = new LoadPortraitRunnable();
        threadPool.submit(task);

        //设置返回按钮的点击响应事件
        backEditMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 加载人脸对比照片
     */
    private void loadPortraitPic(){
        try{
            SharedPreferences faceUrl = getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
            String faceUrlStr = faceUrl.getString(Constant.PORTRAIT_URL_KEY,"");
            if (!"".equals(faceUrlStr)){
                //获取图片
                portraitDrawable = getNetDrawable(faceUrlStr);
                //显示图片
                Message msg = handler.obtainMessage();
                msg.what = Constant.SHOW_PORTRAIT;
                msg.sendToTarget();
//                Picasso.with(PortraitDisplayActivity.this).load(faceUrlStr).into(ivPortrait);
            }
            else {}
        }
        catch (Exception e){
            Log.e("loadFaceIdentifyPic", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * 根据图片的URL转化成Bitmap
     * @param imageUrl
     * @return
     */
    public static Drawable getNetDrawable(String imageUrl) {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        }
        catch (Exception e) {
            Log.e("GetNetDrawable", StringUtil.getExceptionMessage(e));
        }

        return drawable ;
    }

    /**
     * 通过URL加载图片
     */
    class LoadPortraitRunnable implements Runnable{
        @Override
        public void run() {
            try{
                //获取应用程序的显示区域
                Display defaultDisplay = getWindowManager().getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                appShowWidth = point.x;
                appShowHeight = point.y;
                //加载图片
                loadPortraitPic();
            }catch (Exception e){
                Log.e("LoadPortraitRunnable", StringUtil.getExceptionMessage(e));
            }
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                switch (msg.what){
                    case Constant.SHOW_PORTRAIT:
                        ivPortrait.setImageDrawable(portraitDrawable);
                        ViewGroup.LayoutParams params = ivPortrait.getLayoutParams();
                        if ( Constant.DEVICE_HEIGHT_EIGHT_HUNDRED < appShowHeight){
                            //设备高度大于800
                            params.width =  (int)(appShowWidth * Constant.PORTRAIT_WIDTH_RATIO_HIGH);
                        }else {
                            //设备高度小于800
                            tvExplain.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            params.width =  (int)(appShowWidth * Constant.PORTRAIT_WIDTH_RATIO_LOW);
                        }
                        if (null != portraitDrawable){
                            //计算图片的宽高比
                            float widthHeightRatio = (float) portraitDrawable.getIntrinsicWidth()/portraitDrawable.getIntrinsicHeight();
                            params.height = (int)(params.width / widthHeightRatio);
                            //设置控件的宽度和高度
                            ivPortrait.setLayoutParams(params);
                        }else {}
                        break;
                    default:
                        break;
                }
            }
            catch (Exception e){
                Log.e("handleMessage", StringUtil.getExceptionMessage(e));
            }
        }
    };
}
