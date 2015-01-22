package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.exceptions.IllegalMove;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;

import java.io.Serializable;

/**
 * Created by Sergey
 * at 11/4/14 11:06 PM
 */
public abstract class UnitAction implements Serializable {

    public static boolean isAvailable(GameContainer game, MapTile selectedTile, Unit selectedUnit) {
        return false;
    }

    public abstract void execute(GameContainer game, MapTile selectedTile, Unit selectedUnit) throws IllegalMove;
}
