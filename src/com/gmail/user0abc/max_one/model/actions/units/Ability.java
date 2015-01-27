package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.io.Serializable;

/**
 * Created by Sergey
 * at 11/4/14 11:06 PM
 */
public abstract class Ability implements Serializable {

    public static boolean isAvailable(GameContainer game, MapTile selectedTile) {
        return true;
    }

    public abstract void execute(GameContainer game, MapTile selectedTile);
}
