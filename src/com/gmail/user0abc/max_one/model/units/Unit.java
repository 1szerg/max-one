package com.gmail.user0abc.max_one.model.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.ActionStatus;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
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
    protected Ability currentAction;
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

    public Ability getAction(AbilityType abilityType) {
        if(currentAction != null && currentAction.getType().equals(abilityType)){
            return currentAction;
        }
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

    public boolean acceptAttack(double receivedAttackStrength) {
        health -= receivedAttackStrength / defence;
        Logger.log("ATTACK: " + this + " Health left " + Double.toString(health));
        return health > 0;
    }

    public void takeActionPoints(int workDone) {
        actionPoints = Math.max(actionPoints - workDone, 0);
    }

    public Ability getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Ability currentAction) {
        this.currentAction = currentAction;
    }

    public void onTurnStart(){
        if(currentAction != null){
            if(currentAction.execute()){
                currentAction = null;
            }
        }
    };

    public abstract void executeAction(AbilityType abilityType, GameContainer game, MapTile tile);

    public ActionStatus getActionStatus(AbilityType abilityType) {
        if(currentAction == null && ActionFactory.isActionAvailable(abilityType)){
            return ActionStatus.AVAILABLE;
        }
        if(currentAction != null && currentAction.getType().equals(abilityType)){
            return ActionStatus.ACTIVE;
        }
        return ActionStatus.DISABLED;
    }
}
