package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;

/**
 * Created by Sergey
 * at 11/12/14 10:14 PM
 */
public class RemoveBuildingAction extends UnitAction {

    @Override
    public void execute(GameContainer game, MapTile selectedTile, Unit selectedUnit) {
        if(selectedTile.building != null && selectedTile.building.getOwner().equals(selectedUnit.getOwner())){
            selectedTile.building = null;
        }
    }

}
