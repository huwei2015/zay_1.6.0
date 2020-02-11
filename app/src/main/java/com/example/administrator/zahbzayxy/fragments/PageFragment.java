package com.example.administrator.zahbzayxy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.LoginActivity;


/**
 * author：luck
 * project：AppWhenThePage
 * package：com.luck.app.page.when_page
 * email：893855882@qq.com
 * data：2017/2/22
 */
public class PageFragment extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int index = args.getInt("index");
        int layoutId = args.getInt("layoutId");
        int count = args.getInt("count");
        Log.i("hw","hw========="+count);
        rootView = inflater.inflate(layoutId, null);
        // 滑动到最后一页有点击事件
        if (index == count - 1) {
            rootView.findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getActivity().finish();
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("loginMethod","home");
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }else if(index == count -2){
            rootView.findViewById(R.id.img_skip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }else if(index == count -3){
            rootView.findViewById(R.id.img_skip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }

        return rootView;
    }
}
