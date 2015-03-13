package com.gmail.user0abc.max_one.model.entities.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.ActionStatus;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.io.Serializable;
import java.util.List;

/*Created by sergii.ivanov on 10/24/2014.*/
public abstract class Unit extends Entity implements Serializable {
    protected Player owner;
    protected int maxActionPoints;

    public abstract UnitType getUnitType();

    public Ability getAction(AbilityType abilityType) {
        if (super.isActiveAction(abilityType)) {
            return currentAction;
        }
        return ActionFactory.createAction(abilityType);
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

    public void setCurrentAction(Ability currentAction) {
        this.currentAction = currentAction;
    }

    public void onTurnStart() {
        if (currentAction != null) {
            if (currentAction.execute()) {
                currentAction = null;
            }
        }
    }

    ;

    public abstract void executeAction(AbilityType abilityType, GameContainer game, MapTile tile);

    public ActionStatus getActionStatus(AbilityType abilityType) {
        if (currentAction == null && isAbilityAvailable(abilityType)) {
            return ActionStatus.AVAILABLE;
        }
        if (super.isActiveAction(abilityType)) {
            return ActionStatus.ACTIVE;
        }
        return ActionStatus.DISABLED;
    }

    public void setMaxActionPoints(int maxActionPoints) {
        this.maxActionPoints = maxActionPoints;
    }

    public int getMaxActionPoints() {
        return maxActionPoints;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Unit{");
        sb.append("owner=").append(owner);
        sb.append(", currentTile=").append(getCurrentTile().x).append(":").append(getCurrentTile().y);
        if (currentAction != null) sb.append(", currentAction=").append(currentAction.getType());
        else sb.append(", currentAction=null");
        sb.append(", actionPoints=").append(getActionPoints());
        sb.append(", health=").append(health);
        sb.append('}');
        return sb.toString();
    }

}
