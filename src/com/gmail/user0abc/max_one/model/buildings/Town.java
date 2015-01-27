package com.gmail.user0abc.max_one.model.buildings;

import com.gmail.user0abc.max_one.model.actions.units.AbilityType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey
 * at 11/5/14 9:36 PM
 */
public class Town extends Building {

    public Town() {
        super();
        buildingType = BuildingType.TOWN;
        defence = 10.0;
        health = 100;
    }

    @Override
    public List<AbilityType> getAvailableActions() {
        return Arrays.asList(AbilityType.MAKE_WORKER, AbilityType.MAKE_WARRIOR);
    }
}
