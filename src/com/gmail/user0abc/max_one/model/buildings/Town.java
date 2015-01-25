package com.gmail.user0abc.max_one.model.buildings;

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
}
