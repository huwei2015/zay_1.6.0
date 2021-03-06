package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import com.example.administrator.zahbzayxy.myviews.RoundBackgroundColorSpan;
import com.example.administrator.zahbzayxy.myviews.VerticalImageSpan;
import com.example.administrator.zahbzayxy.myviews.VerticalImageSpanBottom;
import com.example.administrator.zahbzayxy.myviews.VerticalImageSpanRight;

public class TextAndPictureUtil {

    private TextAndPictureUtil mTextAndPictureUtil;

    private TextAndPictureUtil(){}

    public  TextAndPictureUtil getInstance(){
        if (mTextAndPictureUtil==null){
            synchronized (TextAndPictureUtil.class){
                if (mTextAndPictureUtil==null){
                    mTextAndPictureUtil=new TextAndPictureUtil();
                }
            }
        }
        return mTextAndPictureUtil;
    }

    public static SpannableString getText(Context mcontext, String text, int drawId){
        SpannableString spannableString = new SpannableString("  " + text);
        Drawable drawable = mcontext.getResources().getDrawable(drawId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        spannableString.setSpan(new VerticalImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getTextRightImg(Context mcontext, String text, int drawId){
        SpannableString spannableString = new SpannableString(text.trim()+" ");
        Drawable drawable = mcontext.getResources().getDrawable(drawId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        spannableString.setSpan(new VerticalImageSpanRight(drawable), text.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getTextBottomImg(Context mcontext, String text, int drawId){
        SpannableString spannableString = new SpannableString(text);
        Drawable drawable = mcontext.getResources().getDrawable(drawId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        spannableString.setSpan(new VerticalImageSpanBottom(drawable), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    public static SpannableString getTextCssStyle(Context mcontext, String text1,String text2){
        String text=text1+" "+text2;
        SpannableString spannableString = new SpannableString(text);
        int bgColor = Color.parseColor("#2c77f3");
        int textColor = Color.parseColor("#2c77f3");
        RoundBackgroundColorSpan span = new RoundBackgroundColorSpan(bgColor, textColor, 8);
        spannableString.setSpan(span, 0, text1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(14, true),0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
