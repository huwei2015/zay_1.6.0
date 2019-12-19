package com.example.administrator.zahbzayxy.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tu-mengting
 * 创建线程池
 * @date 2019/04/04
 */

public class ThreadPoolUtils {
    private ThreadPoolUtils() {

    }

    static BlockingQueue blockingQueue=new ArrayBlockingQueue<>(10);
    static ThreadPoolExecutor threadPoolExecutor = null;

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        if(null == threadPoolExecutor){
            synchronized (ThreadPoolUtils.class){
                if (null == threadPoolExecutor){
                    threadPoolExecutor=new ThreadPoolExecutor(10, 20,
                            1, TimeUnit.MINUTES, blockingQueue);
                }
            }
        }
        return threadPoolExecutor;
    }
}
