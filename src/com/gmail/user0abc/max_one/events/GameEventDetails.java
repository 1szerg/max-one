package com.gmail.user0abc.max_one.events;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;

/**
 * Created by Sergey
 * at 11/28/14 12:04 AM
 */
public class GameEventDetails {
    private Unit unit;
    private MapTile tile;
    private GameContainer game;

    public GameEventDetails(Unit unit, MapTile tile, GameContainer game) {
        this.unit = unit;
        this.tile = tile;
        this.game = game;
    }

    public Unit getUnit() {
        return unit;
    }

    public MapTile getTile() {
        return tile;
    }

    public GameContainer getGame() {
        return game;
    }
}
