package com.gmail.user0abc.max_one.model.entities.buildings;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.io.Serializable;

/*Created by sergii.ivanov on 10/24/2014.*/
public abstract class Building extends Entity implements Serializable {
    protected BuildingType buildingType;
    protected MapTile currentTile;

    public Building() {
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setCurrentTile(MapTile mapTile) {
        currentTile = mapTile;
    }

    public int getApplesProduction() {
        return 0;
    }

    public int getGoldProduction() {
        return 0;
    }

    @Override
    public void executeAction(AbilityType abilityType, GameContainer gameContainer, MapTile tile) {
        currentAction = ActionFactory.createAction(abilityType);
        currentAction.execute(gameContainer, tile);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Building{");
        sb.append("buildingType=").append(buildingType);
        sb.append(", owner=").append(getOwner());
        sb.append(", health=").append(health);
        sb.append(", currentAction=").append(currentAction);
        sb.append(", protection=").append(protection);
        sb.append('}');
        return sb.toString();
    }

    public MapTile getCurrentTile() {
        return currentTile;
    }
}
