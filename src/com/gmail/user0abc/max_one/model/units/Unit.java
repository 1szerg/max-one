package com.gmail.user0abc.max_one.model.units;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.UnitAction;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sergii.ivanov on 10/24/2014.
 */
public abstract class Unit implements Serializable {
    public Player owner;
    public UnitAction currentAction;
    protected int actionPoints;
    protected int maxActionPoints;
    protected int applesCost;
    protected int goldCost;

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

    public abstract UnitAction getAction(AbilityType abilityType);

    public int getApplesCost() {
        return applesCost;
    }

    public int getGoldCost() {
        return goldCost;
    }
}
