package com.gmail.user0abc.max_one.model.actions;

import com.gmail.user0abc.max_one.exceptions.IllegalMove;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.buildings.Town;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;

/**
 * Created by Sergey
 * at 11/4/14 11:12 PM
 */
public class BuildTown extends UnitAction {

    @Override
    public AbilityType getActionType() {
        return AbilityType.BUILD_TOWN;
    }

    @Override
    public boolean isAvailable(GameContainer game, MapTile selectedTile, Unit selectedUnit) {
        return selectedTile.building == null;
    }

    @Override
    public void execute(GameContainer game, MapTile selectedTile, Unit selectedUnit) throws IllegalMove {
        if(game.currentPlayer == null){
            throw new IllegalMove("Player not selected");
        }
        if(selectedTile.building != null){
            throw new IllegalMove("Tile is built up");
        }
        if(selectedUnit.getActionPoints() < 1){
            throw new IllegalMove("No action points");
        }
        Town town = new Town();
        town.setOwner(game.currentPlayer);
        selectedTile.building = town;
    }

}
