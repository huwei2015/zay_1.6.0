package com.example.administrator.zahbzayxy.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PLessonDetailFragment extends Fragment {
    Context context;
    private View view;
    private TextView detail_introduce_wv;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    public PLessonDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_plesson_detail, container, false);
        detail_introduce_wv=  view.findViewById(R.id.detail_introduce_wv);
        return view;
    }

}
