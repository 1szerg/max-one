package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.buildings.MakeWarrior;
import com.gmail.user0abc.max_one.model.actions.buildings.MakeWorker;
import com.gmail.user0abc.max_one.model.entities.Entity;
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
                return new AttackAction();
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

    public static boolean isActionAvailable(AbilityType abilityType, Entity entity) {
        switch (abilityType) {
            case REMOVE_BUILDING:
                return false;
            case DELETE_UNIT:
                return false;
            case CLEAN_TERRAIN:
                return false;
            case BUILD_TOWN:
                return false;
            case ATTACK_TILE:
                return false;
            case BUILD_FARM:
                return false;
            case BUILD_POST:
                return false;
            case MOVE_ACTION:
                return false;
            case WAIT_ACTION:
                return false;
            case MAKE_WARRIOR:
                return false;
            case MAKE_WORKER:
                return false;
        }
        return false;
    }

}
