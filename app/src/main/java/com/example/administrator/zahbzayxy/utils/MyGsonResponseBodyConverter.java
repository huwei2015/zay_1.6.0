package com.example.administrator.zahbzayxy.utils;

import com.example.administrator.zahbzayxy.beans.SimpleBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Converter;

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private Gson gson;
    private Type type;

    public MyGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) {
        try {
            BufferedSource source = value.source();
            try {
                source.request(Long.MAX_VALUE);
            } catch (IOException e) {
                e.printStackTrace();
                SimpleBean simpleBean = new SimpleBean();
                return gson.fromJson(convert(simpleBean), type);
            }
            Buffer buffer = source.buffer();
            String responseBodyString = buffer.clone().readString(Charset.forName("UTF-8"));
            return gson.fromJson(responseBodyString, type);
        } finally {
            value.close();
        }
    }

    private String convert(SimpleBean simpleBean){
        return new Gson().toJson(simpleBean, SimpleBean.class);
    }

}
