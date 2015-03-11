package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

/**
 * Created by Sergey
 * at 11/12/14 10:15 PM
 */
public class DeleteUnit extends Ability {

    @Override
    public boolean execute(GameContainer game, MapTile selectedTile) {
        if (selectedTile != null && selectedTile.unit != null
                && selectedTile.building != null
                && selectedTile.building.getBuildingType().equals(BuildingType.TOWN)
                && selectedTile.building.getOwner().equals(selectedTile.unit.getOwner())) {
            selectedTile.unit = null;
        }
        return true;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.DELETE_UNIT;
    }

}
