package com.example.administrator.zahbzayxy.httputils;

import com.example.administrator.zahbzayxy.beans.TestPracticeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/16 0016.
 */
public class HttpUtils {
    public static List<TestPracticeBean>getSaveJson(String json) {
        List<TestPracticeBean> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String title = obj.getString("title");
                String icon = obj.getString("icon");
                String desc = obj.getString("desc");
                String reviewcount = obj.getString("reviewcount");
                String id = obj.getString("id");
               //TestPracticeBean bean = new TestPracticeBean(title, icon, desc, reviewcount, id);
               // list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
