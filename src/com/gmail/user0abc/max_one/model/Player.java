package com.gmail.user0abc.max_one.model;

import java.io.Serializable;

/*Created by Sergey on 10/24/2014.*/
public class Player implements Serializable {
    public boolean ai;
    private int apples;
    private int gold;
    public int banner;
    public AiProcessor aiProcessor;

    public Player() {
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player");
        sb.append(" ").append(banner);
        return sb.toString();
    }
}
