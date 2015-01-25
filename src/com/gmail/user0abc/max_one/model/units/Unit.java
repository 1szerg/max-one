package com.gmail.user0abc.max_one.model.units;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.units.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.actions.units.UnitAction;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;
import com.gmail.user0abc.max_one.util.Logger;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sergii.ivanov on 10/24/2014.
 */
public abstract class Unit implements Serializable {
    protected Player owner;
    protected MapTile currentTile;
    protected UnitAction currentAction;
    protected int actionPoints;
    protected int maxActionPoints;
    protected int applesCost;
    protected int goldCost;
    protected double attackStrength, defence, health;

    public abstract List<AbilityType> allActions();

    public abstract UnitType getUnitType();

    public int getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }

    public int getMaxActionPoints() {
        return maxActionPoints;
    }

    public void setMaxActionPoints(int maxActionPoints) {
        this.maxActionPoints = maxActionPoints;
    }

    public abstract boolean isActionAvailable(AbilityType abilityType, MapTile tile);

    public UnitAction getAction(AbilityType abilityType) {
        return ActionFactory.createAction(abilityType);
    }

    public int getApplesCost() {
        return applesCost;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public MapTile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(MapTile currentTile) {
        this.currentTile = currentTile;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public abstract List<TerrainType> getPassableTerrain();

    public double getAttackStrength() {
        return attackStrength;
    }

    public boolean acceptAttack(double receivedAttackStrength){
        health -= receivedAttackStrength / defence;
        Logger.log("ATTACK: " + this + " Health left " + Double.toString(health));
        return health > 0;
    }
}
