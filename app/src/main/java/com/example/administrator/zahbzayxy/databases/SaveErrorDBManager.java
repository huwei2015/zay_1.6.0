package com.example.administrator.zahbzayxy.databases;
import android.content.Context;
import android.text.TextUtils;
import com.example.administrator.zahbzayxy.beans.SaveUserErrorDbBean;
import com.example.administrator.zahbzayxy.beans.SaveUserErrorDbBeanDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
/**
 * Created by ${ZWJ} on 2017/4/14 0014.
 */
public class SaveErrorDBManager {
    SaveUserErrorDbBeanDao saveUserErrorDbBeanDao;
    private static SaveErrorDBManager db=null;

    public SaveErrorDBManager(Context context) {
        DaoSession daoSession = DaoMaster.newDevSession(context, "saveError");
        saveUserErrorDbBeanDao = daoSession.getSaveUserErrorDbBeanDao();
    }
    public static SaveErrorDBManager getInstance(Context context){
        if (db==null){
            db=new SaveErrorDBManager(context);
        }
        return db;

    }

    //插入单条数据
    public void insert(SaveUserErrorDbBean s){
        saveUserErrorDbBeanDao.insertOrReplace(s);
    }
    //删除单条数据
    public void delete(SaveUserErrorDbBean s){
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
    public List<SaveUserErrorDbBean> queryAll(){
        Query<SaveUserErrorDbBean> build =saveUserErrorDbBeanDao.queryBuilder().build();
        List<SaveUserErrorDbBean> list = build.list();
        return list;
    }
    //按queLibsId查询
    public List<SaveUserErrorDbBean> queryAll(int quesLibsId){
        QueryBuilder<SaveUserErrorDbBean> build =saveUserErrorDbBeanDao.queryBuilder();
        Query<SaveUserErrorDbBean>query=null;
        if (!TextUtils.isEmpty(String.valueOf(quesLibsId))){//按条件查询
            query=build.where(SaveUserErrorDbBeanDao.Properties.QuesLibId.eq(quesLibsId)).build();

        }else {//查询所有
            query=build.build();
        }

        List<SaveUserErrorDbBean> list = build.list();
        return list;
    }

    //按id删除
    public void deleteById(SaveUserErrorDbBean s){
        saveUserErrorDbBeanDao.delete(s);
    }
}
