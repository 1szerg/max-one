package com.gmail.user0abc.max_one.model;

import java.io.Serializable;

/**
 * @author sergii.ivanov
 */
public class PlayerStatus implements Serializable {
    public int goldBalance;
    public int foodBalance;

    public PlayerStatus(){
        goldBalance = 0;
        foodBalance = 0;
    }

    public boolean validate(int food, int gold){
        return (goldBalance >= gold || gold == 0) && (foodBalance >= food || food == 0);
    }
}
