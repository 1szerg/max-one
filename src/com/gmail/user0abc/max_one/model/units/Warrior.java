package com.gmail.user0abc.max_one.model.units;

import com.gmail.user0abc.max_one.model.actions.units.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.actions.units.UnitAction;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 1/19/2015.
 */
public class Warrior extends Unit {
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
    public UnitType getUnitType() {
        return UnitType.WARRIOR;
    }

    @Override
    public boolean isActionAvailable(AbilityType abilityType, MapTile tile) {
        return true;
    }

    @Override
    public UnitAction getAction(AbilityType abilityType) {
        return ActionFactory.createAction(abilityType);
    }
}
