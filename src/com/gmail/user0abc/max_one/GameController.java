package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.gmail.user0abc.max_one.handlers.TileSelectHandler;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
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
import java.util.concurrent.ExecutionException;

import static com.gmail.user0abc.max_one.util.GameStorage.getStorage;

/**
 * Created by Sergey
 * at 10/24/14 6:43 PM
 */
public class GameController extends Activity {

    private static GameController currentInstance = null;
    TileSelectHandler tileSelectHandler;
    private GameField gameField;
    private Unit selectedUnit;
    private Building selectedBuilding;
    private MapTile selectedTile;
    private TurnProcessor turnProcessor;
    private List<ActionButton> currentActionButtons = new ArrayList<>();
    public boolean isEndTurnEnabled = false;

    public static GameController getCurrentInstance() {
        return currentInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameController.currentInstance = this;
        gameField = new GameField(this);
        setContentView(gameField);
        getStorage().getGame().currentPlayer = getStorage().getGame().players.get(0);
        onStartTurn();
    }

    public MapTile[][] getMap() {
        return getStorage().getGame().map;
    }

    public int getApples() {
        return getStorage().getGame().currentPlayer.getApples();
    }

    public int getGold() {
        return getStorage().getGame().currentPlayer.getGold();
    }

    private void onStartTurn() {
        gameField.clearCommands();
        if(getStorage().getGame().currentPlayer.isStillInGame()){
            TurnProcessor turn = new TurnProcessor();
            turn.execute(GameStorage.getStorage().getGame().currentPlayer);
            try {
                if(turn.get()){
                    onStartTurn();
                }
            } catch (InterruptedException e) {
                Logger.log("ERROR: turn interrupted. Reason: "+e.getLocalizedMessage());
            } catch (ExecutionException e) {
                Logger.log("ERROR: turn execution failed. Reason: " + e.getLocalizedMessage());
            }
        }else{
            if(GameStorage.getStorage().getGame().nonAiPlayersLeft() == 0) {
                initiateGameLost();
            } else if(GameStorage.getStorage().getGame().playersLeft() == 1){
                initiateGameWon();
            }
        }
    }

    private void initiateGameLost() {
        //TODO - process game lost
    }

    private void initiateGameWon() {
        //TODO - process game won
    }

    public void onTileSelect(MapTile tile) {
        Logger.log("Selected tile " + tile);
        if (tileSelectHandler != null) {
            tileSelectHandler.onTileSelect(tile);
            tileSelectHandler = null;
        }
        selectedTile = tile;
        if (tile.unit != null && tile.unit.getOwner().equals(getStorage().getGame().currentPlayer)) {
            selectedUnit = tile.unit;
            currentActionButtons = getButtons(selectedUnit);
            selectedBuilding = null;
        } else if (selectedTile.building != null && selectedTile.building.getOwner().equals(getStorage().getGame().currentPlayer)) {
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
        if(isEndTurnEnabled){
            if (abilityType.equals(AbilityType.END_TURN) && isEndTurnEnabled) {
                if (turnProcessor.onFinish()) {
                    isEndTurnEnabled = false;
                    refreshMap();
                    onStartTurn();
                }
            } else if (selectedUnit != null) {
                activateButton(abilityType);
                selectedUnit.executeAction(abilityType, getStorage().getGame(), selectedTile);
            } else if (selectedBuilding != null) {
                activateButton(abilityType);
                selectedBuilding.executeAction(abilityType, getStorage().getGame(), selectedTile);
            }
            refreshMap();
        }
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTitle("Max Game (Turn " + getStorage().getGame().turnsCount + ")");
                Logger.log("Max Game (Turn " + getStorage().getGame().turnsCount + ")");
                gameField.redraw();
            }
        });
    }

    public void refreshTiles(MapTile... tiles) {
        //TODO - implement refreshing of specific map tiles
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.log("onActivityResult requestCode = "+requestCode);
        Logger.log("onActivityResult intent = "+data.toString());
    }

    private void onGameEnd(){

    }
}
