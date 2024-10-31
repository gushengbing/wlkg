package com.wlkg.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    //创建长度为10的线程池
    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable){
        es.submit(runnable);
    }


}
