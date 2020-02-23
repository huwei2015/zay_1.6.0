package com.example.administrator.zahbzayxy.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Html;
import android.widget.TextView;


import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.ScreenUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class MImageGetter implements Html.ImageGetter {
    Context mContext;
    TextView mTv;

    public MImageGetter(TextView text, Context c) {
        this.mContext = c;
        this.mTv = text;

    }

    public Drawable getDrawable(String source) {
        final LevelListDrawable drawable = new LevelListDrawable();
        Picasso.with(mContext).load(source).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (bitmap != null) {
                    int temp = mContext.getResources().getDimensionPixelSize(R.dimen.dp_15_x);
                    int width = ScreenUtil.getScreenWidth(mContext)/2 - temp * 2;
                    int imgWidth;
                    int imgHeight;
                    if (bitmap.getWidth() < width/2){
                        imgWidth = width/2;
                        imgHeight = bitmap.getHeight() * width/2 / bitmap.getWidth();
                    }else if (bitmap.getWidth() < width) {
                        imgWidth = bitmap.getWidth();
                        imgHeight = bitmap.getHeight();
                    } else {
                        imgWidth = width;
                        imgHeight = bitmap.getHeight() * width / bitmap.getWidth();
                    }
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                    drawable.addLevel(1, 1, bitmapDrawable);
                    drawable.setBounds(0, 0, imgWidth, imgHeight);
                    drawable.setLevel(1);
                    mTv.invalidate();
                    mTv.setText(mTv.getText());
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        return drawable;
    }
}