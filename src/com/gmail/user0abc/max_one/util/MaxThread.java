package com.gmail.user0abc.max_one.util;/*Created by Sergey on 3/26/2015.*/

public class MaxThread {

    public static void runThread(Runnable r){
        new Thread(r).start();
    }
}
