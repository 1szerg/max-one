package com.gmail.user0abc.max_one.model.buildings;

import com.gmail.user0abc.max_one.model.actions.AbilityType;

import java.util.ArrayList;
import java.util.List;

/*Created by Sergey on 1/12/2015.*/
public class TradePost extends Building {

    public TradePost() {
        super();
        buildingType = BuildingType.TRADE_POST;
        defence = 10;
        health = 10;
    }

    @Override
    public List<AbilityType> getAvailableActions() {
        return new ArrayList<>();
    }

    @Override
    public int getGoldProduction() {
        return 1;
    }
}
