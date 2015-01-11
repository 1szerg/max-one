package com.gmail.user0abc.max_one.model.buildings;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergii.ivanov on 10/24/2014.
 */
public class Building implements Serializable {
    protected BuildingType buildingType;
    protected Player owner;

    public Building() {
    }

    public List<AbilityType> allActions() {
        return new ArrayList<>(0);  //TODO - implement method
    }

    public boolean isActionAvailable(AbilityType abilityType, MapTile tile) {
        return false;  //TODO - implement method
    }

    public void execute(AbilityType abilityType) {
        //TODO - implement method
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
