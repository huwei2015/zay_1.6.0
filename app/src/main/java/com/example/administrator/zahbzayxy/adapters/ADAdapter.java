package com.example.administrator.zahbzayxy.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by lijie on 2016/10/24.
 */
public class ADAdapter extends PagerAdapter {
    List<ImageView> list;
  public ADAdapter(List<ImageView> list) {
      this.list = list;
  }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(list.get(position));
    }
}
