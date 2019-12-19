package com.example.administrator.zahbzayxy.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MyChengJiDetailAdapter;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiBean;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiListBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.myviews.MyListView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//模考记录
public class NewMyChengJiActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_text_age;
    private PopupWindow pop;
    ListView listView = null;
    private List<NewMyChengJiBean.DataEntity.LibsEntity> datas = new ArrayList<>();
    private SelectAgeAdapter mSelectAdapter;
    private List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> chengJiList = new ArrayList<>();
    private MyChengJiDetailAdapter chengJiDetailAdapter;
    private LayoutInflater inflater;
    private String token;
    private MyListView myChengji_Detail_lv;
    private int getlibId;
    private int scorSize;
    List<NewMyChengJiBean.DataEntity.LibsEntity.ScoresEntity> scores1;
    //折线图
    private ImageView myChegJiBack_iv;
    int examType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_my_cheng_ji);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        initData();
        initView();
    }

    private void initListViewData(int libId) {
        chengJiDetailAdapter = new MyChengJiDetailAdapter(chengJiList, NewMyChengJiActivity.this, getlibId);
        myChengji_Detail_lv.setAdapter(chengJiDetailAdapter);
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyTiKuGradeData(1, 50, token, libId, examType).enqueue(new Callback<NewMyChengJiListBean>() {
            @Override
            public void onResponse(Call<NewMyChengJiListBean> call, Response<NewMyChengJiListBean> response) {
                int code = response.code();
                if (code == 200) {
                    NewMyChengJiListBean body = response.body();
                    if (body != null) {
                        NewMyChengJiListBean.DataEntity data = body.getData();
                        if (data != null) {
                            List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> examScores = data.getExamScores();
                            chengJiList.clear();
                            chengJiList.addAll(examScores);
                            chengJiDetailAdapter.notifyDataSetChanged();
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<NewMyChengJiListBean> call, Throwable t) {
                Toast.makeText(NewMyChengJiActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initView() {
        inflater = LayoutInflater.from(this);
        tv_text_age = (TextView) findViewById(R.id.tv_text_age);
        tv_text_age.setOnClickListener(this);
        myChengji_Detail_lv = (MyListView) findViewById(R.id.myChengji_detail_lv);
        myChegJiBack_iv = (ImageView) findViewById(R.id.myChengJiBack_iv);
        myChegJiBack_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initData() {
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getNewMyChengJiData(token).enqueue(new Callback<NewMyChengJiBean>() {
            @Override
            public void onResponse(Call<NewMyChengJiBean> call, Response<NewMyChengJiBean> response) {
                int code = response.code();
                if (code == 200) {
                    NewMyChengJiBean body = response.body();
                    if (body != null) {
                        String code1 = body.getCode();
                        if (code1.equals("00000")) {
                            NewMyChengJiBean.DataEntity data = body.getData();
                            if (data != null) {
                                List<NewMyChengJiBean.DataEntity.LibsEntity> libs = data.getLibs();
                                datas.addAll(libs);
                                if (libs != null) {
                                    int size = libs.size();
                                    if (size > 0) {
                                        String libName = libs.get(0).getLibName();
                                        tv_text_age.setText(libName);
                                        scores1 = libs.get(0).getScores();
                                        getlibId = libs.get(0).getLibId();
                                        initListViewData(getlibId);
                                        int isShowScoreLine = libs.get(0).getIsShowScoreLine();
                                        if (Integer.valueOf(isShowScoreLine) == 0) {//不显示曲线
                                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.guangzhexian);
                                            linearLayout.removeAllViews();//先remove再add可以实现统计图更新
                                            ImageView imageView = new ImageView(NewMyChengJiActivity.this);
                                            //imageView.setImageDrawable((R.mipmap.zhe1));
                                            imageView.setImageResource(R.mipmap.zhe1);
                                            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));  //image的布局方式
                                            linearLayout.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Toast.makeText(NewMyChengJiActivity.this, "请先购买B套餐,才可以查看成绩曲线", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        } else {
                                            String[] date = {};//X轴的标注
                                            int[] score = {};//图表的数据
                                            List<PointValue> mPointValues = new ArrayList<PointValue>();
                                            List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
                                            LineChartView lineChart = new LineChartView(NewMyChengJiActivity.this);
                                            //   lineChart.setLineChartData(null);
                                            scorSize = scores1.size();
                                            score = new int[scorSize];
                                            date = new String[scorSize];
                                            //x,y轴的初始化
                                            for (int j = 0; j < scorSize; j++) {
                                                int score1 = scores1.get(j).getScore();
                                                score[j] = score1;
                                                date[j] = String.valueOf(j + 1);
                                            }
                                            //获取x轴的标注
                                            for (int i = 0; i < date.length; i++) {
                                                mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
                                            }
                                            //获取坐标点
                                            for (int i = 0; i < score.length; i++) {
                                                mPointValues.add(new PointValue(i, score[i]));
                                            }

                                            Line line = new Line(mPointValues).setColor(Color.parseColor("#4495be"));  //折线的颜色
                                            List<Line> lines = new ArrayList<Line>();
                                            line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
                                            line.setCubic(false);//曲线是否平滑
                                            line.setStrokeWidth(1);//线条的粗细，默认是3
                                            line.setFilled(false);//是否填充曲线的面积
                                            line.setHasLabels(false);//曲线的数据坐标是否加上备注
                                            //		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
                                            line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
                                            line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
                                            lines.add(line);
                                            LineChartData data1 = new LineChartData();
                                            data1.setLines(lines);

                                            //坐标轴
                                            Axis axisX = new Axis(); //X轴
                                            axisX.setHasTiltedLabels(true);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
                                            //	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
                                            axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色

                                            axisX.setName("成绩分析统计图");  //表格名称
                                            axisX.setTextSize(14);//设置字体大小
                                            //	axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
                                            axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
                                            data1.setAxisXBottom(axisX); //x 轴在底部
                                            //	    data.setAxisXTop(axisX);  //x 轴在顶部
                                            axisX.setHasLines(true); //x 轴分割线


                                            Axis axisY = new Axis();  //Y轴
                                            axisY.setName("成绩/分");//y轴标注
                                            axisY.setTextSize(14);//设置字体大小
                                            data1.setAxisYLeft(axisY);  //Y轴设置在左边
                                            //data.setAxisYRight(axisY);  //y轴设置在右边
                                            //设置行为属性，支持缩放、滑动以及平移
                                            lineChart.setInteractive(true);
                                            lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
                                            lineChart.setMaxZoom((float) 3);//缩放比例

                                            lineChart.setLineChartData(data1);
                                            lineChart.setVisibility(View.VISIBLE);
                                            Viewport v = new Viewport(lineChart.getMaximumViewport());
                                            v.left = 0;
                                            v.right = 7;
                                            lineChart.setCurrentViewport(v);

                                            //制作曲线图，貌似不好下手只能变抄边理解，阿门
                                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.guangzhexian);
                                            linearLayout.removeAllViews();//先remove再add可以实现统计图更新
                                            linearLayout.addView(lineChart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT));

                                        }

                                    }
                                }
                            }
                        } else if (code1.equals("99999")) {
                            Toast.makeText(NewMyChengJiActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code1.equals("00003")) {
                            Toast.makeText(NewMyChengJiActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<NewMyChengJiBean> call, Throwable t) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_text_age) {
            if (pop == null) {
                listView = new ListView(this);
                listView.setDividerHeight(1);
                listView.setBackgroundResource(R.drawable.bg_gray_rectangle);
                listView.setCacheColorHint(0x00000000);
                mSelectAdapter = new SelectAgeAdapter(NewMyChengJiActivity.this, datas);
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
                WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
                p.height = (int) (d.getHeight() * 1.0) / 3; // 高度设置为屏幕的1.0
                p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
                p.alpha = 1.0f; // 设置本身透明度
                p.dimAmount = 0.0f; // 设置黑暗度
                pop = new PopupWindow(listView, p.width, p.height, false);
                pop.setTouchable(true);
                // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
                pop.setOutsideTouchable(true);
                pop.setAnimationStyle(R.style.take_photo_anim);

            } else {
                dismissPopWindow();
            }
            listView.setAdapter(mSelectAdapter);
            pop.setBackgroundDrawable(new ColorDrawable(Color.RED));
            pop.showAsDropDown(tv_text_age, 0, 0);

        }
    }

    public void dismissPopWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    class SelectAgeAdapter extends BaseAdapter {
        Context context;
        List<NewMyChengJiBean.DataEntity.LibsEntity> datas;

        public SelectAgeAdapter(Context context, List<NewMyChengJiBean.DataEntity.LibsEntity> datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas != null && datas.size() > 0 ? datas.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item, parent, false);
                holder.tv_name = convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NewMyChengJiBean.DataEntity.LibsEntity libsEntity = datas.get(position);

            final String libName = libsEntity.getLibName();
            if (!TextUtils.isEmpty(libName)) {
                holder.tv_name.setText(libName);
            }

            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("userLibId", getlibId + ",11111");

                    NewMyChengJiBean.DataEntity.LibsEntity libsEntity1 = datas.get(position);
                    final int libId = libsEntity1.getLibId();
                    getlibId = libId;
                    initListViewData(getlibId);
                    String libName2 = libsEntity1.getLibName();
                    tv_text_age.setText(libName2);
                    int isShowScoreLine = libsEntity1.getIsShowScoreLine();
                    if (Integer.valueOf(isShowScoreLine) == 0) {//不显示曲线
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.guangzhexian);
                        linearLayout.removeAllViews();//先remove再add可以实现统计图更新
                        ImageView imageView = new ImageView(NewMyChengJiActivity.this);
                        //imageView.setImageDrawable((R.mipmap.zhe1));
                        imageView.setImageResource(R.mipmap.zhe1);
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));  //image的布局方式
                        linearLayout.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(NewMyChengJiActivity.this, "请先购买独家解析题库,才可以查看成绩曲线", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dismissPopWindow();
                    } else {//显示曲线
                        String[] date = {};//X轴的标注
                        int[] score = {};//图表的数据
                        List<PointValue> mPointValues = new ArrayList<PointValue>();
                        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
                        String libName1 = libsEntity1.getLibName();
                        tv_text_age.setText(libName1);
                        scores1 = libsEntity1.getScores();
                        scorSize = scores1.size();
                        score = new int[scorSize];
                        date = new String[scorSize];
                        //x,y轴的初始化
                        for (int j = 0; j < scorSize; j++) {
                            int score1 = scores1.get(j).getScore();
                            score[j] = score1;
                            date[j] = String.valueOf(j + 1);
                        }
                        //获取x轴的标注
                        for (int i = 0; i < date.length; i++) {
                            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
                        }
                        //获取坐标点
                        for (int i = 0; i < score.length; i++) {
                            mPointValues.add(new PointValue(i, score[i]));
                        }
                        LineChartView lineChart = new LineChartView(NewMyChengJiActivity.this);
                        Line line = new Line(mPointValues).setColor(Color.parseColor("#4495be"));  //折线的颜色
                        List<Line> lines = new ArrayList<Line>();
                        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
                        line.setCubic(false);//曲线是否平滑
                        line.setStrokeWidth(1);//线条的粗细，默认是3
                        line.setFilled(false);//是否填充曲线的面积
                        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
                        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
                        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
                        lines.add(line);
                        LineChartData data = new LineChartData();
                        data.setLines(lines);

                        //坐标轴
                        Axis axisX = new Axis(); //X轴
                        axisX.setHasTiltedLabels(true);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
                        axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色

                        axisX.setName("成绩分析统计图");  //表格名称
                        axisX.setTextSize(14);//设置字体大小
                        //	axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
                        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
                        data.setAxisXBottom(axisX); //x 轴在底部
//	    data.setAxisXTop(axisX);  //x 轴在顶部
                        axisX.setHasLines(true); //x 轴分割线
                        Axis axisY = new Axis();  //Y轴
                        axisY.setName("成绩/分");//y轴标注
                        axisY.setTextSize(14);//设置字体大小
                        data.setAxisYLeft(axisY);  //Y轴设置在左边
                        //data.setAxisYRight(axisY);  //y轴设置在右边
                        //设置行为属性，支持缩放、滑动以及平移
                        lineChart.setInteractive(true);
                        lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
                        lineChart.setMaxZoom((float) 3);//缩放比例
                        lineChart.setLineChartData(data);
                        lineChart.setVisibility(View.VISIBLE);
                        /**注：下面的7，10只是代表一个数字去类比而已
                         * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
                         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
                         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
                         * 若设置axisX.setMaxLabelChars(int count)这句话,
                         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
                         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
                         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
                         * 并且Y轴是根据数据的大小自动设置Y轴上限
                         * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
                         */
                        Viewport v1 = new Viewport(lineChart.getMaximumViewport());
                        v1.left = 0;
                        v1.right = 7;
                        lineChart.setCurrentViewport(v1);
                        //制作曲线图，貌似不好下手只能变抄边理解
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.guangzhexian);
                        linearLayout.removeAllViews();//先remove再add可以实现统计图更新
                        linearLayout.addView(lineChart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        dismissPopWindow();
                    }
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv_name;
    }
}


