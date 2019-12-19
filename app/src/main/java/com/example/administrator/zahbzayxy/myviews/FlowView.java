package com.example.administrator.zahbzayxy.myviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ${Mrs.Zhao} on 2017/1/10.
 */
public class FlowView extends ViewGroup {
    public FlowView(Context context) {
        super(context);
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //  此方法返回的 LayoutParams  为  当前ViewGroup内所有的ChildView
    // 通过getLayoutParams方法获取到的 LayoutParams
    //  不重写该方法的时候  默认返回  ViewGroup.LayoutParams
  /*  @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }*/

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //  1.获取 全部的数据信息
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // wrap_content 的 情况下的计算：
        // 记录 当前View 的 最终宽高的 变量
        int width = 0;
        int height = 0;

        //记录每行的 宽高
        int lineWidth = 0;
        int lineHeight = 0;

        // 拿到所有的childView 的数目
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            // 循环遍历所有的ChildView
            View child = getChildAt(i);
            // 对当前childView 的宽高进行测量
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //  获取 child 的  布局参数

            // 获取 child 的实际宽高
            //getMeasuredWidth  该方法 等同于 getWidth  但是  getWidth方法 在 onMeasure方法中获取的值是  0
            int childWidth = child.getMeasuredWidth() + 10;
            int childHeight = child.getMeasuredHeight() + 10;

            // 实际 当前View  给child  填充的 宽度
            int groupWidth = widthSize - getPaddingLeft() - getPaddingRight();
            if (lineWidth + childWidth > groupWidth) {
                // 换行
                //1. 换行标志上一行的结束   对比取最大值
                width = Math.max(width, lineWidth);
                //2.高度 叠加
                height += lineHeight;
                //3.新开一行
                lineHeight = childHeight;
                lineWidth = childWidth;

            } else {
                // 正常依次叠加
                // 1.宽度叠加
                lineWidth += childWidth;
                // 2.高度取最大值
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一行
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight+20;
            }
        }


        //  判断 当前的 模式 是什么
        //  如果是   内容包裹 AT_MOST    则使用上面计算出来的 宽高   width
        //  如果是   其他情况 则直接使用   获取到的 宽高      widthSize
        setMeasuredDimension(width,height);
    }


    // 集合用于存储全部的 View
    ArrayList<ArrayList<View>> mAllViews = new ArrayList<>();
    // 集合 用于存储 每行的行高的集合
    ArrayList<Integer> heightList = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 当 该方法 多次执行的时候  需要清空数据保证数据准确性
        mAllViews.clear();
        heightList.clear();
        //获取当前View 的 宽度
        int width = getWidth();
        // 记录每行的 宽高
        int lineWidth = 0;
        int lineHeight = 0;
        // 记录每一行的View
        ArrayList<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int childWidth = child.getMeasuredWidth() + 10;
            int childHeight = child.getMeasuredHeight() + 10;
            //判断换行
            if (lineWidth + childWidth > width - getPaddingLeft() - getPaddingRight()) {
                // 换行记录上一行的行高
                heightList.add(lineHeight);
                // 换行 将上一行的 view 集合 添加到 全部的集合中
                mAllViews.add(lineViews);

                // 开启新的一行
                lineWidth = 0;
                lineHeight = 0;
                //  必须 new   不能clear
                lineViews = new ArrayList<>();
            }
            // 不换行的时候 执行
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
            lineViews.add(child);

        }
        // 记录最后一行的 行高
        heightList.add(lineHeight);
        //  添加最后一行的view 到 全部的集合中
        mAllViews.add(lineViews);


        // 获取 当前View 的 padding
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < mAllViews.size(); i++) {
            // 取出 每一行的 所有的View
            lineViews = mAllViews.get(i);
            lineHeight = heightList.get(i);
            // 循环遍历  每一行的View
            for (int j = 0; j < lineViews.size(); j++) {
                View view = lineViews.get(j);


                int childL = paddingLeft +5;
                int childT = paddingTop + 5;
                int childR = childL + view.getMeasuredWidth();
                int childB = childT + view.getMeasuredHeight();

                //  摆放View的位置
                view.layout(childL, childT, childR, childB);
                // 叠加   前一个View 的 实际宽度
                paddingLeft += view.getMeasuredWidth() + 10;
            }
            // 换行了    left 恢复
            paddingLeft =  getPaddingLeft();
            // 高度叠加
            paddingTop+=lineHeight;
        }
    }
}
