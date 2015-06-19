package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.entities.units.Unit;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.GameUtils;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey
 * at 11/12/14 10:18 PM
 */
public class MoveAction extends Ability implements TileSelectReceiver {

    private Unit walkingUnit;
    private MapTile start, destination, location;

    @Override
    public boolean execute(GameContainer game, MapTile selectedTile) {
        if (destination == null) {
            if (selectedTile != null && selectedTile.unit != null) {
                start = selectedTile;
                walkingUnit = selectedTile.unit;
                GameController.getCurrentInstance().selectAnotherTile(this);
            }
            return false;
        }
        walk();
        if (location.equals(destination)) {
            walkingUnit.setCurrentAction(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean execute() {
        walk();
        return walkingUnit.getCurrentTile().equals(destination);
    }

    @Override
    public AbilityType getType() {
        return AbilityType.MOVE_ACTION;
    }

    @Override
    public void onTileSelect(MapTile tile) {
        destination = tile;
        location = start;
        walk();
    }

    private void walk() {
        while (makeStep()) {
            Logger.log("Unit " + walkingUnit + " is walking ");
            GameUtils.sleep(50);
        }
    }

    private boolean makeStep() {
        MapTile nextTile = getNextTileInPath(destination, location);
        if (nextTile != null) {
            Double dist = dist(location, nextTile);
            if (dist.compareTo(walkingUnit.getActionPoints()) <= 0) {
                moveUnit(walkingUnit, location, nextTile);
                walkingUnit.setActionPoints(walkingUnit.getActionPoints() - dist);
                if(nextTile.equals(destination)){
                    walkingUnit.setCurrentAction(null);
                }
                return !nextTile.equals(destination);
            }
        }
        return false;
    }

    private double dist(MapTile start, MapTile nextTile) {
        return GameUtils.distance(start.x, start.y, nextTile.x, nextTile.y);
    }

    private MapTile getNextTileInPath(MapTile destination, MapTile location) {
        double dX = destination.x - location.x;
        double dY = destination.y - location.y;
        int sX = (int) Math.signum(dX);
        int sY = (int) Math.signum(dY);
        List<MapTile> stepCandidates = new ArrayList<>();
        addTileIfPassable(stepCandidates, GameController.getCurrentInstance().getMap(), location.x + sX, location.y + sY);
        if(sX == 0){
            addTileIfPassable(stepCandidates, GameController.getCurrentInstance().getMap(), location.x + 1, location.y + sY);
            addTileIfPassable(stepCandidates, GameController.getCurrentInstance().getMap(), location.x -1, location.y + sY);
        }
        if(sY == 0){
            addTileIfPassable(stepCandidates, GameController.getCurrentInstance().getMap(), location.x + sX, location.y + 1);
            addTileIfPassable(stepCandidates, GameController.getCurrentInstance().getMap(), location.x + sX, location.y - 1);
        }
        if (stepCandidates.size() > 0) return stepCandidates.get(0);
        return null;
    }

    private void addTileIfPassable(List<MapTile> stepCandidates, MapTile[][] map, int x, int y) {
        if(GameUtils.tileExists(map, x, y)){
            if (isTilePassable(map[x][y])) stepCandidates.add(map[x][y]);
        }
    }

    private boolean isTilePassable(MapTile tile) {
        boolean canPassTerrain = destination != null && start != null
                && (walkingUnit != null && walkingUnit.getPassableTerrain().contains(destination.terrainType));
        boolean isTileEmpty = destination != null && (destination.unit == null);
        boolean canEnterBuilding = destination != null
                && (destination.building == null
                || (destination.building.getBuildingType().equals(BuildingType.TOWN) && destination.building.getOwner().equals(walkingUnit.getOwner()))
                || (!destination.building.getBuildingType().equals(BuildingType.TOWN)));
        return canPassTerrain && canEnterBuilding && isTileEmpty;
    }

    private void moveUnit(Unit unit, MapTile startTile, MapTile finishTile) {
        startTile.unit = null;
        finishTile.unit = unit;
        unit.setCurrentTile(finishTile);
        location = finishTile;
        GameController.getCurrentInstance().refreshMap();
    }

    public static boolean isAvailable(Entity entity) {
        return entity != null && entity.getActionPoints() > 0;
    }
}
