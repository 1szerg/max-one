package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.exceptions.IllegalMove;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.buildings.BuildingsFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;
import com.gmail.user0abc.max_one.model.units.Unit;

import java.util.Arrays;

/**
 * Created by Sergey
 * at 11/12/14 10:17 PM
 */
public class BuildPost extends Ability {

    @Override
    public void execute(GameContainer game, MapTile selectedTile) {
        if(selectedTile != null && selectedTile.unit != null && selectedTile.building == null){
            TerrainType[] applicableTerrainTypes = {TerrainType.GRASS};
            if (Arrays.asList(applicableTerrainTypes).contains(selectedTile.terrainType)) {
                BuildingsFactory.createBuildingAtLocation(selectedTile, selectedTile.unit.getOwner(), BuildingType.TRADE_POST);
            }
        }
    }

}
