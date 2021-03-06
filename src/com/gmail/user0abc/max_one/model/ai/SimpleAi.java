package com.gmail.user0abc.max_one.model.ai;/*Created by Sergey on 4/4/2015.*/

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.entities.buildings.Town;
import com.gmail.user0abc.max_one.model.entities.units.UnitType;
import com.gmail.user0abc.max_one.model.entities.units.Warrior;
import com.gmail.user0abc.max_one.model.entities.units.Worker;
import com.gmail.user0abc.max_one.model.mapUtils.TileSearcher;
import com.gmail.user0abc.max_one.model.mapUtils.filters.NoBuildingsFilter;
import com.gmail.user0abc.max_one.model.mapUtils.filters.TerrainWhiteListFilter;
import com.gmail.user0abc.max_one.model.mapUtils.filters.TileFilter;
import com.gmail.user0abc.max_one.model.mapUtils.searchers.SpiralTileSearcher;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.GameStorage;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.*;

public class SimpleAi implements AiProcessor {

    Map<Player, List<Entity>> mapScan = new HashMap<>();

    private final static int FARMS_PER_TOWN = 6;
    private final static int POSTS_PER_TOWN = 6;
    private final static int WARRIORS_PER_TOWN = 2;
    private final static int WORKERS_PER_TOWN = 2;
    private List<AiTask> tasksQueue = new ArrayList<>();

    @Override
    public void manageTasks(GameContainer game) {
        mapScan = GameStorage.getStorage().getEntitiesMap();
        analyzeScan(game);
        Collections.sort(tasksQueue, new AiTaskPriorityComparator());
        Logger.log("[SimpleAi] tasks in queue " + tasksQueue.size());
        for(Entity entity : mapScan.get(GameStorage.getStorage().getGame().currentPlayer)){
            if( entity.getCurrentAction() != null ){
                entity.getCurrentAction().execute();
            }else{
                for(AiTask task : tasksQueue){
                    if(task.isAssigned()){
                        if(task.getAssigned().isAlive()){
                            continue;
                        }else{
                            task.setAssigned(null);
                        }
                    }
                    if( entity.getAvailableActions().contains(task.getType())){
                        task.setAssigned(entity);
                        executeTask(task);
                    }
                }
            }
            if(entity.getCurrentAction() == null){
                idleEntity(entity);
            }
        }
        //todo remove
//        for (AiTask t : tasksQueue) {
//            if (t.isAssigned() && !t.getAssigned().isAlive()) {
//                t.setAssigned(null);
//            } else if (!t.isAssigned()) {
//                assignTask(t);
//            } else {
//                executeTask(t);
//            }
//        }
    }

    private void idleEntity(Entity entity) {
        if(entity instanceof Worker){
            //explore
        }else if(entity instanceof Warrior){
            //search and destroy
        }else if(entity instanceof Town){
            // shoot invaders
        }
    }

    private void assignTask(final AiTask task) {
        for (Entity entity : mapScan.get(GameStorage.getStorage().getGame().currentPlayer)) {
            if (entity.getCurrentAction() != null) continue;
            List<AbilityType> canDo = entity.getAvailableActions();
            if (canDo.contains(task.getType())) {
                Logger.log("[SimpleAi] assigning task " + task + " to " + entity);
                task.setAssigned(entity);
                executeTask(task);
                return;
            }
        }
    }

    private void executeTask(AiTask task) {
        if (task == null || task.getAssigned() == null) return;
        if (task.getLocation() == null) task.setLocation(findTaskLocation(task));
        if (task.getLocation() == null) {
            Logger.log("[SimpleAi] task " + task + " removed as invalid");
            tasksQueue.remove(task);
            return;
        }
        if (task.getAssigned().getCurrentTile().equals(task.getLocation())) {
            Logger.log("[SimpleAi] executing task " + task + " feat " + task.getAssigned());
            task.getAssigned().executeAction(task.getType(), task.getLocation());
        } else {
            Logger.log("[SimpleAi] unit " + task.getAssigned() + " is heading to location " + task.getLocation());
            task.getAssigned().executeAction(AbilityType.MOVE_ACTION, task.getLocation());
        }
    }

    private MapTile findTaskLocation(AiTask task) {
        if (task.isBuilding()) return findBuildingSite(task);
        if (Arrays.asList(AbilityType.MAKE_WARRIOR, AbilityType.MAKE_WORKER).contains(task.getType()))
            return task.getAssigned().getCurrentTile();
        return null;
    }

    private MapTile findBuildingSite(AiTask task) {
        //todo Verify filtering mechanics
        List<TileFilter> filters = new ArrayList<>();
        filters.add(new NoBuildingsFilter());
        //filters.add(new VisibleTilesFilter(GameStorage.getStorage().getGame().currentPlayer));
        filters.add(new TerrainWhiteListFilter(ActionFactory.createAction(task.getType()).getApplicableTerrains()));
        TileSearcher tileSearcher = new SpiralTileSearcher(getEmpireCenter(GameStorage.getStorage().getGame().currentPlayer));
        Logger.log("[SimpleAi] looking spot for building " + task.getType());
        List<MapTile> possibleLocations = tileSearcher.searchTiles(GameStorage.getStorage().getGame().map, filters);
        Logger.log("[SimpleAi] found " + possibleLocations.size() + " spot(s) for building " + task.getType());
        if (possibleLocations.size() < 1) return null;
        return possibleLocations.get(0);
    }

    private MapTile getEmpireCenter(Player currentPlayer) {
        if(GameStorage.getStorage().getEntitiesMap().get(currentPlayer) == null
                || GameStorage.getStorage().getEntitiesMap().get(currentPlayer).isEmpty()) return null;
        int x = 0;
        int y = 0;
        for(Entity entity : GameStorage.getStorage().getEntitiesMap().get(currentPlayer)){
            x =+ entity.getCurrentTile().x;
            y =+ entity.getCurrentTile().y;
        }
        return GameStorage.getStorage().getGame().map
                [ (int)Math.round(x / GameStorage.getStorage().getEntitiesMap().get(currentPlayer).size()) ]
                [ (int)Math.round(y / GameStorage.getStorage().getEntitiesMap().get(currentPlayer).size()) ];
    }

    private void analyzeScan(GameContainer game) {
        int townsCount = AiUtils.countBuildings(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), BuildingType.TOWN);
        int farmsCount = AiUtils.countBuildings(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), BuildingType.FARM);
        int postsCount = AiUtils.countBuildings(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), BuildingType.TRADE_POST);
        int workersCount = AiUtils.countUnits(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), UnitType.WORKER);
        int warriorsCount = AiUtils.countUnits(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), UnitType.WARRIOR);
        int townsNeeded = 1 - townsCount - ordered(AbilityType.BUILD_TOWN);
        int farmsNeeded = townsCount * FARMS_PER_TOWN - farmsCount - ordered(AbilityType.BUILD_FARM);
        int postsNeeded = townsCount * POSTS_PER_TOWN - postsCount - ordered(AbilityType.BUILD_POST);
        int workersNeeded = townsCount * WORKERS_PER_TOWN - workersCount - ordered(AbilityType.MAKE_WORKER);
        int warriorsNeeded = townsCount * WARRIORS_PER_TOWN - warriorsCount - ordered(AbilityType.MAKE_WARRIOR);
        if (farmsNeeded == 0 && postsNeeded == 0) {
            townsNeeded = 1 - ordered(AbilityType.BUILD_TOWN);
        }
        addTasks(farmsNeeded, AbilityType.BUILD_FARM, 0);
        addTasks(postsNeeded, AbilityType.BUILD_POST, 0);
        addTasks(townsNeeded, AbilityType.BUILD_TOWN, 50);
        addTasks(workersNeeded, AbilityType.MAKE_WORKER, 25);
        addTasks(warriorsNeeded, AbilityType.MAKE_WARRIOR, 25);
    }

    private int ordered(AbilityType abilityType) {
        int c = 0;
        for (AiTask t : tasksQueue) {
            if (t.getType().equals(abilityType)) c++;
        }
        return c;
    }

    private void addTasks(int needed, AbilityType abilityType, int priority) {
        for (int i = 0; i < needed; i++) {
            tasksQueue.add(new SimpleAiTask(abilityType, priority));
        }
    }

}
