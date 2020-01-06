package com.example.administrator.zahbzayxy.interfaceserver;

import com.example.administrator.zahbzayxy.beans.AllFileBean;
import com.example.administrator.zahbzayxy.beans.FileDelBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by huwei.
 * Data 2019-12-24.
 * Time 12:51.
 * 全部附件接口
 */
public interface AllFileInterface {
    //全部附件
    static final String allFilePath="user/attachment/list";
    @POST(value=allFilePath)
    Call<AllFileBean> getAllFileData(@Query("pageNo") Integer pageNo,
                                     @Query("pageSize") Integer pageSize,
                                     @Query("attaFormat") Integer format,
                                     @Query("token") String token);
    //删除附件
    static final String DelFile = "user/attachment/delete";
    @POST(value = DelFile)
    Call<FileDelBean>getDelData(@Query("id") String id, @Query("token") String token);

}
