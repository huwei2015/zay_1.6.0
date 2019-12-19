package com.example.administrator.zahbzayxy.keys;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import static android.text.TextUtils.isEmpty;

/**
 * Created by ${ZWJ} on 2017/1/4 0004.
 */
public class Keys {
    private static String UTF_8="UTF-8";
    /**
     * DES加密算法<br>
     * password的长度必须是8的倍数
     * @author nan.li
     * @param srcBytes
     * @param password
     * @return
     */
    public static byte[] desEncode(byte[] srcBytes, String password)
    {
        if (!checkDesPasswordLength(password))
        {
            return null;
        }
        try
        {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes(UTF_8));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(srcBytes);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES解密算法<br>
     * password的长度必须是8的倍数
     * @author nan.li
     * @param encodedBytes
     * @param password
     * @return
     * @throws Exception
     */
    public static byte[] desDecode(byte[] encodedBytes, String password)
            throws Exception
    {
        if (!checkDesPasswordLength(password))
        {
            return null;
        }
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes(UTF_8));
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(encodedBytes);
    }

    /**
     * 检查秘钥长度是否合法
     * @author nan.li
     * @param password
     * @return
     */
    private static boolean checkDesPasswordLength(String password)
    {
        if (isEmpty(password))
        {
            System.err.println("password不能为空！");
            return false;
        }
        int length = password.length();
        if (length != 16 && length != 24 && length != 32)
        {
            System.err.println("password的长度必须是16、24或32！当前的长度为：" + length);
            return false;
        }
        if (length == 24 || length == 32)
        {
            //AES algorithm allows 128, 192 or 256 bit key length. which is 16, 24 or 32 byte.
            //your keys length should be 16 , 24 or 32 bytes.
            //默认 Java 中仅支持 128 位密钥，当使用 256 位密钥的时候，会报告密钥长度错误
            // 你需要下载一个支持更长密钥的包。这个包叫做 Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 6
            // 下载之后，解压后，可以看到其中包含两个包：
            // local_policy.jar
            // US_export_policy.jar
            System.err.print("警告：您正在使用 192bit or 256bit的高阶密钥，请务必保证系统JRE环境中引入了JCE包，否则加密将出错！");
        }
        return true;
    }
}
