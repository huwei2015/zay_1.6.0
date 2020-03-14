package com.example.administrator.zahbzayxy.ccvideo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.sdk.mobile.exception.DreamwinException;
import com.bokecc.sdk.mobile.exception.ErrorCode;
import com.bokecc.sdk.mobile.play.DWMediaPlayer;
import com.bokecc.sdk.mobile.play.OnDreamWinErrorListener;
import com.example.administrator.zahbzayxy.DemoApplication;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.FaceRecognitionActivity;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;
import com.example.administrator.zahbzayxy.beans.PLessonPlayTimeBean;
import com.example.administrator.zahbzayxy.beans.PMyLessonPlayBean;
import com.example.administrator.zahbzayxy.fragments.FreeDirectoryFragment;
import com.example.administrator.zahbzayxy.fragments.LesssonTestLiberyFragment;
import com.example.administrator.zahbzayxy.fragments.PLessonDetailFragment;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.myinterface.MyInterface;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.DateUtil;
import com.example.administrator.zahbzayxy.utils.FaceRecognitionUtils;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2020-03-12.
 * Time 16:51.
 */
public class FreePlayActivity extends AppCompatActivity implements DWMediaPlayer.OnBufferingUpdateListener,
        DWMediaPlayer.OnInfoListener, DWMediaPlayer.OnPreparedListener, DWMediaPlayer.OnErrorListener,
        MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, SensorEventListener,
        MediaPlayer.OnCompletionListener, OnDreamWinErrorListener {
    private boolean networkConnected = true;
    private DWMediaPlayer player;
    private Subtitle subtitle;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ProgressBar bufferProgressBar;
    private SeekBar skbProgress;
    private ImageView backPlayList;
    private TextView videoIdText, playDuration, videoDuration;
    private TextView tvDefinition;
    private PopMenu definitionMenu;
    private LinearLayout playerTopLayout, volumeLayout;
    private LinearLayout playerBottomLayout;
    private AudioManager audioManager;
    private VerticalSeekBar volumeSeekBar;
    private int currentVolume;
    private int maxVolume;
    private TextView subtitleText;

    private boolean isLocalPlay;
    private boolean isPrepared;
    private Map<String, Integer> definitionMap;

    private Handler playerHandler;
    private Timer timer = new Timer();
    private TimerTask timerTask, networkInfoTimerTask;

    private int currentScreenSizeFlag = 1;
    private int currrentSubtitleSwitchFlag = 0;
    private int currentDefinitionIndex = 0;
    // 默认设置为普清
    private int defaultDefinition = DWMediaPlayer.NORMAL_DEFINITION;

    private boolean firstInitDefinition = true;
    private String path;

    private Boolean isPlaying;
    // 当player未准备好，并且当前activity经过onPause()生命周期时，此值为true
    private boolean isFreeze = false;
    private boolean isSurfaceDestroy = false;

    public int currentPosition;
    private Dialog dialog;

    private String[] definitionArray;
    private final String[] screenSizeArray = new String[]{"满屏", "100%", "75%", "50%"};
    private final String[] subtitleSwitchArray = new String[]{"开启", "关闭"};
    private final String subtitleExampleURL = "http://dev.bokecc.com/static/font/example.utf8.srt";

    private DemoApplication demoApplication;

    private GestureDetector detector;
    private float scrollTotalDistance;
    public int currentPlayPosition;
    private VideoPosition lastVideoPosition;
    private String videoId;
    private RelativeLayout rlBelow, rlPlay;
    private WindowManager wm;
    private ImageView ivFullscreen;
    // 隐藏界面的线程
    private Runnable hidePlayRunnable = new Runnable() {
        @Override
        public void run() {
            setLayoutVisibility(View.GONE, false);
        }
    };

    VideoPositionDBHelper videoPositionDBHelper;
    @BindView(R.id.pMylesson_tab)
    TabLayout lesson_tab;
    @BindView(R.id.pMylessonTab_vp)
    ViewPager lessonTab_vp;
    private LessonFragmentPageAdapter pageAdapter;
    //tabLayout中的标题title
    private List<String> titlesList = new ArrayList<>();
    //viewPager中的fragment
    private List<Fragment> fragmentList = new ArrayList<>();
    private Unbinder bind;
    FragmentManager fragmentManager;

    private int courseId;
    private int sectionId;//自课程id
    private int userCourseId;
    private String token;
    private String getSelectionName;

    private Integer selectionIdGet, getBackSelectionId;
    private double getPlayPercent;
    private boolean isExist;

    //    boolean isNetworkConnected = true;
    int playTime;
    boolean recognizeSuccess = false;
    boolean needRecognized = false;
    //当前播放时间
    int current_time;
    public static volatile WeakReference<FreePlayActivity> mediaPlayWeakReference;
    private String mImagePath;
    int courseType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayWeakReference = new WeakReference<FreePlayActivity>(this);
        // 隐藏标题
        demoApplication = (DemoApplication) getApplication();
        demoApplication.getDRMServer().reset();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        player = new DWMediaPlayer();
        setContentView(R.layout.new_media_play);
        bind = ButterKnife.bind(this);
        videoPositionDBHelper = new VideoPositionDBHelper(demoApplication.getBoxStore());
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        detector = new GestureDetector(this, new MyGesture());

        initView();
        initMyView();
        initPlayHander();
        //initPlayInfo();
        //切换视频
        initFragment();

        if (!isLocalPlay) {
            initNetworkTimerTask();
            if (initLastVideoInfo()) {
                //下载视频信息
                initDownLoadData();
            }

            /*****************FHS START********************/
            if (isNeedVerify()) {
                //设置识别间隔时间
                setRecognizedIntervalTime();
            }
//            isNeedSaveTime = true;
            /*****************FHS END********************/
        }

    }

    private boolean initLastVideoInfo() {
        try {
            String mVideoId = getIntent().getStringExtra("videoId");
            if (mVideoId == null || "".equals(mVideoId)) {
                return true;
            } else {
                videoId = mVideoId;
                selectionIdGet = getIntent().getIntExtra("selectionId", 0);
                getPlayPercent = getIntent().getDoubleExtra("getPlayPercent", 0);
                getSelectionName = getIntent().getStringExtra("getSelectionName");
                currentPosition = getIntent().getIntExtra("currentPosition", 0);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(500);
                            initPlayInfo();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                initPlayInfo();
                return false;

            }
        } catch (Exception e) {
            StringUtil.getExceptionMessage(e);
        }
        return true;
    }

    private void initMyView() {
        courseId = getIntent().getIntExtra("coruseId", 0);
        courseType=getIntent().getIntExtra("courseType",0);
        userCourseId = getIntent().getIntExtra("userCourseId", 0);
        sectionId = getIntent().getIntExtra("selectionId",0);
        selectionIdGet = sectionId;
        Log.i("hw","============initMyView========="+sectionId);
        isLocalPlay = getIntent().getBooleanExtra("isLocalPlay", false);
        this.currentPosition = getIntent().getIntExtra("currentPosition", 0);
        posIndex = getIntent().getIntExtra("posIndex", 0);
        getSelectionName = getIntent().getStringExtra("getSelectionName");
        mImagePath = getIntent().getStringExtra("imagePath");
        ArrayList list = (ArrayList<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean>) getIntent().getSerializableExtra("listsize");
        if (list != null && list.size() > 0) {
            listsize = list;
        }
        if (isLocalPlay == true) {
            initDownloadPlayInfo();
        }
        Log.e("ididdiddidddddddd", courseId + "," + userCourseId);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        Log.e("Token", token);
        try {
            String encodeToken = URLEncoder.encode(token, "utf-8");
            Log.e("encodeToken", encodeToken);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    ArrayList<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> listsize = new ArrayList<>();
    int posIndex = 0;
    private void initDownLoadData() {
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getFreePlayData(courseId,courseType).enqueue(new Callback<PMyLessonPlayBean>() { // courseType =0 是中安云  1是saas平台
            @Override
            public void onResponse(Call<PMyLessonPlayBean> call, Response<PMyLessonPlayBean> response) {
                PMyLessonPlayBean body = response.body();
                String s = new Gson().toJson(body);
                int recordIndex = 0;
                Log.e("lessonPlayBody", s);
                if (body != null) {
                    if (body.getErrMsg() == null) {
                        List<PMyLessonPlayBean.DataBean.ChildCourseListBean> childCourseList = body.getData().getChildCourseList();//总课程数
                        int size = childCourseList.size();
                        if (size > 0) {
                            for (int i = 0; i < size; i++) {
                                PMyLessonPlayBean.DataBean.ChildCourseListBean childCourseListBean = childCourseList.get(i);
                                List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean> chapterList = childCourseListBean.getChapterList();//每个课程是下面有几个章节
                                int size1 = chapterList.size();
                                if (size1 > 0) {
                                    for (int j = 0; j < size1; j++) {
                                        List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> selectionList = chapterList.get(j).getSelectionList();//每个章节下面有几个子课程
                                        int size2 = selectionList.size();
                                        for (int h = 0; h < size2; h++) {
                                            //TODO添加自动播放
                                            PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean selectionListBean1 = selectionList.get(h);
                                            selectionListBean1.setVideoIndex(recordIndex);
                                            listsize.add(selectionListBean1);
                                            boolean currPlay = selectionList.get(h).isCurrPlay();
                                            if (currPlay == true) {
                                                PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean selectionListBean = selectionList.get(h);
                                                videoId = selectionListBean.getVideoId();
                                                selectionIdGet = selectionListBean.getSelectionId();
                                                getPlayPercent = selectionListBean.getPlayPercent();
                                                getSelectionName = selectionListBean.getSelectionName();
                                                posIndex = selectionListBean1.getVideoIndex();//自动播放
                                                Log.i("hw", "=============进来=================" + selectionIdGet);
//                                                currentPosition = selectionListBean.getPlayTime()*1000;
                                                posIndex = recordIndex;
                                                Log.e("postPostion", h + "selectionIdGet:" + selectionIdGet + getSelectionName + "getdownload");
                                                initPlayInfo();
                                            }
                                            recordIndex++;
                                        }
                                    }

                                }
                            }
                        }

                    } else {
                        Object errMsg = body.getErrMsg();
                        Toast.makeText(FreePlayActivity.this, String.valueOf(errMsg), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PMyLessonPlayBean> call, Throwable t) {
                Toast.makeText(FreePlayActivity.this, "网路问题", Toast.LENGTH_SHORT).show();
            }
        });
    }

    FreeDirectoryFragment directoryFragment;
    private void initFragment() {
        titlesList.add("目录");
        titlesList.add("详情");
        titlesList.add("模考题库");
        //回调videoId和selectionId传回来
        directoryFragment = new FreeDirectoryFragment(new MyInterface.ItemClickedListener() {
            @Override
            public void onMyItemClickedListener(String vidioId, int videoIndex, int backSelectionId, double playPercent, String selectionName, int selectionId, int startPlayTime, List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> listBeans) {
                try {
                    bufferProgressBar.setVisibility(View.VISIBLE);
                    getBackSelectionId = selectionIdGet;
                    selectionIdGet = selectionId;
                    videoId = vidioId;
                    getPlayPercent = playPercent;
                    getSelectionName = selectionName;
//                    getBackSelectionId = backSelectionId;
                    Log.i("hw", "==========点击=============" + getBackSelectionId);
                    if (isPrepared) {
                        currentPosition = player.getCurrentPosition();
                    }
//                    listsize.clear();//注释自动播放解决跨章节问题
//                    listsize.addAll(listBeans);
                    updateDataPosition();
                    saveStudyTime();
                    Log.e("selecIdTes", "555,,,selectionIdGet:" + selectionIdGet + "getTotalPlayTime:" + getSelectionName + "getfragment,,," + vidioId);
                    ivCenterPlay.setVisibility(View.GONE);
                    Log.e("getplayPercent", playPercent + ",");

                    isPrepared = false;
                    setLayoutVisibility(View.GONE, false);
                    bufferProgressBar.setVisibility(View.VISIBLE);
                    ivCenterPlay.setVisibility(View.GONE);
                    currentPosition = 0;
                    currentPlayPosition = 0;
                    timerTask.cancel();
                    player.pause();
                    player.stop();
                    // timerTask.cancel();
                    posIndex = videoIndex;//自动播放
                    initPlayInfo();
//                    posIndex = directoryFragment.adapter.currentChildPosition;
                } catch (Exception e) {
                    Log.e("onMyItemClickedListener", StringUtil.getExceptionMessage(e));
                }
            }
        }, mImagePath);
        PLessonDetailFragment titleFragment = new PLessonDetailFragment();
        LesssonTestLiberyFragment lesssonTestLiberyFragment = new LesssonTestLiberyFragment();
        fragmentList.add(directoryFragment);
        fragmentList.add(titleFragment);
        fragmentList.add(lesssonTestLiberyFragment);
        fragmentManager = getSupportFragmentManager();
        pageAdapter = new LessonFragmentPageAdapter(fragmentManager, fragmentList, titlesList);
        lessonTab_vp.setAdapter(pageAdapter);
        lesson_tab.addTab(lesson_tab.newTab().setText(titlesList.get(0)));
        lesson_tab.addTab(lesson_tab.newTab().setText(titlesList.get(1)));
        lesson_tab.addTab(lesson_tab.newTab().setText(titlesList.get(2)));
        lesson_tab.setupWithViewPager(lessonTab_vp);

    }

    //离线会走
    private void initDownloadPlayInfo() {
        String videoId = getIntent().getStringExtra("videoId");

        // 通过定时器和Handler来更新进度
        isPrepared = false;
        player.reset();
        player.setOnDreamWinErrorListener(this);
        player.setOnErrorListener(this);
        player.setOnCompletionListener(this);
        player.setOnVideoSizeChangedListener(this);
        player.setOnInfoListener(this);
        player.setDRMServerPort(demoApplication.getDrmServerPort());

        videoIdText.setText(getSelectionName);
        isLocalPlay = getIntent().getBooleanExtra("isLocalPlay", false);
        try {

            // 播放本地已下载视频
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                path = Environment.getExternalStorageDirectory() + "/".concat(ConfigUtil.DOWNLOAD_DIR).concat("/").concat(videoId).concat(MediaUtil.PCM_FILE_SUFFIX);
                if (!new File(path).exists()) {
                    return;
                }
            }
        } catch (IllegalArgumentException e) {
            Log.e("player error", e.getMessage());
        } catch (SecurityException e) {
            Log.e("player error", e.getMessage());
        } catch (IllegalStateException e) {
            Log.e("player error", e + "");
        }

        // 设置视频字幕
        subtitle = new Subtitle(new Subtitle.OnSubtitleInitedListener() {

            @Override
            public void onInited(Subtitle subtitle) {
                // 初始化字幕控制菜单
            }
        });
        subtitle.initSubtitleResource(subtitleExampleURL);

    }

    private void initNetworkTimerTask() {
        networkInfoTimerTask = new TimerTask() {
            @Override
            public void run() {
                parseNetworkInfo();
            }
        };

        timer.schedule(networkInfoTimerTask, 0, 600);
    }

    @Override
    public void onPlayError(final DreamwinException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    enum NetworkStatus {
        WIFI,
        MOBILEWEB,
        NETLESS,
    }

    private MediaPlayActivity.NetworkStatus currentNetworkStatus;
    ConnectivityManager cm;

    private void parseNetworkInfo() {
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                if (currentNetworkStatus != null && currentNetworkStatus == MediaPlayActivity.NetworkStatus.WIFI) {
                    return;
                } else {
                    currentNetworkStatus = MediaPlayActivity.NetworkStatus.WIFI;
                    showWifiToast();
                }

            } else {
                if (currentNetworkStatus != null && currentNetworkStatus == MediaPlayActivity.NetworkStatus.MOBILEWEB) {
                    return;
                } else {
                    currentNetworkStatus = MediaPlayActivity.NetworkStatus.MOBILEWEB;
                    showMobileDialog();
                }
            }

            startPlayerTimerTask();
            networkConnected = true;
        } else {
            if (currentNetworkStatus != null && currentNetworkStatus == MediaPlayActivity.NetworkStatus.NETLESS) {
                return;
            } else {
                currentNetworkStatus = MediaPlayActivity.NetworkStatus.NETLESS;
                showNetlessToast();
            }

            if (timerTask != null) {
                timerTask.cancel();
            }

            networkConnected = false;
        }
    }

    private void showWifiToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "已切换至wifi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showMobileDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(FreePlayActivity.this);
                AlertDialog dialog = builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).setPositiveButton("继续", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("当前为移动网络，是否继续播放？").create();

                dialog.show();
            }
        });

    }

    private void showNetlessToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "当前无网络信号，无法播放", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPlayerTimerTask() {
        if (timerTask != null) {
            timerTask.cancel();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {

                if (!isPrepared) {
                    return;
                }

                playerHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    ImageView lockView;
    ImageView ivCenterPlay;
    ImageView ivDownload;
    ImageView ivTopMenu;
    TextView tvChangeVideo;
    ImageView ivBackVideo, ivNextVideo, ivPlay;

    private void initView() {

        rlBelow = (RelativeLayout) findViewById(R.id.rl_below_info);
        rlPlay = (RelativeLayout) findViewById(R.id.rl_play);
        rlPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isPrepared) {
                    return false;
                }
                resetHideDelayed();

                // 事件监听交给手势类来处理
                detector.onTouchEvent(event);
                return true;
            }
        });

        rlPlay.setClickable(true);
        rlPlay.setLongClickable(true);
        rlPlay.setFocusable(true);

        ivTopMenu = (ImageView) findViewById(R.id.iv_top_menu);
        ivTopMenu.setOnClickListener(onClickListener);

        surfaceView = (SurfaceView) findViewById(R.id.playerSurfaceView);
        bufferProgressBar = (ProgressBar) findViewById(R.id.bufferProgressBar);

        ivCenterPlay = (ImageView) findViewById(R.id.iv_center_play);
        ivCenterPlay.setOnClickListener(onClickListener);

        backPlayList = (ImageView) findViewById(R.id.backPlayList);
        videoIdText = (TextView) findViewById(R.id.videoIdText);
        ivDownload = (ImageView) findViewById(R.id.iv_download_play);
        ivDownload.setOnClickListener(onClickListener);

        playDuration = (TextView) findViewById(R.id.playDuration);
        videoDuration = (TextView) findViewById(R.id.videoDuration);
        playDuration.setText(ParamsUtil.millsecondsToStr(0));
        videoDuration.setText(ParamsUtil.millsecondsToStr(0));

        ivBackVideo = (ImageView) findViewById(R.id.iv_video_back);
        ivNextVideo = (ImageView) findViewById(R.id.iv_video_next);
        ivPlay = (ImageView) findViewById(R.id.iv_play);

        ivBackVideo.setOnClickListener(onClickListener);
        ivNextVideo.setOnClickListener(onClickListener);
        ivPlay.setOnClickListener(onClickListener);

        tvChangeVideo = (TextView) findViewById(R.id.tv_change_video);
        tvChangeVideo.setOnClickListener(onClickListener);

        tvDefinition = (TextView) findViewById(R.id.tv_definition);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar = (VerticalSeekBar) findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setThumbOffset(2);

        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);
        volumeSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        skbProgress = (SeekBar) findViewById(R.id.skbProgress);
        skbProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);

        playerTopLayout = (LinearLayout) findViewById(R.id.playerTopLayout);
        volumeLayout = (LinearLayout) findViewById(R.id.volumeLayout);
        playerBottomLayout = (LinearLayout) findViewById(R.id.playerBottomLayout);

        ivFullscreen = (ImageView) findViewById(R.id.iv_fullscreen);

        ivFullscreen.setOnClickListener(onClickListener);
        backPlayList.setOnClickListener(onClickListener);
        tvDefinition.setOnClickListener(onClickListener);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //2.3及以下使用，不然出现只有声音没有图像的问题
        surfaceHolder.addCallback(this);

        subtitleText = (TextView) findViewById(R.id.subtitleText);

        lockView = (ImageView) findViewById(R.id.iv_lock);
        lockView.setSelected(false);
        lockView.setOnClickListener(onClickListener);

    }

    @SuppressLint("HandlerLeak")
    private void initPlayHander() {
        playerHandler = new Handler() {
            public void handleMessage(Message msg) {

                if (player == null) {
                    return;
                }

                // 刷新字幕
                //  subtitleText.setText(subtitle.getSubtitleByTime(player
                //         .getCurrentPosition()));

                // 更新播放进度
                currentPlayPosition = player.getCurrentPosition();
                int duration = player.getDuration();

                if (duration > 0) {
                    long pos = skbProgress.getMax() * currentPlayPosition / duration;
                    playDuration.setText(ParamsUtil.millsecondsToStr(player.getCurrentPosition()));
                    skbProgress.setProgress((int) pos);
                }
            }

            ;
        };


    }

    private void initPlayInfo() {
        // 通过定时器和Handler来更新进度
        isPrepared = false;
        if (player != null) {
            player.reset();
            demoApplication.getDRMServer().stop();
            demoApplication.startDRMServer();

            player.setOnDreamWinErrorListener(this);
            player.setOnErrorListener(this);
            player.setOnCompletionListener(this);
            player.setOnVideoSizeChangedListener(this);
            player.setOnInfoListener(this);
            player.setDRMServerPort(demoApplication.getDrmServerPort());
            // videoId = getIntent().getStringExtra("videoId");
            videoIdText.setText(getSelectionName);

            isLocalPlay = getIntent().getBooleanExtra("isLocalPlay", false);
            try {
                if (!isLocalPlay) {// 播放线上视频
                    player.setVideoPlayInfo(videoId, ConfigUtil.USERID, ConfigUtil.API_KEY, "", this);
                    player.setDisplay(surfaceHolder);
                    // 设置默认清晰度
                    player.setDefaultDefinition(defaultDefinition);
                    demoApplication.getDRMServer().reset();
                    player.prepareAsync();
                    // bufferProgressBar.setVisibility(View.GONE);

                } else {// 播放本地已下载视频

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        path = Environment.getExternalStorageDirectory() + "/".concat(ConfigUtil.DOWNLOAD_DIR).concat("/").concat(videoId).concat(MediaUtil.PCM_FILE_SUFFIX);
                        if (!new File(path).exists()) {
                            return;
                        }
                    }
                }

            } catch (IllegalArgumentException e) {
                Log.e("player error", e.getMessage());
            } catch (SecurityException e) {
                Log.e("player error", e.getMessage());
            } catch (IllegalStateException e) {
                Log.e("player error", e + "");
            } catch (Exception ex) {
                Log.d("UploadFaceRecognition", StringUtil.getExceptionMessage(ex));
            }

            // 设置视频字幕
            subtitle = new Subtitle(new Subtitle.OnSubtitleInitedListener() {

                @Override
                public void onInited(Subtitle subtitle) {
                    // 初始化字幕控制菜单
                }
            });
            subtitle.initSubtitleResource(subtitleExampleURL);
        }
    }

    private RelativeLayout.LayoutParams getScreenSizeParams(int position) {
        currentScreenSizeFlag = position;
        int width = 600;
        int height = 400;
        if (isPortrait()) {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight() * 2 / 5; //TODO 根据当前布局更改
        } else {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight();
        }


        String screenSizeStr = screenSizeArray[position];
        if (screenSizeStr.indexOf("%") > 0) {// 按比例缩放
            int vWidth = player.getVideoWidth();
            if (vWidth == 0) {
                vWidth = 600;
            }

            int vHeight = player.getVideoHeight();
            if (vHeight == 0) {
                vHeight = 400;
            }

            if (vWidth > width || vHeight > height) {
                float wRatio = (float) vWidth / (float) width;
                float hRatio = (float) vHeight / (float) height;
                float ratio = Math.max(wRatio, hRatio);

                width = (int) Math.ceil((float) vWidth / ratio);
                height = (int) Math.ceil((float) vHeight / ratio);
            } else {
                float wRatio = (float) width / (float) vWidth;
                float hRatio = (float) height / (float) vHeight;
                float ratio = Math.min(wRatio, hRatio);

                width = (int) Math.ceil((float) vWidth * ratio);
                height = (int) Math.ceil((float) vHeight * ratio);
            }


            int screenSize = ParamsUtil.getInt(screenSizeStr.substring(0, screenSizeStr.indexOf("%")));
            width = (width * screenSize) / 100;
            height = (height * screenSize) / 100;
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        return params;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("recognize", "" + "---> surfaceCreated");
        try {
            surfaceHolder = holder;
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnBufferingUpdateListener(this);
            player.setOnPreparedListener(this);
            player.setDisplay(holder);
            player.setScreenOnWhilePlaying(true);

            if (isLocalPlay) {
                player.setDRMVideoPath(path, this);
                demoApplication.getDRMServer().reset();
                player.prepareAsync();
            }


        } catch (Exception e) {
            Log.e("videoPlayer", "error", e);
        }
        Log.i("videoPlayer", "surface created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        holder.setFixedSize(width, height);
        Log.d("recognize", "" + "---> surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("recognize", "" + "---> surfaceDestroyed");
        if (player == null) {
            return;
        }
        isSurfaceDestroy = true;
        if (isPrepared) {
            currentPosition = player.getCurrentPosition();
            isPrepared = false;
            if (needRecognized) {
                //pass
            } else {
                player.stop();
                player.reset();
            }
        }
        isFreeze = true;

    }

    private void initTimerTask() {
        if (timerTask != null) {
            timerTask.cancel();
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {

                if (!isPrepared) {
                    return;
                }

                playerHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, 0, 1000);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("recognize", "" + "---> onPrepared");
        initTimerTask();
        isPrepared = true;
        if (!isFreeze) {
            if (isPlaying == null || isPlaying.booleanValue()) {
                player.start();
                ivPlay.setImageResource(R.drawable.smallstop_ic);
            }
        }

        if (!isLocalPlay) {
            if (currentPosition > 0) {
                player.seekTo(currentPosition);
            } else {
                lastVideoPosition = videoPositionDBHelper.getVideoPosition(videoId);
                if (lastVideoPosition == null) {
                    lastVideoPosition = new VideoPosition(videoId, 0);
                } else if (lastVideoPosition.getPosition() > 0) {
                    player.seekTo(lastVideoPosition.getPosition());
                }
            }
        } else {
            currentPosition = getIntent().getIntExtra("currentPosition", 0);
            if (currentPosition > 0) {
                player.seekTo(currentPosition);
            }
        }


        definitionMap = player.getDefinitions();
        if (!isLocalPlay) {
            initDefinitionPopMenu();
        }

        bufferProgressBar.setVisibility(View.GONE);
        setSurfaceViewLayout();
        videoDuration.setText(ParamsUtil.millsecondsToStr(player.getDuration()));
    }

    // 设置surfaceview的布局
    private void setSurfaceViewLayout() {
        RelativeLayout.LayoutParams params = getScreenSizeParams(currentScreenSizeFlag);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        surfaceView.setLayoutParams(params);
    }

    private void initDefinitionPopMenu() {
        if (definitionMap.size() > 1) {
            currentDefinitionIndex = 1;
            Integer[] definitions = new Integer[]{};
            definitions = definitionMap.values().toArray(definitions);
            // 设置默认为普清，所以此处需要判断一下
            for (int i = 0; i < definitions.length; i++) {
                if (definitions[i].intValue() == defaultDefinition) {
                    currentDefinitionIndex = i;
                }
            }

//			firstInitDefinition = false;
        }

        definitionMenu = new PopMenu(this, R.drawable.popdown, currentDefinitionIndex, getResources().getDimensionPixelSize(R.dimen.popmenu_height));
        // 设置清晰度列表
        definitionArray = new String[]{};
        definitionArray = definitionMap.keySet().toArray(definitionArray);

        definitionMenu.addItems(definitionArray);
        definitionMenu.setOnItemClickListener(new PopMenu.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                try {

                    currentDefinitionIndex = position;
                    defaultDefinition = definitionMap.get(definitionArray[position]);

                    if (isPrepared) {
                        currentPosition = player.getCurrentPosition();
                        if (player.isPlaying()) {
                            isPlaying = true;
                        } else {
                            isPlaying = false;
                        }
                    }

                    isPrepared = false;

                    setLayoutVisibility(View.GONE, false);
                    bufferProgressBar.setVisibility(View.VISIBLE);

                    demoApplication.getDRMServer().disconnectCurrentStream();
                    player.reset();
                    demoApplication.getDRMServer().reset();

                    player.setDisplay(surfaceHolder);

                    player.setDefinition(getApplicationContext(), defaultDefinition);

                } catch (IOException e) {
                    Log.e("player error", e.getMessage());
                }

            }
        });
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        skbProgress.setSecondaryProgress(percent);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            resetHideDelayed();

            switch (v.getId()) {
                case R.id.backPlayList:
                    if (isPortrait() || isLocalPlay) {
                        finish();
                        if (getBackSelectionId != null) {
                            getBackSelectionId = selectionIdGet;
                            saveStudyTime();
                        }
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                    break;
                case R.id.iv_fullscreen:
                    if (isPortrait()) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                    break;
                case R.id.tv_definition:
                    definitionMenu.showAsDropDown(v);
                    break;
                case R.id.iv_lock:
                    if (lockView.isSelected()) {
                        lockView.setSelected(false);
                        setLayoutVisibility(View.VISIBLE, true);
                        toastInfo("已解开屏幕");
                    } else {
                        lockView.setSelected(true);
                        setLandScapeRequestOrientation();
                        setLayoutVisibility(View.GONE, true);
                        lockView.setVisibility(View.VISIBLE);
                        toastInfo("已锁定屏幕");
                    }
                    break;
                case R.id.iv_center_play:
                case R.id.iv_play:
                    changePlayStatus();
                    break;
                case R.id.iv_download_play:
                    downloadCurrentVideo();
                    break;
                case R.id.iv_top_menu:
                    setLayoutVisibility(View.GONE, false);
                    showTopPopupWindow();
                    break;
                //选集
                case R.id.tv_change_video:
                    setLayoutVisibility(View.GONE, false);
                    showChangeVideoWindow();
                    break;
                case R.id.iv_video_back:
                    changeToBackVideo();
                    break;
                case R.id.iv_video_next:
                    changeToNextVideo(false);
                    break;
            }
        }
    };

    // 设置横屏的固定方向，禁用掉重力感应方向
    private void setLandScapeRequestOrientation() {
        int rotation = wm.getDefaultDisplay().getRotation();
        // 旋转90°为横屏正向，旋转270°为横屏逆向
        if (rotation == Surface.ROTATION_90) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (rotation == Surface.ROTATION_270) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }
    }

    private void changeToNextVideo(boolean isCompleted) {
        if (directoryFragment.adapter != null && directoryFragment.adapter.currentAdapter != null) {
            directoryFragment.adapter.currentAdapter.automChangeItem();
        }
        playTime = current_time;
        getBackSelectionId = selectionIdGet;
        saveStudyTime();
        posIndex++;
        Log.i("hw", "===========自动播放完成================" + getBackSelectionId);
        if (posIndex > listsize.size() || posIndex == listsize.size()) {
            posIndex = 0;
//            for (int i = 0; i < directoryFragment.totalList.size(); i++) {
//                for (int j = 0; j < directoryFragment.totalList.get(i).getChapterList().size(); j++) {
//                    for (int k = 0; k < directoryFragment.totalList.get(i).getChapterList().get(j).getSelectionList().size(); k++) {
//                        PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean item = directoryFragment.totalList.get(i).getChapterList().get(j).getSelectionList().get(k);
//                        if (item.getSelectionId() == selectionIdGet) {
//                            if (j < directoryFragment.totalList.get(i).getChapterList().size() - 1) {
//                                listsize = directoryFragment.totalList.get(i).getChapterList().get(j).getSelectionList();
//                            } else if ((j == directoryFragment.totalList.get(i).getChapterList().size() - 1) && i < directoryFragment.totalList.size() - 1) {
//                                listsize = directoryFragment.totalList.get(i + 1).getChapterList().get(0).getSelectionList();
//                            } else {
//                                listsize = directoryFragment.totalList.get(0).getChapterList().get(0).getSelectionList();
//                            }
//                        }
//                    }
//                }
//            }
        }
        Log.e("gxj", "gxj=====position" + directoryFragment.adapter.currentRootPosition + "|" + directoryFragment.adapter.currentGroupPosition + "|" + directoryFragment.adapter.currentChildPosition);
        changeVideo(posIndex, isCompleted);
    }

    private void changeToBackVideo() {
        int currentPosition = getCurrentVideoIndex();
        int length = PlayFragment.playVideoIds.length;
        int position = 0;
        if (currentPosition == 0) {
            position = length - 1;
        } else {
            position = --currentPosition;
        }
        changeVideo(position, false);
    }

    PlayChangeVideoPopupWindow playChangeVideoPopupWindow;

    private void showChangeVideoWindow() {
        if (playChangeVideoPopupWindow == null) {
            initPlayChangeVideoPopupWindow();
        }
        playChangeVideoPopupWindow.setSelectedPosition(getCurrentVideoIndex()).showAsDropDown(rlPlay);
    }

    private int getCurrentVideoIndex() {
        return Arrays.asList(PlayFragment.playVideoIds).indexOf(videoId);
    }

    private void initPlayChangeVideoPopupWindow() {
        playChangeVideoPopupWindow = new PlayChangeVideoPopupWindow(this, surfaceView.getHeight());

        playChangeVideoPopupWindow.setItem(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeVideo(position, false);
                playChangeVideoPopupWindow.setSelectedPosition(position);
                playChangeVideoPopupWindow.refreshView();
            }
        });
    }

    PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean bean;

    private void changeVideo(int position, boolean isCompleted) {
        if (isCompleted) {
            updateCompleteDataPosition();
        } else {
            updateDataPosition();
        }

        isPrepared = false;

        setLayoutVisibility(View.GONE, false);
        bufferProgressBar.setVisibility(View.VISIBLE);
        ivCenterPlay.setVisibility(View.GONE);

        currentPosition = 0;
        currentPlayPosition = 0;

        timerTask.cancel();

        //videoId = PlayFragment.playVideoIds[position];

        if (playChangeVideoPopupWindow != null) {
            playChangeVideoPopupWindow.setSelectedPosition(getCurrentVideoIndex()).refreshView();
        }
        bean = listsize.get(position);
        videoId = bean.getVideoId();
        sectionId = bean.getSelectionId();
        selectionIdGet = bean.getSelectionId();
//        coursePlayTime = String.valueOf(bean.getPlayTime());
        videoIdText.setText(bean.getSelectionName());
        getSelectionName = bean.getSelectionName();

        player.pause();
        player.stop();
        player.reset();
        player.setDefaultDefinition(defaultDefinition);
        player.setVideoPlayInfo(videoId, ConfigUtil.USERID, ConfigUtil.API_KEY, "", FreePlayActivity.this);
        player.setDisplay(surfaceHolder);
        demoApplication.getDRMServer().reset();
        player.prepareAsync();
        directoryFragment.adapter.getSelectionId = sectionId;
        directoryFragment.adapter.notifyDataSetChanged();

//        //解决自动播放点击错位
//        SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt("sectionId",sectionId);
//        editor.apply();
    }

    PlayTopPopupWindow playTopPopupWindow;

    private void showTopPopupWindow() {
        if (playTopPopupWindow == null) {
            initPlayTopPopupWindow();
        }
        playTopPopupWindow.showAsDropDown(rlPlay);
    }

    private void initPlayTopPopupWindow() {
        playTopPopupWindow = new PlayTopPopupWindow(this, surfaceView.getHeight());
        playTopPopupWindow.setSubtitleCheckLister(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_subtitle_open:// 开启字幕
                        currentScreenSizeFlag = 0;
                        //   subtitleText.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_subtitle_close:// 关闭字幕
                        currentScreenSizeFlag = 1;
                        //    subtitleText.setVisibility(View.GONE);
                        break;
                }
            }
        });

        playTopPopupWindow.setScreenSizeCheckLister(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int position = 0;
                switch (checkedId) {
                    case R.id.rb_screensize_full:
                        position = 0;
                        break;
                    case R.id.rb_screensize_100:
                        position = 1;
                        break;
                    case R.id.rb_screensize_75:
                        position = 2;
                        break;
                    case R.id.rb_screensize_50:
                        position = 3;
                        break;
                }

                Toast.makeText(getApplicationContext(), screenSizeArray[position], Toast.LENGTH_SHORT).show();
                RelativeLayout.LayoutParams params = getScreenSizeParams(position);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                surfaceView.setLayoutParams(params);
            }
        });

    }

    private void downloadCurrentVideo() {
        if (DataSet.hasDownloadInfo(videoId)) {
            Toast.makeText(this, "文件已存在", Toast.LENGTH_SHORT).show();
            return;
        }

        DownloadController.insertDownloadInfo(videoId, videoId, getSelectionName, userCourseId, sectionId, courseId, mImagePath);
        Toast.makeText(this, "文件已加入下载队列", Toast.LENGTH_SHORT).show();
    }

    private void toastInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        int progress = 0;

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (isLocalPlay) {
                if (getPlayPercent * 100 >= 100) {
                    player.seekTo(progress);
                    playerHandler.postDelayed(hidePlayRunnable, 5000);
                }
            } else {
                if (getPlayPercent * 100 >= 100) {
                    player.seekTo(progress);
                    playerHandler.postDelayed(hidePlayRunnable, 5000);
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            playerHandler.removeCallbacks(hidePlayRunnable);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (networkConnected || isLocalPlay) {
                this.progress = progress * player.getDuration() / seekBar.getMax();
            }
        }
    };

    VerticalSeekBar.OnSeekBarChangeListener seekBarChangeListener = new VerticalSeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            currentVolume = progress;
            volumeSeekBar.setProgress(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            playerHandler.removeCallbacks(hidePlayRunnable);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            playerHandler.postDelayed(hidePlayRunnable, 5000);
        }

    };

    // 控制播放器面板显示
    private boolean isDisplay = false;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // 监测音量变化
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN
                || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {

            int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (currentVolume != volume) {
                currentVolume = volume;
                volumeSeekBar.setProgress(currentVolume);
            }

            if (isPrepared) {
                setLayoutVisibility(View.VISIBLE, true);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * @param visibility 显示状态
     * @param isDisplay  是否延迟消失
     */
    private void setLayoutVisibility(int visibility, boolean isDisplay) {
        if (player == null || player.getDuration() <= 0) {
            return;
        }

        playerHandler.removeCallbacks(hidePlayRunnable);

        this.isDisplay = isDisplay;

        if (definitionMenu != null && visibility == View.GONE) {
            definitionMenu.dismiss();
        }

        if (isDisplay) {
            playerHandler.postDelayed(hidePlayRunnable, 5000);
        }

        if (isPortrait()) {
            ivFullscreen.setVisibility(visibility);

            lockView.setVisibility(View.GONE);

            volumeLayout.setVisibility(View.GONE);
            tvDefinition.setVisibility(View.GONE);
            tvChangeVideo.setVisibility(View.GONE);
            ivTopMenu.setVisibility(View.GONE);
            ivBackVideo.setVisibility(View.GONE);//后退按钮
            ivNextVideo.setVisibility(View.GONE);//前进显示
        } else {
            ivFullscreen.setVisibility(View.GONE);

            lockView.setVisibility(visibility);
            if (lockView.isSelected()) {
                visibility = View.GONE;
            }

            volumeLayout.setVisibility(visibility);
            tvDefinition.setVisibility(visibility);
            tvChangeVideo.setVisibility(visibility);
            ivTopMenu.setVisibility(visibility);
            //横屏显示快进快退(默认不显示)
//            ivBackVideo.setVisibility(visibility);
//            ivNextVideo.setVisibility(visibility);
        }

        if (isLocalPlay) {
            ivDownload.setVisibility(View.GONE);
            ivTopMenu.setVisibility(View.GONE);

            ivBackVideo.setVisibility(View.GONE);
            ivNextVideo.setVisibility(View.GONE);
            tvChangeVideo.setVisibility(View.GONE);
            tvDefinition.setVisibility(View.GONE);
            ivFullscreen.setVisibility(View.INVISIBLE);
        }

        playerTopLayout.setVisibility(visibility);
        playerBottomLayout.setVisibility(visibility);
    }

    @SuppressLint("HandlerLeak")
    private Handler alertHandler = new Handler() {
        AlertDialog.Builder builder;
        AlertDialog.OnClickListener onClickListener = new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        };

        @Override
        public void handleMessage(Message msg) {

            String message = "";
            boolean isSystemError = false;
            if (ErrorCode.INVALID_REQUEST.Value() == msg.what) {
                message = "无法播放此视频，请检查视频状态";

            } else if (ErrorCode.NETWORK_ERROR.Value() == msg.what) {
                message = "无法播放此视频，请检查网络状态";
            } else if (ErrorCode.PROCESS_FAIL.Value() == msg.what) {
                message = "无法播放此视频，请检查帐户信息";
            } else {
                isSystemError = true;
                //toastInfo("视频异常，无法播放。");
                // player.pause();
                saveStudyTime();
                player.stop();
                initPlayInfo();
            }

            if (!isSystemError) {
                builder = new AlertDialog.Builder(FreePlayActivity.this);
                dialog = builder.setTitle("提示").setMessage(message)
                        .setPositiveButton("OK", onClickListener)
                        .setCancelable(false).show();
            }

            super.handleMessage(msg);
        }

    };

    @Override
    public boolean onError(MediaPlayer mp, final int what, int extra) {
        Log.d("recognize", "" + "---> onError" + "    what= " + what);
        if (DWMediaPlayer.MEDIA_ERROR_DRM_LOCAL_SERVER == what) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // toastInfo("server_error");
                    saveStudyTime();
                    player.stop();
                    initPlayInfo();
                }
            });
        }

        Message msg = new Message();
        msg.what = what;
        if (alertHandler != null) {
            alertHandler.sendMessage(msg);
        }
        //此处返回false，在触发onError事件的时候会同时触发onCompletion事件，返回true，在触发onError事件的时候不会触发onCompletion事件
        //return false;
        return true;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        setSurfaceViewLayout();
    }


    // 重置隐藏界面组件的延迟时间
    private void resetHideDelayed() {
        playerHandler.removeCallbacks(hidePlayRunnable);
        playerHandler.postDelayed(hidePlayRunnable, 5000);
    }

    // 手势监听器类
    private class MyGesture extends GestureDetector.SimpleOnGestureListener {

        private Boolean isVideo;
        private float scrollCurrentPosition;
        private float scrollCurrentVolume;

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (lockView.isSelected()) {
                return true;
            }
            if (isVideo == null) {
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    isVideo = true;
                } else {
                    isVideo = false;
                }
            }

            if (isVideo.booleanValue()) {
                parseVideoScroll(distanceX);
            } else {
                parseAudioScroll(distanceY);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        private void parseVideoScroll(float distanceX) {
            if (!isDisplay) {
                setLayoutVisibility(View.VISIBLE, true);
            }

            scrollTotalDistance += distanceX;

            float duration = (float) player.getDuration();

            float width = wm.getDefaultDisplay().getWidth() * 0.75f; // 设定总长度是多少，此处根据实际调整
            //右滑distanceX为负
            float currentPosition = scrollCurrentPosition - (float) duration * scrollTotalDistance / width;

            if (currentPosition < 0) {
                currentPosition = 0;
            } else if (currentPosition > duration) {
                currentPosition = duration;
            }

            if (isLocalPlay) {
                player.seekTo((int) currentPosition);
                playDuration.setText(ParamsUtil.millsecondsToStr((int) currentPosition));
                int pos = (int) (skbProgress.getMax() * currentPosition / duration);
                skbProgress.setProgress(pos);
            } else {
                if (getPlayPercent * 100 == 100) {
                    player.seekTo((int) currentPosition);
                    playDuration.setText(ParamsUtil.millsecondsToStr((int) currentPosition));
                    int pos = (int) (skbProgress.getMax() * currentPosition / duration);
                    skbProgress.setProgress(pos);
                }
            }
        }

        private void parseAudioScroll(float distanceY) {
            if (!isDisplay) {
                setLayoutVisibility(View.VISIBLE, true);
            }
            scrollTotalDistance += distanceY;

            float height = wm.getDefaultDisplay().getHeight() * 0.75f;
            // 上滑distanceY为正
            currentVolume = (int) (scrollCurrentVolume + maxVolume * scrollTotalDistance / height);

            if (currentVolume < 0) {
                currentVolume = 0;
            } else if (currentVolume > maxVolume) {
                currentVolume = maxVolume;
            }

            volumeSeekBar.setProgress(currentVolume);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            scrollTotalDistance = 0f;
            isVideo = null;

            scrollCurrentPosition = (float) player.getCurrentPosition();
            scrollCurrentVolume = currentVolume;

            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (lockView.isSelected()) {
                return true;
            }
            if (!isDisplay) {
                setLayoutVisibility(View.VISIBLE, true);
            }
            changePlayStatus();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (isDisplay) {
                setLayoutVisibility(View.GONE, false);
            } else {
                setLayoutVisibility(View.VISIBLE, true);
            }
            return super.onSingleTapConfirmed(e);
        }
    }

    private void changePlayStatus() {
        if (player.isPlaying()) {
            player.pause();
            ivCenterPlay.setVisibility(View.VISIBLE);
            ivPlay.setImageResource(R.drawable.smallbegin_ic);
        } else {
            player.start();
            ivCenterPlay.setVisibility(View.GONE);
            ivPlay.setImageResource(R.drawable.smallstop_ic);
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case DWMediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (player.isPlaying()) {
                    bufferProgressBar.setVisibility(View.VISIBLE);
                }
                break;
            case DWMediaPlayer.MEDIA_INFO_BUFFERING_END:
                bufferProgressBar.setVisibility(View.GONE);
                break;
        }
        return false;
    }

    // 获得当前屏幕的方向
    private boolean isPortrait() {
        int mOrientation = getApplicationContext().getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        } else {
            return true;
        }
    }

    private int mX, mY, mZ;
    private long lastTimeStamp = 0;
    private Calendar mCalendar;
    private SensorManager sensorManager;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null) {
            return;
        }

        if (!lockView.isSelected() && (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)) {
            int x = (int) event.values[0];
            int y = (int) event.values[1];
            int z = (int) event.values[2];
            mCalendar = Calendar.getInstance();
            long stamp = mCalendar.getTimeInMillis() / 1000l;

            int second = mCalendar.get(Calendar.SECOND);// 53

            int px = Math.abs(mX - x);
            int py = Math.abs(mY - y);
            int pz = Math.abs(mZ - z);

            int maxvalue = getMaxValue(px, py, pz);
            if (maxvalue > 2 && (stamp - lastTimeStamp) > 1) {
                lastTimeStamp = stamp;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
            mX = x;
            mY = y;
            mZ = z;
        }
    }

    /**
     * 获取一个最大值
     *
     * @param px
     * @param py
     * @param pz
     * @return
     */
    private int getMaxValue(int px, int py, int pz) {
        int max = 0;
        if (px > py && px > pz) {
            max = px;
        } else if (py > px && py > pz) {
            max = py;
        } else if (pz > px && pz > py) {
            max = pz;
        }
        return max;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onBackPressed() {

        if (isPortrait() || isLocalPlay) {
            super.onBackPressed();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onResume() {
        Log.d("recognize", "" + "---> onResume");
//        boolean isConnected = NetworkUtils.isConnected(MediaPlayActivity.this);
        // || isLocalPlay && !isConnected  //加在判断里面
        //开启人脸识别定时器
        if (needRecognized) {
            startFaceRecognitionTimer();
        }


        if (isFreeze) {
            isFreeze = false;
            if (isPrepared) {
                player.start();
            }
        } else {
            if (isPlaying != null && isPlaying.booleanValue() && isPrepared) {
                player.start();
            }
        }
        super.onResume();
        if (!isLocalPlay) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        }
        if (isExist && !isLocalPlay) {
//            if (recognizeSuccess) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        initPlayInfo();
//                    }
//                }, 100);
//            } else {
            initDownLoadData();
//            }
            isExist = false;
        }
    }

    @Override
    public void onPause() {
        Log.d("recognize", "" + "---> onPause" + "   isPrepared= " + isPrepared);
        //关闭定时器
        stopFaceRecognitionTimer();

        if (isPrepared) {
            // 如果播放器prepare完成，则对播放器进行暂停操作，并记录状态
            if (player.isPlaying()) {
                //暂停
                player.pause();
                ivCenterPlay.setVisibility(View.VISIBLE);
                ivPlay.setImageResource(R.drawable.smallbegin_ic);
                setLayoutVisibility(View.VISIBLE, false);
                isPlaying = true;
            } else {
                isPlaying = false;
                player.start();
                ivCenterPlay.setVisibility(View.GONE);
                ivPlay.setImageResource(R.drawable.smallstop_ic);
            }
            player.pause();
            ivCenterPlay.setVisibility(View.GONE);
        } else {
            // 如果播放器没有prepare完成，则设置isFreeze为true
            isFreeze = true;
        }
        super.onPause();
        getBackSelectionId = selectionIdGet;
        saveStudyTime();
        isExist = true;
        recognizeSuccess = true;
    }

    @Override
    protected void onStop() {
        if (!isLocalPlay) {
            sensorManager.unregisterListener(this);
            setLandScapeRequestOrientation();
        }
        super.onStop();
    }

    private void updateDataPosition() {
        if (isLocalPlay) {
            return;
        }

        if (currentPlayPosition > 0 && lastVideoPosition != null) {
            lastVideoPosition.setPosition(currentPosition);
            videoPositionDBHelper.updateVideoPosition(lastVideoPosition);
        }
    }

    private void updateCompleteDataPosition() {
        lastVideoPosition.setPosition(0);
        videoPositionDBHelper.updateVideoPosition(lastVideoPosition);
    }

    @Override
    protected void onDestroy() {
        Log.d("recognize", "" + "---> onDestroy");

        mediaPlayWeakReference = null;

        if (timerTask != null) {
            timerTask.cancel();
        }
        playerHandler.removeCallbacksAndMessages(null);
        playerHandler = null;

        alertHandler.removeCallbacksAndMessages(null);
        alertHandler = null;

        updateDataPosition();

        if (player != null) {
            player.reset();
            player.release();
            player = null;
        }

        demoApplication.getDRMServer().disconnectCurrentStream();

        if (dialog != null) {
            dialog.dismiss();
        }
        if (!isLocalPlay) {
            networkInfoTimerTask.cancel();
        }
        getBackSelectionId = selectionIdGet;


        if (bind != null) {
            bind.unbind();
        }

        needRecognized = false;
        recognizeSuccess = false;
        if (isLocalPlay) {
            //离线有网保存学习进度
            SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
            String mVideoId = getIntent().getStringExtra("videoId");
            int userCourseId = getIntent().getIntExtra("userCourseId", 0);
            int selectionId = getIntent().getIntExtra("selectionId", 0);
            int selectId = getIntent().getIntExtra("selectId", 0);
            getBackSelectionId = selectionId;
            lastVideoPosition = videoPositionDBHelper.getVideoPosition(String.valueOf(selectId));
            if (lastVideoPosition == null) {
                lastVideoPosition = new VideoPosition(String.valueOf(selectId), 0);
            }
            sharedPreferences.edit().putInt("currentPosition" + mVideoId, this.currentPosition).apply();
        }
        saveStudyTime();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        if (isPrepared) {
            // 刷新界面
            setLayoutVisibility(View.GONE, false);
            setLayoutVisibility(View.VISIBLE, true);
        }

        lockView.setSelected(false);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            rlBelow.setVisibility(View.VISIBLE);
            ivFullscreen.setImageResource(R.drawable.fullscreen_close);

            if (playChangeVideoPopupWindow != null) {
                playChangeVideoPopupWindow.dismiss();
            }

            if (playTopPopupWindow != null) {
                playTopPopupWindow.dismiss();
            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            rlBelow.setVisibility(View.GONE);
            ivFullscreen.setImageResource(R.drawable.fullscreen_open);
        }

        setSurfaceViewLayout();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (isLocalPlay) {
            toastInfo("播放完成！");
            finish();
            saveStudyTime();
            return;
        }
        if (isPrepared) {
            CharSequence text = playDuration.getText();
            String s = text.toString();
            Integer seconds = DateUtil.HoursFormatSeconds(s);
            int duration = player.getDuration();
            Log.e("getDuration", seconds + ",," + duration);
            player.stop();
            //自动播放下一个视频
            changeToNextVideo(false);
//            if (seconds>=duration/1000-1){
//                lastVideoPosition.setPosition(0);
//                videoPositionDBHelper.updateVideoPosition(lastVideoPosition);
//                player.stop();
//                initPlayInfo();
//            }else {
//                //Toast.makeText(MediaPlayActivity.this, "bfwc", Toast.LENGTH_SHORT).show();
//                player.stop();
//                initPlayInfo();
//            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getBackSelectionId != null) {
            getBackSelectionId = selectionIdGet;
            saveStudyTime();
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            getBackSelectionId = selectionIdGet;
            saveStudyTime();
        }
        return super.onKeyDown(keyCode, event);
    }

    //保存学时接口
    private void saveStudyTime() {
        CharSequence text = playDuration.getText();
        String s = text.toString();
        Integer playTime = DateUtil.HoursFormatSeconds(s);
        if (playTime != null) {
            if (lastVideoPosition != null) {
                lastVideoPosition.setPosition(playTime * 1000);
                videoPositionDBHelper.updateVideoPosition(lastVideoPosition);
                Log.e("selecIdTest", "444,seconds:+" + playTime + "selectionIdGet:" + selectionIdGet + "userCourseId:" + userCourseId + "post," + videoId);
                PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                aClass.getPMyLessonPlayTimeData(playTime, getBackSelectionId, token, userCourseId).enqueue(new Callback<PLessonPlayTimeBean>() {
                    @Override
                    public void onResponse(Call<PLessonPlayTimeBean> call, Response<PLessonPlayTimeBean> response) {
                        if (response != null) {
                            PLessonPlayTimeBean body = response.body();
                            if (body != null) {
                                if (body.getErrMsg() == null && body.getCode().equals("00000")) {
//                                    Toast.makeText(MediaPlayActivity.this, "保存播放时长成功" + getBackSelectionId, Toast.LENGTH_SHORT).show();
                                }
                            } else {
//                                Toast.makeText(MediaPlayActivity.this, "保存播放时长失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PLessonPlayTimeBean> call, Throwable t) {
                        if (!isLocalPlay) {
                            Toast.makeText(FreePlayActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private Timer recognizeTimer;
    private int mIntervalTime = 0;
    private int intervalTime = 0;
    private TimerTask recognizeTimerTask;

    /**
     * @author dxx
     * 开启人脸识别定时器
     */
    private void startFaceRecognitionTimer() {

        stopFaceRecognitionTimer();

        recognizeTimer = new Timer();
        //开启定时任务
        recognizeTimerTask = new TimerTask() {
            @Override
            public void run() {
//                Log.d("recognize","mIntervalTime= "+mIntervalTime);
//                getBackSelectionId = selectionIdGet;
                if (mIntervalTime >= intervalTime) {
                    mIntervalTime = 0;
                    startFaceRecognition();//开始识别
                } else if (player != null && player.isPlaying()) {
                    mIntervalTime++;
                } else {
                    return;
                }
            }
        };
        recognizeTimer.schedule(recognizeTimerTask, 0, 1000);
    }

    private void stopFaceRecognitionTimer() {
        if (recognizeTimer != null) {
            recognizeTimer.cancel();
            recognizeTimer = null;
        }
        if (recognizeTimerTask != null) {
            recognizeTimerTask.cancel();
            recognizeTimerTask = null;
        }
    }

    private boolean isNeedVerify() {
        SharedPreferences userDb = getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
        final int needVerify = userDb.getInt(Constant.IS_NEED_VERIFY_KEY, 1);
//        boolean isConnected = NetworkUtils.isConnected(MediaPlayActivity.this);
        if (needVerify == 1) {
            needRecognized = true;
            return true;
        }
        return false;
    }

    /**
     * @author FHS dxx
     */
    private void setRecognizedIntervalTime() {
        SharedPreferences sp = getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
        String strIntervalTime = sp.getString(Constant.INTERVAL_TIME_KEY, "60");
        if (strIntervalTime != null) {
            intervalTime = Integer.parseInt(strIntervalTime);
//            intervalTime = 40;
        }

    }

    private void startFaceRecognition() {
        int mSeconds = 0;
        if (playDuration != null) {
            CharSequence text = playDuration.getText();
            String s = text.toString();
            Integer seconds = DateUtil.HoursFormatSeconds(s);
            if (seconds != null) {
                playTime = seconds;
                mSeconds = seconds;
            } else {
                playTime = 0;
            }
        }

        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        //初始化人脸识别SDK
        FaceRecognitionUtils.initContrastFaceRecognition(FreePlayActivity.this);
        Intent intent = new Intent(FreePlayActivity.this, FaceRecognitionActivity.class);
        Bundle bundle = new Bundle();
//        int mSelectionId;
//        if (getBackSelectionId == null) {
//            mSelectionId = selectionIdGet == null ? 0 : selectionIdGet;
//        } else {
//            mSelectionId = selectionIdGet;
//            selectionIdGet = getBackSelectionId;
//        }
        getBackSelectionId = selectionIdGet;
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", courseId);
        bundle.putString("token", token);
        bundle.putInt("selectionId", selectionIdGet);
        bundle.putBoolean("isLocalPlay", isLocalPlay);
        bundle.putInt("playTime", playTime);
        bundle.putInt("seconds", mSeconds);
        bundle.putInt("rootIn", 2);
        bundle.putString("videoId", videoId);
        bundle.putDouble("getPlayPercent", getPlayPercent);
        bundle.putString("getSelectionName", getSelectionName);
        currentPosition = player.getCurrentPosition();
//        bundle.putInt("currentPosition", currentPosition);
        bundle.putInt("posIndex", posIndex);
        bundle.putSerializable("listsize", listsize);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
//        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            recognizeSuccess = true;
            needRecognized = true;
//            recognizeHandler.postDelayed(recognizeRunnable,1000);
        }
    }


    private boolean comeFromRecognition() {
        boolean isComeFromRecognition = getIntent().getBooleanExtra("isComeFromRecognition", false);
        return isComeFromRecognition;
    }

    public double getPercent() {
        double pos = 0;
        // 更新播放进度
//        currentPlayPosition = player.getCurrentPosition();
        int duration = player.getDuration();
        if (duration > 0) {
            pos = currentPlayPosition * 1.00 / (duration * 1.00);
        }
        Log.e("gxj-getPercent", currentPlayPosition + "|" + duration + "|" + pos);
        return (pos);
    }

}
