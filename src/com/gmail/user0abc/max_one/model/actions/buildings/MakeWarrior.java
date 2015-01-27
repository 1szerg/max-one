package com.gmail.user0abc.max_one.model.actions.buildings;


import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.units.Ability;
import com.gmail.user0abc.max_one.model.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.UnitType;
import com.gmail.user0abc.max_one.model.units.UnitsFactory;

import java.io.Serializable;

/*Created by Sergey on 1/26/2015.*/

public class MakeWarrior extends Ability {

    @Override
    public void execute(GameContainer game, MapTile selectedTile) {
        if(selectedTile != null && selectedTile.building != null && selectedTile.unit == null
                && selectedTile.building.getBuildingType().equals(BuildingType.TOWN)){
            selectedTile.unit = UnitsFactory.createUnitAtLocation(selectedTile, selectedTile.building.getOwner(), UnitType.WARRIOR);
        }
    }
}
