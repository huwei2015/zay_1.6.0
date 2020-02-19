package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.R.attr.name;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ${ZWJ} on 2017/3/19 0019.
 */
public class ImageUtils {
//将图片转换成二进制流

    public static byte[] getBitmapByte(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //参数1转换类型，参数2压缩质量，参数3字节流资源
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    //将二进制流转换成图片（Bitmap）

    public static Bitmap getBitmapFromByte(byte[] temp){
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0,
                    temp.length);
            return bitmap;
        }else{
            return null;
        }
    }

    //将二进制流转换成图片（Drawable）

//    public Drawable getBitmapFromByte(byte[] temp){
//        if(temp != null){
//            Drawable drawable = Drawable.createFromStream(bais, "image");
//            return drawable ;
//        }else{
//            return null;
//        }
//    }

    //将Bitmap转换成Drawable

    public static Bitmap drawableToBitmap(Drawable drawable){

        int width = drawable.getIntrinsicWidth();

        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height,

                drawable.getOpacity() != PixelFormat.OPAQUE ?
                        Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0,0,width,height);

        drawable.draw(canvas);

        return bitmap;



    }
    public static Bitmap getBitmap(String imgPath){
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        // Bitmap bm = BitmapFactory.decodeFile(imgPath, options);

        int oldWidth = options.outWidth;
        int oldHeight = options.outHeight;

        int sampleWidth = oldWidth / 200;
        int sampleHeight = oldHeight / 200;

        int size = Math.max(sampleWidth, sampleHeight);

        options.inSampleSize = size;

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
        return bitmap;
    }

    /** 设置初始图片，写入本地文件testSP,KEY值：imageFirst */
    public static String saveBitmapToSharedPreferences(Bitmap bitmap,Context context) {
        // 第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        // 第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray,
                Base64.DEFAULT));
        // 第三步:将String保持至SharedPreferences
        SharedPreferences sharedPreferences = context
                .getSharedPreferences("testSP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imageFirst", imageString);
        editor.commit();
        return imageString;
    }

    /** 读取本地文件testSP，KEY:image */
    public static Bitmap getBitmapFromSharedPreferences(String image,Context context) {
        /** 传入初始状态的bitMap转出的String */
        //  String image = saveBitmapToSharedPreferences(context);
        SharedPreferences sharedPreferences = context
                .getSharedPreferences("testSP", MODE_PRIVATE);
        // 第一步:取出字符串形式的Bitmap
        /** 第二个值为初始状态 */
        String imageString = sharedPreferences.getString(name + "", image);
        // 第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                byteArray);
        // 第三步:利用ByteArrayInputStream生成Bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }

    /**
     * 质量压缩方法
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static byte[] compressImage(Bitmap image, int imageSize) {
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream isBm = null;
        try {
            baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            int i = 0;
            while ((baos.toByteArray().length / 1024) > imageSize) {    //循环判断如果压缩后图片是否大于imageSize,大于继续压缩
                i++;
                baos.reset();//重置baos即清空baos
                options -= 10;//每次都减少10
                if (options <= 0) options = 2;
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                if (options == 2) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (isBm != null) {
                try {
                    isBm.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos.toByteArray();
    }


}
