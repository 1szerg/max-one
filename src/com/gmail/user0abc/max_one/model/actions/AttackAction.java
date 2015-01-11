package com.gmail.user0abc.max_one.model.actions;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;

/**
 * Created by Sergey
 * at 11/12/14 10:16 PM
 */
public class AttackAction extends UnitAction {

    @Override
    public AbilityType getActionType() {
        return null;  //TODO implement method
    }

    @Override
    public boolean isAvailable(GameContainer game, MapTile selectedTile, Unit selectedUnit) {
        return false;  //TODO implement method
    }

    @Override
    public void execute(GameContainer game, MapTile selectedTile, Unit selectedUnit) {
        //TODO implement method
    }

}
