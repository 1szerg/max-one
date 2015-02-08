package com.gmail.user0abc.max_one.model.actions.buildings;


import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.UnitType;
import com.gmail.user0abc.max_one.model.units.UnitsFactory;

/*Created by Sergey on 1/26/2015.*/

public class MakeWarrior extends Ability {

    @Override
    public boolean execute(GameContainer game, MapTile selectedTile) {
        if (selectedTile != null && selectedTile.building != null && selectedTile.unit == null
                && selectedTile.building.getBuildingType().equals(BuildingType.TOWN)
                && selectedTile.building.getOwner().getApples() >= 2 && selectedTile.building.getOwner().getGold() >= 2) {
            selectedTile.unit = UnitsFactory.createUnitAtLocation(selectedTile, selectedTile.building.getOwner(), UnitType.WARRIOR);
        }
        return true;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.MAKE_WARRIOR;
    }
}
