package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.exceptions.NotImplementedException;
import com.gmail.user0abc.max_one.util.Logger;

/**
 * Created by Sergey
 * at 11/12/14 10:06 PM
 */
public class ActionFactory {

    public static UnitAction createAction(AbilityType abilityType){
        switch (abilityType){
            case REMOVE_BUILDING:
                return new RemoveBuildingAction();
            case DELETE_UNIT:
                return new DeleteUnitAction();
            case CLEAN_TERRAIN:
                return new CleanTerrainAction();
            case BUILD_TOWN:
                return new BuildTownAction();
            case ATTACK_TILE:
                return new AttackAction();
            case BUILD_FARM:
                return new BuildFarmAction();
            case BUILD_POST:
                return new BuildPostAction();
            case MOVE_ACTION:
                return new MoveAction();
            case WAIT_ACTION:
                return new WaitAction();
            default:
                Logger.log("TODO : action " + abilityType + " not implemented");
                return null;
        }
    }

}
