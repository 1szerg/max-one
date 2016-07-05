package com.gmail.user0abc.max_one.model;

import com.gmail.user0abc.max_one.model.ai.AiProcessor;

import java.io.Serializable;

/*Created by Sergey on 10/24/2014.*/
public class Player implements Serializable {
    public boolean isAi;
    private PlayerStatus playerStatus;
    public int banner;
    public AiProcessor aiProcessor;
    private boolean stillInGame = true;

    public Player() {
    }

    public Player(boolean isAiPlayer, int playersBanner) {
        isAi = isAiPlayer;
        banner = playersBanner;
        setPlayerStatus(new PlayerStatus());
    }

    public Player(boolean ai) {
        isAi = ai;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player");
        sb.append(" ").append(banner);
        return sb.toString();
    }

    public void setDead() {
        stillInGame = false;
    }

    public boolean isStillInGame() {
        return stillInGame;
    }
}
