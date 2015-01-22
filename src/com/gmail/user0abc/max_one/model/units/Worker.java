package com.gmail.user0abc.max_one.model.units;

import com.gmail.user0abc.max_one.model.actions.units.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.actions.units.UnitAction;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey
 * at 11/3/14 10:27 PM
 */
public class Worker extends Unit {

    public Worker() {
        maxActionPoints = 4;
        goldCost = 0;
        applesCost = 1;
    }

    @Override
    public List<AbilityType> allActions() {
        List<AbilityType> actions = new ArrayList<>();
        actions.add(AbilityType.MOVE_ACTION);
        actions.add(AbilityType.WAIT_ACTION);
        actions.add(AbilityType.BUILD_FARM);
        actions.add(AbilityType.BUILD_POST);
        actions.add(AbilityType.BUILD_TOWN);
        actions.add(AbilityType.REMOVE_BUILDING);
        actions.add(AbilityType.CLEAN_TERRAIN);
        actions.add(AbilityType.DELETE_UNIT);
        return actions;
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.WORKER;
    }

    @Override
    public boolean isActionAvailable(AbilityType abilityType, MapTile tile) {
        switch (abilityType) {
            case WAIT_ACTION:
                return true;
            case MOVE_ACTION:
                return actionPoints > 0;
            case BUILD_FARM:
                return tile.building == null && actionPoints > 0 && tile.terrainType.equals(TerrainType.GRASS);
            case BUILD_POST:
                return tile.building == null && actionPoints > 0 && tile.terrainType.equals(TerrainType.GRASS);
            case CLEAN_TERRAIN:
                return tile.terrainType.equals(TerrainType.TREE) && actionPoints > 0;
            case REMOVE_BUILDING:
                return tile.building != null && tile.building.getOwner().equals(this.owner) && actionPoints > 0;
            case DELETE_UNIT:
                return true;
        }
        return false;
    }

    @Override
    public UnitAction getAction(AbilityType abilityType) {
        return ActionFactory.createAction(abilityType);
    }
}
