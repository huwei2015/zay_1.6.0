package com.example.administrator.zahbzayxy.beans;

import java.util.ArrayList;

/**
 * Created by ${ZWJ} on 2018/9/17 0017.
 */

public class UserInfoData {
    /**
     * 创建测试数据
     */
    public static ArrayList<Category> getHangYeData() {
        ArrayList<Category> listData = new ArrayList<Category>();
//        Category categoryOne = new Category("高危行业");
//        categoryOne.addItem("A 矿山");
//        categoryOne.addItem("B 金属冶炼");
//        categoryOne.addItem("C 建筑施工");
//        categoryOne.addItem("D 道路运输单位和危险物品生产单位");
//        categoryOne.addItem("E 危险物品的经营单位");
//        categoryOne.addItem("F 危险物品储存单位");

        Category categoryOne = new Category("煤矿");
//        categoryOne.addItem("A 矿山");
        categoryOne.addItem("B 井工煤矿");
        categoryOne.addItem("C 露天煤矿");
//        categoryOne.addItem("D 道路运输单位和危险物品生产单位");
//        categoryOne.addItem("E 危险物品的经营单位");
//        categoryOne.addItem("F 危险物品储存单位");
        Category categoryTwo = new Category("金属非金属矿山");
        categoryTwo.addItem("E 地下矿山");
        categoryTwo.addItem("F 露天矿山");

        Category categoryThree = new Category("危险化学品");
        categoryThree.addItem("H 危险化学品生产");
        categoryThree.addItem("I 危险化学品经营");
        categoryThree.addItem("J 危险化学品储存");

        Category categoryFour = new Category("烟花爆竹");
        categoryFour.addItem("L 烟花爆竹生产");
        categoryFour.addItem("M 烟花爆竹批发");

        Category categoryFive = new Category("建筑施工");

        Category categorySix = new Category("交通运输");

        Category categorySeven = new Category("工贸");
        categorySeven.addItem("Q 冶金");
        categorySeven.addItem("R 有色");
        categorySeven.addItem("S 机械");
        categorySeven.addItem("T 建材");
        categorySeven.addItem("U 轻工");
        categorySeven.addItem("Y 纺织");
        categorySeven.addItem("W 烟草");
        categorySeven.addItem("X 商贸");
//        categoryTwo.addItem("P 金融业");
//        categoryTwo.addItem("Q 房地产业");
//        categoryTwo.addItem("R 租赁和商务服务业");
//        categoryTwo.addItem("S 科学研究和技术服务业");
//        categoryTwo.addItem("T 水利、环境和公共设施管理业");
//        categoryTwo.addItem("U 居民服务、修理和其他服务业");
//        categoryTwo.addItem("V 教育");
//        categoryTwo.addItem("W 卫生和社会工作");
//        categoryTwo.addItem("X 文化、体育和娱乐业");
//        categoryTwo.addItem("Y 公共管理、社会保障和社会组织");
//        categoryTwo.addItem("Z 国际组织");
        listData.add(categoryOne);
        listData.add(categoryTwo);
        listData.add(categoryThree);
        listData.add(categoryFour);
        listData.add(categoryFive);
        listData.add(categorySix);
        listData.add(categorySeven);
        return listData;
    }



    /**
     * 创建测试数据
     */
    public static ArrayList<Category> getGangWeiData() {
        ArrayList<Category> listData = new ArrayList<Category>();
        Category categoryOne = new Category("主要负责人");
        categoryOne.addItem("A 主要负责人");

        Category categoryThree = new Category("管理人员");
        categoryThree.addItem("B 管理人员");

        Category categoryFour = new Category("一般从业人员");
        categoryFour.addItem("C 一般从业人员");

        Category categoryTwo = new Category("特种作业人员");
        categoryTwo.addItem("E 电工作业");
        categoryTwo.addItem("F 焊接与热切割作业");
        categoryTwo.addItem("G 高处作业");
        categoryTwo.addItem("H 制冷与空调作业");
        categoryTwo.addItem("I 煤矿安全作业");
        categoryTwo.addItem("J 金属非金属矿山安全作业");
        categoryTwo.addItem("K 石油天然气安全作业");
        categoryTwo.addItem("L 冶金（有色）生产安全作业");
        categoryTwo.addItem("M 危险化学品安全作业");
        categoryTwo.addItem("N 烟花爆竹安全作业");
        categoryTwo.addItem("O 其他作业特种作业人员");
        listData.add(categoryOne);
        listData.add(categoryThree);
        listData.add(categoryFour);
        listData.add(categoryTwo);
        return listData;
    }
}
