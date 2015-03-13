package com.gmail.user0abc.max_one.model.actions;/*Created by Sergey on 2/4/2015.*/

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingsFactory;
import com.gmail.user0abc.max_one.model.entities.units.Unit;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.List;

public abstract class MakeBuilding extends Ability {

    protected MakeBuilding actionInProgress;
    protected MapTile actionLocation;
    protected int turnsLeft;
    protected Unit actionAuthor;
    protected GameContainer game;

    @Override
    public boolean execute(GameContainer game, MapTile selectedTile) {
        if (actionInProgress == null) {
            if (selectedTile != null && selectedTile.unit != null && selectedTile.building == null) {
                if (getApplicableTerrains().contains(selectedTile.terrainType)) {
                    this.game = game;
                    actionLocation = selectedTile;
                    actionAuthor = selectedTile.unit;
                    actionAuthor.setCurrentAction(this);
                    return startAction();
                }
            }
        } else {
            return continueAction();
        }
        return true;
    }

    @Override
    public boolean execute() {
        if (game != null && actionLocation != null && actionAuthor != null) {
            return continueAction();
        }
        return true;
    }

    @Override
    public void cancel() {
        actionLocation.building = null;
        clear();
    }

    protected abstract List<TerrainType> getApplicableTerrains();

    private boolean startAction() {
        actionInProgress = this;
        turnsLeft = getTurnsRequired();
        Logger.log("Unit " + actionAuthor + " starts " + getBuildingType() + " at " + actionLocation.x + "," + actionLocation.y + "(turns left " + turnsLeft + ")");
        if (turnsLeft < 1) {
            return finishAction();
        }
        return false;
    }

    protected abstract int getTurnsRequired();

    private boolean continueAction() {
        if (actionAuthor.equals(actionLocation.unit)) {
            turnsLeft--;
            Logger.log("Unit " + actionAuthor + " builds " + getBuildingType() + " at " + actionLocation.x + "," + actionLocation.y + "(turns left " + turnsLeft + ")");
            if (turnsLeft == 0) {
                return finishAction();
            }
        }
        return false;
    }

    private boolean finishAction() {
        if (actionLocation.unit != null) {
            BuildingsFactory.createBuildingAtLocation(actionLocation, actionLocation.unit.getOwner(), getBuildingType());
            clear();
        }
        return true;
    }

    private void clear() {
        actionInProgress = null;
        actionLocation = null;
        actionAuthor.setCurrentAction(null);
        actionAuthor = null;
    }

    public static boolean isAvailable(Entity entity) {
        return isNoBuilding(entity)
                && entity.getActionPoints() >= getAPCost();
    }


    protected abstract BuildingType getBuildingType();
}
