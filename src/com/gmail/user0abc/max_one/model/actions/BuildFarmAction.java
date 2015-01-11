package com.gmail.user0abc.max_one.model.actions;

import com.gmail.user0abc.max_one.exceptions.IllegalMove;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.buildings.Farm;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;

/**
 * Created by Sergey
 * at 11/12/14 10:16 PM
 */
public class BuildFarmAction extends UnitAction {

    @Override
    public AbilityType getActionType() {
        return null;  //TODO implement method
    }

    @Override
    public boolean isAvailable(GameContainer game, MapTile selectedTile, Unit selectedUnit) {
        return false;  //TODO implement method
    }

    @Override
    public void execute(GameContainer game, MapTile selectedTile, Unit selectedUnit) throws IllegalMove {
        if(selectedTile.building != null){
            throw new IllegalMove("Tile is already built up");
        }
        if(game.currentPlayer == null){
            throw new IllegalMove("Player not selected");
        }
        if(game.currentPlayer.gold < 1){
            throw new IllegalMove("Not enough gold");
        }
        if(selectedUnit.getActionPoints() < 1){
            throw new IllegalMove("No actions point to build");
        }
        Farm farm = new Farm();
        farm.setOwner(game.currentPlayer);
        selectedTile.building =  farm;
    }

}
