package com.gmail.user0abc.max_one.model.entities.buildings;

import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Protection;
import com.gmail.user0abc.max_one.model.entities.ProtectionFactory;

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
        health = 100;
        goldProduction = 1;
    }

    @Override
    public List<AbilityType> getAvailableActions() {
        return Arrays.asList(AbilityType.MAKE_WORKER, AbilityType.MAKE_WARRIOR);
    }

    public static Protection defaultProtection() {
        return ProtectionFactory.makeProtection(2.5, 2.5, 2.5);
    }
}
