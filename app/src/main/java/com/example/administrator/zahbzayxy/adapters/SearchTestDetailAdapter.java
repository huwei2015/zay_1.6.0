package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.SearchTestBean;

import java.util.List;

/**
 * @author {ZWJ}
 * @date 2019/1/15 0015.
 * description:
 */

public class SearchTestDetailAdapter extends RecyclerView.Adapter<SearchTestDetailAdapter.SearchTestDetailViewHolder>{

    List<SearchTestBean.DataBean> totalList;
    Context mContext;

    public SearchTestDetailAdapter(List<SearchTestBean.DataBean> totalList, Context mContext) {
        this.totalList = totalList;
        this.mContext = mContext;
    }

    @Override
    public SearchTestDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchTestDetailViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_search_test_detail_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(SearchTestDetailViewHolder holder, int position) {
        initFuYong(holder,position);
        StringBuilder stringBuilder=new StringBuilder();
        SearchTestBean.DataBean dataBean = totalList.get(position);
        String content = dataBean.getContent();
        holder.content_tv.setText(Html.fromHtml(content));

        List<SearchTestBean.DataBean.OptDtosBean> optDtos = dataBean.getOptDtos();

        holder.selectA_rb.setText("A."+optDtos.get(0).getContent());

        holder.selectB_rb.setText("B."+optDtos.get(1).getContent());

        int optSize = optDtos.size();
        if (optSize>=3){
            holder.selectC_rb.setVisibility(View.VISIBLE);
            holder.selectC_rb.setText("C."+optDtos.get(2).getContent());
        }if (optSize>=4){
            holder.selectD_rb.setVisibility(View.VISIBLE);
            holder.selectD_rb.setText("D."+optDtos.get(3).getContent());
        }if (optSize>=5){
            holder.selectE_rb.setVisibility(View.VISIBLE);
            holder.selectE_rb.setText("E."+optDtos.get(4).getContent());
        }if (optSize>=6){
            holder.selectF_rb.setText("F."+optDtos.get(5).getContent());
            holder.selectF_rb.setVisibility(View.VISIBLE);
        }if (optSize>=7){
            holder.selectG_rb.setText("G."+optDtos.get(6).getContent());
            holder.selectG_rb.setVisibility(View.VISIBLE);
        }
        for (int i=0;i<optSize;i++){
            int isRightAnswer = optDtos.get(i).getIsRightAnswer();
            if (isRightAnswer==1){
               if (i==0){
                   stringBuilder.append("A");
                   holder.selectA_rb.setTextColor(mContext.getResources().getColor(R.color.testBlueTv));
               }if (i==1){
                    stringBuilder.append("B");
                    holder.selectB_rb.setTextColor(mContext.getResources().getColor(R.color.testBlueTv));
                }if (i==2){
                    stringBuilder.append("C");
                    holder.selectC_rb.setTextColor(mContext.getResources().getColor(R.color.testBlueTv));
                }if (i==3){
                    stringBuilder.append("D");
                    holder.selectD_rb.setTextColor(mContext.getResources().getColor(R.color.testBlueTv));
                }if (i==4){
                    stringBuilder.append("E");
                    holder.selectE_rb.setTextColor(mContext.getResources().getColor(R.color.testBlueTv));
                }if (i==5){
                    stringBuilder.append("F");
                    holder.selectF_rb.setTextColor(mContext.getResources().getColor(R.color.testBlueTv));
                }if (i==6){
                    stringBuilder.append("G");
                    holder.selectG_rb.setTextColor(mContext.getResources().getColor(R.color.testBlueTv));
                }
            }
        }

        holder.answer_tv.setText("正确答案:"+stringBuilder.toString());
    }

    private void initFuYong(SearchTestDetailViewHolder holder, int position) {
        holder.selectA_rb.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.selectB_rb.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.selectC_rb.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.selectD_rb.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.selectE_rb.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.selectF_rb.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.selectG_rb.setTextColor(mContext.getResources().getColor(R.color.black));


    }


    @Override
    public int getItemCount() {
        return totalList.size();
    }

    public class SearchTestDetailViewHolder extends RecyclerView.ViewHolder{
        TextView content_tv,answer_tv;
        RadioButton selectA_rb,selectB_rb,selectC_rb,selectD_rb,selectE_rb,selectF_rb,selectG_rb;

        public SearchTestDetailViewHolder(View itemView) {
            super(itemView);
            content_tv=itemView.findViewById(R.id.item_search_detail_content_tv);
            answer_tv=itemView.findViewById(R.id.item_search_detail_answer_tv);
            selectA_rb=itemView.findViewById(R.id.item_search_detail_a_rb);
            selectB_rb=itemView.findViewById(R.id.item_search_detail_b_rb);
            selectC_rb=itemView.findViewById(R.id.item_search_detail_c_rb);
            selectD_rb=itemView.findViewById(R.id.item_search_detail_d_rb);
            selectE_rb=itemView.findViewById(R.id.item_search_detail_e_rb);
            selectF_rb=itemView.findViewById(R.id.item_search_detail_f_rb);
            selectG_rb=itemView.findViewById(R.id.item_search_detail_g_rb);
        }
    }
}
