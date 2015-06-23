package com.gmail.user0abc.max_one.model.ai;/*Created by Sergey on 4/4/2015.*/

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.MoveAction;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.entities.units.UnitType;
import com.gmail.user0abc.max_one.model.mapUtils.SpiralTileSearcher;
import com.gmail.user0abc.max_one.model.mapUtils.TileSearcher;
import com.gmail.user0abc.max_one.model.mapUtils.filters.NoBuildingsFilter;
import com.gmail.user0abc.max_one.model.mapUtils.filters.TerrainWhiteListFilter;
import com.gmail.user0abc.max_one.model.mapUtils.filters.TileFilter;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;
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
        for (AiTask t : tasksQueue) {
            if (t.isAssigned() && !t.getAssigned().isAlive()) t.setAssigned(null);
            if (!t.isAssigned()) assignTask(t);
            if (t.isAssigned()) executeTask(t);
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
            Logger.log("[SimpleAi] executing task " + task + " feat "+task.getAssigned());
            task.getAssigned().executeAction(task.getType(), task.getLocation());
            return;
        }
        Logger.log("[SimpleAi] unit "+task.getAssigned()+" is heading to location "+task.getLocation());
        task.getAssigned().executeAction(AbilityType.MOVE_ACTION, task.getAssigned().getCurrentTile());
        ((MoveAction)task.getAssigned().getCurrentAction()).onTileSelect(task.getLocation());
    }

    private MapTile findTaskLocation(AiTask task) {
        if (task.isBuilding()) return findBuildingSite(task);
        if (Arrays.asList(AbilityType.MAKE_WARRIOR, AbilityType.MAKE_WORKER).contains(task.getType()))
            return task.getAssigned().getCurrentTile();
        return null;
    }

    private MapTile findBuildingSite(AiTask task) {
        List<TileFilter> filters = new ArrayList<>();
        filters.add(new NoBuildingsFilter());
        //filters.add(new VisibleTilesFilter(GameStorage.getStorage().getGame().currentPlayer));
        switch (task.getType()) {
            case BUILD_TOWN:
                break;
            case BUILD_FARM:
                filters.add(new TerrainWhiteListFilter(Arrays.asList(TerrainType.GRASS, TerrainType.HILL)));
                break;
            case BUILD_POST:
                filters.add(new TerrainWhiteListFilter(Arrays.asList(TerrainType.GRASS, TerrainType.HILL, TerrainType.SAND)));
                break;
            default:
        }
        TileSearcher tileSearcher = new SpiralTileSearcher(task.getAssigned().getCurrentTile());
        List<MapTile> possibleLocations = tileSearcher.searchTiles(GameStorage.getStorage().getGame().map, filters);
        if (possibleLocations.size() < 1) return null;
        return possibleLocations.get(0);
    }

    private void analyzeScan(GameContainer game) {
        int townsCount = AiUtils.countBuildings(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), BuildingType.TOWN);
        int farmsCount = AiUtils.countBuildings(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), BuildingType.FARM);
        int postsCount = AiUtils.countBuildings(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), BuildingType.TRADE_POST);
        int workersCount = AiUtils.countUnits(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), UnitType.WORKER);
        int warriorsCount = AiUtils.countUnits(GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer), UnitType.WARRIOR);
        int townsNeeded = 1 - townsCount;
        int farmsNeeded = townsCount * FARMS_PER_TOWN - farmsCount - ordered(AbilityType.BUILD_FARM);
        int postsNeeded = townsCount * POSTS_PER_TOWN - postsCount - ordered(AbilityType.BUILD_POST);
        int workersNeeded = townsCount * WORKERS_PER_TOWN - workersCount - ordered(AbilityType.MAKE_WORKER);
        int warriorsNeeded = townsCount * WARRIORS_PER_TOWN - warriorsCount - ordered(AbilityType.MAKE_WARRIOR);
        if (farmsNeeded == 0 && postsNeeded == 0) {
            townsNeeded = 1;
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
