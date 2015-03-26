package com.gmail.user0abc.max_one.model.entities.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.Attack;
import com.gmail.user0abc.max_one.model.actions.AttackType;
import com.gmail.user0abc.max_one.model.entities.Protection;
import com.gmail.user0abc.max_one.model.entities.ProtectionFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.*;

/*Created by Sergey on 1/19/2015.*/
public class Warrior extends Unit {

    public Warrior() {}

    @Override
    public Ability getAction(AbilityType abilityType) {
        if (owner.getGold() < 1) {
            return null;
        }
        return super.getAction(abilityType);
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.WARRIOR;
    }

    @Override
    public List<TerrainType> getPassableTerrain() {
        return Arrays.asList(TerrainType.GRASS, TerrainType.TREE);
    }

    @Override
    public void executeAction(AbilityType abilityType, GameContainer game, MapTile tile) {

    }

    @Override
    public List<AbilityType> getAvailableActions() {
        return Arrays.asList(
                AbilityType.MOVE_ACTION,
                AbilityType.ATTACK_TILE,
                AbilityType.WAIT_ACTION,
                AbilityType.DELETE_UNIT
        );
    }

    public static Protection defaultProtection() {
        return ProtectionFactory.makeProtection(1.0, 0.8, 1.2);
    }
}
