package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.os.Bundle;
import com.gmail.user0abc.max_one.exceptions.NotImplementedException;
import com.gmail.user0abc.max_one.handlers.TileSelectHandler;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.TurnProcessor;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.ActionStatus;
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
    private Unit selectedUnit;
    private Building selectedBuilding;
    private MapTile selectedTile;
    private TurnProcessor turnProcessor;

    public static GameController getCurrentInstance() {
        return currentInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameController.currentInstance = this;
        game = GameStorage.getStorage().getGameContainer();
        gameField = new GameField(this);
        setContentView(gameField);
        game.currentPlayer = game.players.get(0);
        turnProcessor = new TurnProcessor(game);
        onStartTurn();
    }

    public MapTile[][] getMap() {
        return game.map;
    }

    public int getApples() {
        return game.currentPlayer.getApples();
    }

    public int getGold() {
        return game.currentPlayer.getGold();
    }


    private void onStartTurn(){
        turnProcessor.onStart();
        if(game.currentPlayer.ai){
            Logger.log("Ai move processing for player "+game.players.indexOf(game.currentPlayer));
            game.currentPlayer.aiProcessor.makeTurn(game);
            turnProcessor.onFinish();
            onStartTurn();
        }
    }

    public void onTileSelect(MapTile tile) {
        if (tileSelectHandler != null) {
            tileSelectHandler.onTileSelect(tile);
            tileSelectHandler = null;
        }
        selectedTile = tile;
        if(tile.unit != null && tile.unit.getOwner().equals(game.currentPlayer)){
            selectedUnit = tile.unit;
        }else{
            selectedUnit = null;
        }
        if(selectedTile.building != null && selectedTile.building.getOwner().equals(game.currentPlayer)){
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

    public boolean isActionAvailable(AbilityType abilityType, MapTile tile) {
        if (selectedUnit != null) {
            return selectedUnit.isActionAvailable(abilityType, tile);
        }
        return false;
    }

    public void onActionButtonSelect(AbilityType abilityType) {
        if (abilityType.equals(AbilityType.END_TURN)) {
            if(turnProcessor.onFinish()){
                onStartTurn();
            }
        } else if (selectedUnit != null) {
            selectedUnit.executeAction(abilityType, game, selectedTile);
        }else if (selectedBuilding != null) {
            selectedBuilding.executeAction(abilityType, game, selectedTile);
        }
        turnProcessor.calculatePlayerBalances();
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

    public ActionStatus getActionStatus(AbilityType abilityType) {
        if(selectedUnit != null){
            return selectedUnit.getActionStatus(abilityType);
        }
        if(selectedBuilding != null){
            return selectedBuilding.getActionStatus(abilityType);
        }
        return ActionStatus.DISABLED;
    }
}
