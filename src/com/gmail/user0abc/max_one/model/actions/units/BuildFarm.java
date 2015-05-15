package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.MakeBuilding;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey
 * at 11/12/14 10:16 PM
 */
public class BuildFarm extends MakeBuilding {

    @Override
    public List<TerrainType> getApplicableTerrains() {
        return Arrays.asList(TerrainType.GRASS, TerrainType.HILL, TerrainType.SAND);
    }

    @Override
    protected int getTurnsRequired() {
        return 3;
    }

    @Override
    protected BuildingType getBuildingType() {
        return BuildingType.FARM;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.BUILD_FARM;
    }

}
