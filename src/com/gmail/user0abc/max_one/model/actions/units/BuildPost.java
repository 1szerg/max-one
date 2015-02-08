package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.MakeBuilding;
import com.gmail.user0abc.max_one.model.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.buildings.BuildingsFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
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
        return Arrays.asList(TerrainType.GRASS);
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
