package com.gmail.user0abc.max_one.model.buildings;

import com.gmail.user0abc.max_one.exceptions.NotImplementedException;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.Logger;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sergii.ivanov on 10/24/2014.
 */
public abstract class Building implements Serializable {
    protected BuildingType buildingType;
    protected Player owner;
    protected MapTile tile;
    protected double defence, health;

    public Building() {
    }

    /**
     * Calculates if building could execute the action given at the moment
     *
     * @param abilityType
     * @param tile
     * @return
     * @throws NotImplementedException
     */
    public static boolean isActionAvailable(AbilityType abilityType, MapTile tile) throws NotImplementedException {
        throw new NotImplementedException("Method is not implemented");
    }

    public abstract List<AbilityType> getAvailableActions();

    /**
     * @param abilityType
     */
    public void execute(AbilityType abilityType, MapTile tile, GameContainer gameContainer) {
        ActionFactory.createAction(abilityType).execute(gameContainer, tile);
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setTile(MapTile mapTile) {
        tile = mapTile;
    }

    public boolean acceptAttack(double attackStrength) {
        health -= attackStrength / defence;
        Logger.log("ATTACK: " + this + " Health left " + Double.toString(health));
        return health > 0;
    }

    public int getApplesProduction(){
        return 0;
    };

    public int getGoldProduction() {
        return 0;
    }
}
