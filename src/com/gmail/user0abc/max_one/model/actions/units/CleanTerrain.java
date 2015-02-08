package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

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

    @Override
    public AbilityType getType() {
        return AbilityType.CLEAN_TERRAIN;
    }

}
