package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.exceptions.IllegalMove;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.buildings.BuildingsFactory;
import com.gmail.user0abc.max_one.model.buildings.Town;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;
import com.gmail.user0abc.max_one.model.units.Unit;

import java.util.Arrays;

/**
 * Created by Sergey
 * at 11/4/14 11:12 PM
 */
public class BuildTownAction extends UnitAction {

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
        TerrainType[] applicableTerrainTypes = {TerrainType.GRASS};
        if(!Arrays.asList(applicableTerrainTypes).contains(selectedTile.terrainType)){
            throw new IllegalMove("Town could not be build on " + selectedTile.terrainType.name());
        }
        BuildingsFactory.createBuildingAtLocation(selectedTile, game.currentPlayer, BuildingType.TOWN);
    }

}
