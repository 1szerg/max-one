package com.gmail.user0abc.max_one.util;

import com.gmail.user0abc.max_one.model.GameContainer;

/**
 * Created by Sergey
 * at 10/31/14 10:13 PM
 */
public class GameStorage {
    private static GameStorage storage;
    private GameContainer gameContainer;

    public static GameStorage getStorage() {
        if (storage == null) {
            storage = new GameStorage();
        }
        return storage;
    }

    private GameStorage() {
    }

    public GameContainer getGameContainer() {
        return gameContainer;
    }

    public void setGameContainer(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }
}
