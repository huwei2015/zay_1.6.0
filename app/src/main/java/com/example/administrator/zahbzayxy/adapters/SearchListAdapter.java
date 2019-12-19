package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.SearchTestBean;

import java.util.List;

/**
 * @author {ZWJ}
 * @date 2019/1/11 0011.
 * description:
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>{
    List<SearchTestBean.DataBean>listBeans;
    Context mContext;

    public SearchListAdapter(List<SearchTestBean.DataBean> listBeans, Context mContext) {
        this.listBeans = listBeans;
        this.mContext = mContext;
    }

    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, final int position) {
        String content = listBeans.get(position).getContent();
       // holder.item_searchList_tv.setText(DelHTMLTag.delHTMLTag(content)+"");
        holder.item_searchList_tv.setText(Html.fromHtml(content));
        holder.item_searchList_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemOnClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBeans.size()>0?listBeans.size():0;
    }

    public class SearchListViewHolder extends RecyclerView.ViewHolder{
        TextView item_searchList_tv;
        LinearLayout item_searchList_ll;

        public SearchListViewHolder(View itemView) {
            super(itemView);
            item_searchList_tv=itemView.findViewById(R.id.item_searchList_tv);
            item_searchList_ll=itemView.findViewById(R.id.item_searchList_ll);

        }
    }
    private SearchListAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SearchListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemOnClick(int position);
    }
}
