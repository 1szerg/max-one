package com.gmail.user0abc.max_one.model.entities.buildings;

import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Protection;
import com.gmail.user0abc.max_one.model.entities.ProtectionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey
 * at 11/26/14 1:09 AM
 */
public class Farm extends Building {
    public Farm() {
        super();
        buildingType = BuildingType.FARM;
        health = 10;
    }

    @Override
    public List<AbilityType> getAvailableActions() {
        return new ArrayList<>();
    }

    @Override
    public int getApplesProduction() {
        return 1;
    }

    public static Protection defaultProtection() {
        return ProtectionFactory.makeProtection(1.5, 1.5, 1.5);
    }
}
