package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;

/**
 * Created by Sergey
 * at 11/12/14 10:18 PM
 */
public class MoveAction extends Ability implements TileSelectReceiver {

    private Unit walkingUnit;
    private MapTile start, destination;

    @Override
    public void execute(GameContainer game, MapTile selectedTile) {
        if (selectedTile != null && selectedTile.unit != null) {
            start = selectedTile;
            walkingUnit = selectedTile.unit;
            GameController.getCurrentInstance().selectAnotherTile(this);
        }
    }

    @Override
    public void onTileSelect(MapTile tile) {
        destination = tile;
        boolean canPassTerrain = destination != null && start != null
                && (walkingUnit != null && walkingUnit.getPassableTerrain().contains(destination.terrainType));
        boolean isTileEmpty = destination != null && (destination.unit == null);
        boolean canEnterBuilding = destination != null
                && (destination.building == null
                || (destination.building.getBuildingType().equals(BuildingType.TOWN) && destination.building.getOwner().equals(walkingUnit.getOwner()))
                || (!destination.building.getBuildingType().equals(BuildingType.TOWN)));
        if (canPassTerrain && canEnterBuilding && isTileEmpty) {
            moveUnit(walkingUnit, start, destination);
        }
    }

    private void moveUnit(Unit unit, MapTile startTile, MapTile finishTile) {
        startTile.unit = null;
        finishTile.unit = unit;
        GameController.getCurrentInstance().refreshMap();
    }

}
