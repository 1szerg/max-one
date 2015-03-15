package com.gmail.user0abc.max_one.util;/*Created by Sergey on 3/14/2015.*/

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandUtil {
    private static Map<Integer, Random> randCache;


    public static Random getRand(int seed) {
        if (!getRandCache().containsKey(seed)){
            getRandCache().put(seed, new Random(seed));
        }
        return getRandCache().get(seed);
    }

    public static Map<Integer,Random> getRandCache() {
        if(randCache == null) randCache = new HashMap<>();
        return randCache;
    }
}
