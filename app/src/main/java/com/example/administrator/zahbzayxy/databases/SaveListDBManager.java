package com.example.administrator.zahbzayxy.databases;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.zahbzayxy.beans.DaoMaster;
import com.example.administrator.zahbzayxy.beans.DaoSession;
import com.example.administrator.zahbzayxy.beans.QuesListBean;
import com.example.administrator.zahbzayxy.beans.QuesListBeanDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/14 0014.
 */
public class SaveListDBManager {
    QuesListBeanDao saveUserErrorDbBeanDao;
    private static SaveListDBManager db=null;

    public SaveListDBManager(Context context) {
        DaoSession daoSession = DaoMaster.newDevSession(context, "saveList");
        saveUserErrorDbBeanDao = daoSession.getQuesListBeanDao();
    }
    public static SaveListDBManager getInstance(Context context){
        if (db==null){
            db=new SaveListDBManager(context);
        }
        return db;

    }

    //插入单条数据
    public void insert(QuesListBean s){
        saveUserErrorDbBeanDao.insertOrReplace(s);
    }
    //删除单条数据
    public void delete(QuesListBean s){
        saveUserErrorDbBeanDao.delete(s);
    }
    //删除所有数据
    public void deleteAll(){
        saveUserErrorDbBeanDao.deleteAll();
    }
    //按条件删除
    public void deleteAll(int quesLibsId){
        saveUserErrorDbBeanDao.deleteAll();
    }
    //查询所有数据
    public List<QuesListBean> queryAll(){
        Query<QuesListBean> build =saveUserErrorDbBeanDao.queryBuilder().build();
        List<QuesListBean> list = build.list();
        return list;
    }
    //按queLibsId查询
    public List<QuesListBean> queryAll(int quesLibsId){
        QueryBuilder<QuesListBean> build =saveUserErrorDbBeanDao.queryBuilder();
        Query<QuesListBean>query=null;
        if (!TextUtils.isEmpty(String.valueOf(quesLibsId))){//按条件查询
            query=build.where(QuesListBeanDao.Properties.QuesLibId.eq(quesLibsId)).build();

        }else {//查询所有
            query=build.build();
        }

        List<QuesListBean> list = build.build().list();
        return list;
    }

    //按id删除
    public void deleteById(QuesListBean s){
        saveUserErrorDbBeanDao.delete(s);
    }
}
