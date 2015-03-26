package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.MakeBuilding;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey
 * at 11/4/14 11:12 PM
 */
public class BuildTown extends MakeBuilding {

    @Override
    protected List<TerrainType> getApplicableTerrains() {
        return Arrays.asList(TerrainType.GRASS);
    }

    @Override
    protected int getTurnsRequired() {
        return 5;
    }

    @Override
    protected BuildingType getBuildingType() {
        return BuildingType.TOWN;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.BUILD_TOWN;
    }

}
