package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.os.Bundle;
import com.gmail.user0abc.max_one.handlers.TileSelectHandler;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.TurnProcessor;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.ActionButton;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.Building;
import com.gmail.user0abc.max_one.model.entities.units.Unit;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.GameStorage;
import com.gmail.user0abc.max_one.util.Logger;
import com.gmail.user0abc.max_one.view.GameField;

import java.util.ArrayList;
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
    private List<ActionButton> currentActionButtons = new ArrayList<>();
    private boolean isEndTurnEnabled = false;

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

    private void onStartTurn() {
        gameField.clearCommands();
        while (game.currentPlayer.isAi) {
            turnProcessor.onStart();
            Logger.log("Ai move processing for player " + game.players.indexOf(game.currentPlayer));
            game.currentPlayer.aiProcessor.makeTurn(game);
            turnProcessor.onFinish();
        }
        turnProcessor.onStart();
        isEndTurnEnabled = true;
    }

    public void onTileSelect(MapTile tile) {
        if (tileSelectHandler != null) {
            tileSelectHandler.onTileSelect(tile);
            tileSelectHandler = null;
        }
        selectedTile = tile;
        if (tile.unit != null && tile.unit.getOwner().equals(game.currentPlayer)) {
            selectedUnit = tile.unit;
            currentActionButtons = getButtons(selectedUnit);
            selectedBuilding = null;
        } else if (selectedTile.building != null && selectedTile.building.getOwner().equals(game.currentPlayer)) {
            selectedBuilding = tile.building;
            currentActionButtons = getButtons(selectedBuilding);
            selectedUnit = null;
        } else {
            selectedUnit = null;
            selectedBuilding = null;
            currentActionButtons.clear();
        }
    }

    private List<ActionButton> getButtons(Entity entity){
        List<ActionButton> buttons = new ArrayList<>();
        for(AbilityType ability : entity.getAvailableActions()){
            buttons.add(new ActionButton(ability, entity.isAbilityAvailable(ability), entity.isActiveAction(ability)));
        }
        return buttons;
    }

    public List<ActionButton> getCurrentActionButtons() {
        return currentActionButtons;
    }

    public void onActionButtonSelect(AbilityType abilityType) {
        if (abilityType.equals(AbilityType.END_TURN) && isEndTurnEnabled) {
            if (turnProcessor.onFinish()) {
                isEndTurnEnabled = false;
                onStartTurn();
            }
        } else if (selectedUnit != null) {
            activateButton(abilityType);
            selectedUnit.executeAction(abilityType, game, selectedTile);
        } else if (selectedBuilding != null) {
            activateButton(abilityType);
            selectedBuilding.executeAction(abilityType, game, selectedTile);
        }
        turnProcessor.calculatePlayerBalances();
        refreshMap();
    }

    private void activateButton(AbilityType abilityType) {
        for(ActionButton button : currentActionButtons){
            if(button.getAbilityType().equals(abilityType)){
                button.setActiveAction(true);
            }else{
                button.setActiveAction(false);
            }
        }
    }


    public void selectAnotherTile(TileSelectReceiver receiver) {
        tileSelectHandler = new TileSelectHandler(receiver);
    }

    public void refreshMap() {
        setTitle("Max Game (Turn " + game.turnsCount + ")");
        Logger.log("Max Game (Turn " + game.turnsCount + ")");
        gameField.redraw();
    }

}
