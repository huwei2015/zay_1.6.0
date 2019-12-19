package com.example.administrator.zahbzayxy.myviews;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author {ZWJ}
 * @date 2019/1/7 0007.
 * description:
 */

public class MyFlowView extends FlowView{
    public MyFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       /* 从上边我们可以看出，我们没有改变widthMeasureSpec，仅仅是调用了makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST)方法，
        该方法会返回一个带有模式和大小信息的int值的，第一个参数Integer.MAX_VALUE >> 2，我们知道我们的控件的大小的最大值是用30位表示的
        （int占32位，其中前两位用来表示文章开头所说的三种模式）。那么Integer.MAX_VALUE来获取int值的最大值，然后右移2位，就得到这个最大值了
        因为是要最大值所以只能选择AT_MOST模式。最后 super.onMeasure（）方法将我们的高度值传进去就可以使ListView内容都展示出来了。*/
        int i = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, i);
    }
}
