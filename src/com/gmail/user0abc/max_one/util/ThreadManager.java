package com.gmail.user0abc.max_one.util;/*Created by Sergey on 3/25/2015.*/

public class ThreadManager {


    public static void runThread(final ThreadPayload payload, final ThreadEndListener callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                payload.work();
                callback.onThreadFinished(payload);
            }
        }).start();
    }


    public static void runThread(final ThreadPayload payload){
        new Thread(new Runnable() {
            @Override
            public void run() {
                payload.work();
            }
        }).start();
    }

}
