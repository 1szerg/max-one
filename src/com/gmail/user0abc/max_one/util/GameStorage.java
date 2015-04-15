package com.gmail.user0abc.max_one.util;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.entities.Entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey
 * at 10/31/14 10:13 PM
 */
public class GameStorage {
    private static GameStorage storage;
    private GameContainer gameContainer;
    private Map<Player, List<Entity>> entitiesMap;

    private GameStorage() {
        entitiesMap = new HashMap<>();
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

    public Map<Player, List<Entity>> getEntitiesMap() {
        return entitiesMap;
    }

    public void setEntitiesMap(Map<Player, List<Entity>> entitiesMap) {
        this.entitiesMap = entitiesMap;
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
