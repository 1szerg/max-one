package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.gmail.user0abc.max_one.exceptions.IllegalMove;
import com.gmail.user0abc.max_one.exceptions.NotImplementedException;
import com.gmail.user0abc.max_one.handlers.TileSelectHandler;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.Calculations;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.EndTurn;
import com.gmail.user0abc.max_one.model.ai.AiTurn;
import com.gmail.user0abc.max_one.model.buildings.Building;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;
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
    TileSelectHandler tileSelectHandler;
    private GameContainer game;
    private GameField gameField;
    private Player currentPlayer;
    private Unit selectedUnit;
    private Building selectedBuilding;
    private MapTile selectedTile;

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
        startGame();
    }

    public void startGame() {
        game.currentPlayer = currentPlayer;
        calculateMap();
    }

    private void calculateMap() {
        currentPlayer.setApples(Calculations.calculatePlayerFood(game, currentPlayer));
        currentPlayer.setGold(Calculations.calculatePlayerGold(game, currentPlayer));
        Logger.log("INFO: Balance Apples " + currentPlayer.getApples() + " Gold " + currentPlayer.getGold());
    }

    private void resetUnitActionPoints(Unit unit, MapTile mapTile) throws IllegalMove {
        unit.setActionPoints(unit.getMaxActionPoints());
    }

    public MapTile[][] getMap() {
        return game.map;
    }

    public int getApples() {
        return currentPlayer.getApples();
    }

    public int getGold() {
        return currentPlayer.getGold();
    }

    public void onTileSelect(MapTile tile) {
        if (tileSelectHandler != null) {
            tileSelectHandler.onTileSelect(tile);
            tileSelectHandler = null;
        }
        selectedTile = tile;
        if(tile.unit != null && tile.unit.getOwner().equals(currentPlayer)){
            selectedUnit = tile.unit;
        }else{
            selectedUnit = null;
        }
        if(selectedTile.building != null && selectedTile.building.getOwner().equals(currentPlayer)){
            selectedBuilding = tile.building;
        }else{
            selectedBuilding = null;
        }
    }

    public List<AbilityType> getUnitActions() {
        if (selectedUnit != null) {
            return selectedUnit.allActions();
        }
        return null;
    }

    public List<AbilityType> getBuildingActions() {
        if (selectedUnit == null && selectedBuilding != null) {
            return selectedBuilding.getAvailableActions();
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
        if (abilityType.equals(AbilityType.END_TURN)) {
            new EndTurn().execute(game, null);
            while (game.currentPlayer.ai) {
                AiTurn.executeAiTurn(game, game.currentPlayer);
                new EndTurn().execute(game, null);
            }
        } else if (selectedUnit != null) {
            Ability action = selectedUnit.getAction(abilityType);
            if (action != null) {
                action.execute(game, selectedTile);
            }
        }
        if (selectedBuilding != null) {
            selectedBuilding.execute(abilityType, selectedTile, game);
        }
        calculateMap();
        refreshMap();
    }


    public void selectAnotherTile(TileSelectReceiver receiver) {
        tileSelectHandler = new TileSelectHandler(receiver);
    }

    public void refreshMap() {
        setTitle("Max Game (Turn "+game.turnsCount+")");
        Logger.log("Max Game (Turn "+game.turnsCount+")");
        gameField.redraw();
    }
}
