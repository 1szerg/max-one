package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey
 * at 11/12/14 10:15 PM
 */
public class CleanTerrain extends Ability {

    @Override
    public boolean execute(GameContainer game, MapTile selectedTile) {
        if (selectedTile != null && selectedTile.unit != null && selectedTile.terrainType.equals(TerrainType.TREE)) {
            selectedTile.terrainType = TerrainType.GRASS;
        }
        return true;
    }

    public static int getAPCost(){
        return 3;
    };

    @Override
    public AbilityType getType() {
        return AbilityType.CLEAN_TERRAIN;
    }

    public static boolean isAvailable(Entity entity) {
        List<TerrainType> cleanableTerrain = Arrays.asList(TerrainType.TREE);
        return isNoBuilding(entity)
                && cleanableTerrain.indexOf(entity.getCurrentTile().terrainType) > -1
                && entity.getActionPoints() >= getAPCost();
    }
}
