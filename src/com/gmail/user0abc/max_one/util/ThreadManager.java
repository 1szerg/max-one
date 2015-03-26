package com.gmail.user0abc.max_one.util;/*Created by Sergey on 3/25/2015.*/

import android.os.Handler;
import android.os.Looper;

import java.util.List;

public class ThreadManager {
    private static Handler mainHandler;
    private static ThreadManager instance;
    private List<Thread> pool;


    public static void runThread(final ThreadPayload payload, final ThreadEndListener callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                payload.work();
                callback.onThreadFinished(payload);
            }
        }).start();
    }

    private void run(final ThreadPayload payload, final ThreadEndListener callback){
    }

    public ThreadManager() {
        mainHandler = new Handler(Looper.getMainLooper());
    }

    private static ThreadManager getManager() {
        if(instance == null) instance = new ThreadManager();
        return instance;
    }



}
