package com.gmail.user0abc.max_one.model;

import java.io.Serializable;

/*Created by sergii.ivanov on 10/24/2014.*/
public class Player implements Serializable {
    public boolean ai;
    private int apples;
    private int gold;
    public int banner;

    public Player(){}

    public Player(boolean isAiPlayer, int playersBanner) {
        ai = isAiPlayer;
        banner = playersBanner;
        setApples(0);
        setGold(0);
    }

    public int getApples() {
        return apples;
    }

    public void setApples(int apples) {
        this.apples = Math.max(0, apples);
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = Math.max(0, gold);
    }
}
