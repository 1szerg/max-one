package com.gmail.user0abc.max_one.model.entities.buildings;

import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Protection;
import com.gmail.user0abc.max_one.model.entities.ProtectionFactory;

import java.util.ArrayList;
import java.util.List;

/*Created by Sergey on 1/12/2015.*/
public class Camp extends Building {

    public Camp() {
        super();
        buildingType = BuildingType.CAMP;
        health = 10;
    }

    @Override
    public List<AbilityType> getAvailableActions() {
        return new ArrayList<>();
    }

    public static Protection defaultProtection() {
        return ProtectionFactory.makeProtection(1.5, 1.5, 1.5);
    }
}
