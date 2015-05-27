package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.buildings.MakeWarrior;
import com.gmail.user0abc.max_one.model.actions.buildings.MakeWorker;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.Arrays;

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
                return RemoveBuilding.isAvailable(entity);
            case DELETE_UNIT:
                return DeleteUnit.isAvailable(entity);
            case CLEAN_TERRAIN:
                return CleanTerrain.isAvailable(entity);
            case BUILD_TOWN:
                return BuildTown.isAvailable(entity);
            case ATTACK_TILE:
                return AttackAction.isAvailable(entity);
            case BUILD_FARM:
                return BuildFarm.isAvailable(entity);
            case BUILD_POST:
                return BuildPost.isAvailable(entity);
            case MOVE_ACTION:
                return MoveAction.isAvailable(entity);
            case WAIT_ACTION:
                return WaitAction.isAvailable(entity);
            case MAKE_WARRIOR:
                return MakeWarrior.isAvailable(entity);
            case MAKE_WORKER:
                return MakeWorker.isAvailable(entity);
        }
        return false;
    }

    public static boolean ifBuilding(AbilityType type) {
        return Arrays.asList(
                AbilityType.BUILD_POST,
                AbilityType.BUILD_TOWN,
                AbilityType.BUILD_FARM,
                AbilityType.CLEAN_TERRAIN,
                AbilityType.REMOVE_BUILDING
        ).contains(type);
    }
}
