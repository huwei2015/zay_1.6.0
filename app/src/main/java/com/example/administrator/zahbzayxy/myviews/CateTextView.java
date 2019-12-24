package com.example.administrator.zahbzayxy.myviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;

import javax.annotation.Nullable;

/**
 * Created by ${ZWJ} on 2017/1/4 0004.
 */

public class CateTextView extends AppCompatTextView {

    private String str;
    private int dataId;

    public CateTextView(Context context) {
        super(context);
    }

    public CateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //从attrs.xml 文件加载一个 叫MyImageView的declare-styleable 的对象
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CateTextView);
        //将attrs.xml 文件中的 imageview属性 与 类中的属性str关联
        str = typedArray.getString(R.styleable.CateTextView_textvalue);
        dataId = typedArray.getColor(R.styleable.CateTextView_dataId, Color.RED);
        //回收typeArray
        typedArray.recycle();
        setDataId(dataId);
        setStr(str);
        setTextSize(20);
        setGravity(Gravity.CENTER);
    }

    public CateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }
}
