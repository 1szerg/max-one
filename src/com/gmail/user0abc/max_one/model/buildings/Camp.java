package com.gmail.user0abc.max_one.model.buildings;

import com.gmail.user0abc.max_one.model.actions.units.AbilityType;

import java.util.ArrayList;
import java.util.List;

/*Created by Sergey on 1/12/2015.*/
public class Camp extends Building {

    public Camp() {
        super();
        buildingType = BuildingType.CAMP;
        defence = 10;
        health = 10;
    }

    @Override
    public List<AbilityType> getAvailableActions() {
        return new ArrayList<>();
    }
}
