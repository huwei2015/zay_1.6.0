package com.example.administrator.zahbzayxy.httputils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ${ZWJ} on 2017/3/8 0008.
 */
public class HttpUrlConnection {

    public  static void phoneByGet(String phone) {

        try {
            // 设置请求的地址 通过URLEncoder.encode(String s, String enc)
            // 使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded 格式
            /*String spec = "<span style="color:#ff6666;">http://172.16.237.200:8080/video/login.do</span>?username="
                    + URLEncoder.encode(userName, "UTF-8") + "&userpass="
                    + URLEncoder.encode(userPass, "UTF-8");*/
            // 根据地址创建URL对象(网络访问的url)
            String spec="http://192.168.1.233/users/security/chkphone?phone="+ URLEncoder.encode(phone, "UTF-8");
            URL url = new URL(spec);
            // url.openConnection()打开网络链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            // 设置请求的头
           /* urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");*/
            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                os.close();
                // 返回字符串
                String result = new String(os.toByteArray());
                Log.e("exitqqqqqqqqqqqqqqq","onResponse: " +result);

            } else {
                System.out.println("------------------链接失败-----------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public  static void codeByGet(String phone) {

        try {
            // 设置请求的地址 通过URLEncoder.encode(String s, String enc)
            // 使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded 格式
            /*String spec = "<span style="color:#ff6666;">http://172.16.237.200:8080/video/login.do</span>?username="
                    + URLEncoder.encode(userName, "UTF-8") + "&userpass="
                    + URLEncoder.encode(userPass, "UTF-8");*/
            // 根据地址创建URL对象(网络访问的url)
            String spec="http://192.168.1.233/users/security/registerVerCode?phone="+ URLEncoder.encode(phone, "UTF-8");
            URL url = new URL(spec);
            // url.openConnection()打开网络链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            // 设置请求的头
           /* urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");*/
            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                os.close();
                // 返回字符串
                String result = new String(os.toByteArray());
                Log.e("codeqqqqqqqqqqqqqqqq","onResponse: " +result);
            } else {
                System.out.println("------------------链接失败-----------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  static void RegisterByGet(String password,String phone,int regSource,String verCode) {

        try {
            String spec="http://192.168.1.233/users/security/registerUser?phone="+ URLEncoder.encode(phone, "UTF-8")
                    +"&password="+URLEncoder.encode(password, "UTF-8")+"&verCode="+URLEncoder.encode(verCode, "UTF-8")
                    +"&regSource="+URLEncoder.encode(Integer.toString(regSource), "UTF-8");

            URL url = new URL(spec);
            // url.openConnection()打开网络链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            // 设置请求的头
           /* urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");*/
            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                os.close();
                // 返回字符串
                String result = new String(os.toByteArray());
                Log.e("registeraaqqqqqqqqqqq","onResponse: " +result);
            } else {
                System.out.println("------------------链接失败-----------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public  static void loginByGet(String deviceNumber,String password,String phone) {

        try {
            String spec="http://192.168.1.233/users/security/login?deviceNumber="+ URLEncoder.encode(deviceNumber,"UTF-8")
                    +"&password="+URLEncoder.encode(password,"UTF-8")+"&phone="+URLEncoder.encode(phone,"UTF-8");
            URL url = new URL(spec);
            // url.openConnection()打开网络链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            // 设置请求的头
           /* urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");*/
            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                os.close();
                // 返回字符串
                String result = new String(os.toByteArray());
                Log.e("loginraaqqqqqqqqqqq","onResponse: " +result);
            } else {
                Log.e("loginraaqqqqqqqqqqq","onResponse: " +"链接错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public  static void TestListByGet(int currentPage,int pCateId,int pageSize,int subCateId) {

        try {
            String spec="http://192.168.1.233/quesLib/quesLibPage?currentPage="+ URLEncoder.encode(Integer.toString(currentPage),"UTF-8")
                    +"&pCateId="+URLEncoder.encode(Integer.toString(pCateId),"UTF-8")+
                    "&pageSize="+URLEncoder.encode(Integer.toString(pageSize),"UTF-8")+"subCateId="
                    +URLEncoder.encode(Integer.toString(subCateId),"UTF-8");
            URL url = new URL(spec);
            // url.openConnection()打开网络链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            // 设置请求的头
           /* urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");*/
            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                os.close();
                // 返回字符串
                String result = new String(os.toByteArray());
                Log.e("TestListraaqqqqqqqqqqq","onResponse: " +result);
            } else {
                Log.e("loginraaqqqqqqqqqqq","onResponse: " +"链接错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}



