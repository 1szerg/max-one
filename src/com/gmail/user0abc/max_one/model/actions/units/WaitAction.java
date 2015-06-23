package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

/**
 * Created by Sergey
 * at 11/5/14 9:29 PM
 */
public class WaitAction extends Ability {

    @Override
    public boolean execute(Entity selectedEntity, MapTile selectedTile) {
        //TODO implement method
        return true;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.WAIT_ACTION;
    }

    public static boolean isAvailable(Entity entity) {
        return true;
    }
}
