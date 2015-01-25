package com.gmail.user0abc.max_one.model.buildings;

import com.gmail.user0abc.max_one.exceptions.NotImplementedException;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.units.AbilityType;
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
     * Builds a list of actions that this Building could do
     *
     * @return list of all possible actions
     * @throws NotImplementedException
     */
    public List<AbilityType> allActions() throws NotImplementedException {
        throw new NotImplementedException("Method is not implemented");
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

    /**
     * @param abilityType
     */
    public void execute(AbilityType abilityType, Building building, GameContainer gameContainer) {
        Logger.log("TODO : Action is not implemented");
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
}
