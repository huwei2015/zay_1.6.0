package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.myviews.CateTextView;
import com.example.administrator.zahbzayxy.myviews.CustomLayout;
import com.example.administrator.zahbzayxy.utils.DisplayUtil;
import com.example.administrator.zahbzayxy.utils.TextAndPictureUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class ListClassifyAdapter extends BaseAdapter {
    private Context context;
    private List<CourseCatesBean.DataBean.Cates> list;
    String price, token;
    private OnItemClickListener mOnItemClickListener;
    private Integer cateId=0;
    private Integer s_cateId=0;
    private Map<Integer,CateTextView> cmap;
    private OnClickListener mOnClickListener;
    private Map<Integer,List<CourseCatesBean.DataBean.Cates>> catesLv3Map;
    private Map<String,CustomLayout> lv3Map;
    private Map<Integer,CateTextView> lv3cmap;
    private CateTextView clickLv2;

    private Integer level=3;

    public interface OnClickListener {
        public void setSelectedNum(int num);
    }

    public interface OnItemClickListener {
        //item点击事件
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public ListClassifyAdapter(Context context) {
        this.context = context;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ListClassifyAdapter(List<CourseCatesBean.DataBean.Cates> list, Context context, String token,Integer cateId,Integer level,Integer s_cateId) {
        this.list = list;
        this.context = context;
        this.token = token;
        mOnClickListener = (OnClickListener) context;
        this.level=level;
        this.s_cateId=s_cateId;
    }

    public ListClassifyAdapter(List<CourseCatesBean.DataBean.Cates> list, Context context, String token,Integer level,Integer s_cateId) {
        this.list = list;
        this.context = context;
        this.token = token;
        this.cateId=cateId;
        mOnClickListener = (OnClickListener) context;
        this.level=level;
        this.s_cateId=s_cateId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        myViewHold myViewHold;
        CourseCatesBean.DataBean.Cates cate = (CourseCatesBean.DataBean.Cates) getItem(position);

        if(cmap==null){
            cmap=new HashMap<Integer,CateTextView>();
        }
        if(catesLv3Map==null){
            catesLv3Map=new HashMap<>();
        }
        if(lv3Map==null){
           lv3Map=new HashMap<>();
        }
        if(lv3cmap==null){
            lv3cmap=new HashMap<>();
        }
        if(convertView==null){
            myViewHold = new myViewHold();
        }else{
            myViewHold= (ListClassifyAdapter.myViewHold) convertView.getTag();
        }
        if(!cmap.containsKey(cate.getId())) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_classify_layout, parent,false);
            myViewHold.itemClassify=convertView.findViewById(R.id.itemClassify);
            myViewHold.txtRL = convertView.findViewById(R.id.txtRL);
            myViewHold.mainClassify = convertView.findViewById(R.id.mainClassify);
            myViewHold.mainClassify.setText(cate.getCateName());
            if(position==0){
                myViewHold.itemClassify.setVisibility(View.GONE);
                myViewHold.mainClassify.setVisibility(View.GONE);
                myViewHold.txtRL.setVisibility(View.GONE);
            }
            if (cate.getChilds().size() > 0) {
                LinearLayout alllayout=new LinearLayout(context);
                LinearLayout.LayoutParams pall = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                alllayout.setLayoutParams(pall);
                alllayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout layout=new LinearLayout(context);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layout.setLayoutParams(p);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                int len=0;
                int i=0;;
                Resources resources = context.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                int width3 = dm.widthPixels;
                int limit=(width3/6*5)-DisplayUtil.dipToPix(context,36);

                String ids=",";
                for (CourseCatesBean.DataBean.Cates c : cate.getChilds()) {
                    boolean flag=true;
                    CateTextView tv = new CateTextView(context);
                    tv.setText(c.getCateName());
                    if(position==0){
                        tv.setVisibility(View.GONE);
                    }else{
                        tv.setId(c.getId());
                        tv.setText(c.getCateName());
                        tv.setDataId(c.getId());
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        if(c.getId()==s_cateId){
                            tv.setBackgroundResource(R.drawable.corner_text_view);
                            tv.setText(TextAndPictureUtil.getTextRightImg(context,  tv.getText().toString().trim(), R.mipmap.circle_right));
                            tv.setStr("1");
                        }else {
                            tv.setStr("0");
                            tv.setBackgroundResource(R.drawable.corner_text_view_normal);
                        }

                        int specTV = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        tv.measure(specTV,specTV);
                        int widthTV = tv.getMeasuredWidth();
                        len+=widthTV;
                        cmap.put(c.getId(), tv);
                        ids+=c.getId()+",";
                        if(c.getChilds().size()>0){
                            catesLv3Map.put(c.getId(),c.getChilds());
                        }
                        layout.addView(tv);
                        onclickItem(tv);

                        if(i<cate.getChilds().size()-1){
                            CateTextView temp_tv = new CateTextView(context);
                            temp_tv.setId(c.getId());
                            temp_tv.setText(cate.getChilds().get(i+1).getCateName());
                            temp_tv.setDataId(c.getId());
                            temp_tv.setStr("0");
                            temp_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                            temp_tv.setBackgroundResource(R.drawable.corner_text_view_normal);
                            //获取textView宽度
                            int specTemp = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            temp_tv.measure(specTemp,specTemp);
                            int tempWidth = temp_tv.getMeasuredWidth();
                            if((tempWidth+len)>=limit){
                                alllayout.addView(layout);
                                len=0;
                                //==================================多加一行
                                alllayout = addCustomLayout(alllayout,ids);
                                ids=",";
                                //===================================
                                layout=new LinearLayout(context);
                                LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                layout.setLayoutParams(p1);
                                layout.setOrientation(LinearLayout.HORIZONTAL);
                                flag=false;
                            }
                        }
                    }

                    if(len>limit && flag){
                        alllayout.addView(layout);
                        len=0;
                        //==================================多加一行
                        alllayout = addCustomLayout(alllayout,ids);
                        ids=",";
                        //===================================
                        layout=new LinearLayout(context);
                        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layout.setLayoutParams(p1);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                    }else{
                        if(i==cate.getChilds().size()-1){
                            alllayout.addView(layout);
                            //==================================多加一行
                            alllayout = addCustomLayout(alllayout,ids);
                            ids=",";
                            //===================================
                        }
                    }
                    i++;
                }
                myViewHold.txtRL.addView(alllayout);
            }
            if(position==0){
                convertView.setVisibility(View.GONE);
            }
            convertView.setTag(myViewHold);
        }else{
            myViewHold = (ListClassifyAdapter.myViewHold) convertView.getTag();
        }

        if(cmap.containsKey(cateId)){
            cmap.get(cateId).setBackgroundResource(R.drawable.corner_text_view);
            cmap.get(cateId).setText(TextAndPictureUtil.getTextRightImg(context,  cmap.get(cateId).getText().toString().trim(), R.mipmap.circle_right));
            cmap.get(cateId).setStr("1");
        }
        return convertView;
    }


    public LinearLayout addCustomLayout(LinearLayout alllayout,String ids){
        CustomLayout clayout=new CustomLayout(context);
        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        clayout.setLayoutParams(cp);
        clayout.setGrivate(3);
        clayout.setVisibility(View.GONE);
        lv3Map.put(ids,clayout);
        alllayout.addView(clayout);
        return alllayout;
    }

    /**
     * 二级分类点击事件
     * @param tv
     */
    public void onclickItem(CateTextView tv){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ((CateTextView) v).getStr();
                cateId=((CateTextView) v).getDataId();
                Log.i("================",str);
                if ("0".equals(str)) {
                    int dataId = ((CateTextView) v).getDataId();
                    for (Integer index : cmap.keySet()) {
                        CateTextView temp=cmap.get(index);
                        temp.setBackgroundResource(R.drawable.corner_text_view_normal);
                        temp.setText(temp.getText().toString().trim());
                        temp.setStr("0");
                    }
                    ((CateTextView) v).setBackgroundResource(R.drawable.corner_text_view);
                    ((CateTextView) v).setText(TextAndPictureUtil.getTextRightImg(context, ((CateTextView) v).getText().toString().trim(), R.mipmap.circle_right));
                    ((CateTextView) v).setStr("1");
                    clickLv2=((CateTextView) v);
                    if(level!=2){
                        for(String ids:lv3Map.keySet()){
                           if(ids.indexOf(","+dataId+",")!=-1){
                                CustomLayout clayout=lv3Map.get(ids);
                                clayout.removeAllViews();
                                List<CourseCatesBean.DataBean.Cates> cs=catesLv3Map.get(dataId);
                                if(cs!=null && cs.size()>0){
                                    lv3cmap.clear();
                                    int i=0;
                                    for(CourseCatesBean.DataBean.Cates cte:cs){
                                        CateTextView temp_tv = new CateTextView(context);
                                        temp_tv.setText(cte.getCateName());
                                        temp_tv.setDataId(cte.getId());
                                        temp_tv.setStr("0");
                                        temp_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                        temp_tv.setTextColor(context.getResources().getColor(R.color.shikan_text_color));
                                        temp_tv.setBackgroundResource(R.drawable.tv_padding);

                                        ImageView img=new ImageView(context);
                                        img.setImageDrawable(context.getResources().getDrawable(R.mipmap.fg_line));
                                        img.setPadding(0,DisplayUtil.dipToPix(context,14),0,0);
                                        lv3cmap.put(cte.getId(),temp_tv);
                                        onclickItemLv3(temp_tv);
                                        clayout.addView(temp_tv);
                                        if(i<(cs.size()-1)){
                                            clayout.addView(img);
                                        }
                                        i++;
                                    }
                                    clayout.setVisibility(View.VISIBLE);
                                }

                           }else{
                               CustomLayout clayout=lv3Map.get(ids);
                               clayout.setVisibility(View.GONE);
                           }
                        }
                    }else{
                        mOnClickListener.setSelectedNum(dataId);
                    }
                } else {
                    v.setBackgroundResource(R.drawable.corner_text_view_normal);
                    ((CateTextView) v).setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    ((CateTextView) v).setText(((CateTextView) v).getText().toString().trim());
                    ((CateTextView) v).setStr("0");
                    if(level==2){
                        mOnClickListener.setSelectedNum(0);
                    }
                }
            }
        });
    }

    /**
     * 三级分类点击事件
     * @param tv
     */
    public void onclickItemLv3(CateTextView tv){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ((CateTextView) v).getStr();
                cateId=((CateTextView) v).getDataId();
                if ("0".equals(str)) {
                    int dataId = ((CateTextView) v).getDataId();
                    for (Integer index : lv3cmap.keySet()) {
                        CateTextView temp=lv3cmap.get(index);
                        temp.setText(temp.getText().toString().trim());
                        temp.setBackgroundResource(R.drawable.tv_padding);
                        temp.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        temp.setStr("0");
                    }
                    ((CateTextView) v).setText(((CateTextView) v).getText().toString()+"  ");
                    Drawable drawableLeft = context.getResources().getDrawable(R.mipmap.copy_right);
                    ((CateTextView) v).setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                    ((CateTextView) v).setBackgroundResource(R.drawable.tv_padding_sel);
                    ((CateTextView) v).setStr("1");

                    //去掉点击 二级分类上边的对号
                    clickLv2.setBackgroundResource(R.drawable.corner_text_view_nopic);
                    clickLv2.setText(clickLv2.getText().toString().trim());
                    clickLv2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    clickLv2.setStr("1");

                    mOnClickListener.setSelectedNum(dataId);
                } else {
                    ((CateTextView) v).setText(((CateTextView) v).getText().toString().trim());
                    ((CateTextView) v).setBackgroundResource(R.drawable.tv_padding);
                    ((CateTextView) v).setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    ((CateTextView) v).setStr("0");
                }
            }
        });
    }


    static class myViewHold {
        LinearLayout itemClassify;
        LinearLayout txtRL;
        TextView mainClassify;
    }


}
