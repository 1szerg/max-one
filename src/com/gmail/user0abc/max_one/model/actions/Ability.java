package com.gmail.user0abc.max_one.model.actions;

import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sergey
 * at 11/4/14 11:06 PM
 */
public abstract class Ability implements Serializable {

    public static int getAPCost(){
        return 1;
    };

    /**
     * Starts/continues action
     *
     *
     * @param selectedEntity
     * @param selectedTile
     * @return true when action is finished and false when it is not
     */
    public abstract boolean execute(Entity selectedEntity, MapTile selectedTile);

    /**
     * continues action
     *
     * @return true when action is finished and false when it is not
     */
    public boolean execute() {
        return true;
    }

    public void cancel() {
    }

    public abstract AbilityType getType();

    public static boolean isNoBuilding(Entity entity){
        return entity != null
                && entity.getCurrentTile() != null
                && entity.getCurrentTile().building == null;
    }

    public static boolean isNoUnit(Entity entity){
        return entity != null
                && entity.getCurrentTile() != null
                && entity.getCurrentTile().unit == null;
    }

    public static boolean isSameOwner(Entity entity){
        return entity != null
                && entity.getCurrentTile() != null
                && entity.getCurrentTile().building != null
                && entity.getCurrentTile().unit != null
                && entity.getCurrentTile().building.getOwner().equals(entity.getCurrentTile().unit.getOwner());
    }


    public List<TerrainType> getApplicableTerrains(){
        return null;
    };
}
