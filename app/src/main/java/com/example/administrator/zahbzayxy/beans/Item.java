package com.example.administrator.zahbzayxy.beans;

/**
 * Created by Luhao on 2016/6/3.
 */
public class Item {
    public static final int ITEM = 0;//判断是否是普通item
    public static final int SECTION = 1;//判断是否是需要置顶悬停的item

    public final int type;//外部传入的标记
    public final Category category;//外部传入的数据，这里我们将它写成城市实体类，可以任意更换

    //如果该item是头，则集合标记失效
    public int sectionPosition;//头标记
    public int listPosition;//集合标记

    public Item(int type, Category category) {
        this.type = type;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
