package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

/**
 * Created by Sergey
 * at 11/12/14 10:14 PM
 */
public class RemoveBuilding extends Ability {

    @Override
    public void execute(GameContainer game, MapTile selectedTile) {
        if (selectedTile != null && selectedTile.unit != null && selectedTile.building != null
                && selectedTile.building.getOwner().equals(selectedTile.unit.getOwner())) {
            selectedTile.building = null;
        }
    }

}
