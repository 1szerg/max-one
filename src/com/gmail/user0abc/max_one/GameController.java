package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.gmail.user0abc.max_one.exceptions.IllegalMove;
import com.gmail.user0abc.max_one.exceptions.NotImplementedException;
import com.gmail.user0abc.max_one.handlers.TileSelectHandler;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.units.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.UnitAction;
import com.gmail.user0abc.max_one.model.buildings.Building;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;
import com.gmail.user0abc.max_one.util.GameMessages;
import com.gmail.user0abc.max_one.util.GameStorage;
import com.gmail.user0abc.max_one.util.Logger;
import com.gmail.user0abc.max_one.view.GameField;

import java.util.List;

/**
 * Created by Sergey
 * at 10/24/14 6:43 PM
 */
public class GameController extends Activity {

    private static GameController currentInstance = null;
    private GameContainer game;
    private GameField gameField;
    private Player currentPlayer;
    private Unit selectedUnit;
    private Building selectedBuilding;
    private MapTile selectedTile;
    TileSelectHandler tileSelectHandler;

    public static GameController getCurrentInstance() {
        return currentInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameController.currentInstance = this;
        Intent intent = getIntent();
        game = GameStorage.getStorage().getGameContainer();
        gameField = new GameField(this);
        setContentView(gameField);
        currentPlayer = game.players.get(0);
        startTurn();
    }

    public void startTurn() {
        game.currentPlayer = currentPlayer;
        calculateMap();
    }

    private void calculateMap() {
        int applesBalance = 0;
        int goldBalance = 0;
        for (int x = 0; x < getMap().length; x++) {
            for (int y = 0; y < getMap()[0].length; y++) {
                if (getMap()[x][y].unit != null && getMap()[x][y].unit.getOwner().equals(currentPlayer)) {
                    try {
                        resetUnitActionPoints(getMap()[x][y].unit, getMap()[x][y]);
                    } catch (IllegalMove illegalMove) {
                        GameMessages.add(getMap()[x][y], illegalMove.getLocalizedMessage());
                    }
                    applesBalance -= getMap()[x][y].unit.getApplesCost();
                    goldBalance -= getMap()[x][y].unit.getGoldCost();
                }
            }
        }
        Logger.log("INFO: Balance Apples " + applesBalance + " Gold " + goldBalance);
    }

    private void resetUnitActionPoints(Unit unit, MapTile mapTile) throws IllegalMove {
        unit.setActionPoints(unit.getMaxActionPoints());
    }

    public MapTile[][] getMap() {
        return game.map;
    }

    public int getApples() {
        return 5;
    }

    public int getGold() {
        return 5;
    }

    public void onTileSelect(MapTile tile) {
        if (tileSelectHandler != null) {
            tileSelectHandler.onTileSelect(tile);
            tileSelectHandler = null;
        }
        selectedTile = tile;
        if (selectedUnit == null) { // no unit selected
            if (tile.unit != null) { // tile has unit
                selectedUnit = tile.unit;
                selectedBuilding = null;
            } else {
                if (tile.building != null) {// there is a building
                    selectedBuilding = tile.building;
                }
            }
        } else { // unit selected
            if (selectedUnit.equals(tile.unit)) { // same unit second click
                if (tile.building != null) { // try building
                    selectedUnit = null;
                    selectedBuilding = tile.building;
                } else { // no building - deselect
                    selectedUnit = null;
                }
            } else { // not the same unit
                if (tile.unit == null) { // no new unit to select
                    selectedUnit = null;
                } else { // another unit
                    selectedUnit = tile.unit;
                }
            }
        }

    }

    public void endTurn() {
        int nextPlayerIndex = game.players.indexOf(currentPlayer) + 1;
        if (nextPlayerIndex < game.players.size()) {
            currentPlayer = game.players.get(nextPlayerIndex);
        } else {
            game.players.get(0);
            game.turnsCount++;
        }
    }


    public List<AbilityType> getAvailableActions() throws NotImplementedException {
        if (selectedUnit != null) {
            return selectedUnit.allActions();
        }
        if (selectedBuilding != null) {
            return selectedBuilding.allActions();
        }
        return null;
    }

    public boolean isActionAvailable(AbilityType abilityType, MapTile tile) throws NotImplementedException {
        if (selectedUnit != null) {
            return selectedUnit.isActionAvailable(abilityType, tile);
        }
        if (selectedBuilding != null) {
            return Building.isActionAvailable(abilityType, tile);
        }
        return false;
    }

    public void onActionButtonSelect(AbilityType abilityType) {
        if (selectedUnit != null) {
            UnitAction action = selectedUnit.getAction(abilityType);
            try {
                action.execute(game, selectedTile, selectedUnit);
            } catch (IllegalMove illegalMove) {
                GameMessages.add(selectedTile, illegalMove.getLocalizedMessage());
            }
        }
        if (selectedBuilding != null) {
            selectedBuilding.execute(abilityType, selectedBuilding, game);
        }
        //TODO - implement method
    }


    public void selectAnotherTile(TileSelectReceiver receiver) {
        tileSelectHandler = new TileSelectHandler(receiver);
    }

    public void refreshMap() {
        gameField.redraw();
    }
}
