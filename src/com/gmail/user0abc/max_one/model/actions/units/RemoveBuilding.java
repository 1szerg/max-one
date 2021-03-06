package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

/**
 * Created by Sergey
 * at 11/12/14 10:14 PM
 */
public class RemoveBuilding extends Ability {

    @Override
    public boolean execute(Entity selectedEntity, MapTile selectedTile) {
        if (selectedTile != null && selectedTile.unit != null && selectedTile.building != null
                && selectedTile.building.getOwner().equals(selectedTile.unit.getOwner())) {
            selectedTile.building = null;
        }
        return true;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.REMOVE_BUILDING;
    }

    public static int getAPCost(){
        return 3;
    };

    public static boolean isAvailable(Entity entity) {
        return isSameOwner(entity)
                && entity.getActionPoints() >= getAPCost();
    }
}
