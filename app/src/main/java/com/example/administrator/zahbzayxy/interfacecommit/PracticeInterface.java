package com.example.administrator.zahbzayxy.interfacecommit;

import com.example.administrator.zahbzayxy.beans.PCuoTiJiLuBean;
import com.example.administrator.zahbzayxy.beans.PLookCuoTiBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ${ZWJ} on 2017/4/11 0011.
 */
public interface PracticeInterface {
    //保存用户错题记录
    static final String saveUserErrorPath = "userExam/saveErrorRecord?";

  /*  @Query("userQuesLibId") Integer userQuesLibId,@Query("quesLibPackageId") Integer quesLibPackageId,
    @Query("quesLibId") Integer quesLibId*/
    //@GET(value = saveUserErrorPath)
   // Call<SuccessBean> saveUserData(@Query("errorRecords") String errorRecords, @Query("token") String token);
    //保存用户错题记录
   // static final String saveUserErrorPath = "userExam/saveErrorRecord?";
    @FormUrlEncoded
    @POST(value =saveUserErrorPath)
    Call<SuccessBean> saveUserData0(@Field("errorRecords") String errorRecords,
                                    @Field("token") String token);
    //查看用户错题记录
    static final String lookErrorPath = "userExam/errorRecordQuesLibPage?";
    @GET(value = lookErrorPath)
    Call<PCuoTiJiLuBean> lookErrorData(@Query("currentPage") Integer currentPage, @Query("pageSize") Integer pageSize, @Query("token") String token);
//查看错题
    static final  String lookError1Path="userExam/errorRecords?";
    @GET(value = lookError1Path)
    Call<PLookCuoTiBean> lookErrorDeatilData(@Query("quesLibId") Integer quesLibId, @Query("token") String token);
    //删除错题
    static final  String deleteErrorPath="userExam/deleteErrorRecord?";
    @GET(value = deleteErrorPath)
    Call<SuccessBean> deleteErrorDeatilData(@Query("quesLibId") Integer quesLibId,@Query("questionId") Integer questionId, @Query("token") String token);

}
