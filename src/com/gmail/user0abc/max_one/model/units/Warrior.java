package com.gmail.user0abc.max_one.model.units;

import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey on 1/19/2015.
 */
public class Warrior extends Unit {

    public Warrior() {
        maxActionPoints = 4;
        goldCost = 2;
        applesCost = 2;
        attackStrength = 2;
        defence = 2;
        health = 10;
    }

    @Override
    public List<AbilityType> allActions() {
        List<AbilityType> abilities = new ArrayList<AbilityType>();
        abilities.add(AbilityType.MOVE_ACTION);
        abilities.add(AbilityType.WAIT_ACTION);
        abilities.add(AbilityType.ATTACK_TILE);
        abilities.add(AbilityType.DELETE_UNIT);
        return abilities;
    }

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
    public boolean isActionAvailable(AbilityType abilityType, MapTile tile) {
        return true;
    }

    @Override
    public List<TerrainType> getPassableTerrain() {
        return Arrays.asList(TerrainType.GRASS, TerrainType.TREE);
    }
}
