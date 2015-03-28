package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.MakeBuilding;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey
 * at 11/12/14 10:17 PM
 */
public class BuildPost extends MakeBuilding {

    @Override
    protected List<TerrainType> getApplicableTerrains() {
        return Arrays.asList(TerrainType.GRASS, TerrainType.SAND, TerrainType.HILL);
    }

    @Override
    protected int getTurnsRequired() {
        return 3;
    }

    @Override
    protected BuildingType getBuildingType() {
        return BuildingType.TRADE_POST;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.BUILD_POST;
    }
}
