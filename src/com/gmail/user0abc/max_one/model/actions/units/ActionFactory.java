package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.buildings.MakeWarrior;
import com.gmail.user0abc.max_one.model.actions.buildings.MakeWorker;
import com.gmail.user0abc.max_one.util.Logger;

/**
 * Created by Sergey
 * at 11/12/14 10:06 PM
 */
public class ActionFactory {

    public static Ability createAction(AbilityType abilityType) {
        switch (abilityType) {
            case REMOVE_BUILDING:
                return new RemoveBuilding();
            case DELETE_UNIT:
                return new DeleteUnit();
            case CLEAN_TERRAIN:
                return new CleanTerrain();
            case BUILD_TOWN:
                return new BuildTown();
            case ATTACK_TILE:
                return new Attack();
            case BUILD_FARM:
                return new BuildFarm();
            case BUILD_POST:
                return new BuildPost();
            case MOVE_ACTION:
                return new MoveAction();
            case WAIT_ACTION:
                return new WaitAction();
            case MAKE_WORKER:
                return new MakeWorker();
            case MAKE_WARRIOR:
                return new MakeWarrior();
            default:
                Logger.log("TODO : action " + abilityType + " not implemented in ActionFactory");
                return null;
        }
    }

    public static boolean isActionAvailable(AbilityType type){
        return true;
    }

}
