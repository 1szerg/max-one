package com.gmail.user0abc.max_one.model.entities.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.Attack;
import com.gmail.user0abc.max_one.model.actions.AttackType;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.entities.Protection;
import com.gmail.user0abc.max_one.model.entities.ProtectionFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.*;

/**
 * Created by Sergey
 * at 11/3/14 10:27 PM
 */
public class Worker extends Unit {

    public Worker() {}

    @Override
    public UnitType getUnitType() {
        return UnitType.WORKER;
    }

    @Override
    public List<TerrainType> getPassableTerrain() {
        return Arrays.asList(TerrainType.GRASS, TerrainType.TREE);
    }

    @Override
    public void executeAction(AbilityType abilityType, GameContainer game, MapTile tile) {
        if (currentAction != null && !currentAction.getType().equals(abilityType)) {
            currentAction.cancel();
            // todo avoid action cancel when same action selected
        } else {
            currentAction = ActionFactory.createAction(abilityType);
        }
        if (currentAction != null) {
            currentAction.execute(game, tile);
        }
    }

    @Override
    public List<AbilityType> getAvailableActions() {
        return Arrays.asList(
                AbilityType.MOVE_ACTION,
                AbilityType.CLEAN_TERRAIN,
                AbilityType.BUILD_TOWN,
                AbilityType.BUILD_FARM,
                AbilityType.BUILD_POST,
                AbilityType.DELETE_UNIT);
    }

    @Override
    public Attack getAttack() {
        return null;
    }

    @Override
    public Protection getProtection() {
        return protection;
    }

    public static Protection defaultProtection(){
        return ProtectionFactory.makeProtection(0.7, 0.7, 0.7);
    }

}
