package com.gmail.user0abc.max_one.util;

import com.gmail.user0abc.max_one.model.GameContainer;

import java.util.Arrays;

/**
 * Created by Sergey
 * at 10/31/14 10:13 PM
 */
public class GameStorage {
    private static GameStorage storage;
    private GameContainer gameContainer;

    private GameStorage() {
    }

    public static GameStorage getStorage() {
        if (storage == null) {
            storage = new GameStorage();
        }
        return storage;
    }

    public GameContainer getGame() {
        return gameContainer;
    }

    public void setGame(GameContainer newGameContainer) {
        if(gameContainer != null){
            gameContainer = null;
        }
        gameContainer = newGameContainer;
    }

    private void clearContainer() {
        if(gameContainer.map != null){
            Arrays.fill(gameContainer.map, null);
            gameContainer.map = null;
        }
        if(gameContainer.players != null){
            gameContainer.players.clear();
            gameContainer.players = null;
        }
        gameContainer = null;
        System.gc();
    }
}
