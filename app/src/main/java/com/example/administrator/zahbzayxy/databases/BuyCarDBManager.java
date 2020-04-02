package com.example.administrator.zahbzayxy.databases;

import android.content.Context;
import com.example.administrator.zahbzayxy.beans.BuyCarBean;
import com.example.administrator.zahbzayxy.beans.BuyCarBeanDao;
import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/2 0002.
 */
public class BuyCarDBManager {
    BuyCarBeanDao buyCarBeanDao;
    private static BuyCarDBManager db=null;
    private BuyCarDBManager(Context context){
        DaoSession daoSession= DaoMaster.newDevSession(context,"buyCar");
        buyCarBeanDao=daoSession.getBuyCarBeanDao();
    }
    public static BuyCarDBManager getInstance(Context context){
        if (db==null){
            db=new BuyCarDBManager(context);
        }
        return db;
    }
    //插入单条数据
    public void insert(BuyCarBean s){
        buyCarBeanDao.insertOrReplace(s);
    }
    //删除单条数据
    public void delete(BuyCarBean s){
       buyCarBeanDao.delete(s);
    }
    //删除所有数据
    public void deleteAll(){
       buyCarBeanDao.deleteAll();
    }
    //查询所有数据
    public List<BuyCarBean> queryAll(){
        Query<BuyCarBean> build =buyCarBeanDao.queryBuilder().build();
        List<BuyCarBean> list = build.list();
        return list;
    }
    //按id删除
    public void deleteById(BuyCarBean s){
       buyCarBeanDao.delete(s);
    }

}
